package de.sealcore.networking.packets;

import java.util.ArrayList;

public class PacketHandler
{
    //This queue contains all packets and will be worked on by the game loop
    private static ArrayList<Packet> packetQueue = new ArrayList<Packet>();

    public static void AddToQueue(Packet packet)
    {
        packetQueue.add(packet);
    }

    public static void HandleNext()
    {
        //Take the next packet out of the queue and handle it
        packetQueue.get(0).handle();
        packetQueue.removeFirst();
    }
}
