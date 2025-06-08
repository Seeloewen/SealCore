package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.game.entities.inventory.Inventory;
import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.game.items.ItemType;
import de.sealcore.server.Server;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class InventoryGetPacket extends Packet
{
    private Inventory inventory;

    public InventoryGetPacket(Inventory inv)
    {
        super(PacketType.INVENTORYGET);

        inventory = inv;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);
        JsonArray slotObjects = args.getArray("slots");

        //Construct list of slots from data
        ArrayList<InventorySlot> slots = new ArrayList<InventorySlot>();
        for(int i = 0; i < slotObjects.getSize() - 1; i++)
        {
            JsonObject o = (JsonObject)slotObjects.get(i);
            int index = o.getInt("index");
            ItemType type = ItemType.values()[o.getInt("type")];
            String id = o.getString("id");
            int amount = o.getInt("amount");
            String tag = o.getString("tag");

            slots.add(new InventorySlot(index, type, id, amount, tag));
        }

        //Create an inventory of the given slots
        Inventory inv = Server.game.inventoryManager.createInventory(slots.toArray(new InventorySlot[0]));

        return new InventoryGetPacket(inv);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        JsonArray slots = JsonArray.fromScratch();

        for (InventorySlot s : inventory.getSlots())
        {
            JsonObject o = JsonObject.fromScratch();
            o.addInt("index", s.index);
            o.addInt("type", s.type.ordinal());
            o.addString("id", s.id);
            o.addInt("amount", s.amount);
            o.addString("tag", s.tag);
        }

        args.addArray("slots", slots);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void handle()
    {
        //
    }
}
