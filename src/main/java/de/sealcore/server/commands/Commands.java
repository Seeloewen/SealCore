package de.sealcore.server.commands;

import de.sealcore.server.debugrenderer.DebugRenderer;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class Commands
{
    public static void HandlePong(String[] args) //Args amount
    {
        int amount = Integer.parseInt(args[0]);

        for(int i = 0; i < amount; i++)
        {
            Log.info(LogType.MAIN, "PONG");
        }
    }

    public static void HandleDebugRenderer()
    {
        CommandHandler.mode = InputMode.DEBUGRENDERER;
        DebugRenderer.start();
    }
}
