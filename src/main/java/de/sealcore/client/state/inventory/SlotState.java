package de.sealcore.client.state.inventory;

import de.sealcore.client.Client;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.game.entities.inventory.InventorySlotType;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.InventoryMovePacket;
import de.sealcore.networking.packets.InventorySwapPacket;
import de.sealcore.util.Color;

public class SlotState
{
    private static final int WIDTH = 75;
    private static final int HEIGHT = 75;

    private int x;
    private int y;
    private boolean alignedBot; //true if y is aligned to bottom

    public final int index;
    public final InventorySlotType type;

    String id = "";
    int amount;

    public boolean isHotbar = false;
    public int hotbarSlotIndex = -1;

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

    public void setLocation(int x, int y, boolean alignedBot)
    {
        this.x = x;
        if(alignedBot) {
            this.y = Resolution.HEIGHT-y;
        } else {
            this.y = y;
        }
        this.alignedBot = alignedBot;
    }

    public void handleMouseClick(int button, int action)
    {
        if (isMouseOver())
        {
            InventoryState inv = Client.instance.inventoryState;

            if (inv.selectedSlot == null || inv.selectedSlot == this) //If no slot is currently selected, just select this slot (if it's not empty)
            {
                if (amount != 0) inv.selectedSlot = this;
                return;
            }

            //If a slot is selected, either move or swap them (if the types match)
            if (inv.selectedSlot.type == type)
            {
                if (amount == 0)
                {
                    NetworkHandler.send(new InventoryMovePacket(inv.selectedSlot.index, index));
                }
                else
                {
                    NetworkHandler.send(new InventorySwapPacket(inv.selectedSlot.index, index));
                }
            }

            inv.selectedSlot = null;
        }
    }

    public void render()
    {
        SlotState selSlot = Client.instance.inventoryState.selectedSlot;

        if(alignedBot) {
            PrimitiveRenderer.drawRectangle(new Rectangle(x, Resolution.HEIGHT-y, x + WIDTH, Resolution.HEIGHT-y + HEIGHT), getBorderColor(), 0.01f); //Slot border
            PrimitiveRenderer.drawRectangle(new Rectangle(x + 3, Resolution.HEIGHT-y + 3, x + WIDTH - 3, Resolution.HEIGHT-y + HEIGHT - 3),getSlotColor() , 0f); //Actual slot
        } else {
            PrimitiveRenderer.drawRectangle(new Rectangle(x, y, x + WIDTH, y + HEIGHT), getBorderColor(), 0.01f); //Slot border
            PrimitiveRenderer.drawRectangle(new Rectangle(x + 3, y + 3, x + WIDTH - 3, y + HEIGHT - 3), getSlotColor(), 0f); //Actual slot
        }


        //If the slot is not empty, render the item and possibly amount
        if (!id.isEmpty() && amount > 0)
        {
            if(alignedBot) {
                TextureRenderer.drawTexture(id, new Rectangle(x + 8, Resolution.HEIGHT-y + 8, x + WIDTH - 8, Resolution.HEIGHT-y + HEIGHT - 8), -0.01f);
            } else {
                TextureRenderer.drawTexture(id, new Rectangle(x + 8, y + 8, x + WIDTH - 8, y + HEIGHT - 8), -0.01f);
            }


            if(alignedBot) {
                if (amount > 1) {
                    TextRenderer.drawString(x + WIDTH - 40, Resolution.HEIGHT-y + HEIGHT - 30, 3, String.valueOf(amount), -0.02f);
                } else {
                    TextRenderer.drawString(x + WIDTH - 40, Resolution.HEIGHT-y + HEIGHT - 30, 3, String.valueOf(amount), -0.02f);
                }
            } else {
                if (amount > 1) {
                    TextRenderer.drawString(x + WIDTH - 40, y + HEIGHT - 30, 3, String.valueOf(amount), -0.02f);
                } else {
                    TextRenderer.drawString(x + WIDTH - 40, y + HEIGHT - 30, 3, String.valueOf(amount), -0.02f);
                }
            }
        }
    }

    public Color getBorderColor()
    {
        if (Client.instance.inventoryState.selectedSlot == this)
        {
            return new Color(1f, 0.83f, 0f);
        }
        else if (Client.instance.playerState.selectedSlot == index)
        {
            return new Color(0f, 0.941f, 0.286f);
        }
        else
        {
            return new Color(0);
        }
    }

    public Color getSlotColor()
    {
        return switch(type)
        {
            case MATERIAL -> new Color(0.902f, 0.91f, 0.784f);
            case WEAPON -> new Color(0.859f, 0.549f, 0.549f);
            case UNIVERSAL -> new Color(0.882f, 1f, 0.949f);
            case AMMO -> new Color(0.757f, 0.835f, 1f);
            default -> new Color(187);
        };
    }

    public boolean isMouseOver()
    {
        if(!alignedBot) {
            return InputHandler.mouseX >= x && InputHandler.mouseX <= x + WIDTH
                    && InputHandler.mouseY >= y && InputHandler.mouseY <= y + HEIGHT;
        } else {
            return InputHandler.mouseX >= x && InputHandler.mouseX <= x + WIDTH
                    && InputHandler.mouseY >= Resolution.HEIGHT-y && InputHandler.mouseY <= Resolution.HEIGHT-y + HEIGHT;
        }
    }

    public void setAsHotbar(int hIndex)
    {
        isHotbar = true;
        hotbarSlotIndex = hIndex;
    }
}
