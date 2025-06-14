package de.sealcore.client.ui.overlay;

import de.sealcore.client.Client;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.util.Color;
import org.lwjgl.glfw.GLFW;

public class OverlayManager {


    static boolean showInventory = false;






    public static void init() {

    }


    public static void render() {
        /*if(test) {

            PrimitiveRenderer.drawRectangle(new Rectangle(100, 100, 200, 200), new Color(0.7f, 0.3f, 0.1f), 0f);

            TextRenderer.drawString(200, 200, 3, "Berr Hert", 0f);
        }*/

        if(showInventory) Client.instance.inventoryState.render();
        Client.instance.inventoryState.renderHotbar();
    }



    public static void handleMousePress(int button, int action) {

    }

    public static void handleKeyPress(int key) {
        if(key == GLFW.GLFW_KEY_E) showInventory = !showInventory;
    }



}
