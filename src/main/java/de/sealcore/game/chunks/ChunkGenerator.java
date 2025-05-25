package de.sealcore.game.chunks;

import de.sealcore.game.floors.FloorRegister;
import de.sealcore.game.maps.MapLayout;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.FastNoiseLite;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class ChunkGenerator
{
    private final long seed;
    private final MapLayout layout;
    public static FastNoiseLite terrainNoise;
    public static FastNoiseLite blockNoise;

    public ChunkGenerator(long seed, MapLayout layout)
    {
        this.seed = seed;
        this.layout = layout;

        //Setup noise generators
        terrainNoise = new FastNoiseLite((int) seed);
        terrainNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        terrainNoise.SetFrequency(0.1f);
        blockNoise = new FastNoiseLite((int) seed);
        blockNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        blockNoise.SetFrequency(0.5f);
    }

    public Chunk genChunk(int x, int y)
    {
        Chunk c = Chunk.getEmptyChunk(x, y);

        //Generate the chunk based on the layout
        switch (layout)
        {
            case NORMAL -> genNormalChunk(c);
            case CAVE -> genCaveChunk(c);
            case DUNGEON -> genDungeonChunk(c);
        }

        genBlocks(c);

        return c;
    }

    private void genNormalChunk(Chunk c)
    {
        //Fill normal chunk with floors
        for (int y = 0; y < Chunk.LENGTH; y++)
        {
            for (int x = 0; x < Chunk.WIDTH; x++)
            {
                float n = getTerrainNoise(x + Chunk.WIDTH * c.x, y + Chunk.LENGTH * c.y);

                c.setFloor(x, y, FloorRegister.getFloor(n >= 0.7 ? "f:water" : "f:grass"));
            }
        }
    }

    private void genCaveChunk(Chunk c)
    {
        //Fill cave chunk with floors
        for (int y = 0; y < Chunk.LENGTH; y++)
        {
            for (int x = 0; x < Chunk.WIDTH; x++)
            {
                c.setFloor(x, y, FloorRegister.getFloor("f:stone"));
            }
        }
    }

    private void genDungeonChunk(Chunk c)
    {
        //Fill dungeon chunk with floors
        for (int y = 0; y < Chunk.LENGTH; y++)
        {
            for (int x = 0; x < Chunk.WIDTH; x++)
            {
                c.setFloor(x, y, FloorRegister.getFloor("f:stone_bricks"));
            }
        }
    }

    private void genBlocks(Chunk c)
    {
        //TODO
    }

    private float getTerrainNoise(int x, int y)
    {
        //Noise normally generates between -1 and 1. This shifts it to 0 to 1.
        return (terrainNoise.GetNoise(x, y) + 1.0f) / 2.0f;
    }

    private float getBlockNoise(int x, int y)
    {
        //Noise normally generates between -1 and 1. This shifts it to 0 to 1.
        return (blockNoise.GetNoise(x, y) + 1.0f) / 2.0f;
    }
}
