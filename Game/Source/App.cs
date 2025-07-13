using System;

using SealCore.Networking;
using SealCore.client;

namespace SealCore
{
    internal class App
    {

        public static void Main(string[] args)
        {
            Console.WriteLine("Hello Seal");

            switch (args[0].ToLower())
            {
                case "client":
                    Client client = new Client();
                    client.Run();
                    break;
                case "server":
                    Console.WriteLine("server not implemented");
                    break;
                default:
                    Console.WriteLine("invalid run mode");
                    break;
                    
            }
            
            Console.WriteLine("program finished");
        }

    }
}
