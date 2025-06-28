package de.sealcore.networking.packets;

import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.server.Server;
import de.sealcore.util.json.JsonObject;

public class InventorySwapPacket extends Packet
{
    private int i1;
    private int i2;

    public InventorySwapPacket(int i1, int i2)
    {
        super(PacketType.INVENTORYSWAP);

        this.i1 = i1;
        this.i2 = i2;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int oldIndex = args.getInt("i1");
        int newIndex = args.getInt("i2");

        return new InventorySwapPacket(oldIndex, newIndex);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("i1", i1);
        args.addInt("i2", i2);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        //Swap the two inventory slots
        InventorySlot slot1 = Server.game.players.get(getSender()).inventory.getSlot(i1);
        InventorySlot slot2 = Server.game.players.get(getSender()).inventory.getSlot(i2);

        slot1.swap(slot2);
    }
}
