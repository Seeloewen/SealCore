package de.sealcore.game.floors;

public abstract class Floor
{
    public FloorInfo info;

    protected Floor(String id, String name, boolean isSolid)
    {
        info = new FloorInfo(id, name, isSolid);
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
