using System.Diagnostics;
using System.Runtime.InteropServices.ComTypes;
using OpenTK.Windowing.GraphicsLibraryFramework;

namespace SealCore.Client.Input;

public static class InputHandler
{

    public static (int x, int y) mouseMovement;
    internal static (int x, int y) mousePosition;

    private static bool[] pressedKeys;


    public static void StartFrame()
    {
        mouseMovement = (0, 0);
        GLFW.PollEvents();
    }

    public static unsafe void Init(Window* window)
    {
        GLFW.SetInputMode(window, CursorStateAttribute.Cursor, CursorModeValue.CursorDisabled);
        GLFW.SetCursorPosCallback(window, (_, x, y) =>
        {
            mouseMovement = (mouseMovement.x + (int)x - mousePosition.x, mouseMovement.y + (int)y - mousePosition.y);
            mousePosition = ((int)x, (int)y);
        });
        GLFW.SetKeyCallback(window, (_, k, s, a, m) =>
        {
            if (KeyBinds.keyBinds.TryGetValue(k, out var key))
            {
                pressedKeys[key] = a != InputAction.Release;
            }
        });
        GLFW.GetCursorPos(window, out double x, out double y);
        mousePosition = ((int)x, (int)y);

        pressedKeys = new bool[KeyBinds.LENGTH];
        KeyBinds.SetDefault();

    }

    public static bool IsPressed(int keybind)
    {
        Debug.Assert(keybind >= 0 && keybind < KeyBinds.LENGTH);
        return pressedKeys[keybind];
    }

}