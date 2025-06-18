package de.sealcore.game.blocks;

public class BlockRegister
{
    public static Block getBlock(String id)
    {
        //Go through the register and return a new instance of the specified block
        return switch(id)
        {
            case "b:small_rock" -> new SmallRock();
            case "b:oak_tree" -> new OakTree();
            case "b:work_bench" -> new WorkBench();
            case "b:core_1" -> new Core_1();
            case "b:core_2" -> new Core_2();
            case "b:core_3" -> new Core_3();
            case "b:core_4" -> new Core_4();
            default -> null;
        };
    }
}
