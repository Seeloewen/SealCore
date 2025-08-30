using System;
using System.IO;
using System.Reflection;

namespace SealCore.Util.Resources;

public static class ResourceManager
{





    public static string ReadFile(string filename)
    {
        var assembly = Assembly.GetExecutingAssembly();
        using Stream stream = assembly.GetManifestResourceStream($"Game.Resources.{filename}");
        using StreamReader reader = new StreamReader(stream);
        return reader.ReadToEnd();
    }
    
    
    
    
}