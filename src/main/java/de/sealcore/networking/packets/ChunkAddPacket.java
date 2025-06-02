package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.util.ArrayList;

public class ChunkAddPacket extends Packet
{
    private int id;
    private String[] floors;

    public ChunkAddPacket(String[] f, int id)
    {
        super(PacketType.CHUNKADD);

        floors = f;
        this.id = id;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);
        JsonArray floorObjects = args.getArray("floors");

        ArrayList<String> floors = new ArrayList<String>();
        for (Object o : floorObjects)
        {
            //o is only string in this case
            floors.add(((TextNode) o).asText());
        }

        int id = args.getInt("id");

        return new ChunkAddPacket(floors.toArray(new String[0]), id);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        JsonArray floors = JsonArray.fromScratch();

        for (String s : this.floors)
        {
            floors.addString(s);
        }

        args.addInt("id", id);
        args.addArray("floors", floors);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void handle()
    {

        Client.instance.gameState.loadChunk(id, floors);
    }
}
