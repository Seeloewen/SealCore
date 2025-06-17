package de.sealcore.client.config;

import de.sealcore.util.ResourceManager;
import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.io.IOException;
import java.util.HashMap;

public class Items {

    public static HashMap<String, ItemConfig> itemConfigs;

    public static void init() {
        itemConfigs = new HashMap<>();
        try {
            var o = JsonObject.fromString(ResourceManager.getResourceFileAsString("client_item_config.json"));
            var itemObjects = o.getArray("items");
            for(Object j : itemObjects) {
                var itemObject = (JsonObject) j;
                itemConfigs.put(itemObject.getString("id"), ItemConfig.fromJson(itemObject));
            }

        } catch (IOException e) {
            Log.error(LogType.GAME, "error reading client item config \n" + e);
        }
    }


    public static ItemConfig get(String id) {
        return itemConfigs.get(id);
    }







}
