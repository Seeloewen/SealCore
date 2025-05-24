package de.sealcore.game.blocks;

public class BlockRegister
{
    public static Block getBlock(String id)
    {
        //Go through the register and return a new instance of the specified block
        return switch(id)
        {
            case "b:rock" -> new Rock();
            case "b:tree" -> new Tree();
            default -> null;
        };
    }
}
