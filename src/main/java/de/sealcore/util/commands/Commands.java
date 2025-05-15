package de.sealcore.util.commands;

public class Commands
{
    public static void HandlePong(String[] args) //Args amount
    {
        int amount = Integer.parseInt(args[0]);

        for(int i = 0; i < amount; i++)
        {
            System.out.println("PONG");
        }
    }
}
