package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.client.ui.overlay.DebugOverlay;
import de.sealcore.client.ui.overlay.OverlayManager;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class EntityUpdatePosPacket extends Packet
{
    private int id;
    private double x;
    private double y;
    private double z;
    private double angleX;
    private double velX;
    private double velY;

    public EntityUpdatePosPacket(int id, double x, double y, double z, double angleX, double velX, double velY)
    {
        super(PacketType.ENTITYUPDATEPOS);

        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.angleX = angleX;
        this.velX = velX;
        this.velY = velY;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int id = args.getInt("id");
        double x = args.getDouble("x");
        double y = args.getDouble("y");
        double z = args.getDouble("z");
        double angleX = args.getDouble("angleX");
        double velX = args.getDouble("velX");
        double velY = args.getDouble("velY");

        return new EntityUpdatePosPacket(id, x, y, z, angleX, velX, velY);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();

        args.addInt("id", id);
        args.addDouble("x", x);
        args.addDouble("y", y);
        args.addDouble("z", z);
        args.addDouble("angleX", angleX);
        args.addDouble("velX", velX);
        args.addDouble("velY", velY);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        Client.instance.gameState.updateMeshPos(id, x, y, z, angleX, velX, velY);
        if(id == Client.instance.camera.following)
        {
            DebugOverlay.direction = angleX;
            DebugOverlay.posX = x;
            DebugOverlay.posY = y;
        }
    }
}
