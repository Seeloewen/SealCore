package de.sealcore.client.model.loading;

import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.model.mesh.MeshSide;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.io.IOException;

public class MeshLoader {




    public static Mesh loadMesh(String id) {
        try {
            String path = entityIdToPath(id);
            var builder = Parser.parse(path);
            var sides = MeshGenerator.generate(builder,  getScale(id));
            return new Mesh(sides);


        } catch (InvalidFileFormatException e) {
            Log.error(LogType.RENDERING, "Invalid File Format for " + id + "\n" + e.getMessage());
        } catch (IOException e) {
            Log.error(LogType.RENDERING, "IOException occurred on loading mesh for " + id + "\n" + e.getMessage());
        }
        return new Mesh(new MeshSide[0]);

    }



    public static String entityIdToPath(String entityID) {
        return switch(entityID) {
            case "f:grass" -> "test_objects/Grass.txt";
            case "e:player" -> "test_objects/Player.txt";
            case "e:grassling" -> "test_objects/Grass_Enemy.txt";
            case "f:water" -> "test_objects/Water.txt";
            case "b:spruce_tree" -> "test_objects/Tree.txt";
            case "b:oak_tree" -> "test_objects/Tree.txt";
            default -> throw new IllegalArgumentException("entityId not found: " + entityID);
        };
    }


    public static float getScale(String entityID) {
        return switch(entityID) {
            case "f:grass" -> 1/8f;
            case "f:water" -> 1/8f;
            case "e:player" -> 0.6f/10;
            case "e:grassling" -> 0.9f*(1/8f);
            case "b:spruce_tree" -> 1/10f;
            case "b:oak_tree" -> 1/10f;
            default -> throw new IllegalStateException("Unexpected value: " + entityID);
        };
    }



}
