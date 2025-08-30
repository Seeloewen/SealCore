using System;
using OpenTK.Graphics.OpenGL4;
using OpenTK.Mathematics;
using SealCore.Util.Resources;

namespace SealCore.Client.rendering;

internal class Shader
{
    private int handle;


    internal Shader(string name)
    {
        int vert = CreateShader(ShaderType.VertexShader);
        int frag = CreateShader(ShaderType.FragmentShader);
        handle = GL.CreateProgram();
        GL.AttachShader(handle, vert);
        GL.AttachShader(handle, frag);
        GL.LinkProgram(handle);
        GL.GetProgram(handle, GetProgramParameterName.LinkStatus, out int success);
        if (success == 0)
        {
            string msg = GL.GetProgramInfoLog(handle);
            throw new Exception(msg);
        }
        GL.DeleteShader(vert);
        GL.DeleteShader(frag);
    }

    public void Use()
    {
        GL.UseProgram(handle);
    }


    public void SetUniform(string name, Matrix4 matrix)
    {
        Use();
        int location = GL.GetUniformLocation(handle, name);
        GL.UniformMatrix4(location, false, ref matrix);
    }
    
    

    private static int CreateShader(ShaderType type)
    {
        string file = "shaders.mesh." + type switch
        {
            ShaderType.VertexShader => "vertex.glsl",
            ShaderType.FragmentShader => "fragment.glsl",
            _ => throw new Exception("Unknown shader type.")
        };
        string source = ResourceManager.ReadFile(file);
        
        int handle = GL.CreateShader(type);
        GL.ShaderSource(handle, source);
        GL.CompileShader(handle);
        GL.GetShader(handle, ShaderParameter.CompileStatus, out int success);
        if(success == 0) {
            string msg = GL.GetShaderInfoLog(handle);
            throw new Exception("failed to compile shader: " + msg);
        }
        return handle;
    } 
    
    
    
    
}