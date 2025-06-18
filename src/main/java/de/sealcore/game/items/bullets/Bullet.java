package de.sealcore.game.items.bullets;

import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemType;
import de.sealcore.game.items.weapons.Weapon;

public abstract class Bullet extends Item
{
    public BulletInfo bulletInfo;

    protected Bullet(String id, String name, ItemType type, int maxAmount, int damage)
    {
        super(id, name, type, maxAmount, 0);
        bulletInfo = new BulletInfo(damage);
    }
}
