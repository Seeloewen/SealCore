package de.sealcore.server;

import de.sealcore.game.Game;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.NetworkType;
import de.sealcore.networking.packets.PacketHandler;
import de.sealcore.networking.packets.SetTextPacket;
import de.sealcore.server.commands.CommandHandler;
import de.sealcore.server.waves.WaveManager;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import de.sealcore.util.timing.DeltaTimer;

import java.util.Scanner;

public class Server
{
    public static Game game;

    public final double TICKRATE = 1/40d;

    public double totalTime = 0;
    public int seconds = 0;

    private Server()
    {
        //Initialize game
        game = new Game();

        //Initialize networking
        NetworkHandler.init("", 5000, NetworkType.SERVER);
    }



    public static void main()
    {
        //Thread for command handling
        Thread t = new Thread(() -> getCommands(), "command input reader");
        t.start();

        WaveManager.init();

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
            WaveManager.update(TICKRATE);
            game.tick(TICKRATE);
            totalTime += TICKRATE;
            if((int)totalTime > seconds) {
                seconds++;
                if(seconds%60<10) {
                    NetworkHandler.send(new SetTextPacket((seconds/60) + ":0" + (seconds%60), 1));
                } else {
                    NetworkHandler.send(new SetTextPacket((seconds/60) + ":" + (seconds%60), 1));
                }
            }
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
