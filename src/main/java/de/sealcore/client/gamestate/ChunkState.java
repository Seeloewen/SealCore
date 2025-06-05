package de.sealcore.client.gamestate;

import de.sealcore.client.rendering.ground.FloorMesh;
import de.sealcore.client.rendering.objects.MeshRenderer;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.Color;
import org.joml.Matrix4f;

class ChunkState {

    FloorState[] floors;
    BlockState[] blocks;
    final int index;

    FloorMesh floorMesh;

    ChunkState(int index, String[] floors) {
        this.floors = new FloorState[64];
        for(int i = 0; i < 64; i++) {
            this.floors[i] = new FloorState(floors[i]);
        }
        this.index = index;
    }

    private void generateFloorMesh() {
        floorMesh = new FloorMesh(floors);
        int x = ChunkIndex.toX(index);
        int y = ChunkIndex.toY(index);
        floorMesh.position = new Matrix4f().translate(8f*x, 8f*y, 0);
    }

    void renderFloor(MeshRenderer renderer) {
        if(floorMesh == null) generateFloorMesh();
        renderer.render(floorMesh);
    }



}
