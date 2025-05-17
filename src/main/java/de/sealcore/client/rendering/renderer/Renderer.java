package de.sealcore.client.rendering.renderer;


import de.sealcore.client.rendering.abstractions.*;
import de.sealcore.util.timing.DeltaTimer;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    VertexBuffer buffer;
    Shader shader;

    public Renderer() {
        float[] v = {
                0.0f, 0.5f, 1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, -0.5f, 0.0f, 0.0f, 1.0f
        };
        var layout = new VertexArrayLayout()
                .add(2).add(3);
        buffer = new VertexBuffer(v, layout);

        shader = new Shader("test_shader");
    }


    public void render() {
        buffer.bind();
        shader.use();

        shader.setUniformFloat("brightness", 0.5f + (float)(0.5*Math.sin(0.4 * DeltaTimer.getCurrentTime())));

        float o = 0.4f * (float)Math.sin(DeltaTimer.getCurrentTime());
        shader.setUniformVec3("offset", new Vector3f(o, o, 0f));

        glDrawArrays(GL_TRIANGLES, 0, 3);
    }



}
