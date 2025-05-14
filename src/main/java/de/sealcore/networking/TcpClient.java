package de.sealcore.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClient
{
    public Socket socket;
    public PrintWriter writer;
    public BufferedReader reader;
    public final int id;

    TcpClient(int id)
    {
        this.id = id;
    }

    public void connect(String ip, int port) throws IOException
    {
        //Setup connection to server
        System.out.println("Connecting...");

        socket = new Socket(ip, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Connected to server!");

        //Start receiving data from server
        Thread t = new Thread(() -> readStream());
        t.start();
    }

    public void disconnect()
    {
        try
        {
            //Try closing the connection to the server
            socket.close();
        }
        catch(Exception ex)
        {
            System.out.println("Could not close connection to server: " + ex.getMessage());
        }
    }

    private void readStream()
    {
        while (true)
        {
            //Read from the server until some exception occurs
            try
            {
                String data = reader.readLine();
                NetworkHandler.parseData(data); //Parse data to packet
            }
            catch (Exception ex)
            {
                System.err.println("Could not read stream from server: " + ex.getMessage());
                disconnect();
                break;
            }
        }
    }

    public void send(String data)
    {
        //Send data to server
        writer.println(data);
    }
}
