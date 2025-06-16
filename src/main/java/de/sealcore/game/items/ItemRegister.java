package de.sealcore.game.items;

public class ItemRegister
{
    public static Item getItem(String id)
    {
        return switch(id)
        {
            case "grass_block" -> new Grass_Block();
            default -> null;
        };
    }
}
