package de.sealcore.game.blocks;

public class BlockRegister
{
    public static Block genBlock(String id)
    {
        //Go through the register and return a new instance of the specified block
        switch(id)
        {
            case "b:rock" -> new Rock();
            case "b:tree" -> new Tree();
        }

        return null;
    }
}
