package de.sealcore.networking;

import com.sun.source.tree.TryTree;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import jdk.jshell.spi.ExecutionControlProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TcpServer
{
    public static int nextId = 0;

    private ServerSocket socket;
    final ArrayList<TcpClient> clients = new ArrayList<TcpClient>();

    public void start(int port) throws IOException
    {
        //Setup the socket and start receiving connections
        Log.info(LogType.NETWORKING, "Starting server...");
        socket = new ServerSocket(port);
        Log.info(LogType.NETWORKING, "Server started, waiting for client connections...");

        Thread t = new Thread(() -> awaitConnections());
        t.start();
    }

    private void stop()
    {
        try
        {
            //Remove all clients
            for(TcpClient client : clients)
            {
                client.socket.close();
            }
            clients.clear();

            //Close the server socket
            socket.close();
            Log.info(LogType.NETWORKING, "Stopped server.");
        }
        catch(Exception ex)
        {
            Log.error(LogType.NETWORKING, "Could not stop the server: " + ex.getMessage());

            //Log stack trace if verbose logging is enabled
            for(StackTraceElement e : ex.getStackTrace())
            {
                if(NetworkHandler.verboseLogging) Log.error(LogType.NETWORKING, e.toString());
            }
        }
    }

    private void awaitConnections()
    {
        while (true)
        {
            try
            {
                //Try to accept new connections and get their input/output streams
                TcpClient client = new TcpClient(nextId++);
                client.socket = socket.accept();
                client.reader = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
                client.writer = new PrintWriter(client.socket.getOutputStream(), true);
                clients.add(client);

                //Start reading from the client
                Thread t = new Thread(() -> readStream(client));
                t.start();

                Log.info(LogType.NETWORKING, "Connection with client #" + client.id + " established");
            }
            catch(Exception ex)
            {
                Log.error(LogType.NETWORKING, "Error while awaiting new connections: " + ex.getMessage());

                //Log stack trace if verbose logging is enabled
                for(StackTraceElement e : ex.getStackTrace())
                {
                    if(NetworkHandler.verboseLogging) Log.error(LogType.NETWORKING, e.toString());
                }

                stop();
                break;
            }
        }
    }

    private void readStream(TcpClient client)
    {
        while (true)
        {
            //Read the stream until some exception occurs
            try
            {
                String data = client.reader.readLine();
                NetworkHandler.parseData(this, data); //Parse data to packet
                sendExcept(client, data); //Forward data to other clients
            }
            catch (Exception ex)
            {
                Log.error(LogType.NETWORKING, "Could not read from client #" + client.id + ":  " + ex.getMessage());

                //Log stack trace if verbose logging is enabled
                for(StackTraceElement e : ex.getStackTrace())
                {
                    if(NetworkHandler.verboseLogging) Log.error(LogType.NETWORKING, e.toString());
                }

                client.disconnect();
                break;
            }
        }
    }

    public void send(String data)
    {
        //Sends data to all clients
        for(TcpClient client : clients)
        {
            client.writer.println(data);
        }
    }

    public void sendOnly(TcpClient client, String data)
    {
        //Sends data to only the specified client
        client.writer.println(data);
    }

    public void sendExcept(TcpClient client, String data)
    {
        //Sends to data to all clients except the specified one
        for(TcpClient c : clients)
        {
            if(c != client) c.writer.println(data);
        }
    }
}
