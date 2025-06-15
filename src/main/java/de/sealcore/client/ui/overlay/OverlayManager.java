package de.sealcore.client.ui.overlay;

import de.sealcore.client.Client;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.util.Color;
import org.lwjgl.glfw.GLFW;

public class OverlayManager {


    private static boolean showInventory = false;
    private static boolean showDebugOverlay = false;




    public static void init() {

    }


    public static void render() {
        if(showDebugOverlay) DebugOverlay.render();
        if(showInventory) Client.instance.inventoryState.render();
        Client.instance.inventoryState.renderHotbar();
    }



    public static void handleMousePress(int button, int action) {
        Client.instance.inventoryState.handleMouseClick(button, action);
    }

    public static void handleKeyPress(int key) {
        if(key == GLFW.GLFW_KEY_E)
        {
            showInventory = !showInventory;
            InputHandler.showMouse = !InputHandler.showMouse;
            InputHandler.changeMouseMode();
        }
        else if(key == GLFW.GLFW_KEY_F3)
        {
            showDebugOverlay = !showDebugOverlay;
        }
    }



}
