package de.sealcore.networking.packets;

public abstract class Packet
{
    public final PacketType type;

    protected Packet(PacketType type)
    {
        this.type = type;
    }

    public abstract Packet fromJson(String json);
    public abstract String toJson();
    public abstract void handle();
}
