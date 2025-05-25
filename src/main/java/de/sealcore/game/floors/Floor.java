package de.sealcore.game.floors;

public abstract class Floor
{
    public String id;
    public String name;

    protected Floor(String id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
