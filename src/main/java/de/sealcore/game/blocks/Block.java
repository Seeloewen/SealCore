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
}
