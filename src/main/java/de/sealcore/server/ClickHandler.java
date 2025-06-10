package de.sealcore.server;

import de.sealcore.game.InteractableObject;
import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.game.items.ItemRegister;

public class ClickHandler
{
    public static void handleRightClick(int playerId, InteractableObject facedObject, InventorySlot selectedSlot)
    {
        int result = -1;

        if(facedObject != null)
        {
            result = facedObject.onRightClick(playerId); //Store as result whether the object has an action
        }

        if(result == -1) //If the object did not have any right click action, handle the item right click action
        {
            result = ItemRegister.getItem(selectedSlot.id).onRightClick(playerId);
        }
    }

    public static void handleLeftClick(int playerId, InteractableObject facedObject, InventorySlot selectedSlot)
    {
        int result = -1;

        if(facedObject != null)
        {
            result = facedObject.onLeftClick(playerId); //Store as result whether the object has an action
        }

        if(result == -1) //If the object did not have any left click action, handle the item left click action
        {
            result = ItemRegister.getItem(selectedSlot.id).onLeftClick(playerId);
        }
    }
}
