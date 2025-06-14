package de.sealcore.game.entities.general;

import de.sealcore.server.Server;

public class Player extends Entity{

    private int clientID;

    public Player(int clientID) {
        super("e:player");
        this.clientID = clientID;
        sizeX = 0.6;
        sizeY = sizeX * 16.0/10;
        sizeZ = sizeX * 32.0/10;


        inventory = Server.game.inventoryManager.createInventory(clientID, 5, 3, 3, 0);
    }


    public void updateInputs(int x, int y, double angleHor) {
        double f = 1/Math.sqrt(x*x + y*y);
        moveInputX = x*f;
        moveInputY = y*f;
        this.rotZ = angleHor;
    }



}
