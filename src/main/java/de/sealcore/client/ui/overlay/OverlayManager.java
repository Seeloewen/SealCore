package de.sealcore.client.ui.overlay;

import de.sealcore.client.Client;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import org.lwjgl.glfw.GLFW;

public class OverlayManager
{


    private static boolean showInventory = false;
    private static boolean showDebugOverlay = false;


    public static void init()
    {

    }


    public static void render()
    {
        if (showDebugOverlay) DebugOverlay.render();
        if (showInventory) Client.instance.inventoryState.render();

        Client.instance.inventoryState.renderHotbar();

        Client.instance.playerState.render();
    }


    public static void handleMousePress(int button, int action)
    {
        if (showInventory)
        {
            Client.instance.inventoryState.handleMouseClick(button, action);
        }
        else
        {
            if (action == GLFW.GLFW_PRESS)
            {
                Client.instance.playerState.handleMousePress(button);
            }
        }

    }

    public static void handleKeyPress(int key)
    {
        if (key == GLFW.GLFW_KEY_E)
        {
            showInventory = !showInventory;
            if(!showInventory) Client.instance.inventoryState.selectedSlot = null;
            InputHandler.changeMouseMode();
        }
        else if (key == GLFW.GLFW_KEY_F3)
        {
            showDebugOverlay = !showDebugOverlay;
        }
        else if (key == GLFW.GLFW_KEY_1)
        {
            Client.instance.playerState.setSelectedHotbarSlot(1);
        }
        else if (key == GLFW.GLFW_KEY_2)
        {
            Client.instance.playerState.setSelectedHotbarSlot(2);
        }
        else if (key == GLFW.GLFW_KEY_3)
        {
            Client.instance.playerState.setSelectedHotbarSlot(3);
        }
    }



}
