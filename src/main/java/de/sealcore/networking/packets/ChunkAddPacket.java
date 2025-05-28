package de.sealcore.networking.packets;

import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.util.ArrayList;

public class ChunkAddPacket extends Packet
{
    private String[] floors;

    public ChunkAddPacket(String[] f)
    {
        super(PacketType.EXAMPLE);

        floors = f;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);
        JsonArray floorObjects = args.getArray("floors");

        ArrayList<String> floors = new ArrayList<String>();
        for(JsonObject o : floorObjects)
        {
            floors.add(o.getString("id"));
        }

        return new ChunkAddPacket(floors.toArray(new String[0]));
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        JsonArray floors = JsonArray.fromScratch();

        for(String s : this.floors)
        {
            floors.addString("id", s);
        }

        args.addObject("floors", floors);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void handle()
    {

    }
}
