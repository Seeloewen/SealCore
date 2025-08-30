using System;
using System.Net.Sockets;
using OpenTK.Graphics.OpenGL4;
using OpenTK.Windowing.GraphicsLibraryFramework;
using SealCore.Client.rendering;

namespace SealCore.Client
{
    
    public unsafe class Client
    {
        private TcpClient tcpClient { get; init; }
        private Renderer renderer { get; init; }
        private Window* window { get; init; }
        
        public Client()
        {
            if (!GLFW.Init()) throw new Exception("Failed to initialize GLFW");
            GLFW.WindowHint(WindowHintBool.Resizable, false);
            window = GLFW.CreateWindow(1300, 700, "SealCore Client 1.1-pre1", null, null);
            if(window == null) throw new Exception("Failed to create window");
            GLFW.MakeContextCurrent(window);
            GL.LoadBindings(new GLFWBindingsContext());

            renderer = new Renderer();

        }


        public void Run()
        {
            Cube cube = new Cube();
            
            GL.ClearColor(0.1f, 0.1f, 0.3f, 1.0f);
            
            while (!GLFW.WindowShouldClose(window))
            {
                GL.Clear(ClearBufferMask.ColorBufferBit | ClearBufferMask.DepthBufferBit);



                //renderer.primitiveRenderer.DrawRectangle();
                renderer.meshRenderer.RenderCube(cube);
                
                GLFW.SwapBuffers(window);
                GLFW.PollEvents();
            }
        }



    }
}
