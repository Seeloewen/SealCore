package de.sealcore.server.debugrenderer;

import de.sealcore.game.chunks.Chunk;
import de.sealcore.game.blocks.Block;
import de.sealcore.game.floors.Floor;
import de.sealcore.server.Server;
import de.sealcore.server.commands.CommandHandler;
import de.sealcore.server.commands.InputMode;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

public class DebugRenderer
{
    public static DebugRenderMode mode = DebugRenderMode.FLOOR;

    public static int curX = 0;
    public static int curY = 0;

    public static void start()
    {
        Log.info(LogType.DEBUGRENDERER, "Started Debug Renderer.\n" +
                "Type \"help\" for a list of commands\n" +
                "Type \"exit\" to return to command handler");

        renderChunk(0, 0);
    }

    public static void parseInput(String input)
    {
        String[] cmd = input.split(" "); //Split input into parts

        switch (cmd[0])
        {
            //Inputs to "move" around
            case "w" -> renderChunk(curX, curY + 1);
            case "a" -> renderChunk(curX - 1, curY);
            case "s" -> renderChunk(curX, curY - 1);
            case "d" -> renderChunk(curX + 1, curY);

            case "m" ->
            {
                mode = mode == DebugRenderMode.FLOOR ? DebugRenderMode.BLOCK : DebugRenderMode.FLOOR; //Toggle the render mode
                Log.info(LogType.DEBUGRENDERER, "Toggled render mode to " + mode);
                renderChunk(curX, curY);
            }
            case "exit" ->
            {
                //Exit Debug Renderer
                Log.info(LogType.DEBUGRENDERER, "Exiting Debug Renderer");
                CommandHandler.mode = InputMode.COMMANDS;
            }
            case "c" -> //Render chunk
            {
                if (!sufficientArgs(cmd, 3)) return;
                renderChunk(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
            }
            case "cinfo" -> //Display info about chunk
            {
                if (!sufficientArgs(cmd, 3)) return;
                showChunkInfo(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
            }
            case "finfo" -> //Display info about floor
            {
                if (!sufficientArgs(cmd, 3)) return;
                showFloorInfo(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
            }
            case "binfo" -> //Display info about block
            {
                if (!sufficientArgs(cmd, 3)) return;
                showBlockInfo(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
            }
            case "help" ->
            {
                Log.info(LogType.DEBUGRENDERER, helpMsg);
            }
            default ->
            {
                Log.info(LogType.DEBUGRENDERER, "Unknown command. Type /help for a list.");
            }
        }
    }

    public static void renderChunk(int x, int y)
    {
        curX = x;
        curY = y;

        //Get the chunk - generate it if it doesn't exist
        Chunk c = Server.game.getCurrentMap().getChunk(x, y);
        if (c == null) c = Server.game.getCurrentMap().genChunk(x, y);

        //Display the entire chunk
        Log.info(LogType.DEBUGRENDERER, "Displaying chunk x" + x + " y" + y);
        for (int i = Chunk.HEIGHT - 1; i >= 0; i--)
        {
            StringBuilder s = new StringBuilder();

            for (int j = 0; j < Chunk.WIDTH; j++)
            {
                //Log the first letter of the block/floor (depending on mode)
                if (mode == DebugRenderMode.FLOOR)
                {
                    s.append(c.getFloor(j, i).info.name().charAt(0));
                }
                else if (mode == DebugRenderMode.BLOCK)
                {
                    Block b = c.getBlock(j, i);
                    s.append(b != null ? b.info.name().charAt(0) : "-"); //Append the name when there is a block, otherwise a minus
                }
            }

            Log.info(LogType.DEBUGRENDERER, s.toString());
        }
    }

    public static void showBlockInfo(int x, int y)
    {
        Block b = Server.game.getCurrentMap().getChunk(curX, curY).getBlock(x, y);

        //Display info about the block specified in the currently selected chunk
        Log.info(LogType.DEBUGRENDERER, "=> Info for Block at x" + x + " y" + y);
        Log.info(LogType.DEBUGRENDERER, "Name: " + b.info.name());
        Log.info(LogType.DEBUGRENDERER, "Id: " + b.info.id());
    }

    public static void showFloorInfo(int x, int y)
    {
        Floor f = Server.game.getCurrentMap().getChunk(curX, curY).getFloor(x, y);

        //Display info about the floor specified in the currently selected chunk
        Log.info(LogType.DEBUGRENDERER, "=> Info for Floor at x" + x + " y" + y);
        Log.info(LogType.DEBUGRENDERER, "Name: " + f.info.name());
        Log.info(LogType.DEBUGRENDERER, "Id: " + f.info.id());
    }

    public static void showChunkInfo(int x, int y)
    {
        Chunk c = Server.game.getCurrentMap().getChunk(curX, curY);

        //Display info about the given chunk
        Log.info(LogType.DEBUGRENDERER, "=> Info for Chunk at x" + x + " y" + y);
        Log.info(LogType.DEBUGRENDERER, "(None available)");
    }

    private static boolean sufficientArgs(String[] cmd, int expectedArgs)
    {
        //Check if the amount of arguments in the command matches the expected amount
        if (cmd.length - 1 != expectedArgs) //Don't count the first string in array since it's the command
        {
            Log.error(LogType.DEBUGRENDERER, "Insufficient arguments provided");
            return false;
        }

        return true;
    }

    private final static String helpMsg = "Available inputs for Debug Renderer:\n" +
            "w - Move one chunk up\n" +
            "a - Move one chunk left\n" +
            "s - Move one chunk down\n" +
            "d - Move one chunk right\n" +
            "exit - Exit the Debug Renderer\n" +
            "c <x> <y> - Render the specified chunk\n" +
            "cinfo <x> <y> - Show info about the specified chunk\n" +
            "binfo <x> <y> - Show info about the specified block in the currently loaded chunk\n" +
            "finfo <x> <y> - Show info about the specified floor in the currently loaded floor\n" +
            "help - Shows this menu\n";
}
