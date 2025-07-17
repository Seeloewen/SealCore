package de.sealcore.client;

import de.sealcore.client.input.CamMoveInput;
import de.sealcore.client.input.InputHandler;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;

public class Camera {

    private static final float MOVE_SPEED = 8f;
    private static final float ROT_FACTOR = 0.001f;
    private static final float PI = 3.14159265358979f;

    public int following = -1;

    private Vector3f position;
    public double angleHor;
    private double angleVert;


    public Camera() {
        position = new Vector3f(-5f, 0f, 0f);

    }

    void update(CamMoveInput input, double dt) {
        updateRotation(input.dx(), input.dy());
        if( InputHandler.camMode) {
            Vector3f movement = new Vector3f(input.getTranslation());
            movement.mul((float)dt).mul(MOVE_SPEED);
            movement.rotateZ((float)angleHor);
            position.add(movement);
        }
    }

    private void updateRotation(double dx, double dy) {
        dx *= ROT_FACTOR;
        dy *= ROT_FACTOR;
        angleVert += dy;
        angleHor -= dx;
        if(angleVert >= PI/2) angleVert = PI/2;
        if(angleVert <= -PI/2) angleVert = -PI/2;
        if(angleHor >= PI) angleHor -= 2*PI;
        if(angleHor <= -PI) angleHor += 2*PI;

    }

    public Matrix4fc getView() {
        if(InputHandler.camMode) {
            return new Matrix4f().translate(position).rotateZ((float)angleHor).rotateY((float)angleVert);
        } else {
            var e = Client.instance.gameState.loadedMeshes.get(following);
            return new Matrix4f().translate( (float)e.posX+0.3f , (float)e.posY+0.48f,  (float)e.posZ+1.8f)
                    .rotateZ((float)angleHor).rotateY((float)angleVert);
        }
    }

    public void follow(int id) {
        following = id;
    }


    public void updateTargeted() {
        if(following == -1) return;
        var player = Client.instance.gameState.getMesh(following);
        double[] origin = {player.posX+0.3f, player.posY+0.48f, player.posZ+1.8f};
        double[] direction = {
                Math.cos(angleVert) * Math.cos(angleHor),
                Math.cos(angleVert) * Math.sin(angleHor),
                Math.sin(-angleVert)
        };

        var ps = Client.instance.playerState;

        ps.targetEntity = -1;
        ps.distTargetEntity = -1;
        for(var p : Client.instance.gameState.loadedMeshes.entrySet()) {
            if(p.getKey() == following) continue;
            double t = p.getValue().rayIntersect(origin, direction);
            if(t >= 0 && (ps.targetEntity == -1 || ps.distTargetEntity > t )) {
                ps.targetEntity = p.getKey();
                ps.distTargetEntity = t;
            }
        }

        ps.distTargetBlock = -1;
        ps.distTargetFloor = -1;
        for(var c : Client.instance.gameState.loadedChunks.values()) {
            c.rayIntersectChunks(origin, direction);
        }


        /*if(targetEntity != -1) {
            Log.info(LogType.GAME, "intersect! with " + targetEntity);
        }*/
    }






}
