package de.sealcore.game.entities.general;

import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.EntityAddPacket;
import de.sealcore.networking.packets.EntityUpdatePosPacket;
import de.sealcore.server.Server;
import org.joml.Vector2d;

public abstract class Entity {

    static private int nextID = 0;

    private final int id;

    private final double MOVE_SPEED = 3;

    private String entityType;

    protected double sizeX;
    protected double sizeY;

    protected double posX;
    protected double posY;
    protected double velX;
    protected double velY;


    protected double moveInputX;
    protected double moveInputY;


    public Entity(String entityType) {
        this.id = nextID++;
        this.entityType = entityType;
        posX = 0;
        posY = 0;
        velX = 0;
        velY = 0;
    }



    public void doPhysicsTick(double dt) {

        velX = moveInputX * MOVE_SPEED;
        velY = moveInputY * MOVE_SPEED;

        double dx = velX * dt;
        double dy = velY * dt;
        tryMove(dx, dy);

        NetworkHandler.send(new EntityUpdatePosPacket(id, posX, posY, 0, 0));
    }


    private void tryMove(double dx, double dy) {

        /*var game = Server.game;

        if(dx < 0) {
            int x1 = toBlock(posX);
            int x2 = toBlock(posX+dx);
            if(x1 != x2) {
                int top = toBlock(posY + sizeY);
                int bot = toBlock(posY);
                posX += dx;
                for(int y = bot; y <= top; y++) {
                    if(game.isSolid(x2, y)) posX = (double)x2 + 1.0 + 0.001;
                }
            }
        } else if( dx > 0) {
            int x1 = toBlock(posX+sizeX);
            int x2 = toBlock(posX+dx+sizeX);
            if(x1 != x2) {
                int top = toBlock(posY + sizeY);
                int bot = toBlock(posY);
                posX += dx;
                for(int y = bot; y <= top; y++) {
                    if(game.isSolid(x2, y)) posX = (double)x2 - 0.001 - sizeX;
                }
            }
        }
        if(dy < 0) {
            int y1 = toBlock(posY);
            int y2 = toBlock(posY+dy);
            if(y1 != y2) {
                int left = toBlock(posX);
                int right = toBlock(posX + sizeX);
                posY += dy;
                for(int x = left; x <= right; x++) {
                    if(game.isSolid(x, y2)) posY = y2 + 1 + 0.001;
                }
            }
        } else if(dy > 0) {
            int y1 = toBlock(posY + sizeY);
            int y2 = toBlock(posY + dy + sizeY);
            if(y1 != y2) {
                int left = toBlock(posX);
                int right = toBlock(posX + sizeX);
                posY += dy;
                for(int x = left; x <= right; x++) {
                    if(game.isSolid(x, y2)) posY = y2 - sizeY - 0.001;
                }
            }
        }*/

        posX += dx;
        posY += dy;


    }


    public void sendAdd() {
        NetworkHandler.send(new EntityAddPacket(getID(), entityType, posX, posY, 0, 0));
    }

    public void sendAdd(int id) {
        NetworkHandler.sendOnly(id, new EntityAddPacket(getID(), entityType, posX, posY, 0, 0));
    }


    public int getID() {
        return id;
    }


    private int toBlock(double v) {
        return v >= 0 ? (int)v : ((int)v)-1;
    }



}
