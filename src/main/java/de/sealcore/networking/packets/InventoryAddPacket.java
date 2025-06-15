package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class InventoryAddPacket extends Packet
{
    private String id;
    private int amount;
    private String tag;
    private int index;

    public InventoryAddPacket(String id, int amount, String tag, int index)
    {
        super(PacketType.INVENTORYADD);

        this.id = id;
        this.amount = amount;
        this.tag = tag;
        this.index = index;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        String id = args.getString("id");
        int amount = args.getInt("amount");
        String tag = args.getString("tag");
        int index = args.getInt("index");

        return new InventoryAddPacket(id, amount, tag, index);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addString("id", id);
        args.addInt("amount", amount);
        args.addString("tag", tag);
        args.addInt("index", index);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        //
    }
}
