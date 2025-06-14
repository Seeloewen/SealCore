package de.sealcore.game.entities.general;

import de.sealcore.game.entities.inventory.Inventory;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.EntityAddPacket;
import de.sealcore.networking.packets.EntityUpdatePosPacket;
import de.sealcore.server.Server;

public abstract class Entity {

    protected PathFinder pathFinder;

    static private int nextID = 0;

    private final int id;

    public double moveSpeed = 3;

    private String entityType;

    protected double sizeX = 0.8;
    protected double sizeY = 0.8;
    protected double sizeZ = 0.8;
    //sizeZ/sizeX of grass enemy : 1.5

    protected double posX;
    protected double posY;
    protected double velX;
    protected double velY;

    protected double rotZ;
    protected double moveInputX;
    protected double moveInputY;

    public Inventory inventory;

    public Entity(String entityType) {
        this.id = nextID++;
        this.entityType = entityType;
        posX = 0;
        posY = 0;
        velX = 0;
        velY = 0;
    }

    public Entity(String entityType, double x, double y) {
        this.id = nextID++;
        this.entityType = entityType;
        posX = x;
        posY = y;
        velX = 0;
        velY = 0;
    }

    public void doPhysicsTick(double dt) {
        double f = 1/Math.sqrt(moveInputX*moveInputX + moveInputY*moveInputY);
        moveInputX *= f;
        moveInputY *= f;

        velX = moveSpeed * (moveInputX * Math.cos(rotZ) - moveInputY * Math.sin(rotZ));
        velY = moveSpeed * (moveInputX * Math.sin(rotZ) + moveInputY * Math.cos(rotZ));

        double dx = velX * dt;
        double dy = velY * dt;
        tryMove(dx, dy);

        NetworkHandler.send(new EntityUpdatePosPacket(id, posX, posY, 0, rotZ));
    }


    private void tryMove(double dx, double dy) {
        //Skull
        var game = Server.game;

        if(dx < 0) {
            int x1 = toBlock(posX);
            int x2 = toBlock(posX+dx);
            posX += dx;
            if(x1 != x2) {
                int top = toBlock(posY + sizeY);
                int bot = toBlock(posY);
                for(int y = bot; y <= top; y++) {
                    if(game.isSolid(x2, y)) {
                        posX = (double)x2 + 0.001 + 1.0;
                    }
                }
            }
        } else if( dx > 0) {
            int x1 = toBlock(posX+sizeX);
            int x2 = toBlock(posX+dx+sizeX);
            posX += dx;
            if(x1 != x2) {
                int top = toBlock(posY + sizeY);
                int bot = toBlock(posY);
                for(int y = bot; y <= top; y++) {
                    if(game.isSolid(x2, y)) {
                        posX = (double)x2 - 0.001 - sizeX;
                    }
                }
            }
        }
        if(dy < 0) {
            int y1 = toBlock(posY);
            int y2 = toBlock(posY+dy);
            posY += dy;
            if(y1 != y2) {
                int left = toBlock(posX);
                int right = toBlock(posX + sizeX);
                for(int x = left; x <= right; x++) {
                    if(game.isSolid(x, y2)) {
                        posY = y2 + 1 + 0.001;
                    }
                }
            }
        } else if(dy > 0) {
            int y1 = toBlock(posY + sizeY);
            int y2 = toBlock(posY + dy + sizeY);
            posY += dy;
            if(y1 != y2) {
                int left = toBlock(posX);
                int right = toBlock(posX + sizeX);
                for(int x = left; x <= right; x++) {
                    if(game.isSolid(x, y2)) {
                        posY = y2 - sizeY - 0.001;
                    }
                }
            }
        }

        //posX += dx;
        //posY += dy;


    }


    public void updateInputs(int x, int y, double angleHor) {
        double f = 1/Math.sqrt(x*x + y*y);
        moveInputX = x*f; //forward
        moveInputY = y*f; //side
        this.rotZ = angleHor; //horizontal dir (0= +x)
    }

    public void sendAdd() {
        NetworkHandler.send(new EntityAddPacket(getID(), entityType, posX, posY, 0, sizeX, sizeY, 1.8, 0));
    }

    public void sendAdd(int id) {
        NetworkHandler.sendOnly(id, new EntityAddPacket(getID(), entityType, posX, posY, 0, sizeX, sizeY, 1.8, 0));
    }


    public int getID() {
        return id;
    }


    private int toBlock(double v) {
        return v >= 0 ? (int)v : ((int)v)-1;
    }



}
