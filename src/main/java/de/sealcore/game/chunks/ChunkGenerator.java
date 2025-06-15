package de.sealcore.game.chunks;

import de.sealcore.game.maps.MapLayout;

import java.util.Random;

public class ChunkGenerator
{
    private final TerrainGenerator terrainGenerator;
    private final BlockGenerator blockGenerator;

    private final int seed;
    private final MapLayout layout;

    public ChunkGenerator(int seed, MapLayout layout)
    {
        terrainGenerator = new TerrainGenerator(seed);
        blockGenerator = new BlockGenerator(seed);

        this.layout = layout;
        this.seed = seed;
    }

    public Chunk getChunk(int cX, int cY)
    {
        //Creates a new chunk, generates the terrain and blocks (structures)
        Chunk c = Chunk.getEmptyChunk(cX, cY);

        genTerrain(c);
        genBlocks(c);

        return c;
    }

    public void genTerrain(Chunk c)
    {
        //Generate the chunk based on the layout
        switch (layout)
        {
            case NORMAL -> terrainGenerator.genNormalChunk(c);
            case CAVE -> terrainGenerator.genCaveChunk(c);
            case DUNGEON -> terrainGenerator.genDungeonChunk(c);
        }
    }

    private void genBlocks(Chunk c)
    {
        Random rnd = new Random(seed);

        //Generate structures based on the layout
        for (int x = 0; x < Chunk.WIDTH; x++)
        {
            for (int y = 0; y < Chunk.HEIGHT; y++)
            {
                if (!blockGenerator.isLocalMaxNoise(c.index, x, y)) continue; //Only generate structures at local max points, ensures even distribution

                if (!c.getFloor(x, y).info.isSolid()) continue; //Skip all floors that are not solid

                //Roll the structure to generate
                int structure = rnd.nextInt(0, 1);

                if (structure == 0) blockGenerator.genTree(c, x, y); //Trees
                else if (structure == 1) blockGenerator.genRock(c, x, y); //Rocks

            }
        }
    }
}
