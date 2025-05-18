package de.sealcore.client.input;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;

public record CamMoveInput(
        boolean forward, boolean back, boolean left, boolean right, boolean up, boolean down
) {

    public static CamMoveInput generate() {
        return new CamMoveInput(
                InputHandler.isPressed(KeyBinds.MOVE_FORWARD),
                InputHandler.isPressed(KeyBinds.MOVE_BACK),
                InputHandler.isPressed(KeyBinds.MOVE_LEFT),
                InputHandler.isPressed(KeyBinds.MOVE_RIGHT),
                InputHandler.isPressed(KeyBinds.MOVE_UP),
                InputHandler.isPressed(KeyBinds.MOVE_BACK)
        );
    }

    public Matrix4fc calc() {
        Matrix4f m = new Matrix4f();
        if(forward) m.translate(1f,0f,0f);
        if(back) m.translate(-1f,0f,0f);
        if(left) m.translate(0f,1f,0f);
        if(right) m.translate(0f,-1f,0f);
        if(up) m.translate(0f,0f,1f);
        if(down) m.translate(0f,0f,-1f);
        return m;
    }


}
