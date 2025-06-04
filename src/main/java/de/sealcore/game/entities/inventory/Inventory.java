package de.sealcore.game.entities.inventory;

public abstract class Inventory
{
    public final int width;
    public final int height;

    private InventorySlot[] slots;

    public Inventory(int width, int height)
    {
        this.width = width;
        this.height = height;

        slots = new InventorySlot[width * height];
    }

    public void add(int x, int y, String id, int amount)
    {

    }

    public void add(String id, int amount)
    {
        //for(InventorySlot)
    }

    public void remove(String id, int amount)
    {

    }
}
