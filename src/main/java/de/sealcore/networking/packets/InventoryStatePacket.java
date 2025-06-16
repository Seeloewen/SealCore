package de.sealcore.networking.packets;

import de.sealcore.client.Client;
import de.sealcore.game.entities.inventory.Inventory;
import de.sealcore.game.entities.inventory.InventoryManager;
import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.game.items.ItemType;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class InventoryStatePacket extends Packet
{
    private Inventory inventory;

    public InventoryStatePacket(Inventory inv)
    {
        super(PacketType.INVENTORYSTATE);

        inventory = inv;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);
        JsonArray slotObjects = args.getArray("slots");

        //Construct list of slots from data
        InventorySlot[] slots = new InventorySlot[slotObjects.getSize()];
        for(int i = 0; i < slotObjects.getSize(); i++)
        {
            JsonObject o = (JsonObject)slotObjects.get(i);
            int index = o.getInt("index");
            ItemType type = ItemType.values()[o.getInt("type")];
            String id = o.getString("id");
            int amount = o.getInt("amount");
            String tag = o.getString("tag");

            slots[i] = new InventorySlot(index, type, id, amount, tag);
        }

        //Create an inventory of the given slots
        Inventory inv = InventoryManager.createInventory(slots);

        return new InventoryStatePacket(inv);
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
            if(s == null) continue;

            JsonObject o = JsonObject.fromScratch();
            o.addInt("index", s.index);
            o.addInt("type", s.type.ordinal());
            o.addString("id", s.id);
            o.addInt("amount", s.amount);
            o.addString("tag", s.tag);

            slots.addObject(o);
        }

        args.addArray("slots", slots);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        for(InventorySlot s : inventory.getSlots())
        {
            Client.instance.inventoryState.updateSlot(s.index, s.id, s.amount);
        }
    }
}
