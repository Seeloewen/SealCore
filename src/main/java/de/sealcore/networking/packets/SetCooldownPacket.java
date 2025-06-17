package de.sealcore.networking.packets;

import de.sealcore.client.Client;
import de.sealcore.util.json.JsonObject;

public class SetCooldownPacket extends Packet
{
    private double t;

    public SetCooldownPacket(double t)
    {
        super(PacketType.SETCOOLDOWN);

        this.t = t;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        double t = args.getDouble("t");

        return new SetCooldownPacket(t);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addDouble("t", t);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        Client.instance.playerState.cooldownTotal = t;
        Client.instance.playerState.cooldownProgress = 0;
    }
}
