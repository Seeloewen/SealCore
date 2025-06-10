package de.sealcore.game.entities.general;

import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.EntityAddPacket;

public class Player extends Entity{

    private int clientID;

    public Player(int clientID) {
        super("e:player");
        this.clientID = clientID;
    }


    public void updateInputs(int x, int y) {
        double f = 1/Math.sqrt(x*x + y*y);
        x*=f;
        y*=f;
        moveInputX = x;
        moveInputY = y;
    }



}
