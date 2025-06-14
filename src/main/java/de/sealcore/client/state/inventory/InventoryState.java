package de.sealcore.client.state.inventory;

import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.game.entities.inventory.InventorySlotType;
import de.sealcore.util.Color;

public class InventoryState
{
    private final int WIDTH = 445;
    private final int HEIGHT = 230;

    private int x = 20;
    private int y = 20;

    private SlotState[] slots;
    private int w;
    private int a;
    private int m;
    private int u;

    public InventoryState(int w, int a, int m, int u)
    {
        this.w = w;
        this.a = a;
        this.m = m;
        this.u = u;

        slots = new SlotState[w + a + m + u];

        for (int i = 0; i < slots.length; i++)
        {
            //Create the slots based on the type
            if (u > 0)
            {
                slots[i] = new SlotState(i, InventorySlotType.UNIVERSAL);
                u--;
            }
            else if (m > 0)
            {
                slots[i] = new SlotState(i, InventorySlotType.MATERIAL);
                m--;
            }
            else if (w > 0)
            {
                slots[i] = new SlotState(i, InventorySlotType.WEAPON);
                w--;
            }
            else if (a > 0)
            {
                slots[i] = new SlotState(i, InventorySlotType.AMMO);
                a--;
            }
        }

        positionSlots();
    }

    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void positionSlots()
    {
        int offset = 0;

        //Weapon slots (hotbar)
        for (int i = 0; i < w; i++)
        {
            if (i != 0) offset = offset + 85;
            getSlotN(InventorySlotType.WEAPON, i).setLocation(35 + offset, Resolution.HEIGHT - 110);
        }

        //Material slots
        offset = 0;
        for (int i = 0; i < m; i++)
        {
            if (i != 0) offset = offset + 85;
            getSlotN(InventorySlotType.MATERIAL, i).setLocation(35 + offset, y + 50);
        }

        //Ammo slots
        offset = 0;
        for (int i = 0; i < a; i++)
        {
            if (i != 0) offset = offset + 85;
            getSlotN(InventorySlotType.AMMO, i).setLocation(35 + offset, y + 135);
        }
    }

    public void handleMouseClick(int button, int action)
    {
        if(action == InputHandler.MOUSE_DOWN) //Mouse down
        {
            for(SlotState s : slots)
            {
                s.handleMouseClick(button, action);
            }
        }
    }

    public void render()
    {
        PrimitiveRenderer.drawRectangle(new Rectangle(x, y, x + WIDTH, y+ HEIGHT), new Color(70, 70, 70), 0.02f); //Inv Background
        for(SlotState s : slots) if(s.type != InventorySlotType.WEAPON) s.render(); //Slots
        TextRenderer.drawString(x + 20, y + 13, 3, "Inventory", 0.01f);
    }

    public void renderHotbar()
    {
        PrimitiveRenderer.drawRectangle(new Rectangle(25, Resolution.HEIGHT - 120, 290, Resolution.HEIGHT - 25), new Color(69, 69, 42), 0.02f); //Hotbar background
        for (SlotState s : slots) if (s.type == InventorySlotType.WEAPON) s.render(); //Slots
    }

    private SlotState getSlotN(InventorySlotType type, int n)
    {
        //Gets the n'th slot of this type (based on index)

        int c = 0;

        for (SlotState s : slots)
        {
            if (s.type == type)
            {
                if (c == n) return s;
                c++; //Nice
            }
        }

        return null;
    }
}
