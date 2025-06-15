package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class InventoryRemovePacket extends Packet
{
    private int index;
    private int amount;

    public InventoryRemovePacket(int index, int amount)
    {
        super(PacketType.INVENTORYREMOVE);

        this.index = index;
        this.amount = amount;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int index = args.getInt("index");
        int amount = args.getInt("amount");

        return new InventoryRemovePacket(index, amount);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();

        args.addInt("index", index);
        args.addInt("amount", amount);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        //
    }
}
