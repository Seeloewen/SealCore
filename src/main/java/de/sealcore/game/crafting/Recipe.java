package de.sealcore.game.crafting;

public record Recipe(String workstationId, String id, RecipePart[] ingredients, RecipePart[] output)
{

}
