package de.sealcore.networking.packets;

import de.sealcore.server.Server;
import de.sealcore.util.json.JsonObject;
import de.sealcore.game.entities.general.Player;

public class MoveInputPacket extends Packet
{
    private int x;
    private int y;
    private double angleHor;

    public MoveInputPacket(int x, int y, double angHor)
    {
        super(PacketType.MOVEINPUT);

        this.x = x;
        this.y = y;
        this.angleHor = angHor;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int x = args.getInt("x");
        int y = args.getInt("y");
        double angleHor = args.getDouble("angleHor");

        return new MoveInputPacket(x, y, angleHor);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("x", x);
        args.addInt("y", y);
        args.addDouble("angleHor", angleHor);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void handle()
    {
        Server.game.players.get(getSender()).updateInputs(x, y, angleHor);
    }
}
