package de.sealcore.game.entities.inventory;

import de.sealcore.game.items.ItemRegister;
import de.sealcore.game.items.ItemType;

public class InventorySlot
{
    public int index;
    public String id;
    public int amount;
    public final ItemType type;
    public boolean universalSlot;

    public InventorySlot(ItemType type)
    {
        this.type = type;
    }

    public InventorySlot()
    {
        this.type = null;
        universalSlot = true;
    }

    public void setItem(String id)
    {
        //Reset the slot with the new specified id
        this.id = id;
        amount = 0;
    }

    public int add(int amount)
    {
        int possibleAmount = ItemRegister.getItem(id).info.maxAmount() - this.amount; //Check how many more items can be added to this slot
        int addAmount = Integer.min(amount, possibleAmount); //Calculate how many items will be added

        amount += addAmount;

        return amount - addAmount; //Returns the number of items that still need to be added
    }

    public int move(InventorySlot source, int amount)
    {
        return 0;
    }

    public void swap(InventorySlot counter)
    {

    }

    public int remove(int amount)
    {
        int removeAmount = Integer.min(amount, this.amount); //Calculate how many items will be removed

        amount -= removeAmount;

        return amount - removeAmount; //Returns the amount of items that still needs to be removed
    }

    public boolean isEmpty()
    {
        //Check if the amount is zero or no item is set, both indicators for an empty slot
        return id.isEmpty() || amount == 0;
    }
}
