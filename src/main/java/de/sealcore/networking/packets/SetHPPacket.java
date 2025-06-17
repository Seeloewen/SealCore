package de.sealcore.networking.packets;

import de.sealcore.client.Client;
import de.sealcore.util.json.JsonObject;

public class SetHPPacket extends Packet
{
    private int hp;

    public SetHPPacket(int hp)
    {
        super(PacketType.SETHP);

        this.hp = hp;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int hp = args.getInt("hp");

        return new SetHPPacket(hp);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("hp", hp);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        Client.instance.playerState.hp = hp;
    }
}
