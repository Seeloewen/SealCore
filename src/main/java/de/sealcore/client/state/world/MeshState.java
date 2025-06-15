package de.sealcore.client.state.world;

import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import org.joml.Matrix4f;

public class MeshState {


    String meshID;
    public double posX;
    public double posY;
    public double posZ;

    public boolean visible = true;

    MeshState(String modelID, double posX, double posY, double posZ, double sizeX, double sizeY, double sizeZ) {
        this.meshID = modelID;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

    }

    void setPosition(double x, double y, double z) {
        posX = x;
        posY = y;
        posZ = z;
    }

    void render() {
        if(visible) MeshRenderer.render(meshID, new Matrix4f().translate((float) posX, (float) posY, (float) posZ));
    }


}
