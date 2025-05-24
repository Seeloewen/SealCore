package de.sealcore.client;

import de.sealcore.client.input.CamMoveInput;
import de.sealcore.client.input.InputHandler;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;

public class Camera {

    private static final float MOVE_SPEED = 8f;
    private static final float ROT_FACTOR = 0.003f;
    private static final float PI = 3.14159265358979f;

    private Matrix4f camera;
    private Vector3f position;
    private double angleHor;
    private double angleVert;

    public Camera() {
        position = new Vector3f(-5f, 0f, 0f);

    }

    void update(CamMoveInput input, double dt) {
        if( InputHandler.modeMouseMove) updateRotation(input.dx(), input.dy());
        Vector3f movement = new Vector3f(input.getTranslation());
        movement.mul((float)dt).mul(MOVE_SPEED);
        movement.rotateZ((float)angleHor);
        position.add(movement);
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
        return new Matrix4f().translate(position).rotateZ((float)angleHor).rotateY((float)angleVert);
    }

}
