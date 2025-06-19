package de.sealcore.game.chunks;

import de.sealcore.game.blocks.Block;
import de.sealcore.game.floors.Floor;
import de.sealcore.game.floors.FloorRegister;
import de.sealcore.networking.packets.ChunkUpdatePacket;
import de.sealcore.util.ChunkIndex;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.ChunkAddPacket;

import java.util.ArrayList;

public class Chunk
{
    public final int x;
    public final int y;
    public final int index;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Floor[] floors = new Floor[64];
    private Block[] blocks = new Block[64];

    private Chunk(int x, int y)
    {
        this.x = x;
        this.y = y;
        index = ChunkIndex.toI(x, y);

        //Fill all chunks with grass by default to avoid null pointers later on
        for(int i = 0; i < floors.length; i++)
        {
            floors[i] = FloorRegister.getFloor("f:grass");
        }
    }

    public static Chunk getEmptyChunk(int x, int y)
    {
        //This method only exists so I can make the constructor private
        //The constructor does NOT do the generation, so just calling "Chunk" could be confusing
        //getEmptyChunk does imply that generation is still needed, so here it is :)
        return new Chunk(x, y);
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

    public void setBlock(int x, int y, Block b, boolean sync)
    {
        //Set blocks at specified location
        blocks[Chunk.coordsToIndex(x, y)] = b;
        if(sync) {
            NetworkHandler.send(new ChunkUpdatePacket(index, false, b==null?"":b.info.id(), coordsToIndex(x, y)));
        }
    }

    public Block getBlock(int x, int y)
    {
        //Get floor from specified location
        return blocks[coordsToIndex(x, y)];
    }

    private ChunkAddPacket getAddPacket()
    {
        ArrayList<String> s = new ArrayList<String>();
        for(Floor f : floors)
        {
            s.add(f.info.id());
        }
        ArrayList<String> blocks = new ArrayList<String>();
        for(Block b : this.blocks)
        {
            blocks.add(b == null ? "" : b.info.id());
        }
        return new ChunkAddPacket(s.toArray(new String[0]), blocks.toArray(new String[0]), index);
    }

    public void sendAddPacket() {
        NetworkHandler.send(getAddPacket());
    }

    public void sendAddPacket(int clientID) {
        NetworkHandler.sendOnly(clientID, getAddPacket());
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
