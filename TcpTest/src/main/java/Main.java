import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Wello Horld");

        if(args[0].equals("s"))
        {
            System.out.println("Server");
        }
        else if(args[0].equals("c"))
        {
            System.out.println("Client");
        }

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
    }
}
