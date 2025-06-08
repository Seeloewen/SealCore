package de.sealcore.client.rendering.ui.primitives;

import de.sealcore.client.rendering.Resolution;
import de.sealcore.client.rendering.abstractions.Shader;
import de.sealcore.client.rendering.abstractions.VertexArrayLayout;
import de.sealcore.client.rendering.abstractions.VertexBuffer;
import de.sealcore.util.Color;
import org.lwjgl.opengl.GL33;

public class PrimitiveRenderer {


    private static Shader shader;
    private static VertexBuffer buffer;


    public static void init() {
        shader = new Shader("shaders/primitive");
        buffer = new VertexBuffer(new float[36], new VertexArrayLayout().add(3).add(3));
    }


    public static void drawRectangle(Rectangle rec, Color c, float z) {
        float[] vertices = new float[36];
        put(rec.x1(), rec.y1(), c, z, vertices, 0);
        put(rec.x2(), rec.y1(), c, z, vertices, 1);
        put(rec.x1(), rec.y2(), c, z, vertices, 2);
        put(rec.x2(), rec.y1(), c, z, vertices, 3);
        put(rec.x1(), rec.y2(), c, z, vertices, 4);
        put(rec.x2(), rec.y2(), c, z, vertices, 5);
        shader.use();
        buffer.setVertices(vertices);
        buffer.bind();
        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 6);
    }




    private static void put(int x, int y, Color c, float z, float[] v, int index) {
        int i = index * 6;
        v[i+0] = Resolution.xToScreen(x);
        v[i+1] = Resolution.yToScreen(y);
        v[i+2] = z;
        v[i+3] = c.r();
        v[i+4] = c.g();
        v[i+5] = c.b();
    }


}
