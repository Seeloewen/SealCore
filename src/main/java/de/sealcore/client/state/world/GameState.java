package de.sealcore.client.state.world;


import de.sealcore.client.Client;
import de.sealcore.game.floors.Floor;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.MathUtil;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;


import java.util.HashMap;

public class GameState {

    public HashMap<Integer, ChunkState> loadedChunks;

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

    public void interpolate(double dt) {
        for(var m : loadedMeshes.values()) {
            m.interpolate(dt);
        }
    }

    public void render() {
        var rotZ = Client.instance.camera.angleHor;
        var p = Client.instance.gameState.loadedMeshes.get(Client.instance.camera.following);
        if(p != null) {
            double dy = Math.sin(rotZ);
            double dx = Math.cos(rotZ);
            double x = p.posX;
            double y = p.posY;
            x -= dx * 8;
            y -= dy * 8;


            for (var state : loadedChunks.values()) {
                int i = state.index;
                int cx = ChunkIndex.toX(i) * 8 + 4;
                int cy = ChunkIndex.toY(i) * 8 + 4;
                double cAngle = Math.atan2(cy - y, cx - x);
                boolean cull2 = Math.abs(rotZ - cAngle) >= 0.9 && Math.abs(rotZ - cAngle) <= Math.PI * 2 - 0.9;
                if (!cull2) {
                    state.render();
                }
            }
        }
        for(var mesh : loadedMeshes.values()) {
            mesh.render();
        }

    }


    //chunk creation parameters have to be added
    public void loadChunk(int id, String[] floors, String[] blocks) {
        loadedChunks.put(id, new ChunkState(id, floors, blocks));
    }

    public void unloadChunk(int id) {
        loadedChunks.remove(id);
    }

    public void updateFloorChunk(int id, String floorID, int index) {
        loadedChunks.get(id).floors[index] = new FloorState(floorID, ChunkIndex.toX(id)*8+index%8, ChunkIndex.toY(id)*8+index/8);
    }

    public void updateBlockChunk(int id, String blockID, int index) {
        loadedChunks.get(id).blocks[index] = new BlockState(blockID, ChunkIndex.toX(id)*8+index%8, ChunkIndex.toY(id)*8+index/8);
    }


    public void addMesh(int id, String entityID, double x, double y, double z) {
        loadedMeshes.put(id, new MeshState(entityID, x, y, z));
    }

    public void updateMeshPos(int id, double x, double y, double z, double rotZ, double velX, double velY) {
        loadedMeshes.get(id).setPosition(x, y, z, rotZ, velX, velY);
    }

    public void removeMesh(int id) {
        loadedMeshes.remove(id);
    }

    public MeshState getMesh(int id) {
        return loadedMeshes.get(id);
    }


}
