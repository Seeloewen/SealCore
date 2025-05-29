package de.sealcore.game.blocks;

public class BlockRegister
{
    public static Block getBlock(String id)
    {
        //Go through the register and return a new instance of the specified block
        return switch(id)
        {
            case "b:small_rock" -> new SmallRock();
            case "b:big_rock" -> new BigRock();
            case "b:oak_tree" -> new OakTree();
            case "b:spruce_tree" -> new SpruceTree();
            default -> null;
        };
    }
}
