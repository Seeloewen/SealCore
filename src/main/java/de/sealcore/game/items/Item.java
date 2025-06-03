package de.sealcore.game.items;

public abstract class Item
{
    public ItemInfo info;

    protected Item(String id, String name, ItemType type, int maxAmount)
    {
        info = new ItemInfo(id, name, type, maxAmount);
    }

    public int onLeftClick()
    {
        //Returns -1 if not implemented. Should return 0 if implemented to avoid unwanted behaviour!
        return -1;
    }

    public int onRightClick()
    {
        //Returns -1 if not implemented. Should return 0 if implemented to avoid unwanted behaviour!
        return -1;
    }
}
