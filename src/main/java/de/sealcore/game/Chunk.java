package de.sealcore.game;

import de.sealcore.game.blocks.Block;
import de.sealcore.game.blocks.BlockRegister;
import de.sealcore.game.floors.Floor;
import de.sealcore.game.floors.FloorRegister;

public class Chunk
{
    private final int index;
    public final int WIDTH = 8;
    public final int LENGTH = 8;

    private Floor[] floors = new Floor[64];
    private Block[] blocks = new Block[64];

    Chunk(int index)
    {
        this.index = index;
        generate();
    }

    private void generate()
    {
        for (int i = 0; i< WIDTH; i++)
        {
            for(int j = 0; j < LENGTH; j++)
            {
                setFloor(i, j, FloorRegister.genFloor("f:ground"));
            }
        }
    }

    public void setFloor(int x, int y, Floor f)
    {
        //Set floor at specified location
        floors[Chunk.coordsToIndex(x, y)] = f;
    }

    public Floor getFloor(int x, int y)
    {
        //Get floor from specified location
        return floors[coordsToIndex(x, y)];
    }

    public void setBlock(int x, int y, Block b)
    {
        //Set blocks at specified location
        blocks[Chunk.coordsToIndex(x, y)] = b;
    }

    public Block getBlock(int x, int y)
    {
        //Get floor from specified location
        return blocks[coordsToIndex(x, y)];
    }

    public static int coordsToIndex(int x, int y)
    {
        return x + y * 8;
    }

    public static int indexToX(int i)
    {
        return i % 8;
    }

    public static int indexToY(int i)
    {
        return i / 8;
    }
}
