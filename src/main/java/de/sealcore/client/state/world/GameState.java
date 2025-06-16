package de.sealcore.client.state.world;


import de.sealcore.util.ChunkIndex;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;


import java.util.HashMap;

public class GameState {

    HashMap<Integer, ChunkState> loadedChunks;

    public HashMap<Integer, MeshState> loadedMeshes;


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

        /*Builder builder = null;
        try {
            builder = Parser.parse("test_objects/grass");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //var mesh = MeshGenerator.generate(builder, 1/8f);
        //loadedMeshes.put(0, new Mesh(mesh));




    }



    public void render() {
        for(var state : loadedChunks.values()) {
            state.render();
        }
        for(var mesh : loadedMeshes.values()) {
            mesh.render();
        }

    }


    //chunk creation parameters have to be added
    public void loadChunk(int id, String[] floors, String[] blocks) {
        loadedChunks.put(id, new ChunkState(id, floors, blocks));
        Log.info(LogType.RENDERING, "loaded chunk " + id);
    }

    public void unloadChunk(int id) {
        loadedChunks.remove(id);
        Log.info(LogType.RENDERING, "unloaded chunk " + id);
    }

    public void updateFloorChunk(int id, String floorID, int index) {
        loadedChunks.get(id).floors[index] = new FloorState(floorID, ChunkIndex.toX(id)*8+index%8, ChunkIndex.toY(id)*8+index/8);
    }

    public void updateBlockChunk(int id, String blockID, int index) {
        loadedChunks.get(id).blocks[index] = new BlockState(blockID, ChunkIndex.toX(id)*8+index%8, ChunkIndex.toY(id)*8+index/8);
    }


    public void addMesh(int id, String entityID, double x, double y, double z, double sizeX, double sizeY, double sizeZ) {
        loadedMeshes.put(id, new MeshState(entityID, x, y, z, sizeX, sizeY, sizeZ));
    }

    public void updateMeshPos(int id, double x, double y, double z) {
        loadedMeshes.get(id).setPosition(x, y, z);
    }

    public void removeMesh(int id) {
        loadedMeshes.remove(id);
    }

    public MeshState getMesh(int id) {
        return loadedMeshes.get(id);
    }


}
