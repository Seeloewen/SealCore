package de.sealcore.game.chunks;

import de.sealcore.game.blocks.BlockRegister;
import de.sealcore.util.FastNoiseLite;

import java.util.Random;

public class BlockGenerator
{
    private final FastNoiseLite noise;
    private final Random rnd;

    public BlockGenerator(int seed)
    {
        rnd = new Random(seed);

        //Setup noise
        noise = new FastNoiseLite(seed);
        noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noise.SetFrequency(0.1f);
    }

    private float getBlockNoise(int x, int y)
    {
        //Noise normally generates between -1 and 1. This shifts it to 0 to 1.
        return (noise.GetNoise(x, y) + 1.0f) / 2.0f;
    }

    public boolean isLocalMaxNoise(int cIndex, int x, int y)
    {
        //Transform into absolute coords for later calculations
        x = x + 8 * cIndex;
        y = y + 8 * cIndex;
        float n = getBlockNoise(x, y); //Noise of point that gets checked

        //Check the surrounding noises in each direction 1 block to determine if local maximum
        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                if (i == 0 && j == 0) continue; //Skip the reference noise, obviously

                if (n < getBlockNoise(x + i, y + j)) return false;
            }
        }

        return true;
    }

    //-------------------------------------//

    public void genTree(Chunk c, int x, int y)
    {
        //Generate a random tree
        switch (rnd.nextInt(0, 2))
        {
            case 0 -> c.setBlock(x, y, BlockRegister.getBlock("b:oak_tree")); //Oak
            case 1 -> c.setBlock(x, y, BlockRegister.getBlock("b:spruce_tree")); //Spruce
        }
    }

    public void genRock(Chunk c, int x, int y)
    {
        //Generate a random tree
        switch (rnd.nextInt(0, 1))
        {
            case 0 -> c.setBlock(x, y, BlockRegister.getBlock("b:small_rock")); //Small
        }
    }
}
