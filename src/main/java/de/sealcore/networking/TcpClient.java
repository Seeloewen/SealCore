package de.sealcore.networking;

import de.sealcore.server.Server;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;

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
        Log.info(LogType.NETWORKING, "Connecting...");

        socket = new Socket(ip, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);

        Log.info(LogType.NETWORKING, "Connected to server!");

        //Start receiving data from server
        Thread t = new Thread(() -> readStream());
        t.start();
    }

    public void disconnect()
    {
        try
        {
            Server.game.removePlayer(id);
            //Try closing the connection to the server
            socket.close();
        }
        catch(Exception ex)
        {
            Log.error(LogType.NETWORKING, "Could not close connection to server: " + ex.getMessage());

            //Log stack trace if verbose logging is enabled
            for(StackTraceElement e : ex.getStackTrace())
            {
                if(NetworkHandler.verboseLogging) Log.error(LogType.NETWORKING, e.toString());
            }
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
                if(data == null) {
                    Log.info(LogType.NETWORKING, "hs");
                }
                NetworkHandler.parseData(this, data); //Parse data to packet
            }
            catch (Exception ex)
            {
                Log.error(LogType.NETWORKING, "Could not read stream from server: " + ex.getMessage());

                //Log stack trace if verbose logging is enabled
                for(StackTraceElement e : ex.getStackTrace())
                {
                    if(NetworkHandler.verboseLogging) Log.error(LogType.NETWORKING, e.toString());
                }

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
