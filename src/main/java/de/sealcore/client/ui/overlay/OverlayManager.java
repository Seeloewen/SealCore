package de.sealcore.client.ui.overlay;

import de.sealcore.client.Client;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.util.Color;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import org.lwjgl.glfw.GLFW;

public class OverlayManager {


    private static boolean showInventory = false;
    private static boolean showDebugOverlay = false;




    public static void init() {
        initTextures();
    }


    public static void render() {
        if(showDebugOverlay) DebugOverlay.render();
        if(showInventory) Client.instance.inventoryState.render();
        Client.instance.inventoryState.renderHotbar();

        Client.instance.playerState.render();
    }



    public static void handleMousePress(int button, int action) {
        if(showInventory) {
            Client.instance.inventoryState.handleMouseClick(button, action);
        } else {
            if(action == GLFW.GLFW_PRESS) {
                Client.instance.playerState.handleMousePress(button);
            }
        }

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

    public static void initTextures()
    {
        //Load all the textures required for the game
        try
        {
            TextureRenderer.loadTexture("grass_block", "textures/Grass_Block.png");
        }
        catch(Exception e)
        {
            Log.error(LogType.RENDERING, "Could not load textures: " + e.getMessage());
        }
    }

}
