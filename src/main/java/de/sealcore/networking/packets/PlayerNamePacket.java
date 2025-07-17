package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.server.Server;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class PlayerNamePacket extends Packet
{
    private String displayName;

    public PlayerNamePacket(String displayName)
    {
        super(PacketType.PLAYERNAME);

        this.displayName = displayName;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        String displayName = args.getString("display_name");

        return new PlayerNamePacket(displayName);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addString("display_name", displayName);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        Server.game.players.get(getSender()).setName(displayName);
    }
}
