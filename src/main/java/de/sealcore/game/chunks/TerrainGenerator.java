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
        return (noise.GetNoise(x, y) * 10);
    }

    //--------------------------------------------------//

    public void genNormalChunk(Chunk c)
    {
        //Fill normal chunk with floors
        for (int y = 0; y < Chunk.HEIGHT; y++)
        {
            for (int x = 0; x < Chunk.WIDTH; x++)
            {
                int n = Math.round(getNoise(x + Chunk.WIDTH * c.x, y + Chunk.HEIGHT * c.y));

                c.setFloor(x, y, FloorRegister.getFloor("f:grass", n));
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
                c.setFloor(x, y, FloorRegister.getFloor("f:stone", 0));
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
                c.setFloor(x, y, FloorRegister.getFloor("f:stone_bricks", 0));
            }
        }
    }
}
