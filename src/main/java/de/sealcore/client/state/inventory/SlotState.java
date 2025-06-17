package de.sealcore.client.state.inventory;

import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.game.entities.inventory.InventorySlotType;
import de.sealcore.util.Color;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class SlotState
{
    private static final int WIDTH = 75;
    private static final int HEIGHT = 75;

    private int x;
    private int y;

    public final int index;
    public final InventorySlotType type;

    private String id = "";
    private int amount;

    public boolean isHotbar = false;

    SlotState(int index, InventorySlotType type)
    {
        this.index = index;
        this.type = type;
    }

    public void update(String id, int amount)
    {
        this.amount = amount;
        this.id = id;
    }

    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void handleMouseClick(int button, int action)
    {
        if (isMouseOver())
        {
            Log.info(LogType.RENDERING, "Clicked slot " + index);
        }
    }

    public void render()
    {
        PrimitiveRenderer.drawRectangle(new Rectangle(x, y, x + WIDTH, y + HEIGHT), new Color(0), 0.01f); //Slot border
        PrimitiveRenderer.drawRectangle(new Rectangle(x + 3, y + 3, x + WIDTH - 3, y + HEIGHT - 3), new Color(187), 0f); //Actual slot

        if (!id.isEmpty())
        {
            TextureRenderer.drawTexture(id, new Rectangle(x + 8, y + 8, x + WIDTH - 8, y + HEIGHT - 8), -0.01f);

            if (amount > 1) TextRenderer.drawString(x + WIDTH - 10, y + HEIGHT - 10, 3, String.valueOf(amount), -0.02f);
        }
    }

    public boolean isMouseOver()
    {
        return InputHandler.mouseX >= x && InputHandler.mouseX <= x + WIDTH
                && InputHandler.mouseY >= y && InputHandler.mouseY <= y + HEIGHT;
    }
}
