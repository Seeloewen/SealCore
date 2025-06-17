package de.sealcore.client.config;

import de.sealcore.util.json.JsonObject;

public record ItemConfig(String id, String type, double range) {

    static ItemConfig fromJson(JsonObject json) {
        return new ItemConfig(
                json.getString("id"),
                json.getString("type"),
                json.getDouble("range")
        );
    }

}
