package de.sealcore.client.state.world;

import de.sealcore.util.ChunkIndex;

public class ChunkState {

    FloorState[] floors;
    BlockState[] blocks;
    final int index;


    ChunkState(int index, String[] floors, String[] blocks) {
        this.floors = new FloorState[64];
        this.blocks = new BlockState[64];
        int cx = ChunkIndex.toX(index);
        int cy = ChunkIndex.toY(index);
        for(int i = 0; i < 64; i++) {
            this.floors[i] = new FloorState(floors[i], cx*8 + i%8, cy*8 + i/8);
            if(!blocks[i].equals("")) {
                this.blocks[i] = new BlockState(blocks[i], cx*8 + i%8, cy*8 + i/8);
            }
        }
        this.index = index;
    }


    void render() {

        for(FloorState f : floors) f.render();
        for(BlockState b : blocks) if(b != null) b.render();
    }

    public void rayIntersectChunks(double[] o, double[] d) {
        for(int i = 0; i < 64; i++) {
            if(blocks[i] != null) blocks[i].rayIntersect(o, d);
            if(floors[i] != null) floors[i].rayIntersect(o, d);
        }
    }


}
