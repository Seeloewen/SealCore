package de.sealcore.networking.packets;

import de.sealcore.networking.TcpClient;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public abstract class Packet
{
    public final PacketType type;
    private int sender = -1; //-1 if server (default), 0...n if client

    protected Packet(PacketType type)
    {
        this.type = type;
    }

    public static Packet fromJson(String json) throws Exception
    {
        throw new Exception("Cannot create typeless packet from Json");
    }

    public void setSender(TcpClient client)
    {
        sender = client.id;
    }

    public int getSender()
    {
        return sender;
    }

    public abstract String toJson();

    public void handle() {
        try {
            onHandle();
        } catch(Exception e) {
            Log.warn(LogType.NETWORKING, "Exception while handling Packet \n" + toJson() + "\n" + e.toString());
        }
    }

    public abstract void onHandle();
}
