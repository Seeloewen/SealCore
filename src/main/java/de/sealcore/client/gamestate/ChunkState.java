package de.sealcore.client.gamestate;

import de.sealcore.client.rendering.ground.FloorMesh;
import de.sealcore.client.rendering.objects.MeshRenderer;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.Color;
import org.joml.Matrix4f;

class ChunkState {

    FloorState[] floors;
    final int index;

    FloorMesh floorMesh;

    ChunkState(int index) {
        floors = new FloorState[64];
        for(int i = 0; i < 64; i++) {
            floors[i] = new FloorState(Math.random() < 0.3 ? new Color(0.1f,0.1f,0.9f) : new Color(0.1f,0.9f,0.1f));
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
