package de.sealcore.game.entities.general;

import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.EntityAddPacket;
import de.sealcore.networking.packets.EntityUpdatePosPacket;
import org.joml.Vector2d;

public abstract class Entity {

    static private int nextID = 0;

    private final int id;

    private final double MOVE_SPEED = 3;

    private String entityType;

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
        posX += dx;
        posY += dy;
    }


    public void sendAdd() {
        NetworkHandler.send(new EntityAddPacket(getID(), entityType, posX, posY, 0, 0));
    }


    public int getID() {
        return id;
    }


}
