using System;
using OpenTK.Graphics.OpenGL4;
using OpenTK.Mathematics;

namespace SealCore.Client.rendering._3d;

public class MeshRenderer
{
    private Shader shader;
    private VertexArray vao;

    private Matrix4 proj = Matrix4.CreatePerspectiveFieldOfView(1f, 16 / 9f, 0.1f, 100f);
    private Matrix4 camera;
    
    internal MeshRenderer()
    {
        shader = new Shader("mesh");
        vao = new VertexArray(new[]
        {
            new VBLayout(new[] {new VBElement(3), new VBElement(1)})
        });
    }

    public void SetCamera(float x, float y, float z, float angleHor, float angleVer)
    {
        camera =  Matrix4.CreateTranslation(x, y, z)
                          * Matrix4.CreateRotationZ(angleHor) * Matrix4.CreateRotationY(angleVer)
                          * Matrix4.CreateRotationX(90* Single.Pi/180) * Matrix4.CreateRotationY(-90 * Single.Pi/180);
    }

    public  void RenderCube(Cube cube)
    {
        
        shader.SetUniform("perspective", proj);
        shader.SetUniform("camera", camera);
        shader.Use();
        
        var vertices = cube.getVertices();
        vao.FillData(0, vertices);
        
        GL.DrawArrays(PrimitiveType.Triangles, 0, vertices.Length / 4);
        
    }
    
    
    
    
    
}