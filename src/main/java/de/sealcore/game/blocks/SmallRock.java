package de.sealcore.game.blocks;

import de.sealcore.game.items.tools.ToolType;
import de.sealcore.server.Server;

public class SmallRock extends Block
{
    public SmallRock()
    {
        super("b:small_rock", "Small Rock", true, ToolType.PICKAXE);
    }

    @Override
    public void onDestroy(int source)
    {
        Server.game.players.get(source).inventory.add("i:rock", rnd.nextInt(3, 8));
    }
}
