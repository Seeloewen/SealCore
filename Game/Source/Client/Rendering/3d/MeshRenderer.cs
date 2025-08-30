using System;
using OpenTK.Graphics.OpenGL4;
using OpenTK.Mathematics;

namespace SealCore.Client.rendering._3d;

public class MeshRenderer
{
    private Shader shader;
    private VertexArray vao;

    internal MeshRenderer()
    {
        shader = new Shader("mesh");
        vao = new VertexArray(new[]
        {
            new VBLayout(new[] {new VBElement(3), new VBElement(1)})
        });
    }


    public  void RenderCube(Cube cube)
    {
        Matrix4 perspective = Matrix4.CreatePerspectiveFieldOfView(1f, 16/9f, 0.1f, 100f);
        Matrix4 camera =  Matrix4.CreateTranslation(-5f, 0f, 0f) * Matrix4.CreateRotationX(90* Single.Pi/180) * Matrix4.CreateRotationY(-90 * Single.Pi/180);
        
        shader.SetUniform("perspective",  perspective);
        shader.SetUniform("camera", camera);
        shader.Use();
        
        var vertices = cube.getVertices();
        vao.FillData(0, vertices);
        
        GL.DrawArrays(PrimitiveType.Triangles, 0, vertices.Length / 4);
        
    }
    
    
    
    
    
}