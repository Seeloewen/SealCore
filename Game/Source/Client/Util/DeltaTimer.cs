using OpenTK.Windowing.GraphicsLibraryFramework;

namespace SealCore.Client.Util;

public class DeltaTimer
{
    private float lastTime;



    public DeltaTimer()
    {
        lastTime = 0;
    }


    public float GetDeltaTime()
    {
        float newTime = (float)GLFW.GetTime();
        float dt = newTime - lastTime;
        lastTime = newTime;
        return dt;
    }


}