package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class EntityAddPacket extends Packet
{
    private int id;
    private double x;
    private double y;
    private double z;
    private double angleX;

    public EntityAddPacket(int id, double x, double y, double z, double angleX)
    {
        super(PacketType.ENTITYADD);

        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.angleX = angleX;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int id = args.getInt("id");
        double x = args.getDouble("x");
        double y = args.getDouble("x");
        double z = args.getDouble("z");
        double angleX = args.getDouble("angleX");

        return new EntityAddPacket(id, x, y, z, angleX);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("id", id);
        args.addDouble("x", x);
        args.addDouble("y", x);
        args.addDouble("z", x);
        args.addDouble("angleX", x);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void handle()
    {
        //
    }
}
