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
        sizeX = 0.6;
        sizeY = sizeX * 16.0/10;
        sizeZ = sizeX * 32.0/10;


        inventory = Server.game.inventoryManager.createInventory(clientID, MAT_SLOTS, WEAPON_SLOTS, AMMO_SLOTS, UNI_SLOTS);
    }
}
