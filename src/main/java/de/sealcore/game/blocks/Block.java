package de.sealcore.game.blocks;

import de.sealcore.game.InteractableObject;
import de.sealcore.game.items.tools.ToolType;
import de.sealcore.server.Server;

import java.util.Random;

public abstract class Block extends InteractableObject
{
    public BlockInfo info;
    Random rnd = new Random();

    protected Block(String id, String name, boolean isSolid, ToolType requiredTool)
    {
        info = new BlockInfo(id, name, isSolid, requiredTool);
    }

    public void onDestroy(int source) { }
}
