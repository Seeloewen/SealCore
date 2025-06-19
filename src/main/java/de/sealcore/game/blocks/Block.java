package de.sealcore.game.blocks;

import de.sealcore.game.InteractableObject;
import de.sealcore.game.items.tools.ToolType;

public abstract class Block extends InteractableObject
{
    public BlockInfo info;

    protected Block(String id, String name, boolean isSolid, ToolType requiredTool)
    {
        info = new BlockInfo(id, name, isSolid, requiredTool);
    }

    public void onDestroy(int source) {

    }
}
