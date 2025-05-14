package de.sealcore.server;

import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.NetworkType;
import de.sealcore.networking.packets.ExamplePacket;
import de.sealcore.networking.packets.Packet;
import de.sealcore.networking.packets.PacketHandler;

import java.util.Scanner;

public class Server
{
    private Server()
    {
        NetworkHandler.init(NetworkType.SERVER);
    }

    private void loop()
    {
        while(true)
        {
            //Get amount of packets to handle at beginning of tick - only handle those packets
            //All packets that arrive while handling the current packets will be counted towards the next tick
            int queueSize = PacketHandler.getQueueSize();
            for(int i = 0; i < queueSize; i++)
            {
                PacketHandler.handleNext();
            }
        }
    }

    private static void getCommands()
    {
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            NetworkHandler.send(new ExamplePacket(input, 187));
        }
    }

    public static void main()
    {
        //TODO: seperate thread for command handling
        Thread t = new Thread(() -> getCommands());
        t.start();

        Server server = new Server();
        server.loop();
    }
}
