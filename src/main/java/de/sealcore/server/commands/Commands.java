package de.sealcore.server.commands;

import de.sealcore.game.entities.general.Player;
import de.sealcore.server.Server;
import de.sealcore.server.debugrenderer.DebugRenderer;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class Commands
{

    public static void handlePong(String[] args)
    {
        if(args.length == 0)
        {
            Log.error(LogType.MAIN, "Insufficient arguments: Please provide the amount of pongs you want to receive");
            return;
        }

        int amount = Integer.parseInt(args[0]);

        for(int i = 0; i < amount; i++)
        {
            Log.info(LogType.MAIN, "PONG");
        }
    }

    public static void handleDebugRenderer()
    {
        CommandHandler.mode = InputMode.DEBUGRENDERER;
        DebugRenderer.start();
    }

    public static void handleAddItem(String[] args)
    {
        if(args.length != 3)
        {
            Log.error(LogType.MAIN, "Insufficient arguments: Please provide the player id, item and amount");
            return;
        }

        try
        {
            Player p = Server.game.players.get(Integer.parseInt(args[0]));
            String item = args[1];
            int amount = Integer.parseInt(args[2]);

            p.inventory.add(item, amount);
            Log.info(LogType.MAIN, "Added item " + item + " to player " + p.getName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void handleHelpCommand()
    {
        Log.info(LogType.MAIN, """
                List of commands:
                /help - Displays all available commands
                /ping [amount] - Returns x 'pong' entries
                /debugrenderer - Starts displaying the debug renderer
                /additem [player] [item] [amount] - Gives the specified item x times to a player
                /start - Starts the waves
                /pause - Pauses the waves""");

    }
}
