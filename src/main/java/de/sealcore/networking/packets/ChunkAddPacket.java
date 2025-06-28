package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class ChunkAddPacket extends Packet
{
    private int id;
    private String[] floors;
    private String[] blocks;

    public ChunkAddPacket(String[] f, String[] b, int id)
    {
        super(PacketType.CHUNKADD);

        floors = f;
        blocks = b;
        this.id = id;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);
        JsonArray floorObjects = args.getArray("floors");
        JsonArray blockObjects = args.getArray("blocks");

        ArrayList<String> floors = new ArrayList<String>();
        for (Object o : floorObjects)
        {
            //o is only string in this case
            floors.add(((TextNode) o).asText());
        }
        ArrayList<String> blocks = new ArrayList<String>();
        for (Object o : blockObjects)
        {
            //o is only string in this case
            blocks.add(((TextNode) o).asText());
        }

        int id = args.getInt("id");

        return new ChunkAddPacket(floors.toArray(new String[0]), blocks.toArray(new String[0]), id);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        JsonArray floors = JsonArray.fromScratch();
        JsonArray blocks = JsonArray.fromScratch();

        for (String s : this.floors)
        {
            floors.addString(s);
        }
        for(String s : this.blocks) {
            blocks.addString(s);
        }

        args.addInt("id", id);
        args.addArray("floors", floors);
        args.addArray("blocks", blocks);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {

        Client.instance.gameState.loadChunk(id, floors, blocks);
    }
}
