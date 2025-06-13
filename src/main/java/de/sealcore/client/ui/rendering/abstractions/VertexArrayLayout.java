package de.sealcore.client.ui.rendering.abstractions;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL33.*;


public class VertexArrayLayout {

    ArrayList<Integer> counts;
    int stride;

    public VertexArrayLayout() {
        counts = new ArrayList<>();
    }

    //only floats implemented for now
    public VertexArrayLayout add(int count) {
        assert(count >= 1 && count <= 4);
        counts.add(count);
        stride += 4 * count;
        return this;
    }

    void set() {
        int p = 0;
        for(int i = 0; i < counts.size(); i++) {
            int c = counts.get(i);
            glEnableVertexAttribArray(i);
            glVertexAttribPointer(i, c, GL_FLOAT, false, stride, p);
            p += c * 4;

        }
    }


}
