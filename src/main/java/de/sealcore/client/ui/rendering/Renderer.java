package de.sealcore.client.ui.rendering;


import de.sealcore.client.Camera;
import de.sealcore.client.state.world.GameState;
import de.sealcore.client.ui.overlay.OverlayManager;
import de.sealcore.client.ui.rendering.line.LineRenderer;
import de.sealcore.client.ui.rendering.mesh.MeshRenderer;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.client.ui.rendering.texture.TextureRenderer;
import de.sealcore.util.Color;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLDebugMessageCallback;

import java.io.IOException;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    MeshRenderer meshRenderer;

    GameState game;

    public Renderer(GameState gameState) {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        GL43.glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
            System.err.println("GL Debug Message: " + GLDebugMessageCallback.getMessage(length, message));
        }, 0);


        this.game = gameState;

        TextureRenderer.init();
        PrimitiveRenderer.init();
        TextRenderer.init();
        MeshRenderer.init();
        LineRenderer.init();


    }


    public void render(Camera camera) {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        MeshRenderer.setCamera(camera);
        LineRenderer.setCamera(camera);


        game.render();

        glClear(GL_DEPTH_BUFFER_BIT);


        OverlayManager.render();

    }



}
