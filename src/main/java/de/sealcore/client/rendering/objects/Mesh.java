package de.sealcore.client.rendering.objects;

import de.sealcore.client.rendering.abstractions.VertexArrayLayout;
import de.sealcore.client.rendering.abstractions.VertexBuffer;
import org.joml.Matrix4f;

public class Mesh {

    public Matrix4f position;

    private VertexBuffer buffer;


    public Mesh() {

        VertexArrayLayout layout = new VertexArrayLayout().add(3).add(3);
        buffer = new VertexBuffer(v, layout);

        position = new Matrix4f();
    }

    public void bind() {
        buffer.bind();
    }

    //stored here because i cant collapse it in my ide
    private final float[] v = {
            0f, 0f, 0f, 0.3f, 0.0f, 0.0f,
            0f, 0f, 1f, 0.3f, 0.0f, 0.0f,
            0f, 1f, 0f, 0.3f, 0.0f, 0.0f,
            0f, 0f, 1f, 0.3f, 0.0f, 0.0f,
            0f, 1f, 0f, 0.3f, 0.0f, 0.0f,
            0f, 1f, 1f, 0.3f, 0.0f, 0.0f,

            1f, 0f, 0f, 0.4f, 0.0f, 0.0f,
            1f, 0f, 1f, 0.4f, 0.0f, 0.0f,
            1f, 1f, 0f, 0.4f, 0.0f, 0.0f,
            1f, 0f, 1f, 0.4f, 0.0f, 0.0f,
            1f, 1f, 0f, 0.4f, 0.0f, 0.0f,
            1f, 1f, 1f, 0.4f, 0.0f, 0.0f,

            0f, 0f, 0f, 0.5f, 0.0f, 0.0f,
            0f, 0f, 1f, 0.5f, 0.0f, 0.0f,
            1f, 0f, 0f, 0.5f, 0.0f, 0.0f,
            0f, 0f, 1f, 0.5f, 0.0f, 0.0f,
            1f, 0f, 0f, 0.5f, 0.0f, 0.0f,
            1f, 0f, 1f, 0.5f, 0.0f, 0.0f,

            0f, 1f, 0f, 0.6f, 0f, 0f,
            0f, 1f, 1f, 0.6f, 0f, 0f,
            1f, 1f, 0f, 0.6f, 0f, 0f,
            0f, 1f, 1f, 0.6f, 0f, 0f,
            1f, 1f, 0f, 0.6f, 0f, 0f,
            1f, 1f, 1f, 0.6f, 0f, 0f,

            0f, 0f, 0f, 0.7f, 0f, 0f,
            0f, 1f, 0f, 0.7f, 0f, 0f,
            1f, 0f, 0f, 0.7f, 0f, 0f,
            0f, 1f, 0f, 0.7f, 0f, 0f,
            1f, 0f, 0f, 0.7f, 0f, 0f,
            1f, 1f, 0f, 0.7f, 0f, 0f,

            0f, 0f, 1f, 0.8f, 0f, 0f,
            0f, 1f, 1f, 0.8f, 0f, 0f,
            1f, 0f, 1f, 0.8f, 0f, 0f,
            0f, 1f, 1f, 0.8f, 0f, 0f,
            1f, 0f, 1f, 0.8f, 0f, 0f,
            1f, 1f, 1f, 0.8f, 0f, 0f,
    };

}
