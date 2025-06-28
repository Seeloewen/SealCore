package de.sealcore.networking.packets;

import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.util.ArrayDeque;

public class PacketHandler
{
    //This queue contains all packets and will be worked on by the game loop
    private static ArrayDeque<Packet> packetQueue = new ArrayDeque<>();

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
         var p = packetQueue.poll();

         if(p != null)
         {
             p.handle();
         }
         else
         {
             Log.error(LogType.NETWORKING, "Could not pull next packet from queue");
         }
    }
}
