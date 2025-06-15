package de.sealcore.networking.packets;

import de.sealcore.client.Client;
import de.sealcore.util.json.JsonObject;

public class SetFollowCamPacket extends Packet
{
    private int id;

    public SetFollowCamPacket(int id)
    {
        super(PacketType.SETFOLLOWCAM);

        this.id = id;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int id = args.getInt("id");

        return new SetFollowCamPacket(id);
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
        Client.instance.camera.follow(id);
    }
}
