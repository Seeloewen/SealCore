package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class ChunkUpdatePacket extends Packet
{
    private int cId;
    private boolean isFloor;
    private String floorBlockId;
    private int index;

    public ChunkUpdatePacket(int cId, boolean isFloor, String floorBlockId, int index)
    {
        super(PacketType.CHUNKUPDATE);

        this.cId = cId;
        this.isFloor = isFloor;
        this.floorBlockId = floorBlockId;
        this.index = index;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int cId = args.getInt("cId");
        boolean isFloor = args.getBool("isFloor");
        String floorBlockId = args.getString("floorBlockId");
        int index = args.getInt("index");

        return new ChunkUpdatePacket(cId, isFloor, floorBlockId, index);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();

        args.addInt("cId", cId);
        args.addBool("isFloor", isFloor);
        args.addString("floorBlockId", floorBlockId);
        args.addInt("index", index);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void handle()
    {
        //
    }
}
