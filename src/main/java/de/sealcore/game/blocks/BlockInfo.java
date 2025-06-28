package de.sealcore.game.blocks;

import de.sealcore.game.items.tools.ToolType;

public record BlockInfo(String id, String name, boolean isSolid, ToolType requiredTool)
{
}
