package de.sealcore.game.entities.general;

import de.sealcore.game.chunks.Chunk;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.ChunkUnloadPacket;
import de.sealcore.server.Server;
import de.sealcore.util.ChunkIndex;

import java.util.HashSet;

import static de.sealcore.util.MathUtil.*;
import static de.sealcore.util.ChunkIndex.*;

public class Player extends Entity{

    public static final int UNI_SLOTS = 0;
    public static final int WEAPON_SLOTS = 3;
    public static final int AMMO_SLOTS = 3;
    public static final int MAT_SLOTS = 5;

    private int clientID;
    private HashSet<Integer> loadedChunks;

    public Player(int clientID) {
        super("e:player");
        this.clientID = clientID;
        sizeX = 0.6;
        sizeY = sizeX * 16.0/10;
        sizeZ = sizeX * 32.0/10;

        loadedChunks = new HashSet<>();
        inventory = Server.game.inventoryManager.createInventory(clientID, MAT_SLOTS, WEAPON_SLOTS, AMMO_SLOTS, UNI_SLOTS);
    }


    @Override
    public void doPhysicsTick(double dt) {
        double x = posX;
        double y = posY;
        super.doPhysicsTick(dt);
        if(blockToI(toBlock(x), toBlock(y)) != blockToI(toBlock(posX), toBlock(posY))) {
            updateLoadedChunks();
        }
    }

    public void updateLoadedChunks() {
        int range = 2;
        int c = blockToI(toBlock(posX), toBlock(posY));
        int cx = toX(c), cy = toY(c);
        int uy = cy + range + 1;
        int ly = cy - range - 1;
        int rx = cx + range + 1;
        int lx = cx - range - 1;
        for(int x = cx - range; x <= cx + range; x++) {
            for(int y = cy - range; y <= cy + range; y++) {
                load(x, y);

                unload(rx, y);
                unload(lx, y);
            }
            unload(x, ly);
            unload(x, uy);
        }
        unload(rx, ly);
        unload(lx, ly);
        unload(rx, uy);
        unload(lx, uy);
    }

    private void unload(int x, int y) {
        int c = toI(x, y);
        if(loadedChunks.contains(c)) {
            NetworkHandler.sendOnly(clientID, new ChunkUnloadPacket(c));
        }
    }

    private void load(int x, int y) {
        var map = Server.game.getCurrentMap();
        int i = ChunkIndex.toI(x, y);
        Chunk c = map.getChunk(i);
        if(c == null) {
            c = map.genChunk(i);
        }
        c.sendAddPacket(clientID);
        loadedChunks.add(i);
    }

}
