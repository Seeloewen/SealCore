package de.sealcore.networking;

public class NetworkHandler
{
    public static Server server;
    public static Client client;

    public static void send(String mes)
    {
        if (server != null)
        {
            server.send(mes);
        }
        else if (client != null)
        {
            client.send(mes);
        }
    }

    public static void init(NetworkType instance)
    {
        try
        {
            if (instance == NetworkType.SERVER)
            {
                NetworkHandler.server = new Server();
                NetworkHandler.server.start();
            }
            else if (instance == NetworkType.CLIENT)
            {
                NetworkHandler.client = new Client();
                NetworkHandler.client.connect();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Could not initialize networking: " + ex.getMessage());
        }
    }
}
