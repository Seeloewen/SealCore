package de.sealcore.client.gamestate;

import de.sealcore.client.model.loading.ModelLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.rendering.meshes.MeshRenderer;
import de.sealcore.game.floors.Grass;
import de.sealcore.game.floors.Stone;
import de.sealcore.game.floors.StoneBricks;
import de.sealcore.game.floors.Water;
import de.sealcore.util.Color;
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
            case "f:grass" -> ModelLoader.loadMesh("f:grass");
            case "f:water" -> ModelLoader.loadMesh("f:water");
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

}
