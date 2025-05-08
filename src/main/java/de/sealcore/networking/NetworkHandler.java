package de.sealcore.networking;

public class NetworkHandler
{
    public static TcpServer server;
    public static TcpClient client;

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
            System.out.println("Could not initialize networking: " + ex.getMessage());
        }
    }

    public static void send(String mes)
    {
        //Send message depending on the instance
        if (isServer())
        {
            server.send(mes);
        }
        else if (isClient())
        {
            client.send(mes);
        }
    }

    public static void parseData(String data)
    {
        //TODO:
        //Parse json string to get type
        //Add new packet to queue based on type
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
