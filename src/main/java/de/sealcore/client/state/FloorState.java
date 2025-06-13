package de.sealcore.client.state;

import de.sealcore.client.model.loading.MeshLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import org.joml.Matrix4f;

public class FloorState {

    private String type;
    private int x;
    private int y;
    private Mesh mesh;


    FloorState(String id, int globalX, int globalY) {
        type = id;
        x = globalX;
        y = globalY;
        mesh = calcMesh();
        mesh.position = new Matrix4f().translate(x, y, -1);
    }

    void render() {
        MeshRenderer.render(mesh);
    }

    private Mesh calcMesh() {
        return switch(type) {
            case "f:grass" -> MeshLoader.loadMesh("f:grass");
            case "f:water" -> MeshLoader.loadMesh("f:water");
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

}
