package de.sealcore.client.input;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;



public class InputHandler {

    public static boolean modeMouseMove = true; //will be changed to false in init

    private static  HashMap<Integer, Boolean> pressedKeys;

    private static long window;

    private static boolean init = false;
    private static double mouseX;
    private static double mouseY;

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
        if(key == GLFW_KEY_C && action == GLFW_PRESS) changeMouseMode();
        pressedKeys.put(key, action != GLFW_RELEASE);
    }


    private static void changeMouseMode() {
        if(modeMouseMove) {
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        } else {
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        }
        modeMouseMove = !modeMouseMove;
    }






    public static void init(long window) {
        InputHandler.window = window;
        pressedKeys = new HashMap<>();
        glfwSetKeyCallback(window, (w,k,c,a,m) -> {
            keyCallback(k, a);
        });

        changeMouseMode();
        glfwSetCursorPosCallback(window, (w, x, y) -> {
            if(init) {
                mouseDeltaX += x - mouseX;
                mouseDeltaY += y - mouseY;
            }
            mouseX = x;
            mouseY = y;
            init = true;
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
