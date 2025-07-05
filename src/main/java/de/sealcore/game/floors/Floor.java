package de.sealcore.game.floors;

import de.sealcore.game.InteractableObject;

public abstract class Floor extends InteractableObject
{
    public FloorInfo info;
    public int height;

    protected Floor(String id, String name, boolean isSolid, int height)
    {
        info = new FloorInfo(id, name, isSolid);
        this.height = height;
    }
}
