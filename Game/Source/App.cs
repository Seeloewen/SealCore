using System;

using SealCore.Networking;

namespace SealCore
{
    internal class App
    {

        public static void Main()
        {
            Console.WriteLine("Hello Seal");
            TcpClient.hello();
        }

    }
}
