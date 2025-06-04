package de.sealcore.game.items;

public class ItemRegister
{
    public static Item getItem(String id)
    {
        return switch(id)
        {
            default -> null;
        };
    }
}
