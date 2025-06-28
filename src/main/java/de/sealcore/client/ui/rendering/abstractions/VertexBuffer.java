package de.sealcore.client.ui.rendering.abstractions;

import static org.lwjgl.opengl.GL33.*;

public class VertexBuffer {


    private int vao;
    private int vbo;

    public VertexBuffer(float[] vertices, VertexArrayLayout arrayLayout) {
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        arrayLayout.set();

    }

    public void setVertices(float[] vertices) {
        bind();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
    }


    public void bind() {
        glBindVertexArray(vao);
    }


}
