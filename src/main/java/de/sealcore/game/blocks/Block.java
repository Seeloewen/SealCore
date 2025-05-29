package de.sealcore.game.blocks;

public abstract class Block
{
    public String id;
    public String name;

    protected Block(String id, String name)
    {
        this.id = id;
        this.name = name;
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
