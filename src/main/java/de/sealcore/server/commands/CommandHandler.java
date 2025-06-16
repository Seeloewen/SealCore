package de.sealcore.server.commands;

import de.sealcore.server.debugrenderer.DebugRenderer;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class CommandHandler
{
    public static InputMode mode;

    public static void parse(String input)
    {
        if(mode == InputMode.DEBUGRENDERER)
        {
            //If the mode is set to debug renderer, it should catch the input and handle it separately
            DebugRenderer.parseInput(input);
            return;
        }

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
                Commands.handlePong(args);
                break;
            case "debugrenderer":
                Commands.handleDebugRenderer();
                break;
            case "additem":
                Commands.handleAddItem(args);
                break;
            default:
                Log.info(LogType.MAIN, "Unknown Command.");
        }
    }
}
