package de.sealcore.game.items.bullets;

import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemType;
import de.sealcore.game.items.weapons.Weapon;

public abstract class Bullet extends Item
{
    public BulletInfo bulletInfo;

    protected Bullet(String id, String name, ItemType type, int maxAmount, BulletType bType, int damage, int range)
    {
        super(id, name, type, maxAmount, 0);
        bulletInfo = new BulletInfo(bType, damage, range);
    }

    public void onHit(Weapon w, Object e) //TODO: Entity e
    {
        //TODO: e.damage(w.weaponInfo.damage + bulletInfo.damage)
    }
}
