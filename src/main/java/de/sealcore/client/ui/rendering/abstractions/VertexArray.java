package de.sealcore.client.ui.rendering.abstractions;

import static org.lwjgl.opengl.GL33.*;

public class VertexArray {

    private int vao;
    private int[] vbos;

    public VertexArray(VertexBufferLayout[] layouts) {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbos = new int[layouts.length];

        int iAttrib = 0;
        for(int iBuffer = 0; iBuffer < layouts.length; iBuffer++) {
            int vbo = glGenBuffers();
            vbos[iBuffer] = vbo;
            setVertices(iBuffer, new float[0]);
            var layout = layouts[iBuffer];
            int stride = sum(layout.counts()) * 4;
            int offset = 0;
            for(int count : layout.counts()) {
                glEnableVertexAttribArray(iAttrib);
                glVertexAttribPointer(iAttrib, count, GL_FLOAT, false, stride, offset);
                offset += count * 4;
                glVertexAttribDivisor(iAttrib, layout.instanced() ? 1 : 0);
                iAttrib++;
            }

        }

    }

    public void setVertices(int vboIndex, float[] vertices) {
        int vbo = vbos[vboIndex];
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

    }



    public void bind() {
        glBindVertexArray(vao);
    }

    private static int sum(int[] values) {
        int sum = 0;
        for (int v : values) {
            sum += v;
        }
        return sum;
    }
}
