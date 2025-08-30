
using OpenTK.Graphics.OpenGL4;

namespace SealCore.Client.rendering;

public class PrimitiveRenderer
{
    
    private Shader shader;
    private VertexArray vao;

    internal PrimitiveRenderer()
    {
        shader = new Shader("primitive");
        vao = new VertexArray(new[]
        {
            new VBLayout(new[] { new VBElement(3), new VBElement(3) })
        });
        
    }



    public void DrawRectangle()
    {
        float[] v =
        {
            .5f, .5f, .5f, 1f, 0f, 0f,
            -.5f, .5f, .5f, 0f, 0f, 1f,
            .5f, -.5f, .5f, 1f, 0f, 0f,
            -.5f, .5f, .5f, 0f, 0f, 1f,
            .5f, -.5f, .5f, 1f, 0f, 0f,
            -.5f, -.5f, .5f, 0f, 0f, 1f,
        };

        vao.FillData(0, v);
        shader.Use();
        GL.DrawArrays(PrimitiveType.Triangles, 0, 6);
    }
    
    





}