package de.sealcore.game.items;

import de.sealcore.game.blocks.WorkBench;
import de.sealcore.game.items.bullets.NormalBullet;
import de.sealcore.game.items.tools.Axe;
import de.sealcore.game.items.tools.Pickaxe;
import de.sealcore.game.items.weapons.Pistol;
import de.sealcore.game.items.weapons.Rifle;
import de.sealcore.game.items.weapons.Sword;

public class ItemRegister
{
    public static Item getItem(String id)
    {
        return switch(id)
        {
            case "i:sword" -> new Sword();
            case "i:rifle" -> new Rifle();
            case "i:pistol" -> new Pistol();
            case "i:pickaxe" -> new Pickaxe();
            case "i:axe" -> new Axe();
            case "i:normal_bullet" -> new NormalBullet();
            case "i:workbench" -> new WorkBenchItem();
            case "i:log" -> new Log();
            case "i:rock" -> new Rock();
            default -> null;
        };
    }
}
