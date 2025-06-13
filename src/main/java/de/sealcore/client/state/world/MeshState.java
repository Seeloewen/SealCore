package de.sealcore.client.state.world;

import de.sealcore.client.model.loading.MeshLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;

public class MeshState {


    Mesh mesh;
    double posX;
    double posY;
    double posZ;

    MeshState(String modelID, double posX, double posY, double posZ, double sizeX, double sizeY, double sizeZ) {
        mesh = MeshLoader.loadMesh(modelID);
    }

    void setPosition(double x, double y, double z) {
        posX = x;
        posY = y;
        posZ = z;
        mesh.setPosition(x, y, z);
    }

    void render() {
        MeshRenderer.render(mesh);
    }


}
