package de.sealcore.game.blocks;

import de.sealcore.game.InteractableObject;

public abstract class Block extends InteractableObject
{
    public BlockInfo info;

    protected Block(String id, String name, boolean isSolid)
    {
        info = new BlockInfo(id, name, isSolid);
    }
}
