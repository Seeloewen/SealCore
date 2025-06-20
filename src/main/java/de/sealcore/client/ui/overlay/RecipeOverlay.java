package de.sealcore.client.ui.overlay;

import de.sealcore.client.Client;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.game.crafting.RecipePart;
import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemRegister;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.CraftingPacket;
import de.sealcore.util.Color;

public class RecipeOverlay
{
    private final int WIDTH = 260;
    private final int HEIGHT = 100;

    public int index;
    private int x;
    private int y;

    private final String recipeId;
    private final String itemId;
    private final RecipePart[] ingredients;

    public RecipeOverlay(int index, String recipeId, String itemId, RecipePart[] ingredientIds)
    {
        this.index = index;
        this.recipeId = recipeId;
        this.itemId = itemId;
        this.ingredients = ingredientIds;
    }

    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean isMouseOver()
    {
        return InputHandler.mouseX >= x && InputHandler.mouseX <= x + WIDTH
                && InputHandler.mouseY >= y && InputHandler.mouseY <= y + HEIGHT;
    }

    public void handleMouseClick()
    {
        if (isMouseOver())
        {
            if(sufficientResources())
            {
                NetworkHandler.send(new CraftingPacket(recipeId));
            }
        }
    }

    public boolean sufficientResources()
    {
        //Check for each ingredient if the player has the required amount
        for(RecipePart ingredient : ingredients)
        {
            if(Client.instance.inventoryState.getAvailableAmount(ingredient.id()) < ingredient.amount()) return false;
        }

        return true;
    }

    public void render()
    {
        //Background, item name and item texture
        PrimitiveRenderer.drawRectangle(new Rectangle(x, y, x + WIDTH, y + HEIGHT), new Color(index % 2 == 0 ? 0.82f : 0.72f), 0.01f);
        TextRenderer.drawString(x + 10, y + 10, 3, ItemRegister.getItem(itemId).info.name(), 0f);
        TextureRenderer.drawTexture(itemId, new Rectangle(x + 10, y + 40, x + 60, y + 90), 0f);

        //Ingredients
        for (int i = 0; i < ingredients.length; i++)
        {
            Item ingredient = ItemRegister.getItem(ingredients[i].id());

            TextureRenderer.drawTexture(ingredients[i].id(), new Rectangle(x + 110, y + 40 + i * 20, x + 110 + 15, y + 55 + i * 20), 0f);
            TextRenderer.drawString(x + 135, y + 40 + i * 20, 2, ingredient.info.name() + " (" + Client.instance.inventoryState.getAvailableAmount(ingredient.info.id()) + "/" + ingredients[i].amount() + ")", 0f);
        }
    }
}
