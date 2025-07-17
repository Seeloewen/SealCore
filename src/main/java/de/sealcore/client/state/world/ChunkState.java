package de.sealcore.client.state.world;

import de.sealcore.util.ChunkIndex;

public class ChunkState {

    public FloorState[] floors;
    public BlockState[] blocks;
    final int index;

    private boolean topOnly[];

    private int floorLod;

    ChunkState(int index, String[] floors, String[] blocks) {
        this.floors = new FloorState[64];
        this.blocks = new BlockState[64];
        this.topOnly = new boolean[64];
        int cx = ChunkIndex.toX(index);
        int cy = ChunkIndex.toY(index);
        for(int i = 0; i < 64; i++) {
            this.floors[i] = new FloorState(floors[i], cx*8 + i%8, cy*8 + i/8);
            this.topOnly[i] = i/8 > 0 && i/8 < 7 && i%8 > 0 && i%8 < 7
                    && floors[i].equals("f:grass")
                    && floors[i-1].equals("f:grass") && floors[i+1].equals("f:grass")
                    && floors[i-8].equals("f:grass") && floors[i+8].equals("f:grass");
            if(!blocks[i].equals("")) {
                this.blocks[i] = new BlockState(blocks[i], cx*8 + i%8, cy*8 + i/8);
            }
        }
        this.index = index;
    }


    void render(double dist) {

        for(int i = 0; i < 64; i++) {
            floors[i].render(dist >= 1200 ? 1 : 0, topOnly[i]);
        }
        for(BlockState b : blocks) if(b != null) b.render(0);
    }

    public void rayIntersectChunks(double[] o, double[] d) {
        for(int i = 0; i < 64; i++) {
            if(blocks[i] != null) blocks[i].rayIntersect(o, d);
            if(floors[i] != null) floors[i].rayIntersect(o, d);
        }
    }


}
