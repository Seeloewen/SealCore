package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class SetTextPacket extends Packet
{
    private int index;
    private String text;

    public SetTextPacket(String text, int index)
    {
        super(PacketType.SETTEXT);

        this.text = text;
        this.index = index;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        String text = args.getString("text");
        int index = args.getInt("index");

        return new SetTextPacket(text, index);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();

        args.addString("text", text);
        args.addInt("index", index);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {

        switch (index) {
            case 1 -> Client.instance.playerState.text1 = text;
            case 2 -> Client.instance.playerState.text2 = text;
        }

    }
}
