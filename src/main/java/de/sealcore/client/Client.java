package de.sealcore.client;

import de.sealcore.client.input.CamMoveInput;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.rendering.renderer.Renderer;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.NetworkType;
import de.sealcore.networking.packets.PacketHandler;
import de.sealcore.util.timing.DeltaTimer;
import org.lwjgl.glfw.GLFWErrorCallback;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Client {

    private final long window;

    private Renderer renderer;
    private Camera camera;

    private Client() {

        NetworkHandler.init(NetworkType.CLIENT);

        GLFWErrorCallback.createPrint(System.err);

        if(!glfwInit()) throw new IllegalStateException("Failed to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(800, 600, "SealCore (dev)", NULL, NULL);
        if(window == NULL) throw new RuntimeException("Failed to create window");

        glfwMakeContextCurrent(window);

        glfwShowWindow(window);

        InputHandler.init(window);

        GL.createCapabilities();

        glfwSwapInterval(0);

        renderer = new Renderer();

        camera = new Camera();

    }

    private void loop() {

        glClearColor(0.2f, 0.2f, 0.2f, 0.0f);

        DeltaTimer.start();

        while ( !glfwWindowShouldClose(window) ) {

            double dt = DeltaTimer.getDeltaTime();

            int queueSize = PacketHandler.getQueueSize();
            for(int i = 0; i < queueSize; i++)
            {
                PacketHandler.handleNext();
            }

            camera.update(CamMoveInput.generate(), dt);


            renderer.render(camera);
            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

    }


    public static void main() {
        Client client = new Client();

        client.loop();
    }

}
