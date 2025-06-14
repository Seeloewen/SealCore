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


    static boolean showInventory = false;
    public static String debugDir ="";
    public static double debugX = 0;
    public static double debugY = 0;




    public static void init() {

    }


    public static void render() {

        TextRenderer.drawString(10, 10, 3, debugDir, 0);
        TextRenderer.drawString(10, 60, 3, String.valueOf(debugX), 0);
        TextRenderer.drawString(10, 110, 3, String.valueOf(debugY), 0);

        /*if(test) {

            PrimitiveRenderer.drawRectangle(new Rectangle(100, 100, 200, 200), new Color(0.7f, 0.3f, 0.1f), 0f);

            TextRenderer.drawString(200, 200, 3, "Berr Hert", 0f);
        }*/

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
    }



}
