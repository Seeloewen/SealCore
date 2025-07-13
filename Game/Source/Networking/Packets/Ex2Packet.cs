using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SealCore.Networking
{
    [GeneratePacket("client")]
    internal class Ex2Packet : Packet
    {

        internal int x;
        internal int y;


    }
}
