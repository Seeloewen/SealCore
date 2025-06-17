package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.server.Server;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class InventoryMovePacket extends Packet
{
    private int oldIndex;
    private int newIndex;

    public InventoryMovePacket(int oldIndex, int newIndex)
    {
        super(PacketType.INVENTORYMOVE);

        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int oldIndex = args.getInt("oldIndex");
        int newIndex = args.getInt("newIndex");

        return new InventoryMovePacket(oldIndex, newIndex);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("oldIndex", oldIndex);
        args.addInt("newIndex", newIndex);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        InventorySlot oldSlot = Server.game.players.get(getSender()).inventory.getSlot(oldIndex);
        InventorySlot newSlot = Server.game.players.get(getSender()).inventory.getSlot(newIndex);

        newSlot.move(oldSlot, oldSlot.amount);
    }
}
