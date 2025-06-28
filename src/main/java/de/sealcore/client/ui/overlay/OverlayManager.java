package de.sealcore.client.ui.overlay;

import de.sealcore.client.Client;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.state.inventory.InventoryState;
import de.sealcore.client.state.inventory.SlotState;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.game.crafting.CraftingHandler;
import de.sealcore.game.entities.inventory.InventorySlotType;
import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemRegister;
import de.sealcore.game.items.ItemType;
import de.sealcore.game.items.TagHandler;
import de.sealcore.game.items.weapons.Weapon;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.ReloadPacket;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import java.util.Random;

public class OverlayManager
{


    private static boolean showInventory = false;
    private static boolean showDebugOverlay = false;


    public static void init()
    {
        CraftingOverlay.init();
    }


    public static void render()
    {
        if (showDebugOverlay) DebugOverlay.render();
        if (showInventory)
        {
            Client.instance.inventoryState.render();
            CraftingOverlay.render();
        }

        Client.instance.inventoryState.renderHotbar();
        Client.instance.playerState.render();

        //Check if the player is holding a weapon, render the ammo amount
        SlotState selectedSlot = Client.instance.inventoryState.getSlot(Client.instance.playerState.selectedSlot);
        if(selectedSlot.type == InventorySlotType.WEAPON && selectedSlot.amount > 0)
        {
            //We can assume that the item as weapon
            Weapon w = (Weapon)ItemRegister.getItem(selectedSlot.id);
            if(w.info.type() == ItemType.WEAPON_RANGED) TextRenderer.drawString(Resolution.WIDTH - 95, Resolution.HEIGHT - 30, 3,
                    TagHandler.getIntTag(selectedSlot.tag, "ammoAmount") + "/" + w.weaponInfo.magSize(), 0f);
        }
    }


    public static void handleMousePress(int button, int action)
    {
        if (showInventory)
        {
            Client.instance.inventoryState.handleMouseClick(button, action);
            CraftingOverlay.handleMouseClick(button, action);
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
        else if(key == GLFW.GLFW_KEY_R)
        {
            NetworkHandler.send(new ReloadPacket(Client.instance.playerState.selectedSlot));
        }
    }



}
