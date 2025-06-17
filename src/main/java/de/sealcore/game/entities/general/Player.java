package de.sealcore.game.entities.general;

import de.sealcore.game.chunks.Chunk;
import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemRegister;
import de.sealcore.game.items.ItemType;
import de.sealcore.game.items.TagHandler;
import de.sealcore.game.items.weapons.Weapon;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.ChunkUnloadPacket;
import de.sealcore.networking.packets.SetCooldownPacket;
import de.sealcore.networking.packets.SetHPPacket;
import de.sealcore.server.Server;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

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
        super("e:player", 10, 0, 0);
        this.clientID = clientID;
        sizeX = 0.6;
        sizeY = sizeX * 16.0/10;
        sizeZ = sizeX * 32.0/10;

        loadedChunks = new HashSet<>();
        inventory = Server.game.inventoryManager.createInventory(clientID, MAT_SLOTS, WEAPON_SLOTS, AMMO_SLOTS, UNI_SLOTS);
    }


    @Override
    public void damage(int damage, int source) {
        super.damage(damage, source);
        setHP(hp);
    }

    private void setHP(int hp) {
        this.hp = hp;
        NetworkHandler.sendOnly(clientID, new SetHPPacket(hp));
    }


    @Override
    protected void onDeath(int source) {
        posX = 0;
        posY = 0;
        setHP(15);
    }


    public void interact(int slotIndex, boolean leftClick, int te, double dte,
                         int tbx, int tby, double dtb,
                         int tfx, int tfy, double dtf) {
        var slot = inventory.getSlot(slotIndex);
        Item item = ItemRegister.getItem(slot.id);

        NetworkHandler.sendOnly(clientID, new SetCooldownPacket(item.info.cooldown()));

        /*if(item instanceof Weapon w && w.info.type() == ItemType.WEAPON_RANGED) {
            int a = w.getIntTag("ammoAmount");
            if(a > 0) {
                w.writeTag("ammoAmount", a-1);
                Log.debug("ammo"+w.getIntTag("ammoAmount"));
            }
        }*/

        if(dte >= 0 && (dtb < 0 || dte < dtb) && (dtf < 0 || dte < dtf)) {
            switch (item.info.type()) {
                case WEAPON_MELEE -> {
                    Weapon weapon = (Weapon) item;
                    double range = weapon.weaponInfo.range();
                    if(range >= dte) {
                        int damage = weapon.weaponInfo.damage();
                        Server.game.getEntity(te).damage(damage, getID());
                    }
                }
                case WEAPON_RANGED -> {
                    Weapon weapon = (Weapon) item;
                    double range = weapon.weaponInfo.range();
                    int ammo = TagHandler.getIntTag(slot.tag, "ammoAmount");
                    TagHandler.writeTag(slot, "ammoAmount", ammo-1);
                    if(range >= dte && ammo > 0) {
                        int damage = weapon.weaponInfo.damage();
                        Server.game.getEntity(te).damage(damage, getID());
                        Log.debug("shot");
                    }
                    Log.debug("ammo"+ (ammo-1));
                }
            }

        } else if(dtb >= 0 && (dte < 0 || dtb < dte) && (dtf < 0 || dtb < dtf)) {
            Log.info(LogType.GAME, "interact on block " + tbx + "|" + tby + " left=" + leftClick);
        } else if(dtf >= 0 && (dte < 0 || dtf < dte) && (dtb < 0 || dtf < dtb)) {
            Log.info(LogType.GAME, "interact on floor " + tfx + "|" + tfy + " left=" + leftClick);
        }
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
        int range = 5;
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
