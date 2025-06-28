package de.sealcore.game.blocks;

import de.sealcore.game.items.tools.ToolType;
import de.sealcore.server.Server;

import java.util.Random;

public class OakTree extends Block
{
    public OakTree()
    {
        super("b:oak_tree", "Oak Tree", true, ToolType.AXE);
    }

    @Override
    public void onDestroy(int source)
    {
        Server.game.players.get(source).inventory.add("i:log", rnd.nextInt(2, 5));
    }
}
