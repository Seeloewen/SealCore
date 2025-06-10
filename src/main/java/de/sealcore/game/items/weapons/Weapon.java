package de.sealcore.game.items.weapons;

import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemType;
import de.sealcore.game.items.bullets.Bullet;

public abstract class Weapon extends Item
{
    public WeaponInfo weaponInfo;

    protected Weapon(String id, String name, ItemType type, int maxAmount, WeaponType wType, int damage, int range)
    {
        super(id, name, type, maxAmount);
        weaponInfo = new WeaponInfo(wType, damage, range);
    }

    @Override
    public int onRightClick(int playerId)
    {
        //TODO: Get first bullet from players inv

        Bullet b = null;
        shoot(b);

        return 0;
    }

    public void shoot(Bullet b)
    {
        //TODO: Spawn bullet entity with reference to b and this gun
    }
}
