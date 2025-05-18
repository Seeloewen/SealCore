package de.sealcore.client.input;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;



public class InputHandler {


    private static final int ROTATE_RIGHT = GLFW_KEY_E;
    private static final int ROTATE_LEFT = GLFW_KEY_Q;
    private static final int MOVE_FORWARD = GLFW_KEY_W;
    private static final int MOVE_BACK = GLFW_KEY_S;
    private static final int MOVE_RIGHT = GLFW_KEY_D;
    private static final int MOVE_LEFT = GLFW_KEY_A;
    private static final int MOVE_UP = GLFW_KEY_SPACE;
    private static final int MOVE_DOWN = GLFW_KEY_LEFT_SHIFT;


    private static  HashMap<Integer, Boolean> pressedKeys;


    public static boolean isPressed(int key) {
        if(pressedKeys.containsKey(key)) {
            return pressedKeys.get(key);
        } else {
            pressedKeys.put(key, false);
            return false;
        }
    }



    private static void keyCallback(int key, int action) {
        pressedKeys.put(key, action != GLFW_RELEASE);
    }









    public static void init(long window) {
        pressedKeys = new HashMap<>();
        glfwSetKeyCallback(window, (w,k,c,a,m) -> {
            keyCallback(k, a);
        });


    }




}
