package de.sealcore.client.input;

import org.joml.Vector3f;
import org.joml.Vector3fc;

public record CamMoveInput(
        boolean forward, boolean back, boolean left, boolean right, boolean up, boolean down, double dx, double dy
) {

    public static CamMoveInput generate() {
        return new CamMoveInput(
                InputHandler.isPressed(KeyBinds.MOVE_FORWARD),
                InputHandler.isPressed(KeyBinds.MOVE_BACK),
                InputHandler.isPressed(KeyBinds.CAM_MOVE_LEFT),
                InputHandler.isPressed(KeyBinds.CAM_MOVE_RIGHT),
                InputHandler.isPressed(KeyBinds.MOVE_UP),
                InputHandler.isPressed(KeyBinds.MOVE_DOWN),
                InputHandler.useMouseDeltaX(),
                InputHandler.useMouseDeltaY()
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
