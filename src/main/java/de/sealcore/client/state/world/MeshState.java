package de.sealcore.client.state.world;

import de.sealcore.client.config.Entities;
import de.sealcore.client.ui.rendering.line.LineRenderer;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MeshState {

    public String displayName;

    String meshID;
    public double posX;
    public double posY;
    public double posZ;

    public double velX = 0;
    public double velY = 0;

    public double sizeX;
    public double sizeY;
    public double sizeZ;

    public double rotZ;

    public boolean visible = true;

    MeshState(String modelID, String displayName, double posX, double posY, double posZ) {
        this.meshID = modelID;
        this.displayName = displayName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        var json = Entities.get(modelID);
        this.sizeX = json.getDouble("width");
        this.sizeY = json.getDouble("width");
        this.sizeZ = json.getDouble("height");
    }

    void setPosition(double x, double y, double z, double rotZ, double velX, double velY) {
        posX = x;
        posY = y;
        posZ = z;
        this.rotZ = rotZ;
        this.velX = velX;
        this.velY = velY;
    }

    void interpolate(double dt) {
        posX += velX*dt;
        posY += velY*dt;
    }

    void render() {
        var positionTrasform = new Matrix4f()
                .translate((float) posX, (float) posY, (float) posZ)
                .translate((float) (sizeX/2), (float) (sizeY/2), (float) (sizeZ/2))
                .rotateZ((float) rotZ)
                .translate((float) (-sizeX/2), (float) (-sizeY/2), (float) (-sizeZ/2));
        if(visible) {
            MeshRenderer.render(meshID, positionTrasform);
        }
        //LineRenderer.render(new Matrix4f().translate((float) posX, (float) posY, (float) posZ), new Vector3f((float) sizeX, (float) sizeY, (float) sizeZ));
    }


    public double rayIntersect(double[] o, double[] d) {
        double tmin = Double.MIN_VALUE, tmax = Double.MAX_VALUE;

        double[] bmin = {posX, posY, posZ};
        double[] bmax = {posX+sizeX, posY+sizeY, posZ+sizeZ};

        for(int i = 0; i < 3; i++) {
            if(d[i] != 0) {
                double t1 = (bmin[i] - o[i]) / d[i];
                double t2 = (bmax[i] - o[i]) / d[i];

                double tnear = Math.min(t1, t2);
                double tfar = Math.max(t1, t2);

                tmin = Math.max(tmin, tnear);
                tmax = Math.min(tmax, tfar);

                if(tmin > tmax) {
                    return -1;
                }
            } else {
                if(o[i] < bmin[i] || o[i] > bmax[i]) return -1;
            }
        }

        if( tmin >= 0) {
            return tmin;
        } else {
            return tmax;
        }
    }

}
