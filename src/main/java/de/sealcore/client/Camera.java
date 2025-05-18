package de.sealcore.client;

import de.sealcore.client.input.CamMoveInput;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;

public class Camera {

    private static final float MOVE_SPEED = 0.3f;

    private Matrix4f view;

    public Camera() {
        view = new Matrix4f().lookAt(
                new Vector3f(5, 3, 5),
                new Vector3f(-0.5f,1,0),
                new Vector3f(0, 1, 0)
        );
    }

    void update(CamMoveInput input, double dt) {
        Matrix4f movement = new Matrix4f(input.calc());
        movement.scale((float)dt).scale(MOVE_SPEED);
        //view.mul(movement);
    }

    public Matrix4fc getView() {
        return view;
    }

}
