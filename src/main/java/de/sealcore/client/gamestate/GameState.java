package de.sealcore.client.gamestate;


import de.sealcore.client.rendering.objects.MeshRenderer;


import java.util.HashMap;

public class GameState {

    HashMap<Integer, ChunkState> loadedChunks;



    public GameState() {
        loadedChunks = new HashMap<>();

        /*loadChunk(0);
        loadChunk(1);
        loadChunk(2);
        loadChunk(4);
        loadChunk(3);
        loadChunk(5);
*/
    }



    public void render(MeshRenderer renderer) {
        for(var state : loadedChunks.values()) {
            state.renderFloor(renderer);
        }
    }


    //chunk creation parameters have to be added
    public void loadChunk(int id, String[] floors) {
        loadedChunks.put(id, new ChunkState(id, floors));
    }


}
