package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import com.formdev.flatlaf.json.Json;
import de.sealcore.client.Client;
import de.sealcore.client.ui.overlay.CraftingOverlay;
import de.sealcore.game.crafting.RecipePart;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class RecipeInitPacket extends Packet
{
    private String recipeId;
    private String itemId;
    private RecipePart[] ingredients;

    public RecipeInitPacket(String recipeId, String itemId, RecipePart[] ingredients)
    {
        super(PacketType.RECIPEINIT);

        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.itemId = itemId;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);
        JsonArray ingObjects = args.getArray("ingredients");

        RecipePart[] ingredients = new RecipePart[ingObjects.getSize()];
        for(int i = 0; i < ingObjects.getSize(); i++)
        {
            JsonObject obj = ((JsonObject) ingObjects.get(i));
            String s = obj.getString("id");
            int j = obj.getInt("amount");
            ingredients[i] = new RecipePart(s, j);
        }

        String recipeId = args.getString("recipeId");
        String itemId = args.getString("itemId");

        return new RecipeInitPacket(recipeId, itemId, ingredients);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        JsonArray ingObjects = JsonArray.fromScratch();

        for (RecipePart s : ingredients)
        {
            //Construct object from recipepart
            JsonObject ingObj = JsonObject.fromScratch();
            ingObj.addString("id", s.id());
            ingObj.addInt("amount", s.amount());

            ingObjects.addObject(ingObj);
        }

        args.addString("recipeId", recipeId);
        args.addString("itemId", itemId);
        args.addArray("ingredients", ingObjects);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        CraftingOverlay.addRecipe(recipeId, itemId, ingredients);
    }
}
