package de.sealcore.game.entities.general.pathfinding;

import de.sealcore.game.blocks.Block;
import de.sealcore.game.entities.general.Entity;
import de.sealcore.game.entities.general.Player;
import de.sealcore.game.floors.Floor;
import de.sealcore.server.Server;
import org.joml.Vector2d;

import java.util.*;

public class PathFinder
{
    private Thread updateThread;

    public static long globalWorldUpdate = 0; //Used globally by all pathfinders, should be changed when world changes are made
    private long localWorldUpdate = 0; //Pathfinder saves when the last world update was so it knows whether to update the target

    private final Entity source;
    private Node currentPos = new Node(0, 0);
    private Node target;//Actual target the entity is focused on
    public Player playerTarget;

    public boolean isEnabled = true;
    public boolean canFocusPlayer = true;
    public boolean canFocusCore = true;

    private Node waypoint;
    private ArrayDeque<Node> waypoints = new ArrayDeque<>();
    private boolean constructingWaypoints;

    private int originX;
    private int originY;
    public static final int RADIUS = 50;

    private Node[] nodes;
    private PriorityQueue<Node> openQueue = new PriorityQueue<>();
    private HashSet<Node> openSet = new HashSet<>(); //Used for running .contains on the open nodes, faster than the queue
    private HashSet<Node> closedSet = new HashSet<>();

    public PathFinder(Entity source)
    {
        this.source = source;
    }

    public void doStep()
    {
        if (!isEnabled || (updateThread != null && updateThread.isAlive())) return;

        //If the target is set or the target was reached or the world was updated
        if (target == null || targetReached(target.x, target.y) || globalWorldUpdate > localWorldUpdate)
        {
            source.hasCollision = true;
            updateThread = new Thread(() -> updateTarget());
            updateThread.start();
            return;
        }

        //If the current waypoint is not set or reached, get the next one
        if ((waypoint == null || targetReached(waypoint.x, waypoint.y)) && !constructingWaypoints)
        {
            source.hasCollision = false;
            waypoint = getNextWaypoint();
        }

        if (waypoint == null || target == null) return;

        //Calculate angle towards the next waypoint
        double newRot = calcRot(waypoint.x, waypoint.y);

        source.updateInputs(1, 0, newRot);
    }

    public Node getNextWaypoint()
    {
        if (!waypoints.isEmpty())
        {
            return waypoints.removeLast();
        }
        return null;
    }

    public boolean targetReached(double targetX, double targetY)
    {
        //Check if the difference between the source and targets coordinates are so small, that we can assume it has reached the target
        return Math.abs(source.posX - targetX) < 0.2 && Math.abs(source.posY - targetY) < 0.2;
    }

    private double calcRot(double targetX, double targetY)
    {
        return Math.atan2(targetY - source.posY, targetX - source.posX);
    }

    private boolean isOccupied(double x, double y)
    {
        //Check if either the floor or the block at the specified location doesn't allow passing through
        Block b = Server.game.getCurrentMap().getBlock((int) Math.floor(x), (int) Math.floor(y));
        Floor f = Server.game.getCurrentMap().getFloor((int) Math.floor(x), (int) Math.floor(y));

        if (b != null && b.info.isSolid() && !b.info.name().equals("Core")) return true;

        if (f != null)
        {
            return !f.info.isSolid();
        }
        else
        {
            return true;
        }
    }

    private void updateTarget()
    {
        if (globalWorldUpdate > localWorldUpdate) localWorldUpdate = globalWorldUpdate;

        Node newTarget = null;

        //Get the new target
        if (Server.game != null && !Server.game.players.isEmpty())
        {
            //Take player closest to entity if enabled
            if (canFocusPlayer)
            {
                double closestPlayerDistance = Integer.MAX_VALUE;
                Player closestPlayer = null;

                for (int i = 0; i < Server.game.players.size(); i++)
                {
                    Player p = Server.game.players.get(i);

                    double dx = p.posX - source.posX;
                    double dy = p.posY - source.posY;
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    //If this player has the closest distance, store it
                    if (distance < closestPlayerDistance)
                    {
                        closestPlayerDistance = distance;
                        closestPlayer = p;
                    }
                }

                if (closestPlayerDistance < 10)
                {
                    playerTarget = closestPlayer;
                    newTarget = new Node(closestPlayer.posX, closestPlayer.posY);
                }
            }

            //If no player in range, select core as target if enabled
            if (canFocusCore)
            {
                playerTarget = null;
                newTarget = new Node(0, 0);
            }

        }

        constructingWaypoints = true;

        playerTarget = null;
        target = newTarget;
        currentPos = new Node(source.posX, source.posY);

        if (target != null) findPath();
    }


    public void findPath()
    {
        originX = (int) Math.floor(currentPos.x) - RADIUS;
        originY = (int) Math.floor(currentPos.y) - RADIUS;
        nodes = new Node[(int) Math.pow(RADIUS, 2) * 4 + 1];
        createNodes();

        long startTime = System.nanoTime();
        addOpenNode(new Node(currentPos.x, currentPos.y)); //Temp start node, replace with actual start

        Node currentNode;
        while (!openQueue.isEmpty())
        {
            //Get the next node
            currentNode = pollOpenNode();

            //System.out.println("Current Node: " + currentNode.x + " " + currentNode.y);

            //If the target was found, reconstruct the path and save it
            if (currentNode.equals(target))
            {
                System.out.println("Found optimal path");
                long endTime = System.nanoTime();
                System.out.println("Pathfinding took " + (endTime - startTime) / 1000000 + "ms");
                constructPath(currentNode);

                constructingWaypoints = false;
                return;
            }

            //Add the current node to the closed list and expand around it
            closedSet.add(currentNode);
            expandNode(currentNode);
        }

        System.out.println("Absolutely no path found, giving up");

        constructingWaypoints = false;
    }

    public void constructPath(Node currentNode)
    {
        waypoints.clear();
        while (currentNode.predecessor != null)
        {
            waypoints.add(currentNode);
            currentNode = currentNode.predecessor;
        }
    }

    public void createNodes()
    {
        for (int dx = 0; dx < RADIUS * 2; dx++)
        {
            for (int dy = 0; dy < RADIUS * 2; dy++)
            {
                //Create nodes for the pathfinder
                int x = originX + dx;
                int y = originY + dy;

                int i = (-originX + x) + (-originY + y) * RADIUS * 2;
                Node n = new Node(x, y);

                if (isOccupied(x, y)) n.state = NodeState.OCCUPIED;

                //Replace with actual implementation for the specific cases
                nodes[i] = n;
            }

        }
    }

    public void expandNode(Node currentNode)
    {
        Node[] neighbours = new Node[4];
        neighbours[0] = getNode(currentNode.x - 1, currentNode.y); //left
        neighbours[1] = getNode(currentNode.x + 1, currentNode.y); //right
        neighbours[2] = getNode(currentNode.x, currentNode.y + 1); //above
        neighbours[3] = getNode(currentNode.x, currentNode.y - 1); //below

        //Check all neighbours and add them to the open list or update the optimal path to them
        for (Node neighbour : neighbours)
        {
            if (neighbour == null || neighbour.state == NodeState.OCCUPIED || closedSet.contains(neighbour)) continue;

            int g = currentNode.g + 1; //Calculate the length on this path to the neighbour

            //If the neighbour is already on list but this path to it is not better, continue to the next neighbour
            if (openSet.contains(neighbour) && g >= neighbour.g) continue;

            double f = g + getDistance(neighbour, target); //Get the direct distance of the neighbour to the goal

            //If this way is better or completely new, update or add it to the list
            neighbour.predecessor = currentNode;
            neighbour.g = g;
            neighbour.f = f;

            //Either update or add the neighbour
            if (openSet.contains(neighbour))
            {
                //Re-add the neighbour to update the priority index - I have no idea why there isn't a better way for this (java moment)
                openQueue.remove(neighbour);
                openQueue.add(neighbour);
            }
            else
            {
                addOpenNode(neighbour);
            }
        }
    }

    public double getDistance(Node n1, Node n2)
    {
        //return Math.sqrt(Math.pow((n2.y - n1.y), 2) + Math.pow((n2.x - n1.x), 2)); //tactical pythagoras
        return Math.abs(n2.x - n1.x) + Math.abs(n2.y - n1.y); //Manhattan distance
    }

    public void addOpenNode(Node n)
    {
        if (n == null)
        {
            System.out.println("Dingdong");
            return;
        }

        openQueue.add(n);
        openSet.add(n);
    }

    public Node pollOpenNode()
    {
        //Get the most prioritized node
        Node n = openQueue.poll();
        openSet.remove(n);
        return n;
    }

    public Node getNode(double x, double y)
    {
        //Create nodes for the pathfinder
        int i = (int) ((x - originX) + (y - originY) * RADIUS * 2);

        if (i < 0 || i > nodes.length - 1) return null;
        return nodes[i];
    }
}