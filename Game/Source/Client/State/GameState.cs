using SealCore.Client.rendering;

namespace SealCore.Client.State;

public class GameState
{
    private Camera camera;

    public GameState()
    {
        camera = new Camera();
    }


    public void Update(float dt)
    {
        camera.Update(dt);
    }


    public void Render(Renderer renderer)
    {
        camera.SetCamera(renderer);
    }





}