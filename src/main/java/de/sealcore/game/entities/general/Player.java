package de.sealcore.game.entities.general;

import de.sealcore.game.blocks.Block;
import de.sealcore.game.chunks.Chunk;
import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemRegister;
import de.sealcore.game.items.ItemType;
import de.sealcore.game.items.TagHandler;
import de.sealcore.game.items.tools.Tool;
import de.sealcore.game.items.weapons.Weapon;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.ChunkUnloadPacket;
import de.sealcore.networking.packets.SetCooldownPacket;
import de.sealcore.networking.packets.SetHPPacket;
import de.sealcore.server.Server;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.MathUtil;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.util.HashSet;
import java.util.Iterator;

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
        super("e:player", 15, 0, 0);
        this.clientID = clientID;
        sizeX = 0.6;
        sizeY = sizeX * 16.0/10;
        sizeZ = sizeX * 32.0/10;

        loadedChunks = new HashSet<>();
        onDeath(-1);

        inventory = Server.game.inventoryManager.createInventory(clientID, MAT_SLOTS, WEAPON_SLOTS, AMMO_SLOTS, UNI_SLOTS);
    }


    @Override
    public void damage(int damage, int source) {
        super.damage(damage, source);
        setHP(hp);
    }

    private void setHP(int hp) {
        this.hp = hp;
        NetworkHandler.sendOnly(clientID, new SetHPPacket(hp, false));
    }


    @Override
    protected void onDeath(int source) {
        posX = clientID%2==0?-7:7;
        posY = clientID%2==1?-7:7;

        setHP(15);
        for (Iterator<Integer> it = loadedChunks.iterator(); it.hasNext(); ) {
            int id = it.next();
            unload(ChunkIndex.toX(id), ChunkIndex.toY(id));
        }
        updateLoadedChunks();
    }


    public void interact(int slotIndex, boolean leftClick, int te, double dte,
                         int tbx, int tby, double dtb,
                         int tfx, int tfy, double dtf) {
        var slot = inventory.getSlot(slotIndex);
        Item item = ItemRegister.getItem(slot.id);

        switch(item) {
            case Weapon weapon -> {
                //handle ammo
                if(weapon.info.type() == ItemType.WEAPON_RANGED) {
                    int ammo = TagHandler.getIntTag(slot.tag, "ammoAmount");
                    if(ammo > 0) {
                        TagHandler.writeTag(slot, "ammoAmount", ammo-1);
                    } else {
                        return;
                    }
                }

                NetworkHandler.sendOnly(clientID, new SetCooldownPacket(item.info.cooldown()));

                //check if entity is targeted
                if(dte >= 0 && (dtb < 0 || dte < dtb) && (dtf < 0 || dte < dtf)) {
                    if (weapon.weaponInfo.range() >= dte) {
                        int damage = weapon.weaponInfo.damage();
                        Server.game.getEntity(te).damage(damage, getID());
                    }
                }
            }
            case Tool tool -> {
                //check if block is targeted
                if(dtb >= 0 && (dte < 0 || dtb < dte) && (dtf < 0 || dtb < dtf)) {
                    //get targeted block
                    var chunk = Server.game.getCurrentMap().getChunk(MathUtil.toChunk(tbx), MathUtil.toChunk(tby));
                    int x = MathUtil.safeMod(tbx, 8);
                    int y = MathUtil.safeMod(tby, 8);
                    Block block = chunk.getBlock(x, y);

                    if (dtb <= tool.range) { //range check
                        if (block != null && block.info.requiredTool() == tool.toolType) {//null check can probably be skipped; im not brave enough
                            block.hp--;
                            if(block.hp <= 0) {
                                chunk.setBlock(x, y, null, true);
                            }
                            block.onDestroy(getID());
                            NetworkHandler.sendOnly(clientID, new SetCooldownPacket(item.info.cooldown()));
                        }
                    }
                }
            }
            default -> {}
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
        int range = 10;
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
        if(!loadedChunks.contains(c)) {
            c.sendAddPacket(clientID);
        }
        loadedChunks.add(i);
    }

}
