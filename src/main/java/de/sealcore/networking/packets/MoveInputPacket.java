package de.sealcore.networking.packets;

import de.sealcore.server.Server;
import de.sealcore.util.json.JsonObject;

public class MoveInputPacket extends Packet
{
    private int x;
    private int y;

    public MoveInputPacket(int x, int y)
    {
        super(PacketType.MOVEINPUT);

        this.x = x;
        this.y = y;
    }

    public static Packet fromJson(String json)
    {
        //Parse attributes from json object
        JsonObject args = JsonObject.fromString(json);

        int y = args.getInt("x");
        int x = args.getInt("y");

        return new MoveInputPacket(x, y);
    }

    public String toJson()
    {
        //Create json string from attributes
        JsonObject obj = JsonObject.fromScratch();
        obj.addInt("type", type.ordinal());

        JsonObject args = JsonObject.fromScratch();
        args.addInt("x", x);
        args.addInt("y", y);
        obj.addObject("args", args);

        return obj.toString();
    }

    public void handle()
    {
        Server.game.players.get(getSender()).updateInputs(x, y);
    }
}
