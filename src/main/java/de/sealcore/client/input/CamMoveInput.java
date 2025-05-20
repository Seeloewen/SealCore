package de.sealcore.client.input;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

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
                InputHandler.isPressed(KeyBinds.MOVE_DOWN)
        );
    }

    public Vector3fc getTranslation() {
        Vector3f v = new Vector3f();
        if(forward) v.add(1f,0f,0f);
        if(back) v.add(-1f,0f,0f);
        if(left) v.add(0f,1f,0f);
        if(right) v.add(0f,-1f,0f);
        if(up) v.add(0f,0f,1f);
        if(down) v.add(0f,0f,-1f);
        return v;
    }


}
