package de.sealcore.client.rendering.renderer;


import de.sealcore.client.Camera;
import de.sealcore.client.gamestate.GameState;
import de.sealcore.client.rendering.abstractions.*;
import de.sealcore.client.rendering.objects.Mesh;
import de.sealcore.client.rendering.objects.MeshRenderer;
import de.sealcore.util.timing.DeltaTimer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    Shader shader;

    MeshRenderer meshRenderer;

    GameState game;

    public Renderer(GameState gameState) {
        glEnable(GL_DEPTH_TEST);

        this.game = gameState;

        shader = new Shader("test_shader");

        meshRenderer = new MeshRenderer();


    }


    public void render(Camera camera) {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        meshRenderer.setCamera(camera);

        game.render(meshRenderer);

    }



}
