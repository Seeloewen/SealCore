package de.sealcore.game.maps;

import de.sealcore.game.chunks.Chunk;
import de.sealcore.game.chunks.ChunkGenerator;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.ChunkAddPacket;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Map
{
    private final ChunkGenerator generator;

    public final int id;
    private final long seed;
    private final MapLayout layout;

    private Chunk[] chunks = new Chunk[256 * 256];

    public Map(int id, MapLayout layout)
    {
        this.id = id;
        this.layout = layout;

        //Setup chunk generation
        seed = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        generator = new ChunkGenerator(seed, layout);

        //Generate the first four chunks (spawn chunks)
        for (int i = 0; i < 4; i++)
        {
            genChunk(ChunkIndex.toX(i), ChunkIndex.toY(i));
        }
    }

    public Chunk genChunk(int x, int y)
    {
        //Get index from x and y and generate new Chunk
        int i = ChunkIndex.toI(x, y);

        //Generate a new chunk and add it to the register
        Chunk c = generator.genChunk(ChunkIndex.toI(x, y));
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
}