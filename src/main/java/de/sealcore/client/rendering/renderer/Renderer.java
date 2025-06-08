package de.sealcore.client.rendering.renderer;


import de.sealcore.client.Camera;
import de.sealcore.client.gamestate.GameState;
import de.sealcore.client.rendering.abstractions.*;
import de.sealcore.client.rendering.meshes.MeshRenderer;
import de.sealcore.client.rendering.ui.primitives.PrimitiveRenderer;
import de.sealcore.client.rendering.ui.primitives.Rectangle;
import de.sealcore.client.rendering.ui.text.TextRenderer;
import de.sealcore.client.rendering.ui.texture.TextureRenderer;
import de.sealcore.util.Color;

import java.io.IOException;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    MeshRenderer meshRenderer;

    GameState game;

    public Renderer(GameState gameState) {
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        this.game = gameState;

        TextureRenderer.init();
        PrimitiveRenderer.init();
        TextRenderer.init();
        MeshRenderer.init();

        try {
            TextureRenderer.loadTexture("missing_texture", "Missing_Texture.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void render(Camera camera) {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        MeshRenderer.setCamera(camera);

        game.render();


        PrimitiveRenderer.drawRectangle(new Rectangle(100, 100, 200, 200), new Color(0.7f, 0.3f, 0.1f), 0f);


        TextRenderer.drawString(200, 200, 3, "Berr Hert", 0f);


    }



}
