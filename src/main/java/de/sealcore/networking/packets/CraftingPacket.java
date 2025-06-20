package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.server.Server;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class CraftingPacket extends Packet
{
    private String recipeId;

    public CraftingPacket(String recipeId)
    {
        super(PacketType.CRAFTING);

        this.recipeId = recipeId;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);
        String recipeId = args.getString("recipeId");

        return new CraftingPacket(recipeId);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addString("recipeId", recipeId);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        Server.game.craftingHandler.craft(recipeId, 1, getSender());
    }
}
