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
        socket = new Socket("localhost", 5000);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
            }
        }
    }

    void send(String mes)
    {
        writer.write(mes);
    }
}
