package de.sealcore.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
    Socket socket;
    PrintWriter writer;
    BufferedReader reader;

    void connect() throws IOException
    {
        System.out.println("Connecting...");
        socket = new Socket("localhost", 5000);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Connected to server!");

        Thread t = new Thread(() -> readStream());
        t.start();
    }

    void readStream()
    {
        while (true)
        {
            try
            {
                System.out.println(reader.readLine());
            }
            catch (Exception ex)
            {
                System.err.println("Could not read stream from Server: " + ex.getMessage());
                break;
            }
        }
    }

    void send(String mes)
    {
        writer.println(mes);
    }
}
