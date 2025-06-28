package de.sealcore.client.model.loading;

import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.model.mesh.MeshSide;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import de.sealcore.util.ResourceManager;
import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.io.IOException;

public class MeshLoader {




    public static Mesh loadMesh(String path, double scale) {
        try {
            var builder = Parser.parse(path);
            var sides = MeshGenerator.generate(builder, (float)scale);
            return new Mesh(sides);


        } catch (InvalidFileFormatException e) {
            Log.error(LogType.RENDERING, "Invalid File Format for " + path + "\n" + e.getMessage());
        } catch (IOException e) {
            Log.error(LogType.RENDERING, "IOException occurred on loading mesh for " + path + "\n" + e.getMessage());
        }
        return new Mesh(new MeshSide[0]);

    }

    public static void loadMeshes() {
        try {
            var meshObjects = JsonObject.fromString(ResourceManager.getResourceFileAsString("client_content_config/meshes.json"))
                    .getArray("meshes");
            for(Object o : meshObjects) {
                JsonObject json = (JsonObject) o;
                String id = json.getString("id");
                String path = json.getString("path");
                double scale = json.getDouble("scale");
                MeshRenderer.loadMesh(id, path, scale);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






}
