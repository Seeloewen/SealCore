package de.sealcore.util.commands;

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
}
