using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using OpenTK.Graphics.OpenGL4;
using SealCore.Client.rendering._3d;

namespace SealCore.Client.rendering
{
    public class Renderer
    {

        public PrimitiveRenderer primitiveRenderer { get; init; }
        public MeshRenderer meshRenderer { get; init; }
        
        public Renderer()
        {
            GL.Enable(EnableCap.DepthTest);
            
            primitiveRenderer = new PrimitiveRenderer();
            meshRenderer = new MeshRenderer();
            
        }
        
        

    }
}
