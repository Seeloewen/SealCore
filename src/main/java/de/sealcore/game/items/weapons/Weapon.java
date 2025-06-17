package de.sealcore.game.items.weapons;

import de.sealcore.client.input.KeyBinds;
import de.sealcore.game.entities.general.Player;
import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.game.entities.inventory.InventorySlotType;
import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemType;
import de.sealcore.game.items.TagHandler;
import de.sealcore.game.items.bullets.Bullet;
import de.sealcore.server.Server;

public abstract class Weapon extends Item
{
    public WeaponInfo weaponInfo;

    protected Weapon(String id, String name, ItemType type, int maxAmount, WeaponType wType, int damage, double range, int magSize)
    {
        super(id, name, type, maxAmount);
        weaponInfo = new WeaponInfo(wType, damage, range, magSize);

        TagHandler.writeTag(this, "ammoId", Weapon.getDefaultAmmo(wType));
        TagHandler.writeTag(this, "ammoAmount", 0);
    }

    public void reload(int playerId)
    {
        if(TagHandler.getIntTag(tags, "ammoAmount") == weaponInfo.magSize()) return; //Don't reload if the mag is full

        Player p = Server.game.players.get(playerId);

        //The bullet type that will be reloaded is the first bullet found in the inventory
        String ammoId = "";
        for(InventorySlot s : p.inventory.getSlots())
        {
            if(s.type == InventorySlotType.AMMO && !s.isEmpty())
            {
                ammoId = s.id;
                break;
            }
        }

        int invAmount = p.inventory.getAmount(ammoId);
        int magAmount = TagHandler.getIntTag(tags, "ammoAmount");

        TagHandler.writeTag(this, "ammoId", ammoId);
        TagHandler.writeTag(this, "ammoAmount", Math.min(weaponInfo.magSize(), invAmount)); //Either reload the entire mag or the max amount of bullets available in inv, whatever is smaller

        p.inventory.remove(ammoId, weaponInfo.magSize() - magAmount); //Remove the reloaded amount of bullets from player
    }


    public void shoot(Bullet b)
    {
        //TODO: Spawn bullet entity with reference to b and this gun
    }

    @Override
    public void onKeyPress(int key, int playerId)
    {
        if(key == KeyBinds.RELOAD)
        {
            reload(playerId);
        }
    }

    @Override
    public int onRightClick(int playerId)
    {
        //TODO: Get first bullet from players inv

        Bullet b = null;
        shoot(b);

        return 0;
    }

    public static String getDefaultAmmo(WeaponType type)
    {
        return switch (type)
        {
            case BOW -> "arrow";
            case GUN -> "bullet";
            default -> "bullet";
        };
    }
}
