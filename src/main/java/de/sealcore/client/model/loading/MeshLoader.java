package de.sealcore.client.model.loading;

import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.model.mesh.MeshSide;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.io.IOException;

public class MeshLoader {




    public static Mesh loadMesh(String id) {
        try {
            String path = meshIdToPath(id);
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



    public static String meshIdToPath(String entityID) {
        return switch(entityID) {
            case "f:grass" -> "objects/Grass.txt";
            case "e:player" -> "objects/Player.txt";
            case "e:grassling" -> "objects/Grass_Enemy.txt";
            case "f:water" -> "objects/Water.txt";
            case "b:spruce_tree" -> "objects/Tree.txt";
            case "b:oak_tree" -> "objects/Tree.txt";
            case "b:core_1" -> "objects/Core_1.txt";
            case "b:core_2" -> "objects/Core_2.txt";
            case "b:core_3" -> "objects/Core_3.txt";
            case "b:core_4" -> "objects/Core_4.txt";
            default -> throw new IllegalArgumentException("Mesh id not found: " + entityID);
        };
    }


    public static float getScale(String entityID) {
        return switch(entityID) {
            case "f:grass" -> 1/8f;
            case "f:water" -> 1/8f;
            case "b:core_1" -> 1/8f;
            case "b:core_2" -> 1/8f;
            case "b:core_3" -> 1/8f;
            case "b:core_4" -> 1/8f;
            case "e:player" -> 0.6f/10;
            case "e:grassling" -> 0.9f*(1/8f);
            case "b:spruce_tree" -> 1/10f;
            case "b:oak_tree" -> 1/10f;
            default -> throw new IllegalStateException("Unexpected value: " + entityID);
        };
    }



}
