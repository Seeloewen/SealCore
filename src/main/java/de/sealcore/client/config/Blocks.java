package de.sealcore.client.config;

import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.util.ResourceManager;
import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.io.IOException;
import java.util.HashMap;

public class Blocks {

    public static HashMap<String, JsonObject> blockConfigs;

    public static void init() {
        blockConfigs = new HashMap<>();
        try {
            var o = JsonObject.fromString(ResourceManager.getResourceFileAsString("client_content_config/blocks.json"));
            var itemObjects = o.getArray("blocks");
            for(Object j : itemObjects) {
                var itemObject = (JsonObject) j;
                String id = itemObject.getString("id");
                blockConfigs.put(id, itemObject);
            }

        } catch (IOException e) {
            Log.error(LogType.GAME, "error reading client block config \n" + e);
        }
    }


    public static JsonObject get(String id) {
        return blockConfigs.get(id);
    }

}
