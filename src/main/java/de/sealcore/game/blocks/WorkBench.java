package de.sealcore.game.blocks;

import de.sealcore.game.items.tools.ToolType;

public class WorkBench extends Block
{
    public WorkBench()
    {
        super("b:work_bench", "Work Bench", true, ToolType.AXE);
    }

    @Override
    public int onRightClick(int playerId)
    {
        return 0;
    }
}
