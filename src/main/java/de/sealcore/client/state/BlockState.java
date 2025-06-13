package de.sealcore.client.state;

import de.sealcore.client.model.loading.MeshLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.model.mesh.MeshSide;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import de.sealcore.game.blocks.BigRock;
import de.sealcore.game.blocks.OakTree;
import de.sealcore.game.blocks.SmallRock;
import de.sealcore.game.blocks.SpruceTree;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class BlockState {

    private String type;
    private int x;
    private int y;
    private Mesh mesh;


    BlockState(String id, int globalX, int globalY) {
        type = id;
        x = globalX;
        y = globalY;
        mesh = calcMesh();
        mesh.position = new Matrix4f().translate(x, y, 0);
    }

    void render() {
        MeshRenderer.render(mesh);
    }

    private Mesh calcMesh() {
        try {
            return MeshLoader.loadMesh(type);
        } catch (RuntimeException e) {
            Log.error(LogType.RENDERING, "RuntimeException loading mesh of type " + type + ": " + e.getMessage());
        }
        return new Mesh(new MeshSide[0]);
    }



}
