package de.sealcore.game.items;

public record ItemInfo(String id, String name, ItemType type, int maxAmount, double cooldown)
{
}
