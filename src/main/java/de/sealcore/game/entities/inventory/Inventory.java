package de.sealcore.game.entities.inventory;

import de.sealcore.game.items.ItemRegister;
import de.sealcore.game.items.ItemType;

public class Inventory
{
    final int id;
    private final InventorySlot[] slots;

    Inventory(int id, int materialSlots, int weaponSlots, int ammoSlot, int universalSlots)
    {
        this.id = id;

        slots = new InventorySlot[materialSlots + ammoSlot + weaponSlots + universalSlots];

        int m = materialSlots;
        int a = ammoSlot;
        int w = weaponSlots;
        int u = universalSlots;

        //Construct the inventory slot list
        for (int i = 0; i < slots.length; i++)
        {
            //Add all different slot types
            if (u > 0)
            {
                slots[i] = new InventorySlot(this, i, InventorySlotType.UNIVERSAL);
                u--;
            }
            else if (m > 0)
            {
                slots[i] = new InventorySlot(this, i, InventorySlotType.MATERIAL);
                m--;
            }
            else if (w > 0)
            {
                slots[i] = new InventorySlot(this, i, InventorySlotType.WEAPON);
                w--;
            }
            else if (a > 0)
            {
                slots[i] = new InventorySlot(this, i, InventorySlotType.AMMO);
                a--;
            }
        }
    }

    Inventory(InventorySlot[] slots) //ONLY used for packets on client
    {
        this.id = 0; //Id is irrelevant
        this.slots = slots;
    }

    public int add(int index, String id, int amount)
    {
        return add(index, id, amount, "");
    }

    public int add(int index, String id, int amount, String tag)
    {
        if (index < slots.length) //Make sure the slot is inside the range
        {
            InventorySlot slot = slots[index];
            if (!slot.id.equals(id) || !slot.tag.equals(tag))
                slot.setItem(id, tag); //If the slot doesn't have the right id clear it and set the right id

            return slot.add(amount); //Returns the amount of remaining items
        }

        return amount;
    }

    public int add(String id, int amount)
    {
        return add(id, amount, "");
    }

    public int add(String id, int amount, String tag)
    {
        int remainingAmount = amount;

        for (InventorySlot slot : slots) //First, go through all slots and check whether they already contain the item
        {
            if (slot.id.equals(id) && slot.tag.equals(tag)) remainingAmount = slot.add(remainingAmount);
            if (remainingAmount <= 0) return 0; //Break early if all items are added
        }

        for (InventorySlot slot : slots) //If there are still items to be added, fill empty slots
        {
            if (slot.isEmpty() && slotTypesMatch(slot.type, ItemRegister.getItem(id).info.type()))
            {
                slot.setItem(id, tag);
                remainingAmount = slot.add(remainingAmount);
            }
            if (remainingAmount <= 0) return 0; //Break early if all items are added
        }

        return remainingAmount; //In the case that still not all items were added, return the remaining amount (like when the inv is full)
    }

    public InventorySlot getSlot(int i) {
        return slots[i];
    }

    public int remove(int index, int amount)
    {
        return slots[index].remove(amount); //Removes the items from a specific slot
    }

    public int remove(String id, int amount)
    {
        int remainingAmount = amount;

        for (InventorySlot slot : slots) //Go through all slots and check whether they contain the item
        {
            if (slot.id.equals(id)) remainingAmount = slot.remove(remainingAmount);
            if (remainingAmount <= 0) return 0; //Break early if all items are removed
        }

        return remainingAmount; //Return the amount of items that still couldn't be removed
    }

    public InventorySlot[] getSlots()
    {
        return slots;
    }

    public int getAmount(String id)
    {
        int amount = 0;

        //Go through all the slots and count how many of the specified item is available
        for(InventorySlot s : slots)
        {
            if(!s.isEmpty() && s.id.equals(id)) amount += s.amount;
        }

        return amount;
    }

    public boolean slotTypesMatch(InventorySlotType slotType, ItemType itemType)
    {
        //Check if the item type specified is allowed by the slot
        return switch(slotType)
        {
            case WEAPON -> itemType == ItemType.WEAPON_MELEE || itemType == ItemType.WEAPON_RANGED ||itemType == ItemType.TOOL;
            case MATERIAL -> itemType == ItemType.MATERIAL || itemType == ItemType.PLACEABLE;
            case AMMO -> itemType == ItemType.AMMO;
            case UNIVERSAL -> true;
        };
    }
}
