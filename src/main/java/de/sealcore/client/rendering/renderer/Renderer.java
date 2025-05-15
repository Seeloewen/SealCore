package de.sealcore.client.rendering.renderer;


import de.sealcore.client.rendering.abstractions.*;
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
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }



}
