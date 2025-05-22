package de.sealcore.game;

import de.sealcore.util.ChunkIndex;

public class Map
{
    private Chunk[] chunks = new Chunk[256*256];

    Map()
    {
        init();
    }

    void init()
    {
        //Generate the first four chunks
        for(int i = 0; i < 4; i++)
        {
            chunks[i] = new Chunk(i);
        }
    }

    public void genChunk(int x, int y)
    {
        //Get index from x and y and generate new Chunk
        int i = ChunkIndex.toI(x, y);
        chunks[i] = new Chunk(i);
    }

    public Chunk getChunk(int x, int y)
    {
        //Gets the chunk from the list, possibly index out of bounds (may be fixed later, may be not)
        return chunks[ChunkIndex.toI(x, y)];
    }
}