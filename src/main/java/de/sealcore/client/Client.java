package de.sealcore.client;

import de.sealcore.client.input.KeyBinds;
import de.sealcore.client.state.inventory.InventoryState;
import de.sealcore.client.state.world.GameState;
import de.sealcore.client.input.CamMoveInput;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.input.PlayerMoveInputState;
import de.sealcore.client.ui.overlay.DebugOverlay;
import de.sealcore.client.ui.rendering.Renderer;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.overlay.OverlayManager;
import de.sealcore.game.entities.general.Player;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.NetworkType;
import de.sealcore.networking.packets.PacketHandler;
import de.sealcore.util.timing.DeltaTimer;
import org.lwjgl.glfw.GLFWErrorCallback;

import org.lwjgl.opengl.*;


import java.time.LocalDateTime;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Client {

    public static Client instance;
    private long lastTime = 0;

    private long window;

    private Renderer renderer;
    public Camera camera;
    public GameState gameState;
    public InventoryState inventoryState;

    private Client() {
        //MainMenu main = new MainMenu();
        //main.setVisible(true);

        init();
    }

    private void init()
    {
        Client.instance = this;

        while(!NetworkHandler.init(NetworkType.CLIENT)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        GLFWErrorCallback.createPrint(System.err);

        if(!glfwInit()) throw new IllegalStateException("Failed to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(Resolution.WIDTH, Resolution.HEIGHT, "SealCore (dev)", NULL, NULL);
        if(window == NULL) throw new RuntimeException("Failed to create window");

        glfwMakeContextCurrent(window);

        glfwShowWindow(window);

        InputHandler.init(window);

        GL.createCapabilities();

        glfwSwapInterval(0);

        gameState = new GameState();
        inventoryState = new InventoryState(Player.WEAPON_SLOTS, Player.AMMO_SLOTS, Player.MAT_SLOTS, Player.UNI_SLOTS);

        renderer = new Renderer(gameState);

        camera = new Camera();


        OverlayManager.init();

    }

    private void loop() {

        glClearColor(0.5f, 0.88f, 0.95f, 0.0f);

        DeltaTimer.start();

        while ( !glfwWindowShouldClose(window) && !InputHandler.isPressed(KeyBinds.EXIT)) {

            double dt = DeltaTimer.getDeltaTime();

            int queueSize = PacketHandler.getQueueSize();
            for(int i = 0; i < queueSize; i++)
            {
                PacketHandler.handleNext();
            }

            camera.update(CamMoveInput.generate(), dt);
            if(!InputHandler.camMode) PlayerMoveInputState.update();

            camera.updateTargeted();

            renderer.render(camera);
            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    public static void main() {
        Client client = new Client();

        client.loop();
    }

}
