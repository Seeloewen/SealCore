package de.sealcore.server.waves;

import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.SetTextPacket;
import de.sealcore.server.Server;
import de.sealcore.util.ResourceManager;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

public class WaveManager {

    private static double currentTime;

    private static ArrayList<Wave> waves;
    private static int nextWave;

    public static boolean enabled = true;

    public static void init() {
        currentTime = 0;
        waves = new ArrayList<>();
        nextWave = 0;
        try {
            JsonArray jsonArray = JsonObject.fromString(ResourceManager.getResourceFileAsString("wave_config.json"))
                    .getArray("waves");
            for(Object o : jsonArray) {
                JsonObject json = (JsonObject) o;
                waves.add(new Wave(
                        json.getInt("time"),
                        json.getObject("spawns").getInt("e:grassling"),
                        json.getObject("spawns").getInt("e:big_grassling"),
                        json.getObject("spawns").getInt("e:jabbus")
                ));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(double dt) {
        if(enabled) {
            currentTime += dt;
            if (waves.size() <= nextWave) return;
            if (currentTime >= waves.get(nextWave).time()) {
                var wave = waves.get(nextWave);
                Server.game.spawnWave(wave.grasslings(), wave.big_grasslings(), wave.jabbus());
                nextWave++;
                NetworkHandler.send(new SetTextPacket("Wave " + nextWave + " spawned", 2));
            }
        }
    }


}
