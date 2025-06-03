package de.sealcore.client.rendering.objects;

import de.sealcore.client.rendering.abstractions.VertexArrayLayout;
import de.sealcore.client.rendering.abstractions.VertexBuffer;
import de.sealcore.util.Color;
import org.joml.Matrix4f;

public class Mesh {

    public Matrix4f position;

    private VertexBuffer buffer;
    private final int size;

    public Mesh(MeshSide[] sides) {

        float[] vertices = new float[36 * sides.length];
        for(int i = 0; i < sides.length; i++) {
            float[] sideVertices = sides[i].getVertices();
            for(int j = 0; j < 36; j++) {
                vertices[36*i + j] = sideVertices[j];
            }
        }

        VertexArrayLayout layout = new VertexArrayLayout().add(3).add(3);
        buffer = new VertexBuffer(vertices, layout);
        size = vertices.length;

        position = new Matrix4f();
    }

    int size() {
        return size;
    }


    public void bind() {
        buffer.bind();
    }


}
