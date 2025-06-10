package de.sealcore.game.floors;

import de.sealcore.game.InteractableObject;

public abstract class Floor extends InteractableObject
{
    public FloorInfo info;

    protected Floor(String id, String name, boolean isSolid)
    {
        info = new FloorInfo(id, name, isSolid);
    }
}
