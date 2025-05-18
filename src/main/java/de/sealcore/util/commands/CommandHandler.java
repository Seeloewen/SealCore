package de.sealcore.util.commands;

import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogLevel;
import de.sealcore.util.logging.LogType;

public class CommandHandler
{
    public static void parse(String input)
    {
        String[] cmd = input.replace("/", "").split(" "); //Split command into parts

        //Copy only the args into a new array
        String[] args = new String[cmd.length - 1];
        for(int i = 0; i < args.length; i++)
        {
            args[i] = cmd[i + 1];
        }

        //Call respective command
        switch(cmd[0])
        {
            case "ping":
                Commands.HandlePong(args);
                break;
            default:
                Log.info(LogType.MAIN, "Unknown Command.");
        }
    }
}
