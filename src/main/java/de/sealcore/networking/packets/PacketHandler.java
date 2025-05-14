package de.sealcore.networking.packets;

import java.util.ArrayList;

public class PacketHandler
{
    //This queue contains all packets and will be worked on by the game loop
    private static ArrayList<Packet> packetQueue = new ArrayList<Packet>();

    public static int getQueueSize()
    {
        return packetQueue.size();
    }

    public static void addToQueue(Packet packet)
    {
        packetQueue.add(packet);
    }

    public static void handleNext()
    {
        //Take the next packet out of the queue and handle it
        if(packetQueue.isEmpty()) return;

        packetQueue.get(0).handle();
        packetQueue.removeFirst();
    }
}
