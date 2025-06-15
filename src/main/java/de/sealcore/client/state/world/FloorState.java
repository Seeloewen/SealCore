package de.sealcore.client.state.world;

import de.sealcore.client.model.loading.MeshLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import org.joml.Matrix4f;

public class FloorState {

    private String type;
    private int x;
    private int y;
    private String meshID;
    private Matrix4f pos;


    FloorState(String id, int globalX, int globalY) {
        type = id;
        x = globalX;
        y = globalY;
        meshID = id;
        pos = new Matrix4f().translate(x, y, -1);
    }

    void render() {
        MeshRenderer.render(meshID, pos);
    }

}
