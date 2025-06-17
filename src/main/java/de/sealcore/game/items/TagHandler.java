package de.sealcore.game.items;

import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.util.json.JsonObject;

public class TagHandler
{
    public static void writeTag(InventorySlot slot, String tag, String value)
    {
        //Converts tags string to JSON object, writes value, and converts back
        JsonObject t = JsonObject.fromString(slot.tag);
        if (t == null) t = JsonObject.fromScratch(); //If no tag object is available, create one

        t.addString(tag, value);

        slot.tag = t.toString();
    }

    public static void writeTag(Item item, String tag, String value)
    {
        //Converts tags string to JSON object, writes value, and converts back
        JsonObject t = JsonObject.fromString(item.tags);
        if (t == null) t = JsonObject.fromScratch(); //If no tag object is available, create one

        t.addString(tag, value);

        item.tags = t.toString();
    }

    public static void  writeTag(InventorySlot slot, String tag, int value)
    {
        writeTag(slot, tag, String.valueOf(value));
    }

    public static void writeTag(Item item, String tag, int value)
    {
        writeTag(item, tag, String.valueOf(value));
    }

    public static void writeTag(InventorySlot slot, String tag, double value)
    {
        writeTag(slot, tag, String.valueOf(value));
    }

    public static void writeTag(Item item, String tag, double value)
    {
        writeTag(item, tag, String.valueOf(value));
    }

    public static void writeTag(InventorySlot slot, String tag, boolean value)
    {
        writeTag(slot, tag, String.valueOf(value));
    }

    public static void writeTag(Item item, String tag, boolean value)
    {
        writeTag(item, tag, String.valueOf(value));
    }

    public static String getStringTag(String tagObject, String tag)
    {
        //Converts tags string to JSON object and reads the requested tag
        JsonObject t = JsonObject.fromString(tagObject);

        return t.getString(tag); //Warning: Maybe null
    }

    public static int getIntTag(String tagObject, String tag)
    {
        //Converts tags string to JSON object and reads the requested tag
        JsonObject t = JsonObject.fromString(tagObject);

        return t.getInt(tag); //Warning: Maybe null
    }

    public static double getDoubleTag(String tagObject, String tag)
    {
        //Converts tags string to JSON object and reads the requested tag
        JsonObject t = JsonObject.fromString(tagObject);

        return t.getDouble(tag); //Warning: Maybe null
    }

    public static boolean getBoolTag(String tagObject, String tag)
    {
        //Converts tags string to JSON object and reads the requested tag
        JsonObject t = JsonObject.fromString(tagObject);

        return t.getBool(tag); //Warning: Maybe null
    }
}
