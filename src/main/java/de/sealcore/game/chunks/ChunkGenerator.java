package de.sealcore.game.chunks;

import de.sealcore.game.floors.FloorRegister;
import de.sealcore.game.maps.MapLayout;

public class ChunkGenerator
{
    private final long seed;
    private final MapLayout layout;

    public ChunkGenerator(long seed, MapLayout layout)
    {
        this.seed = seed;
        this.layout = layout;
    }

    public Chunk genChunk(int index)
    {
        return switch(layout)
        {
            case NORMAL -> genNormalChunk(index);
            case CAVE -> genCaveChunk(index);
            case DUNGEON -> genDungeonChunk(index);
        };
    }

    public Chunk genNormalChunk(int index)
    {
        Chunk c = Chunk.getEmptyChunk(index);

        //Fill normal chunk with floors
        for(int i = 0; i < Chunk.LENGTH; i++)
        {
            for(int j = 0; j < Chunk.WIDTH; j++)
            {
                c.setFloor(i, j, FloorRegister.getFloor("f:grass"));
            }
        }

        return c;
    }

    public Chunk genCaveChunk(int index)
    {
        Chunk c = Chunk.getEmptyChunk(index);

        //Fill cave chunk with floors
        for(int i = 0; i < Chunk.LENGTH; i++)
        {
            for(int j = 0; j < Chunk.WIDTH; j++)
            {
                c.setFloor(i, j, FloorRegister.getFloor("f:stone"));
            }
        }

        return c;
    }

    public Chunk genDungeonChunk(int index)
    {
        Chunk c = Chunk.getEmptyChunk(index);

        //Fill dungeon chunk with floors
        for(int i = 0; i < Chunk.LENGTH; i++)
        {
            for(int j = 0; j < Chunk.WIDTH; j++)
            {
                c.setFloor(i, j, FloorRegister.getFloor("f:stone_bricks"));
            }
        }

        return c;
    }
}
