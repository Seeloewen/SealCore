package de.sealcore.game.entities.general;

import de.sealcore.server.Server;

public class Player extends Entity{

    public static final int UNI_SLOTS = 0;
    public static final int WEAPON_SLOTS = 3;
    public static final int AMMO_SLOTS = 3;
    public static final int MAT_SLOTS = 5;

    private int clientID;

    public Player(int clientID) {
        super("e:player");
        this.clientID = clientID;

        inventory = Server.game.inventoryManager.createInventory(clientID, MAT_SLOTS, WEAPON_SLOTS, AMMO_SLOTS, UNI_SLOTS);
    }


    public void updateInputs(int x, int y) {
        double f = 1/Math.sqrt(x*x + y*y);
        moveInputX = x*f;
        moveInputY = y*f;
    }



}
