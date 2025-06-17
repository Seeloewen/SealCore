package de.sealcore.game.entities.general;

import de.sealcore.game.blocks.Block;
import de.sealcore.game.floors.Floor;
import de.sealcore.server.Server;
import de.sealcore.util.Direction;
import de.sealcore.util.Point;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class PathFinder {
    private final Entity source;

    public boolean isEnabled = true;

    public final boolean canFocusPlayer = true;
    public final boolean canFocusCore = true;

    public Player playerTarget;

    private Point target = new Point(0, 0); //Actual target the entity is focused on
    private Point dodgeTarget; //Target for going around obstacles

    private Direction lastTempDir = Direction.LEFT;


    public PathFinder(Entity source) {
        this.source = source;
    }

    public void doStep() {
        if (!isEnabled) return;

        //If the no dodge target is set or no main target is set or the target was reached, reset the target
        if (dodgeTarget == null || target == null || targetReached()) target = updateTarget();

        if (target == null && dodgeTarget == null) return;

        //Calculate angle towards either dodge target or main target
        double newRot = calcRot(dodgeTarget != null ? dodgeTarget.x : target.y, dodgeTarget != null ? dodgeTarget.y : target.y);

        //If going towars main target, there might be the need for going around an obstacle
        if (dodgeTarget == null) newRot = avoidObstacle(newRot);


        source.updateInputs(1, 0, newRot);
    }

    public boolean targetReached() {
        //Check if the difference between the source and targets coordinates are so small, that we can assume it has reached the target
        return (Math.abs(source.posX - target.x) < 0.2 && Math.abs(source.posY - target.y) < 0.2)
                || (dodgeTarget != null && Math.abs(source.posX - dodgeTarget.x) < 0.2 && Math.abs(source.posY - dodgeTarget.y) < 0.2);
    }

    private double calcRot(double targetX, double targetY) {
        return Math.atan2(targetY - source.posY, targetX - source.posX);
    }

    private double avoidObstacle(double rot) {
        //Get the coords of the blocks around the current location
        Point[] coordsAround = getCoordsAround(rot);
        Point front = coordsAround[0];
        Point frontLeft = coordsAround[1];
        Point frontRight = coordsAround[2];
        Point left = coordsAround[3];
        Point right = coordsAround[4];
        Point behind = coordsAround[5];

        //Check if just walking forward is possible
        if (!isOccupied(front.x, front.y) && !isOccupied(frontLeft.x, frontLeft.y) && !isOccupied(frontRight.x, frontRight.y))
            return calcRot(target.x, target.y);

        //I don`t even know how to describe this bug, so I won`t even try to. Here`s a very weird workaround that's somewhat working
        if (sloppyWorkaround(front.x, front.y, source.posX)) left.y++;

        //Try going left, then backwards, then right
        if (tryDodgeTarget(left.x, left.y) || tryDodgeTarget(behind.x, behind.y) || tryDodgeTarget(left.x, left.y))
            return calcRot(dodgeTarget.x, dodgeTarget.y);

        //If the entity still couldn't decide on a direction, it's probably stuck. I don`t even know what to do here anymore.
        //Log.warn(LogType.GAME, "Entity " + source.getID() + " might be stuck");

        return calcRot(target.x, target.y);
    }

    private boolean sloppyWorkaround(double frontX, double frontY, double sourceX) {
        return frontX > sourceX && isOccupied(frontX, frontY - 1) && !isOccupied(frontX, frontY) && !isOccupied(frontX, frontY + 1);
    }

    private boolean tryDodgeTarget(double x, double y) {
        //Check if the specified location is available as a dodge target and set it as such if so
        if (!isOccupied(x, y)) {
            dodgeTarget = new Point(x, y);
            return true;
        }

        return false;
    }

    private Point[] getCoordsAround(double rot) {
        double sx = Math.floor(source.posX);
        double sy = Math.floor(source.posY);

        Point front;
        Point frontLeft;
        Point frontRight;
        Point left;
        Point right;
        Point behind;

        //Check which block is front block based on rotation
        if (rot >= -Math.PI / 4 && rot < Math.PI / 4) //
        {
            front = new Point(sx + 1, sy);
            frontLeft = new Point(sx + 1, sy + 1);
            frontRight = new Point(sx + 1, sy - 1);
            left = new Point(sx, sy + 1);
            right = new Point(sx, sy - 1);
            behind = new Point(sx - 1, sy);
        } else if (rot >= Math.PI / 4 && rot < 3 * Math.PI / 4) {
            front = new Point(sx, sy + 1);
            frontLeft = new Point(sx - 1, sy + 1);
            frontRight = new Point(sx + 1, sy + 1);
            left = new Point(sx - 1, sy);
            right = new Point(sx + 1, sy);
            behind = new Point(sx, sy - 1);
        } else if (rot >= 3 * Math.PI / 4 || rot < -3 * Math.PI / 4) {
            front = new Point(sx - 1, sy);
            frontLeft = new Point(sx - 1, sy - 1);
            frontRight = new Point(sx - 1, sy + 1);
            left = new Point(sx, sy - 1);
            right = new Point(sx, sy + 1);
            behind = new Point(sx + 1, sy);
        } else {
            front = new Point(sx, sy - 1);
            frontLeft = new Point(sx + 1, sy - 1);
            frontRight = new Point(sx - 1, sy - 1);
            left = new Point(sx + 1, sy);
            right = new Point(sx - 1, sy);
            behind = new Point(sx, sy + 1);
        }

        //Return the list of available points
        Point[] points = new Point[6];
        points[0] = front;
        points[1] = frontLeft;
        points[2] = frontRight;
        points[3] = left;
        points[4] = right;
        points[5] = behind;

        return points;
    }

    private boolean isOccupied(double x, double y) {
        //Check if either the floor or the block at the specified location doesn't allow passing through
        Block b = Server.game.getCurrentMap().getBlock((int) Math.floor(x), (int) Math.floor(y));
        Floor f = Server.game.getCurrentMap().getFloor((int) Math.floor(x), (int) Math.floor(y));

        if (b != null && b.info.isSolid()) return true;
        return !f.info.isSolid();
    }

    private Point updateTarget() {
        dodgeTarget = null; //Disable the dodge target

        if (Server.game != null && !Server.game.players.isEmpty()) {
            //Take player closest to entity if enabled
            if (canFocusPlayer) {
                double closestPlayerDistance = Integer.MAX_VALUE;
                Player closestPlayer = null;

                for (int i = 0; i < Server.game.players.size(); i++) {
                    Player p = Server.game.players.get(i);

                    double dx = p.posX - source.posX;
                    double dy = p.posY - source.posY;
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    //If this player has the closest distance, store it
                    if (distance < closestPlayerDistance) {
                        closestPlayerDistance = distance;
                        closestPlayer = p;
                    }
                }

                if (closestPlayerDistance < 10) {
                    playerTarget = closestPlayer;
                    return new Point(closestPlayer.posX, closestPlayer.posY);
                }
            }

            //If no player in range, select core as target if enabled
            if (canFocusCore) {
                playerTarget = null;
                return new Point(0, 0); //TODO: Actual coords of core
            }

        }

        playerTarget = null;
        return null;
    }
}