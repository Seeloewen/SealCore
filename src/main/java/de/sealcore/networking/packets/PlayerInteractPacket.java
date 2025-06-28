package de.sealcore.networking.packets;

import com.fasterxml.jackson.databind.node.TextNode;
import de.sealcore.client.Client;
import de.sealcore.server.Server;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;

import java.util.ArrayList;

public class PlayerInteractPacket extends Packet
{
    private int slot;
    private boolean leftClick;

    public int te;
    public double dte;
    public int tbx;
    public int tby;
    public double dtb;
    public int tfx;
    public int tfy;
    public double dtf;


    public PlayerInteractPacket(int slot, boolean leftClick, int te, double dte,
                                int tbx, int tby, double dtb,
                                int tfx, int targetFloorY, double distTargetFloor)
    {
        super(PacketType.PLAYERINTERACT);

        this.slot = slot;
        this.leftClick = leftClick;
        this.te = te;
        this.dte = dte;
        this.tbx = tbx;
        this.tby = tby;
        this.dtb = dtb;
        this.tfx = tfx;
        this.tfy = targetFloorY;
        this.dtf = distTargetFloor;

    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);


        return new PlayerInteractPacket(
                args.getInt("slot"),
                args.getBool("leftClick"),
                args.getInt("te"),
                args.getDouble("dte"),
                args.getInt("tbx"),
                args.getInt("tby"),
                args.getDouble("dtb"),
                args.getInt("tfx"),
                args.getInt("tfy"),
                args.getDouble("dtf")
        );
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();

        args.addInt("slot", slot);
        args.addBool("leftClick", leftClick);
        args.addInt("te", te);
        args.addDouble("dte", dte);
        args.addInt("tbx", tbx);
        args.addInt("tby", tby);
        args.addDouble("dtb", dtb);
        args.addInt("tfx", tfx);
        args.addInt("tfy", tfy);
        args.addDouble("dtf", dtf);

        obj.addObject("args", args);

        return obj.toString();
    }

    public void onHandle()
    {
        Server.game.players.get(getSender()).interact(slot, leftClick, te, dte, tbx, tby, dtb, tfx, tfy, dtf);
    }
}
