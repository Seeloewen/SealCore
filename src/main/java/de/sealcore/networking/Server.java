package de.sealcore.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    ServerSocket socket;
    Socket clientSocket;
    BufferedReader reader;
    PrintWriter writer;

    void start() throws IOException
    {
        System.out.println("Starting server...");
        socket = new ServerSocket(5000);
        System.out.println("Server started, waiting for client connection...");

        clientSocket = socket.accept();

        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(clientSocket.getOutputStream(), true);

        Thread t = new Thread(() -> readStream());
        t.start();

        System.out.println("Connection with client established");
    }

    void readStream()
    {
        while(true)
        {
            try
            {
                System.out.println(reader.readLine());
            }
            catch(Exception ex)
            {
                System.err.println("Could not read from stream as server: " + ex.getMessage());
                break;
            }
        }
    }

    void send(String mes)
    {
        writer.println(mes);
    }
}
