package de.sealcore.game.items.bullets;

import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemType;
import de.sealcore.game.items.weapons.Weapon;

public abstract class Bullet extends Item
{
    protected Bullet(String id, String name, ItemType type, int maxAmount)
    {
        super(id, name, type, maxAmount);
    }

    public void onHit(Weapon w, Object e) //TODO: Entity e
    {
        //TODO: e.damage(w.weaponInfo.damage + bulletInfo.damage)
    }
}
