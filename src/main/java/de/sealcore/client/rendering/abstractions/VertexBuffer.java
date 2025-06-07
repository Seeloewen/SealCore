package de.sealcore.client.rendering.abstractions;

import static org.lwjgl.opengl.GL33.*;

public class VertexBuffer {


    private int vao;
    private int vbo;

    public VertexBuffer(float[] vertices, VertexArrayLayout arrayLayout) {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        arrayLayout.set();

    }

    public void setVertices(float[] vertices) {
        bind();
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
    }


    public void bind() {
        glBindVertexArray(vao);
    }


}
