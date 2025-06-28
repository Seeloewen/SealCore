package me.seeloewen;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class PathFinder
{
    public static int gridWidth;
    public static int gridHeight;

    public static int originX;
    public static int originY;

    public static ArrayList<Node> optimalPath = new ArrayList<>();

    private static Node[] nodes;
    private static PriorityQueue<Node> openQueue = new PriorityQueue<>();
    private static HashSet<Node> openSet = new HashSet<>(); //Used for running .contains on the open nodes, faster than the queue
    private static HashSet<Node> closedSet = new HashSet<>();
    private static Node target = new Node(-5, 30);
    private static Vector2i currentPos;

    public static void findPath(Vector2f currentPos, int radius, boolean cull)
    {
        PathFinder.currentPos = new Vector2i((int)Math.floor(currentPos.x), (int)Math.floor(currentPos.y));
        gridWidth = Math.abs(radius) * 2;
        gridHeight = Math.abs(radius) * 2;
        originX = PathFinder.currentPos.x - Math.abs(radius);
        originY = PathFinder.currentPos.y - Math.abs(radius);
        nodes = new Node[gridHeight * gridWidth];
        createNodes(true);

        long startTime = System.nanoTime();
        addOpenNode(new Node(PathFinder.currentPos.x, PathFinder.currentPos.y)); //Temp start node, replace with actual start

        Node currentNode;
        while (!openQueue.isEmpty())
        {
            //Get the next node
            currentNode = pollOpenNode();

            System.out.println("Current Node: " + currentNode.x + " " + currentNode.y);
            if (Main.ENABLEUI) Main.wnd.update(currentNode.x, currentNode.y, NodeState.VISITED);

            //If the target was found, reconstruct the path and save it
            if (currentNode.equals(target))
            {
                System.out.println("Found optimal path");
                long endTime = System.nanoTime();
                System.out.println("Pathfinding took " + (endTime - startTime) / 1000000 + "ms");

                if (Main.ENABLEUI) constructPath(currentNode);
                return;
            }

            //Add the current node to the closed list and expand around it
            closedSet.add(currentNode);
            expandNode(currentNode);
        }

        if (cull)
        {
            System.out.println("No path found. Retrying without culling.");
            findPath(currentPos, radius, false);
        }
        System.out.println("Absolutely no path found, giving up");
    }

    public static void constructPath(Node currentNode)
    {
        Main.wnd.update(originX, originY, NodeState.OPTIMAL);
        while (currentNode.predecessor != null)
        {
            Main.wnd.update(currentNode.x, currentNode.y, NodeState.OPTIMAL);
            currentNode = currentNode.predecessor;
        }
    }

    public static void createNodes(boolean cull)
    {
        for (int dx = 0; dx < gridWidth; dx++)
        {
            for (int dy = 0; dy < gridHeight; dy++)
            {
                //Create nodes for the pathfinder
                int x = originX + dx;
                int y = originY + dy;

                int i = (-originX + x) + (-originY + y) * gridWidth;
                Node n = new Node(x, y);

                //Replace with actual implementation for the specific cases
                if (Main.perlin.GetNoise(x, y) > 0.3) n.state = NodeState.OCCUPIED;

                if (Main.ENABLEUI) Main.wnd.update(x, y, n.state);

                nodes[i] = n;
            }
        }
    }

    public static void expandNode(Node currentNode)
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

    public static double getDistance(Node n1, Node n2)
    {
        //return Math.sqrt(Math.pow((n2.y - n1.y), 2) + Math.pow((n2.x - n1.x), 2)); //tactical pythagoras
        return Math.abs(n2.x - n1.x) + Math.abs(n2.y - n1.y); //Manhattan distance
    }

    public static void addOpenNode(Node n)
    {
        openQueue.add(n);
        openSet.add(n);
    }

    public static Node pollOpenNode()
    {
        //Get the most prioritized node
        Node n = openQueue.poll();
        openSet.remove(n);
        return n;
    }

    public static Node getNode(int x, int y)
    {
        //Create nodes for the pathfinder
        int i = (x -originX) + (y -originY) * gridWidth;

        if (i < 0 || i > nodes.length) return null;
        return nodes[i];
    }
}
