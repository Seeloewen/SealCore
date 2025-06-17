package de.sealcore.game.items;

import de.sealcore.game.InteractableObject;
import de.sealcore.util.json.JsonObject;

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

    public void writeTag(String tag, int value)
    {
        writeTag(tag, String.valueOf(value));
    }

    public void writeTag(String tag, double value)
    {
        writeTag(tag, String.valueOf(value));
    }

    public void writeTag(String tag, boolean value)
    {
        writeTag(tag, String.valueOf(value));
    }

    public String getStringTag(String tag)
    {
        //Converts tags string to JSON object and reads the requested tag
        JsonObject t = JsonObject.fromString(tags);

        return t.getString(tag); //Warning: Maybe null
    }

    public int getIntTag(String tag)
    {
        //Converts tags string to JSON object and reads the requested tag
        JsonObject t = JsonObject.fromString(tags);

        return t.getInt(tag); //Warning: Maybe null
    }

    public double getDoubleTag(String tag)
    {
        //Converts tags string to JSON object and reads the requested tag
        JsonObject t = JsonObject.fromString(tags);

        return t.getDouble(tag); //Warning: Maybe null
    }

    public boolean getBoolTag(String tag)
    {
        //Converts tags string to JSON object and reads the requested tag
        JsonObject t = JsonObject.fromString(tags);

        return t.getBool(tag); //Warning: Maybe null
    }
}
