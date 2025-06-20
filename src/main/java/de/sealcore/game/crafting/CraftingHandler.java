package de.sealcore.game.crafting;

import de.sealcore.game.entities.general.Player;
import de.sealcore.server.Server;
import de.sealcore.util.ResourceManager;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.util.ArrayList;

public class CraftingHandler
{
    public ArrayList<Recipe> recipes = new ArrayList<>();

    public CraftingHandler()
    {
        parseRecipes("recipes_workbench");
    }

    public void parseRecipes(String fileName)
    {
        try
        {
            JsonObject jsonContent = JsonObject.fromString(ResourceManager.getResourceFileAsString("recipes/" + fileName + ".json"));

            String workstationId = jsonContent.getString("workstationId");

            for (Object o : jsonContent.getArray("recipes"))
            {
                //Get the recipe object and ingredient and output array
                JsonObject recipe = (JsonObject) o;
                JsonArray ingredientArray = recipe.getArray("ingredients");
                JsonArray outputArray = recipe.getArray("output");

                String id = recipe.getString("id");
                RecipePart[] ingredients = new RecipePart[ingredientArray.getSize()];
                RecipePart[] outputs = new RecipePart[outputArray.getSize()];

                //Get all ingredients
                for (int i = 0; i < ingredientArray.getSize(); i++)
                {
                    JsonObject in = (JsonObject) ingredientArray.get(i);
                    ingredients[i] = new RecipePart(in.getString("id"), in.getInt("amount"));
                }

                //Get all outputs
                for (int i = 0; i < outputArray.getSize(); i++)
                {
                    JsonObject out = (JsonObject) outputArray.get(i);
                    outputs[i] = new RecipePart(out.getString("id"), out.getInt("amount"));
                }

                recipes.add(new Recipe(workstationId, id, ingredients, outputs));
            }
        }
        catch (Exception e)
        {
            Log.error(LogType.GAME, "Could not parse recipes from " + fileName + ": " + e.getMessage());
        }
    }

    public int craft(String recipe, int amount, int playerId) //Returns whether the process was successful or not (1 = success, 0 = partial success, -1 = failed)
    {
        //Cycle through the crafting process
        for (int i = 0; i < amount; i++)
        {
            Recipe r = getRecipe(recipe);
            Player p = Server.game.players.get(playerId);

            //Check if the player has the required resources
            for (RecipePart ingredient : r.ingredients())
            {
                int amountInv = p.inventory.getAmount(ingredient.id());

                if (amountInv < ingredient.amount())
                    return i == 0 ? -1 : 0; //If the required resources aren't available, the entire process can be cancelled
            }

            //Add all the outputs to the players inv
            for (RecipePart output : r.output())
            {
                int remaining = p.inventory.add(output.id(), output.amount());

                if (remaining > 0) //If the player didn't have enough space for everything to be added, remove the items again and cancel process
                {
                    p.inventory.remove(output.id(), output.amount() - remaining);
                    return i == 0 ? -1 : 0;
                }
            }

            //Remove all the ingredients from the players inv
            for (RecipePart ingredient : r.ingredients())
            {
                p.inventory.remove(ingredient.id(), ingredient.amount()); //At this point we can assume the player has the required items, so I don't think we need to check if any items weren't removed
            }
        }

        return 1; //If all processes went smooth, return confirmation
    }

    public Recipe getRecipe(String recipe)
    {
        //Go through all recipes and return the requested recipe
        for (Recipe r : recipes)
        {
            if (r.id().equals(recipe)) return r;
        }

        return null;
    }
}
