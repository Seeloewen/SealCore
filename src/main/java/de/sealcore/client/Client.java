package de.sealcore.client;

import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.NetworkType;
import de.sealcore.networking.packets.PacketHandler;
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

        GL.createCapabilities();
    }

    private void loop() {

        glClearColor(0.2f, 0.9f, 0.2f, 0.0f);

        while ( !glfwWindowShouldClose(window) ) {
            int queueSize = PacketHandler.getQueueSize();
            for(int i = 0; i < queueSize; i++)
            {
                PacketHandler.handleNext();
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

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
