package de.sealcore.networking.packets;

import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class ExamplePacket extends Packet
{
    public String s;
    public int i;

    public ExamplePacket(String s, int i)
    {
        super(PacketType.EXAMPLE);

        this.s = s;
        this.i = i;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);
        String s = args.getString("s");
        int i = args.getInt("i");

        return new ExamplePacket(s, i);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addString("s", s);
        args.addInt("i", i);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void handle()
    {
        Log.info(LogType.MAIN, s + i);
    }
}
