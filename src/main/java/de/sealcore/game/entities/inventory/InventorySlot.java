package de.sealcore.game.entities.inventory;

import de.sealcore.game.items.ItemRegister;
import de.sealcore.game.items.ItemType;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.InventoryStatePacket;

public class InventorySlot
{
    public Inventory inv;

    public int index;
    public String id = "";
    public int amount;
    public String tag = "";

    public final InventorySlotType type;

    public InventorySlot(Inventory inv, int index, InventorySlotType type)
    {
        this.index = index;
        this.type = type;
        this.inv = inv;
    }

    public InventorySlot(int index, InventorySlotType type, String id, int amount, String tag)
    {
        this.index = index;
        this.type = type;
        this.id = id;
        this.amount = amount;
        this.tag = tag;
    }

    public void setItem(String id, String tag)
    {
        //Reset the slot with the new specified id
        this.id = id;
        this.tag = tag;
        amount = 0;
    }

    public int add(int amount)
    {
        int possibleAmount = ItemRegister.getItem(id).info.maxAmount() - this.amount; //Check how many more items can be added to this slot
        int addAmount = Integer.min(amount, possibleAmount); //Calculate how many items will be added

        this.amount += addAmount;

        NetworkHandler.sendOnly(inv.id, new InventoryStatePacket(inv));

        return amount - addAmount; //Returns the number of items that still need to be added
    }

    public int move(InventorySlot source, int amount)
    {
        //Check how many items are available for moving
        int sourceAmount = source.amount;
        int remainingAmount = source.remove(amount);
        int moveableAmount = sourceAmount - remainingAmount;
        setItem(source.id, source.tag);
        add(moveableAmount);

        return remainingAmount; //Return the amount of items are still in the first slot.
    }

    public void swap(InventorySlot counter)
    {
        //Temporarily save the values of this slots as it gets overwritten
        String id = this.id;
        int amount = this.amount;
        String tag = this.tag;

        //Move item from counter to this slot
        this.id = counter.id;
        this.amount = counter.amount;
        this.tag = counter.tag;

        //Move items from here to counter
        counter.id = id;
        counter.amount = amount;
        counter.tag = tag;

        NetworkHandler.sendOnly(inv.id, new InventoryStatePacket(inv));
    }

    public int remove(int amount)
    {
        int removeAmount = Integer.min(amount, this.amount); //Calculate how many items will be removed

        this.amount -= removeAmount;

        NetworkHandler.sendOnly(inv.id, new InventoryStatePacket(inv));

        return amount - removeAmount; //Returns the amount of items that still needs to be removed
    }

    public boolean isEmpty()
    {
        //Check if the amount is zero or no item is set, both indicators for an empty slot
        return id.isEmpty() || amount == 0;
    }
}
