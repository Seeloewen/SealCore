using System.Collections.Generic;
using OpenTK.Windowing.GraphicsLibraryFramework;

namespace SealCore.Client.Input;

public class KeyBinds
{
    public const int MOVE_FORWARD = 0;
    public const int MOVE_BACKWARD = 1;
    public const int MOVE_LEFT = 2;
    public const int MOVE_RIGHT = 3;
    public const int MOVE_UP = 4;
    public const int MOVE_DOWN = 5;

    internal const int LENGTH = 6;

    internal static Dictionary<Keys, int> keyBinds = new Dictionary<Keys, int>();

    internal static void SetDefault()
    {
        keyBinds.Add(Keys.W , MOVE_FORWARD);
        keyBinds.Add(Keys.S , MOVE_BACKWARD);
        keyBinds.Add(Keys.A , MOVE_LEFT);
        keyBinds.Add(Keys.D , MOVE_RIGHT);
        keyBinds.Add(Keys.Space , MOVE_UP);
        keyBinds.Add(Keys.LeftShift , MOVE_DOWN);
    }
    
}