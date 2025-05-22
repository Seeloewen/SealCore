package de.sealcore.client.rendering.objects;

import de.sealcore.client.rendering.abstractions.VertexArrayLayout;
import de.sealcore.client.rendering.abstractions.VertexBuffer;
import de.sealcore.util.Color;
import org.joml.Matrix4f;

public class Mesh {

    public Matrix4f position;

    private VertexBuffer buffer;


    protected Mesh(MeshSide[] sides) {

        float[] vertices = new float[36 * sides.length];
        for(int i = 0; i < sides.length; i++) {
            float[] sideVertices = sides[i].getVertices();
            for(int j = 0; j < 36; j++) {
                vertices[36*i + j] = sideVertices[j];
            }
        }

        VertexArrayLayout layout = new VertexArrayLayout().add(3).add(3);
        buffer = new VertexBuffer(vertices, layout);

        position = new Matrix4f();
    }

    public Mesh() {
        this(
                new MeshSide[]{
                        new MeshSide(
                                new MeshVertex(0f, 0f, 0f),
                                new MeshVertex(0f, 0f, 1f),
                                new MeshVertex(0f, 1f, 1f),
                                new MeshVertex(0f, 1f, 0f),
                                new Color(0.3f,0f,0f)
                        ),
                        new MeshSide(
                                new MeshVertex(1f, 0f, 0f),
                                new MeshVertex(1f, 0f, 1f),
                                new MeshVertex(1f, 1f, 1f),
                                new MeshVertex(1f, 1f, 0f),
                                new Color(0.7f,0f,0f)
                        ),
                        new MeshSide(
                                new MeshVertex(0f, 0f, 0f),
                                new MeshVertex(0f, 0f, 1f),
                                new MeshVertex(1f, 0f, 1f),
                                new MeshVertex(1f, 0f, 0f),
                                new Color(0f,0.3f,0f)
                        ),
                        new MeshSide(
                                new MeshVertex(0f, 1f, 0f),
                                new MeshVertex(0f, 1f, 1f),
                                new MeshVertex(1f, 1f, 1f),
                                new MeshVertex(1f, 1f, 0f),
                                new Color(0f,0.7f,0f)
                        ),
                        new MeshSide(
                                new MeshVertex(0f, 0f, 0f),
                                new MeshVertex(0f, 1f, 0f),
                                new MeshVertex(1f, 1f, 0f),
                                new MeshVertex(1f, 0f, 0f),
                                new Color(0f,0f,0.3f)
                        ),
                        new MeshSide(
                                new MeshVertex(0f, 0f, 1f),
                                new MeshVertex(0f, 1f, 1f),
                                new MeshVertex(1f, 1f, 1f),
                                new MeshVertex(1f, 0f, 1f),
                                new Color(0f,0f,0.7f)
                        )
                }
        );
    }

    public void bind() {
        buffer.bind();
    }


}
