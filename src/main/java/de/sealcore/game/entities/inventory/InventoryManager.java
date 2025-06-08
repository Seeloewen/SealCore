package de.sealcore.game.entities.inventory;

import java.util.ArrayList;

public class InventoryManager
{
    public int nextId = 0;
    public ArrayList<Inventory> inventories = new ArrayList<>();

    public Inventory getInventory(int id)
    {
        for(Inventory inv : inventories)
        {
           if(inv.id == id) return inv;
        }

        return null;
    }

    public Inventory createInventory(int mSlots, int wSlots, int aSlots, int uSlots)
    {
        return new Inventory(nextId++, mSlots, wSlots, aSlots, uSlots); //Returns a new inventory with the next id, while incrementing it
    }

    public Inventory createInventory(int id, int mSlots, int wSlots, int aSlots, int uSlots)
    {
        return new Inventory(id, mSlots, wSlots, aSlots, uSlots);
    }

    public Inventory createInventory(InventorySlot[] slots)
    {
        return new Inventory(slots);
    }
}
