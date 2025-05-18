package de.sealcore.client.rendering.renderer;


import de.sealcore.client.rendering.abstractions.*;
import de.sealcore.client.rendering.objects.MeshRenderer;
import de.sealcore.util.timing.DeltaTimer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    Shader shader;

    MeshRenderer meshRenderer;


    public Renderer() {
        glEnable(GL_DEPTH_TEST);

        shader = new Shader("test_shader");

        meshRenderer = new MeshRenderer();

    }


    public void render() {

        meshRenderer.render();


    }



}
