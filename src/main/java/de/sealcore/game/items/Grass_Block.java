package de.sealcore.game.items;

import de.sealcore.game.items.weapons.Weapon;
import de.sealcore.game.items.weapons.WeaponType;

public class Grass_Block extends Weapon
{
    public Grass_Block()
    {
        super("grass_block", "Grass Block", ItemType.WEAPON_RANGED, 64, WeaponType.MELEE, 4, 20, 100, 1.0);
        TagHandler.writeTag(this, "ammoAmount", 50);
    }
}
