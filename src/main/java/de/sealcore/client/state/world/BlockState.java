package de.sealcore.client.state.world;

import de.sealcore.client.model.loading.MeshLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.model.mesh.MeshSide;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import org.joml.Matrix4f;

public class BlockState {

    private String type;
    private int x;
    private int y;
    private Matrix4f pos;



    BlockState(String id, int globalX, int globalY) {
        type = id;
        x = globalX;
        y = globalY;
        pos = new Matrix4f().translate(x, y, 0);
    }

    void render() {
        MeshRenderer.render(type, pos);
    }




}
