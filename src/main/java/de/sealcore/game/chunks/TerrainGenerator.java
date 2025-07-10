package de.sealcore.game.chunks;

import de.sealcore.game.floors.FloorRegister;
import de.sealcore.util.FastNoiseLite;

public class TerrainGenerator
{
    private final FastNoiseLite noise;

    public TerrainGenerator(int seed)
    {
        //Setup noise generators
        noise = new FastNoiseLite(seed);
        noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noise.SetFrequency(0.1f);
    }

    private float getNoise(int x, int y)
    {
        //Noise normally generates between -1 and 1. This shifts it to 0 to 1.
        return (noise.GetNoise(x, y) + 1.0f) / 2.0f;
    }

    //--------------------------------------------------//

    public void genNormalChunk(Chunk c)
    {
        //Fill normal chunk with floors
        for (int y = 0; y < Chunk.HEIGHT; y++)
        {
            for (int x = 0; x < Chunk.WIDTH; x++)
            {
                float n = getNoise(x + Chunk.WIDTH * c.x, y + Chunk.HEIGHT * c.y);

                c.setFloor(x, y, FloorRegister.getFloor(n >= 0.7 ? "f:water" : "f:grass"));
            }
        }
    }

    public void genCaveChunk(Chunk c)
    {
        //Fill cave chunk with floors
        for (int x = 0; x < Chunk.WIDTH; x++)
        {
            for (int y = 0; y < Chunk.HEIGHT; y++)
            {
                c.setFloor(x, y, FloorRegister.getFloor("f:stone"));
            }
        }
    }

    public void genDungeonChunk(Chunk c)
    {
        //Fill dungeon chunk with floors
        for (int x = 0; x < Chunk.WIDTH; x++)
        {
            for (int y = 0; y < Chunk.HEIGHT; y++)
            {
                c.setFloor(x, y, FloorRegister.getFloor("f:stone_bricks"));
            }
        }
    }
}
