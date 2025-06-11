package de.sealcore.game.items;

import de.sealcore.game.InteractableObject;
import de.sealcore.util.json.JsonObject;
import net.bytebuddy.utility.nullability.MaybeNull;

public abstract class Item extends InteractableObject
{
    public ItemInfo info;
    public String tags; //JSON Object in string format

    protected Item(String id, String name, ItemType type, int maxAmount)
    {
        info = new ItemInfo(id, name, type, maxAmount);
    }

    public void writeTag(String tag, String value)
    {
        //Converts tags string to JSON object, writes value, and converts back
        JsonObject t = JsonObject.fromString(tags);

        t.addString(tag, value);

        tags = t.toString();
    }

    @MaybeNull
    public String readTag(String tag)
    {
        //Converts tags string to JSON object and reads the requested tag
        JsonObject t = JsonObject.fromString(tags);

        return t.getString(tag); //Warning: Maybe null
    }
}
