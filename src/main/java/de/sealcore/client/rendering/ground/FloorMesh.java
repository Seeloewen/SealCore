package de.sealcore.client.rendering.ground;

import de.sealcore.client.gamestate.FloorState;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.model.mesh.MeshSide;
import de.sealcore.client.model.mesh.MeshVertex;

public class FloorMesh extends Mesh {


    public FloorMesh(FloorState[] floors) {
        super(toSides(floors));
    }

    private static MeshSide[] toSides(FloorState[] floors) {
        MeshSide[] sides = new MeshSide[64];
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                MeshVertex v0 = new MeshVertex((float)x, (float)y, 0f);
                MeshVertex v1 = new MeshVertex((float)x+1, (float)y, 0f);
                MeshVertex v2 = new MeshVertex((float)x+1, (float)y+1, 0f);
                MeshVertex v3 = new MeshVertex((float)x, (float)y+1, 0f);
                sides[y*8+x] = new MeshSide(v0, v1, v2, v3, floors[y*8+x].getColor());
            }
        }
        return sides;
    }

}
