package de.sealcore.client.gamestate;

import de.sealcore.util.ChunkIndex;

class ChunkState {

    FloorState[] floors;
    BlockState[] blocks;
    final int index;


    ChunkState(int index, String[] floors) {
        this.floors = new FloorState[64];
        int cx = ChunkIndex.toX(index);
        int cy = ChunkIndex.toY(index);
        for(int i = 0; i < 64; i++) {
            this.floors[i] = new FloorState(floors[i], cx*8 + i%8, cy*8 + i/8);
        }
        this.index = index;
    }


    void renderFloor() {
        for(FloorState f : floors) f.render();
    }



}
