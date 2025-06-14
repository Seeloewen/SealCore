package de.sealcore.client.state.inventory;

import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.game.entities.inventory.InventorySlotType;
import de.sealcore.game.items.ItemType;
import de.sealcore.util.Color;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class SlotState
{
    private static final int WIDTH = 75;
    private static final int HEIGHT = 75;

    private final int index;
    public final InventorySlotType type;

    private int x;
    private int y;

    SlotState(int index, InventorySlotType type)
    {
        this.index = index;
        this.type = type;
    }

    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void handleMouseClick(int button, int action)
    {
        if(InputHandler.mouseX >= x && InputHandler.mouseX <= x + WIDTH
            && InputHandler.mouseY >= y && InputHandler.mouseY <= y + HEIGHT)
        {
            Log.info(LogType.RENDERING, "Clicked slot " + index);
        }
    }

    public void render()
    {
        PrimitiveRenderer.drawRectangle(new Rectangle(x, y, x + WIDTH, y + HEIGHT), new Color(0), 0.01f); //Slot border
        PrimitiveRenderer.drawRectangle(new Rectangle(x + 3, y + 3, x + WIDTH - 3, y + HEIGHT - 3), new Color(187), 0f); //Actual slot
    }
}
