package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class EntityAddPacket extends Packet
{
    private int id;
    private String displayName;
    String entityType;
    private double x;
    private double y;
    private double z;

    public EntityAddPacket(int id, String displayName, String entityType, double x, double y, double z)
    {
        super(PacketType.ENTITYADD);

        this.id = id;
        this.displayName = displayName;
        this.entityType = entityType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int id = args.getInt("id");
        String displayName = args.getString("display_name");
        String entityType = args.getString("entity_type");
        double x = args.getDouble("x");
        double y = args.getDouble("x");
        double z = args.getDouble("z");

        return new EntityAddPacket(id, displayName, entityType, x, y, z);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("id", id);
        args.addString("display_name", displayName);
        args.addString("entity_type", entityType);
        args.addDouble("x", x);
        args.addDouble("y", x);
        args.addDouble("z", x);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        Client.instance.gameState.addMesh(id, entityType, displayName, x, y, z);
    }
}
