package de.sealcore.client.gamestate;


import de.sealcore.client.model.loading.Builder;
import de.sealcore.client.model.loading.MeshGenerator;
import de.sealcore.client.model.loading.ModelLoader;
import de.sealcore.client.model.loading.Parser;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.rendering.meshes.MeshRenderer;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;


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
        loadedMeshes.put(0, new Mesh(mesh));


        addMesh(1, "e:grass", 1, 2, 0);


    }



    public void render() {
        for(var state : loadedChunks.values()) {
            state.renderFloor();
        }
        for(var mesh : loadedMeshes.values()) {
            MeshRenderer.render(mesh);
        }

    }


    //chunk creation parameters have to be added
    public void loadChunk(int id, String[] floors) {
        loadedChunks.put(id, new ChunkState(id, floors));
        Log.info(LogType.RENDERING, "loaded chunk " + id);
    }

    public void updateFloorChunk(int id, String floorID, int index) {
        loadedChunks.get(id).floors[index] = new FloorState(floorID);
    }

    public void updateBlockChunk(int id, String blockID, int index) {
        loadedChunks.get(id).blocks[index] = new BlockState(blockID);
    }


    public void addMesh(int id, String entityID, double x, double y, double z) {
        Mesh m = ModelLoader.loadMesh(entityID);
        loadedMeshes.put(id, m);
        m.setPosition(x, y, z);
    }

    public void updateMeshPos(int id, double x, double y, double z) {
        loadedMeshes.get(id).setPosition(x, y, z);
    }

    public void removeMesh(int id) {
        loadedMeshes.remove(id);
    }




}
