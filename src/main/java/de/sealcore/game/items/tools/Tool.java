package de.sealcore.game.items.tools;

import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemType;

public abstract class Tool extends Item
{
    public ToolType toolType;
    public double range;

    protected Tool(String id, String name, ItemType type, int maxAmount, double cooldown, double range, ToolType toolType)
    {
        super(id, name, type, maxAmount, cooldown);
        this.toolType = toolType;
        this.range = range;
    }
}
