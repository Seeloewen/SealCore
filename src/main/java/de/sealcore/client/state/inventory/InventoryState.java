package de.sealcore.client.state.inventory;

import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.game.entities.inventory.InventorySlotType;
import de.sealcore.util.Color;

import java.util.Objects;

public class InventoryState
{
    private final int WIDTH = 380;
    private final int HEIGHT = 320;

    public SlotState selectedSlot;

    private int x = 20;
    private int y = 20;

    private SlotState[] slots;
    private final int w;
    private final int a;
    private final int m;
    private final int u;

    public InventoryState(int w, int a, int m, int u)
    {
        this.w = w;
        this.a = a;
        this.m = m;
        this.u = u;

        createSlots(w, a, m, u);
        positionSlots();
        initHotbar();
    }

    public void createSlots(int w, int a, int m, int u)
    {
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
    }

    public String getSelectedItem(int i) {
        return slots[i].id;
    }

    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void updateSlot(int index, String id, int amount)
    {
        getSlot(index).update(id, amount);
    }

    public void positionSlots()
    {
        int offset = 0;

        //hotbar
        for (int i = 0; i < 3; i++)
        {
            if (i != 0) offset = offset + 85;

            if (i != 2) getSlotN(InventorySlotType.WEAPON, i).setLocation(35 + offset, Resolution.HEIGHT - 110, true);
            else getSlotN(InventorySlotType.MATERIAL, 0).setLocation(35 + offset, Resolution.HEIGHT - 110, true);
        }

        //Material slots
        offset = 0;
        for (int i = 1; i < m; i++)
        {
            if (i != 1) offset = offset + 85;
            getSlotN(InventorySlotType.MATERIAL, i).setLocation(35 + offset, y + 50, false);
        }

        //Ammo slots
        offset = 0;
        for (int i = 0; i < a; i++)
        {
            if (i != 0) offset = offset + 85;
            getSlotN(InventorySlotType.AMMO, i).setLocation(35 + offset, y + 135, false);
        }

        //Weapon slot
        getSlotN(InventorySlotType.WEAPON, 2).setLocation(35, y + 220, false);
    }

    public void initHotbar()
    {
        getSlotN(InventorySlotType.WEAPON, 0).setAsHotbar(1);
        getSlotN(InventorySlotType.WEAPON, 1).setAsHotbar(2);
        getSlotN(InventorySlotType.MATERIAL, 0).setAsHotbar(3);
    }

    public void handleMouseClick(int button, int action)
    {
        if (action == InputHandler.MOUSE_DOWN) //Mouse down
        {
            for (SlotState s : slots)
            {
                s.handleMouseClick(button, action);
            }
        }
    }

    public void render()
    {
        PrimitiveRenderer.drawRectangle(new Rectangle(x, y, x + WIDTH, y + HEIGHT), new Color(70, 70, 70), 0.02f); //Inv Background
        for (SlotState s : slots) if(!s.isHotbar) s.render(); //Slots (excluding hotbar)
        TextRenderer.drawString(x + 20, y + 13, 3, "Inventory", 0.01f);
    }

    public void renderHotbar()
    {
        PrimitiveRenderer.drawRectangle(new Rectangle(25, Resolution.HEIGHT - 120, 290, Resolution.HEIGHT - 25), new Color(0.5f, 0.5f, 0.5f), 0.02f); //Hotbar background
        for (SlotState s : slots) if (s.isHotbar) s.render(); //Hotbar slots
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

    public int hotbarToSlot(int i)
    {
        for(SlotState s : slots)
        {
            if(s.hotbarSlotIndex == i) return s.index;
        }

        return -1;
    }

    private SlotState getSlot(int i)
    {
        //Get the slot with the specified id
        for (SlotState s : slots)
        {
            if (i == s.index) return s;
        }

        return null;
    }

    public int getAvailableAmount(String id)
    {
        int a = 0;
        for(SlotState s : slots)
        {
            if(s.id.equals(id) && s.amount > 0) a += s.amount;
        }

        return a;
    }
}
