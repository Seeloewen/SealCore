package de.sealcore.client.rendering.renderer;


import de.sealcore.client.Camera;
import de.sealcore.client.gamestate.GameState;
import de.sealcore.client.rendering.abstractions.*;
import de.sealcore.client.rendering.meshes.MeshRenderer;
import de.sealcore.client.rendering.ui.primitives.PrimitiveRenderer;
import de.sealcore.client.rendering.ui.primitives.Rectangle;
import de.sealcore.util.Color;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    MeshRenderer meshRenderer;

    GameState game;

    public Renderer(GameState gameState) {
        glEnable(GL_DEPTH_TEST);

        this.game = gameState;

        PrimitiveRenderer.init();

        meshRenderer = new MeshRenderer();


    }


    public void render(Camera camera) {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        meshRenderer.setCamera(camera);

        game.render(meshRenderer);


        PrimitiveRenderer.drawRectangle(new Rectangle(100, 100, 200, 200), new Color(0.7f, 0.3f, 0.1f), 0f);

    }



}
