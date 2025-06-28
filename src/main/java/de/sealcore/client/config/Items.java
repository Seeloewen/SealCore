package de.sealcore.client.config;

import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.util.ResourceManager;
import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.io.IOException;
import java.util.HashMap;

public class Items {

    public static HashMap<String, JsonObject> itemConfigs;

    public static void init() {
        itemConfigs = new HashMap<>();
        try {
            var o = JsonObject.fromString(ResourceManager.getResourceFileAsString("client_content_config/items.json"));
            var itemObjects = o.getArray("items");
            for(Object j : itemObjects) {
                var itemObject = (JsonObject) j;
                String id = itemObject.getString("id");
                itemConfigs.put(id, itemObject);
                TextureRenderer.loadTexture(id, itemObject.getString("texture"));
            }

        } catch (IOException e) {
            Log.error(LogType.GAME, "error reading client item config \n" + e);
        }
    }


    public static JsonObject get(String id) {
        return itemConfigs.get(id);
    }







}
