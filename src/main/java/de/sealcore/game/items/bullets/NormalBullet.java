package de.sealcore.game.items.bullets;

import de.sealcore.game.items.ItemType;

public class NormalBullet extends Bullet
{
    public NormalBullet()
    {
        super("i:normal_bullet", "Normal Bullet", ItemType.AMMO, 64, 1);
    }
}
