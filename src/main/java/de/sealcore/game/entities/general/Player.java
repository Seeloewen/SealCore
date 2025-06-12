package de.sealcore.game.entities.general;

import de.sealcore.game.entities.inventory.Inventory;
import de.sealcore.game.entities.inventory.InventoryManager;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.EntityAddPacket;
import de.sealcore.server.Server;

public class Player extends Entity{

    private int clientID;

    public Player(int clientID) {
        super("e:player");
        this.clientID = clientID;

        inventory = Server.game.inventoryManager.createInventory(clientID, 5, 3, 3, 0);
    }


    public void updateInputs(int x, int y) {
        double f = 1/Math.sqrt(x*x + y*y);
        x*=f;
        y*=f;
        moveInputX = x;
        moveInputY = y;
    }



}
