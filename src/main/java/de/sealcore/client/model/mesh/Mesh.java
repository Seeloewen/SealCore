package de.sealcore.client.model.mesh;

import de.sealcore.client.ui.rendering.abstractions.VertexArray;
import de.sealcore.client.ui.rendering.abstractions.VertexArrayLayout;
import de.sealcore.client.ui.rendering.abstractions.VertexBuffer;
import de.sealcore.client.ui.rendering.abstractions.VertexBufferLayout;
import org.joml.Matrix4f;

public class Mesh {


    float[] vertices;

    public Mesh(MeshSide[] sides) {

        vertices = new float[36 * sides.length];
        for(int i = 0; i < sides.length; i++) {
            float[] sideVertices = sides[i].getVertices();
            for(int j = 0; j < 36; j++) {
                vertices[36*i + j] = sideVertices[j];
            }
        }



    }

    public float[] getVertices() {
        return vertices;
    }

    public int getSize() {
        return vertices.length;
    }

}
