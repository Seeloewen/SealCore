package de.sealcore.client.gamestate;


import de.sealcore.client.model.Builder;
import de.sealcore.client.model.InvalidFileFormatException;
import de.sealcore.client.model.MeshGenerator;
import de.sealcore.client.model.Parser;
import de.sealcore.client.rendering.objects.Mesh;
import de.sealcore.client.rendering.objects.MeshRenderer;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;


import java.io.IOException;
import java.util.HashMap;

public class GameState {

    HashMap<Integer, ChunkState> loadedChunks;

    HashMap<Integer, Mesh> loadedMeshes;


    public GameState() {
        loadedChunks = new HashMap<>();
        loadedMeshes = new HashMap<>();

        /*loadChunk(0);
        loadChunk(1);
        loadChunk(2);
        loadChunk(4);
        loadChunk(3);
        loadChunk(5);
*/

        Builder builder = null;
        try {
            builder = Parser.parse("test_objects/grass");
        } catch (Exception e) {
            e.printStackTrace();
        }
        var mesh = MeshGenerator.generate(builder, 1/8f);
        addMesh(0, mesh);



    }



    public void render(MeshRenderer renderer) {
        for(var state : loadedChunks.values()) {
            state.renderFloor(renderer);
        }
        for(var mesh : loadedMeshes.values()) {
            renderer.render(mesh);
        }

    }


    //chunk creation parameters have to be added
    public void loadChunk(int id, String[] floors) {
        loadedChunks.put(id, new ChunkState(id, floors));
        Log.info(LogType.RENDERING, "loaded chunk " + id);
    }

    public void addMesh(int id, Mesh mesh) {
        loadedMeshes.put(id, mesh);
    }



}
