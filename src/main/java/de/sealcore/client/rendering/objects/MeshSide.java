package de.sealcore.client.rendering.objects;

import de.sealcore.util.Color;

public record MeshSide(MeshVertex v0, MeshVertex v1, MeshVertex v2, MeshVertex v3, Color color) {

    float[] getVertices() {
        float[] vertices = new float[6*6];
        addVertices(vertices,0, v0, color);
        addVertices(vertices,6, v1, color);
        addVertices(vertices,12, v2, color);
        addVertices(vertices,18, v2, color);
        addVertices(vertices,24, v3, color);
        addVertices(vertices,30, v0, color);
        return vertices;
    }



    private static void addVertices(float[] vertices, int index, MeshVertex vertex, Color color) {
        vertices[index] = vertex.x();
        vertices[index+1] = vertex.y();
        vertices[index+2] = vertex.z();
        vertices[index+3] = color.r();
        vertices[index+4] = color.g();
        vertices[index+5] = color.b();
    }

}
