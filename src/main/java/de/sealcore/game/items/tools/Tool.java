package de.sealcore.game.items.tools;

import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemType;

public abstract class Tool extends Item
{
    public ToolType toolType;

    protected Tool(String id, String name, ItemType type, int maxAmount, double range, ToolType toolType)
    {
        super(id, name, type, maxAmount, range);
        this.toolType = toolType;
    }
}
