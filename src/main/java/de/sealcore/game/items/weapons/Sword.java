package de.sealcore.game.items.weapons;

import de.sealcore.game.items.ItemType;

public class Sword extends Weapon
{
    public Sword()
    {
        super("i:sword", "Sword", ItemType.WEAPON_MELEE, 1, 4, 3, 0, 0.5);
    }
}
