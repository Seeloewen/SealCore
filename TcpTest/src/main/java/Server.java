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
        socket = new ServerSocket(5000);
        clientSocket = socket.accept();

        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
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
            }
        }
    }

    void send(String mes)
    {

    }
}
