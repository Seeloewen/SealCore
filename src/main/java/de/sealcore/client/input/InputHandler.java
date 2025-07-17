package de.sealcore.client.input;

import de.sealcore.client.ui.overlay.OverlayManager;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;



public class InputHandler {

    public static final int MOUSE_DOWN = 1;
    public static final int MOUSE_UP = 0;

    public static boolean camMode = false; //will be changed to false in init

    public static boolean hideMouse = false;

    private static  HashMap<Integer, Boolean> pressedKeys;

    private static long window;

    private static boolean init = false;
    public static double mouseX;
    public static double mouseY;

    private static double mouseDeltaX;
    private static double mouseDeltaY;

    public static boolean isPressed(int key) {
        if(pressedKeys.containsKey(key)) {
            return pressedKeys.get(key);
        } else {
            pressedKeys.put(key, false);
            return false;
        }
    }



    private static void keyCallback(int key, int action) {
        if(key == KeyBinds.CAMMODE && action == GLFW_PRESS) camMode = !camMode;
        if(key == KeyBinds.MOUSEMODE && action == GLFW_PRESS) changeMouseMode();
        pressedKeys.put(key, action != GLFW_RELEASE);
        if(action == GLFW_PRESS) {
            OverlayManager.handleKeyPress(key);
        }
    }

    private static void mouseButtonCallback(int button, int action) {
        OverlayManager.handleMousePress(button, action);
    }


    public static void changeMouseMode() {
        if(hideMouse) {
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        } else {
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        }
        hideMouse = !hideMouse;
    }

    public static void setMouseMode(boolean visible)
    {
        //Set the visibility of the mouse cursor to the specified value
        glfwSetInputMode(window, GLFW_CURSOR, visible ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_DISABLED);
        hideMouse = !visible;
    }




    public static void init(long window) {
        InputHandler.window = window;
        pressedKeys = new HashMap<>();
        glfwSetKeyCallback(window, (w,k,c,a,m) -> {
            keyCallback(k, a);
        });

        changeMouseMode();
        glfwSetCursorPosCallback(window, (w, x, y) -> {
            if(init && hideMouse) {
                mouseDeltaX += x - mouseX;
                mouseDeltaY += y - mouseY;
            }
            mouseX = x;
            mouseY = y;
            init = true;
        });
        glfwSetMouseButtonCallback(window, (w, b, a, m) -> {
            mouseButtonCallback(b, a);
        });

    }


    public static double useMouseDeltaX() {
        var x = mouseDeltaX;
        mouseDeltaX = 0;
        return x;
    }

    public static double useMouseDeltaY() {
        var y = mouseDeltaY;
        mouseDeltaY = 0;
        return y;
    }
}
