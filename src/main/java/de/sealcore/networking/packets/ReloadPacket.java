package de.sealcore.networking.packets;

import de.sealcore.game.entities.inventory.InventorySlot;
import de.sealcore.game.items.Item;
import de.sealcore.game.items.ItemRegister;
import de.sealcore.game.items.ItemType;
import de.sealcore.game.items.TagHandler;
import de.sealcore.game.items.weapons.Weapon;
import de.sealcore.server.Server;
import de.sealcore.util.json.JsonObject;
import org.lwjgl.glfw.GLFW;

public class ReloadPacket extends Packet
{
    private int slot;

    public ReloadPacket(int slot)
    {
        super(PacketType.RELOAD);

        this.slot = slot;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int slot = args.getInt("slot");

        return new ReloadPacket(slot);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("slot", slot);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        InventorySlot s = Server.game.players.get(getSender()).inventory.getSlot(slot);
        Item i = ItemRegister.getItem(s.id); //Temp item for reloading
        TagHandler.writeTag(i, "ammoAmount", TagHandler.getIntTag(s.tag, "ammoAmount")); //Write the ammo of the slot to the current temp item

        //Check if the item that the reload was called on is actually a weapon
        if (i.info.type() == ItemType.WEAPON_RANGED)
        {
            Weapon w = (Weapon) i;

            //Reload the weapon and write the new tag to the slot
            w.reload(getSender());
            TagHandler.writeTag(s, "ammoAmount", TagHandler.getIntTag(i.tags, "ammoAmount"));
        }
    }
}
