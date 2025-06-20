package de.sealcore.client.ui.overlay;

import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.game.crafting.RecipePart;
import de.sealcore.util.Color;

import java.util.ArrayList;

public class CraftingOverlay
{
    private static int nextId = 0;

    private static int x;
    private static int y;

    private static final int WIDTH = 280;
    private static final int HEIGHT = 320;

    private static final ArrayList<RecipeOverlay> recipes = new ArrayList<>();

    public static void handleMouseClick(int button, int action)
    {
        if (action == InputHandler.MOUSE_DOWN) //Mouse down
        {
            for (RecipeOverlay s : recipes)
            {
                s.handleMouseClick();
            }
        }
    }

    public static void setLocation(int x, int y)
    {
        CraftingOverlay.x = x;
        CraftingOverlay.y = y;
    }

    public static void init()
    {
        setLocation(Resolution.WIDTH - 300, 20);
    }

    public static void render()
    {
        //Render the background
        PrimitiveRenderer.drawRectangle(new Rectangle(x, y, x + WIDTH, y + HEIGHT), new Color(0.60f), 0.02f);


        int offset = 10;
        for(RecipeOverlay recipe : recipes) //Render the different recipes
        {
            recipe.setLocation(x + 10, y + offset);
            recipe.render();
            offset += 100;
        }
    }

    public static void addRecipe(String recipeId, String itemId, RecipePart[] ingredients)
    {
        recipes.add(new RecipeOverlay(nextId++, recipeId, itemId, ingredients));
    }
}
