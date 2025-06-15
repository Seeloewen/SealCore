package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class ChunkUnloadPacket extends Packet
{
    private int id;

    public ChunkUnloadPacket(int id)
    {
        super(PacketType.CHUNKUNLOAD);

        this.id = id;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int id = args.getInt("id");

        return new ChunkUnloadPacket(id);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("id", id);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        Client.instance.gameState.unloadChunk(id);
    }
}
