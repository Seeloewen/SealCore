package de.sealcore.networking;

import de.sealcore.networking.packets.*;
import de.sealcore.util.json.JsonObject;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class NetworkHandler
{
    public static TcpServer server;
    public static TcpClient client;

    public static boolean verboseLogging = false; //Debug switch, shows all sent and received packets when toggled on

    public static boolean init(String ip, int port, NetworkType instance)
    {
        try
        {
            //Start the appropriate network part
            if (instance == NetworkType.SERVER)
            {
                NetworkHandler.server = new TcpServer();
                NetworkHandler.server.start(port);
            }
            else if (instance == NetworkType.CLIENT)
            {
                NetworkHandler.client = new TcpClient(0);
                NetworkHandler.client.connect(ip, port);
            }
        }
        catch (Exception ex)
        {
            Log.error(LogType.NETWORKING, "Could not initialize networking: " + ex.getMessage());
            return false;
        }
        return true;
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

    public static void sendOnly(int id, Packet packet) {
        server.sendOnly(NetworkHandler.getClient(id), packet.toJson());
        if(verboseLogging) Log.info(LogType.NETWORKING, "Sent packet to " + id + ": " + packet.toJson());
    }

    public static void parseData(Object source, String data)
    {
        if(verboseLogging) Log.info(LogType.NETWORKING, "Received packet: " + data);

        //Convert data to json object and get type
        JsonObject obj = JsonObject.fromString(data);
        PacketType type = PacketType.values()[obj.getInt("type")];

        //Construct packet from type
        Packet p = null;
        String args = obj.getObject("args").toString();
        switch(type)
        {
            case PacketType.CHUNKUNLOAD -> p = ChunkUnloadPacket.fromJson(args);
            case PacketType.CHUNKADD -> p = ChunkAddPacket.fromJson(args);
            case PacketType.CHUNKUPDATE -> p = ChunkUpdatePacket.fromJson(args);
            case PacketType.ENTITYADD -> p = EntityAddPacket.fromJson(args);
            case PacketType.ENTITYREMOVE -> p = EntityRemovePacket.fromJson(args);
            case PacketType.ENTITYUPDATEPOS -> p = EntityUpdatePosPacket.fromJson(args);
            case PacketType.INVENTORYADD -> p = InventoryAddPacket.fromJson(args);
            case PacketType.INVENTORYSTATE -> p = InventoryStatePacket.fromJson(args);
            case PacketType.INVENTORYMOVE -> p = InventoryMovePacket.fromJson(args);
            case PacketType.INVENTORYREMOVE -> p = InventoryRemovePacket.fromJson(args);
            case PacketType.MOVEINPUT -> p = MoveInputPacket.fromJson(args);
            case PacketType.SETFOLLOWCAM -> p = SetFollowCamPacket.fromJson(args);
        }

        //Set the client id if the sender is a client
        if(source instanceof TcpClient)
        {
           p.setSender((TcpClient) source);
        }

        if(p != null) PacketHandler.addToQueue(p);
    }

    public static TcpClient getClient(int id)
    {
        //Go through all servers and return the one with the specified id
        for(TcpClient c : server.clients)
        {
            if(c.id == id) return c;
        }

        return null;
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
