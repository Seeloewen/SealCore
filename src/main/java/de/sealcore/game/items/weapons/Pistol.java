package de.sealcore.game.items.weapons;

import de.sealcore.game.items.ItemType;

public class Pistol extends Weapon
{
    public Pistol()
    {
        super("i:pistol", "Pistol", ItemType.WEAPON_RANGED, 1, 2, 30, 8, 0.8);
    }
}
