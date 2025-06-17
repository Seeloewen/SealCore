package de.sealcore;

import com.formdev.flatlaf.FlatLightLaf;
import de.sealcore.client.Client;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.NetworkType;
import de.sealcore.server.Server;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

import java.util.Scanner;

import static org.lwjgl.glfw.GLFW.glfwInit;

public class Main
{
    public static final String VERSION = "Dev";
    public static final String BUILDDATE = "17.06.2025";

    public static boolean bypassMenu = false;

    public static void main(String[] args)
    {
        Log.info(LogType.MAIN, "SealCore Development Build");
        Scanner scanner = new Scanner(System.in);

        FlatLightLaf.setup();

        parseArgs(args);

        if(!glfwInit()) throw new IllegalStateException("Failed to initialize GLFW");

        switch (getNetworkType(args[0]))
        {
            case NetworkType.CLIENT -> Client.main();
            case NetworkType.SERVER -> Server.main();
        }
    }

    public static NetworkType getNetworkType(String arg)
    {
        //Checks the given arg and returns the corresponding network type
        if (arg == null) return NetworkType.CLIENT; //Default to client if no arg is given

        if (arg.equals("s")) return NetworkType.SERVER;
        if (arg.equals("c")) return NetworkType.CLIENT;

        return null;
    }

    public static void parseArgs(String[] args)
    {
        //Cycle through all args and handle them
        for(String arg : args)
        {
            if(arg.equals("verbose"))
            {
                NetworkHandler.verboseLogging = true;
            }
            else if(arg.equals("bypassMenu"))
            {
                bypassMenu = true;
            }
        }
    }

}
