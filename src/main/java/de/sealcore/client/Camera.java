package de.sealcore.client;

import de.sealcore.client.input.CamMoveInput;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;

public class Camera {

    private static final float MOVE_SPEED = 0.3f;

    private Matrix4f camera;

    public Camera() {

        camera = new Matrix4f();
        camera.translate(-5f,0f,0f);

    }

    void update(CamMoveInput input, double dt) {
        Vector3f movement = new Vector3f(input.getTranslation());
        movement.mul((float)dt).mul(MOVE_SPEED);
        camera.translate(movement);
    }

    public Matrix4fc getView() {
        return camera;
    }

}
