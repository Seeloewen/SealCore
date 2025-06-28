package de.sealcore.game.items.weapons;

import de.sealcore.game.items.ItemType;

public class Rifle extends Weapon
{
    public Rifle()
    {
        super("i:rifle", "Rifle", ItemType.WEAPON_RANGED, 1, 3, 60, 30, 0.2);
    }
}
