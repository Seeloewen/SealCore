package de.sealcore.game.maps;

import de.sealcore.game.blocks.Block;
import de.sealcore.game.chunks.Chunk;
import de.sealcore.game.chunks.ChunkGenerator;
import de.sealcore.game.floors.Floor;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.Maths;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Map
{
    private final ChunkGenerator generator;

    public final int id;
    private final int seed;
    private final MapLayout layout;

    private Chunk[] chunks = new Chunk[256 * 256];

    public Map(int id, MapLayout layout)
    {
        this.id = id;
        this.layout = layout;

        //Setup chunk generation
        seed = (int)(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        generator = new ChunkGenerator(seed, layout);

        //Generate the first four chunks (spawn chunks)
        for (int i = 0; i < 32; i++)
        {
            genChunk(ChunkIndex.toX(i), ChunkIndex.toY(i));
        }
    }

    public Chunk genChunk(int x, int y)
    {
        //Get index from x and y and generate new Chunk
        int i = ChunkIndex.toI(x, y);

        //Generate a new chunk and add it to the register
        Chunk c = generator.getChunk(x, y);
        chunks[i] = c;

        c.sendAddPacket();

        //Returns the chunk in case further action should be applied
        return c;
    }

    public Chunk getChunk(int x, int y)
    {
        //Gets the chunk from the list
        int i = ChunkIndex.toI(x, y);

        if(i >= chunks.length) return null;
        return chunks[i];
    }

    public Chunk getChunk(int i) {
        if(i >= chunks.length) return null;
        return chunks[i];
    }

    public Block getBlock(int x, int y)
    {
        //Effectively deletes the decimal part, resulting in the raw chunk
        int cx = (int)Math.floor((double) x / 8);
        int cy = (int)Math.floor((double) y / 8);

        Chunk c = getChunk(cx, cy);

        x = Maths.safeMod(x, 8);
        y = Maths.safeMod(y, 8);

        if(c != null) return c.getBlock(x, y);

        return null;
    }

    public Floor getFloor(int x, int y)
    {
        //Effectively deletes the decimal part, resulting in the raw chunk
        int cx = (int)Math.floor((double) x / 8);
        int cy = (int)Math.floor((double) y / 8);

        Chunk c = getChunk(cx, cy);

        x = Maths.safeMod(x, 8);
        y = Maths.safeMod(y, 8);

        if(c != null) return c.getFloor(x, y);

        return null;
    }
}