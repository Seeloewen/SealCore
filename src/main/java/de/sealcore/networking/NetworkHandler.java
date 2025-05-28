package de.sealcore.networking;

import de.sealcore.networking.packets.ExamplePacket;
import de.sealcore.networking.packets.Packet;
import de.sealcore.networking.packets.PacketHandler;
import de.sealcore.networking.packets.PacketType;
import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class NetworkHandler
{
    public static TcpServer server;
    public static TcpClient client;

    public static boolean verboseLogging = false; //Debug switch, shows all sent and received packets when toggled on

    public static void init(NetworkType instance)
    {
        try
        {
            //Start the appropriate network part
            if (instance == NetworkType.SERVER)
            {
                NetworkHandler.server = new TcpServer();
                NetworkHandler.server.start(5000);
            }
            else if (instance == NetworkType.CLIENT)
            {
                NetworkHandler.client = new TcpClient(0);
                NetworkHandler.client.connect("localhost", 5000);
            }
        }
        catch (Exception ex)
        {
            Log.error(LogType.NETWORKING, "Could not initialize networking: " + ex.getMessage());
        }
    }

    public static void send(Packet packet)
    {
        //Verbose logging
        if(verboseLogging) Log.info(LogType.NETWORKING, "Sent packet: " + packet.toJson());

        //Send message depending on the instance
        if (isServer())
        {
            server.send(packet.toJson());
        }
        else if (isClient())
        {
            client.send(packet.toJson());
        }
    }

    public static void parseData(String data)
    {
        if(verboseLogging) Log.info(LogType.NETWORKING, "Received packet: " + data);

        //Convert data to json object and get type
        JsonObject obj = JsonObject.fromString(data);
        PacketType type = PacketType.values()[obj.getInt("type")];

        //Construct packet from type
        Packet p = null;
        switch(type)
        {
            case PacketType.EXAMPLE:
                p = ExamplePacket.fromJson(obj.getObject("args").toString());
                break;
        }

        if(p != null) PacketHandler.addToQueue(p);
    }

    public static boolean isClient()
    {
        return client != null;
    }

    public static boolean isServer()
    {
        return server != null;
    }
}
