package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.client.config.Blocks;
import de.sealcore.game.floors.Floor;
import de.sealcore.game.floors.FloorRegister;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class ChunkAddPacket extends Packet
{
    private int id;
    private String[] floors;
    private int[] floorHeights;
    private String[] blocks;

    public ChunkAddPacket(String[] f, int[] fh, String[] b, int id)
    {
        super(PacketType.CHUNKADD);

        floors = f;
        floorHeights = fh;
        blocks = b;
        this.id = id;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);
        JsonArray floorObjects = args.getArray("floors");
        JsonArray blockObjects = args.getArray("blocks");

        String[] floors = new String[floorObjects.getSize()];
        int[] floorHeights = new int[floorObjects.getSize()];
        for (int i = 0; i < floorObjects.getSize(); i++)
        {
            JsonObject f = (JsonObject)floorObjects.get(i);
            floors[i] = f.getString("id");
            floorHeights[i] = f.getInt("height");
        }
        String[] blocks = new String[blockObjects.getSize()];
        for (int i = 0; i < blockObjects.getSize(); i++)
        {
            blocks[i] = ((TextNode)(blockObjects.get(i))).asText();
        }

        int id = args.getInt("id");

        return new ChunkAddPacket(floors, floorHeights, blocks, id);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        JsonArray floors = JsonArray.fromScratch();
        JsonArray blocks = JsonArray.fromScratch();

        for (int i = 0; i < this.floors.length; i++)
        {
            JsonObject fo = JsonObject.fromScratch();
            fo.addString("id", this.floors[i]);
            fo.addInt("height", this.floorHeights[i]);

            floors.addObject(fo);
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
