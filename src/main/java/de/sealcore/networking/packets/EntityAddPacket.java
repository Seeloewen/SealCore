package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class EntityAddPacket extends Packet
{
    private int id;
    String entityType;
    private double x;
    private double y;
    private double z;
    private double sizeX;
    private double sizeY;
    private double sizeZ;
    private double angleX;

    public EntityAddPacket(int id, String entityType, double x, double y, double z, double sizeX, double sizeY, double sizeZ, double angleX)
    {
        super(PacketType.ENTITYADD);

        this.id = id;
        this.entityType = entityType;
        this.x = x;
        this.y = y;
        this.z = z;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.angleX = angleX;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int id = args.getInt("id");
        String entityType = args.getString("entity_type");
        double x = args.getDouble("x");
        double y = args.getDouble("x");
        double z = args.getDouble("z");
        double sizeX = args.getDouble("sizeX");
        double sizeY = args.getDouble("sizeY");
        double sizeZ = args.getDouble("sizeZ");
        double angleX = args.getDouble("angleX");

        return new EntityAddPacket(id, entityType, x, y, z, sizeX, sizeY, sizeZ, angleX);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("id", id);
        args.addString("entity_type", entityType);
        args.addDouble("x", x);
        args.addDouble("y", x);
        args.addDouble("z", x);
        args.addDouble("sizeX", sizeX);
        args.addDouble("sizeY", sizeY);
        args.addDouble("sizeZ", sizeZ);
        args.addDouble("angleX", x);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void handle()
    {
        Client.instance.gameState.addMesh(id, entityType, x, y, z, sizeX, sizeY, sizeZ);
    }
}
