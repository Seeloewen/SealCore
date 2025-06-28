package de.sealcore.client.ui.overlay;

import de.sealcore.Main;
import de.sealcore.client.ui.rendering.text.TextRenderer;

public class DebugOverlay
{
    public static double fps = 0;
    public static double posX = 0;
    public static double posY = 0;
    public static double direction = 0;

    public static void render()
    {
        TextRenderer.drawString(10, 10, 3, "SealCore Version " + Main.VERSION + " (" + Main.BUILDDATE + ")", 0.05f);
        TextRenderer.drawString(10, 40, 3, "FPS: " + Math.round(fps), 0.05f);
        TextRenderer.drawString(10, 70, 3, "PosX: " + posX + " (" + Math.floor(posX) + ")", 0.05f);
        TextRenderer.drawString(10, 100, 3, "PosY: " + posY+ " (" + Math.floor(posY) + ")", 0.05f);
        TextRenderer.drawString(10, 130, 3, "DirX: " + direction, 0.05f);
    }
}
