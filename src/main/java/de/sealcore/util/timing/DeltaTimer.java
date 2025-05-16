package de.sealcore.util.timing;

import org.lwjgl.glfw.*;

public class DeltaTimer {

    private static double timeStart;
    private static double timeLast;



    public static void start() {
        timeStart = GLFW.glfwGetTime();
        timeLast = timeStart;
    }

    public static double getDeltaTime() {
        double timeNew = GLFW.glfwGetTime();
        double diff = timeNew - timeLast;
        timeLast = timeNew;
        return diff;
    }

    public static double getCurrentTime() {
        return GLFW.glfwGetTime()-timeStart;
    }


}
