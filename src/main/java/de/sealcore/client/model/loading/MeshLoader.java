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





    public static Builder parseMesh(String path) {
        try {
            return Parser.parse(path);
        } catch (InvalidFileFormatException e) {
            Log.error(LogType.RENDERING, "Invalid File Format for " + path + "\n" + e.getMessage());
        } catch (IOException e) {
            Log.error(LogType.RENDERING, "IOException occurred on loading mesh for " + path + "\n" + e.getMessage());
        }
        return new Builder(0, 0, 0, 0, 0, 0);
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
                var builder = parseMesh(path);
                if(json.keyExists("lod")) {
                    int lod = json.getInt("lod");
                    for(int i = 0; i <= lod; i++) {
                        Mesh m = new Mesh(MeshGenerator.generate(builder, (float)scale, false, true));
                        MeshRenderer.addMesh(String.format("%s:%d", id, i), m);
                        if(id.equals("f:grass")) {
                            Mesh mt = new Mesh(MeshGenerator.generate(builder, (float)scale, true, true));
                            MeshRenderer.addMesh(String.format("%s:%d:topOnly", id, i), mt);
                        }

                        builder = builder.genLOD();
                        scale *= 2;
                    }
                } else {
                    Mesh m = new Mesh(MeshGenerator.generate(builder, (float)scale, false, false));
                    MeshRenderer.addMesh(id, m);
                }

            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






}
