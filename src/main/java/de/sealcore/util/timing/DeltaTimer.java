package de.sealcore.util.timing;

import de.sealcore.client.ui.overlay.DebugOverlay;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import org.lwjgl.glfw.*;


public class DeltaTimer {

    private static double timeStart;
    private static double timeLast;

    private static double lastPrint;
    private static double min;
    private static double sum;
    private static int c;
    private static double max;

    public static void start() {
        timeStart = GLFW.glfwGetTime();
        timeLast = timeStart;
        lastPrint = timeStart;
    }

    public static double getDeltaTime() {
        double timeNew = GLFW.glfwGetTime();
        double diff = timeNew - timeLast;
        timeLast = timeNew;
        if(diff < min) min = diff;
        if(diff > max) max = diff;
        sum += diff;
        c++; //nice
        if(timeNew >= lastPrint + 1) {
            printStats();
        }
        return diff;
    }

    private static void printStats() {
        Log.info(LogType.PERFORMANCE, String.format("dt last second=(%.3f|%.3f|%.3f)", min, sum/c, max));
        min = Float.MAX_VALUE;
        max = Float.MIN_VALUE;
        sum = 0;
        c = 0;
        lastPrint = timeLast;
        DebugOverlay.fps = sum/c;
    }

    public static double getCurrentTime() {
        return GLFW.glfwGetTime()-timeStart;
    }

    public static boolean blockToTarget(double targetDeltaTime) {
        double target = timeLast + targetDeltaTime;

        if(target < GLFW.glfwGetTime()) {
            timeLast = GLFW.glfwGetTime();
            return false;
        }

        timeLast = target;

        while(true) {
            double t = GLFW.glfwGetTime();
            if(target <= t) break;
        }
        return true;
    }

}
