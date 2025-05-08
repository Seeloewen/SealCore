package de.sealcore;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.NetworkType;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Scanner scanner = new Scanner(System.in);

        NetworkHandler.init(getNetworkType(args[0]));

        while(true)
        {
            String input = scanner.nextLine();
            NetworkHandler.send(input);
        }
    }

    public static NetworkType getNetworkType(String arg)
    {
        //Checks the given arg and returns the corresponding network type
        if(arg == null) return NetworkType.CLIENT; //Default to client if no arg is given

        if(arg.equals("s")) return NetworkType.SERVER;
        if(arg.equals("c")) return NetworkType.CLIENT;

        return null;
    }

}
