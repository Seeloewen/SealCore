package de.sealcore.networking.packets;

import java.io.InvalidObjectException;

public abstract class Packet
{
    public final PacketType type;

    protected Packet(PacketType type)
    {
        this.type = type;
    }

    public static Packet fromJson(String json) throws Exception
    {
        throw new Exception("Cannot create typeless packet from Json");
    }
    public abstract String toJson();
    public abstract void handle();
}
