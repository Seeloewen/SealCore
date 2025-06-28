package de.sealcore.server.commands;

import de.sealcore.game.entities.general.Player;
import de.sealcore.server.Server;
import de.sealcore.server.debugrenderer.DebugRenderer;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class Commands
{
    //TODO: Handling of wrong/insufficient arguments

    public static void handlePong(String[] args) //Args amount
    {
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
        Player p = Server.game.players.get(Integer.parseInt(args[0]));
        String item = args[1];
        int amount = Integer.parseInt(args[2]);

        p.inventory.add(item, amount);
    }

    //TODO: /help command for list of commands
}
