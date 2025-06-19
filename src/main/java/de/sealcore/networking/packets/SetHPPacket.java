package de.sealcore.networking.packets;

import de.sealcore.client.Client;
import de.sealcore.util.json.JsonObject;

public class SetHPPacket extends Packet
{
    private int hp;
    private boolean core;

    public SetHPPacket(int hp, boolean core)
    {
        super(PacketType.SETHP);

        this.hp = hp;
        this.core = core;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int hp = args.getInt("hp");
        boolean core = args.getBool("core");

        return new SetHPPacket(hp, core);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("hp", hp);
        args.addBool("core", core);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        if(!core) {
            Client.instance.playerState.hp = hp;
        } else {
            Client.instance.playerState.coreHP = hp;
        }
    }
}
