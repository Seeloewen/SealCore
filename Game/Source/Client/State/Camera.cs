using System;
using SealCore.Client.Input;
using SealCore.Client.rendering;
using SealCore.Client.rendering._3d;

namespace SealCore.Client.State;

public class Camera
{
    private const float MOUSE_SENSITIVITY = 0.008f;
    private const float MOVE_SPEED = 1.0f;
    private const float PI = 3.14159263538979323846f;
    
    private float angleHor;
    private float angleVert;
    private float x, y, z;




    internal void Update(float dt)
    {
        angleHor += InputHandler.mouseMovement.x * MOUSE_SENSITIVITY;
        if (angleHor > PI) angleHor -= 2 * PI;
        if (angleHor < -PI) angleHor += 2 * PI;
        angleVert += InputHandler.mouseMovement.y * MOUSE_SENSITIVITY;
        if(angleVert > PI/2) angleVert = PI/2;
        if (angleVert < -PI / 2) angleVert = -PI / 2;

        float mx = 0, my = 0;
        if (InputHandler.IsPressed(KeyBinds.MOVE_FORWARD)) mx += 1;
        if (InputHandler.IsPressed(KeyBinds.MOVE_BACKWARD)) mx -= 1;
        if (InputHandler.IsPressed(KeyBinds.MOVE_LEFT)) my += 1;
        if(InputHandler.IsPressed(KeyBinds.MOVE_RIGHT)) my -= 1;
        if(InputHandler.IsPressed(KeyBinds.MOVE_UP)) z += dt * MOVE_SPEED;
        if(InputHandler.IsPressed(KeyBinds.MOVE_DOWN)) z -= dt * MOVE_SPEED;

        float dx = mx * float.Cos(angleHor) + my * float.Sin(angleHor);
        float dy = mx * float.Sin(angleHor) - my * float.Cos(angleHor);
        dx *= dt * MOVE_SPEED;
        dy *= dt * MOVE_SPEED;
        x += dx;
        y += dy;
    }

    internal void SetCamera(Renderer renderer)
    {
        renderer.meshRenderer.SetCamera(x, y, z, -angleHor, -angleVert);
    }









}