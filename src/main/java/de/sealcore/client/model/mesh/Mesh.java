package de.sealcore.client.model.mesh;

import de.sealcore.client.rendering.abstractions.VertexArrayLayout;
import de.sealcore.client.rendering.abstractions.VertexBuffer;
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

    public int size() {
        return size;
    }

    public void setPosition(double x, double y, double z) {
        position = new Matrix4f().translate((float) x, (float)y, (float)z);
    }

    public void bind() {
        buffer.bind();
    }


}
