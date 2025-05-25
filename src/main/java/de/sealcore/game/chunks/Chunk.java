package de.sealcore.game.chunks;

import de.sealcore.game.blocks.Block;
import de.sealcore.game.floors.Floor;
import de.sealcore.game.floors.FloorRegister;

public class Chunk
{
    private final int index;
    public static final int WIDTH = 8;
    public static final int LENGTH = 8;

    private Floor[] floors = new Floor[64];
    private Block[] blocks = new Block[64];

    private Chunk(int index)
    {
        this.index = index;

        //Fill all chunks with grass by default to avoid null pointers later on
        for(int i = 0; i < floors.length; i++)
        {
            floors[i] = FloorRegister.getFloor("f:grass");
        }
    }

    public static Chunk getEmptyChunk(int index)
    {
        //This method only exists so I can make the constructor private
        //The constructor does NOT do the generation, so just calling "Chunk" could be confusing
        //getEmptyChunk does imply that generation is still needed, so here it is :)
        return new Chunk(index);
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
