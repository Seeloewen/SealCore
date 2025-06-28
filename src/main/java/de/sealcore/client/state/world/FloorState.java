package de.sealcore.client.state.world;

import de.sealcore.client.Client;
import de.sealcore.client.model.loading.MeshLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import org.joml.Matrix4f;

public class FloorState {

    public String type;
    private int x;
    private int y;
    private String meshID;
    private Matrix4f pos;


    FloorState(String id, int globalX, int globalY) {
        type = id;
        x = globalX;
        y = globalY;
        meshID = id;
        pos = new Matrix4f().translate(x, y, -1);
    }

    void render() {
        MeshRenderer.render(meshID, pos);
    }



    public void rayIntersect(double[] o, double[] d) {
        double tmin = Double.MIN_VALUE, tmax = Double.MAX_VALUE;

        double[] bmin = {x, y, -0.2};
        double[] bmax = {x+1, y+1, 0};

        for(int i = 0; i < 3; i++) {
            if(d[i] != 0) {
                double t1 = (bmin[i] - o[i]) / d[i];
                double t2 = (bmax[i] - o[i]) / d[i];

                double tnear = Math.min(t1, t2);
                double tfar = Math.max(t1, t2);

                tmin = Math.max(tmin, tnear);
                tmax = Math.min(tmax, tfar);

                if(tmin > tmax) {
                    return;
                }
            } else {
                if(o[i] < bmin[i] || o[i] > bmax[i]) return;
            }
        }

        if( tmin >= 0) {
            var ps = Client.instance.playerState;
            if(tmin < ps.distTargetFloor || ps.distTargetFloor < 0) {
                ps.targetFloorX = x;
                ps.targetFloorY = y;
                ps.distTargetFloor = tmin;
            }
        } else if(tmax >= 0){
            var ps = Client.instance.playerState;
            if(tmax < ps.distTargetFloor || ps.distTargetFloor < 0) {
                ps.targetFloorX = x;
                ps.targetFloorY = y;
                ps.distTargetFloor = tmax;
            }
        }
    }





}
