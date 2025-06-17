package de.sealcore.server;

import de.sealcore.game.Game;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.NetworkType;
import de.sealcore.networking.packets.PacketHandler;
import de.sealcore.server.commands.CommandHandler;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import de.sealcore.util.timing.DeltaTimer;

import java.util.Scanner;

public class Server
{
    public static Game game;

    private final double TICKRATE = 1/40d;

    public int ticksToEvent;

    private Server()
    {
        //Initialize game
        game = new Game();

        //Initialize networking
        NetworkHandler.init(NetworkType.SERVER);
    }

    public void setTicksToEvent(double seconds) {
        ticksToEvent = (int)(seconds/TICKRATE);
    }

    public static void main()
    {
        //Thread for command handling
        Thread t = new Thread(() -> getCommands(), "command input reader");
        t.start();

        //Actual server main loop
        Server server = new Server();
        server.loop();
    }

    private void loop()
    {
        //DeltaTimer.start();
        while(true)
        {
            //Get amount of packets to handle at beginning of tick - only handle those packets
            //All packets that arrive while handling the current packets will be counted towards the next tick
            int queueSize = PacketHandler.getQueueSize();
            for(int i = 0; i < queueSize; i++)
            {
                PacketHandler.handleNext();
            }
            if(!DeltaTimer.blockToTarget(TICKRATE)) Log.warn(LogType.PERFORMANCE, "last server tick exceeded tickrate");
            game.tick(TICKRATE);
            ticksToEvent--;
        }
    }

    private static void getCommands()
    {
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            CommandHandler.parse(input);
            //NetworkHandler.send(new ExamplePacket(input, 187));
        }
    }
}
