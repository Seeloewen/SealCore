package de.sealcore.game.blocks;

public abstract class Block
{
    public BlockInfo info;

    protected Block(String id, String name, boolean isSolid)
    {
        info = new BlockInfo(id, name, isSolid);
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
