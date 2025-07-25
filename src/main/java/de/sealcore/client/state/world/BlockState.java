package de.sealcore.client.state.world;

import de.sealcore.client.Client;
import de.sealcore.client.model.loading.MeshLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.model.mesh.MeshSide;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import org.joml.Matrix4f;

public class BlockState {

    public String type;
    private int x;
    private int y;
    private Matrix4f pos;



    public BlockState(String id, int globalX, int globalY) {
        type = id;
        x = globalX;
        y = globalY;
        pos = new Matrix4f().translate(x, y, 0);
    }

    void render(int lodBlock) {
        if(type.equals("b:oak_tree") || type.equals("b:small_rock")) {
            MeshRenderer.render(String.format("%s:%d", type, lodBlock), pos);
        } else {
            MeshRenderer.render(type, pos);
        }
    }


    public void rayIntersect(double[] o, double[] d) {
        double tmin = Double.MIN_VALUE, tmax = Double.MAX_VALUE;

        double[] bmin = {x, y, 0};
        double[] bmax = {x+1, y+1, 3};

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
            if(tmin < ps.distTargetBlock || ps.distTargetBlock < 0) {
                ps.targetBlockX = x;
                ps.targetBlockY = y;
                ps.distTargetBlock = tmin;
            }
        } else if(tmax >= 0){
            var ps = Client.instance.playerState;
            if(tmax < ps.distTargetBlock || ps.distTargetBlock < 0) {
                ps.targetBlockX = x;
                ps.targetBlockY = y;
                ps.distTargetBlock = tmax;
            }
        }
    }

}
