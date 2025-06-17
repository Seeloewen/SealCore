package de.sealcore.game.items;

import de.sealcore.game.InteractableObject;
import de.sealcore.util.json.JsonObject;

public abstract class Item extends InteractableObject
{
    public ItemInfo info;
    public String tags; //JSON Object in string format

    protected Item(String id, String name, ItemType type, int maxAmount, double cooldown)
    {
        info = new ItemInfo(id, name, type, maxAmount, cooldown);
        JsonObject t = JsonObject.fromScratch();
        tags = t.toString();
    }


}
