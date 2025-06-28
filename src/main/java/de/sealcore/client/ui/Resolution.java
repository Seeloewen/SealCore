package de.sealcore.client.ui;

import de.sealcore.client.ui.overlay.CraftingOverlay;
import de.sealcore.client.ui.overlay.RecipeOverlay;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL33;

public class Resolution {

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    public static float RATIO = (float) HEIGHT / WIDTH;

    public static float xToScreen(int x) {
        return (float)x/(WIDTH /2)-1f;
    }

    public static float yToScreen(int y) {
        return -((float)y/(HEIGHT /2)-1f);
    }

    public static int xToPixel(float x) {
        return (int)((x+1)*(WIDTH /2));
    }

    public static int yToPixel(float y) {
        return (int)((-y+1)*(HEIGHT /2));
    }


    public static void resize(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        RATIO = (float) HEIGHT / WIDTH;
        MeshRenderer.refreshProjection();
        CraftingOverlay.setLocation(WIDTH-300,20);
        GL33.glViewport(0, 0, width, height);
    }

}
