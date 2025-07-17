package de.sealcore.client.ui.rendering.line;

import de.sealcore.client.Camera;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.abstractions.Shader;
import de.sealcore.client.ui.rendering.abstractions.VertexArrayLayout;
import de.sealcore.client.ui.rendering.abstractions.VertexBuffer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL33;

public class LineRenderer {

    private static Shader shader;

    private static Matrix4f perspective;

    private static Matrix4f viewRot;

    private static VertexBuffer buffer;

    public static void init() {
        perspective = new Matrix4f().perspective(1f, 1/Resolution.RATIO, 0.5f, 100f);

        buffer = new VertexBuffer(new float[0], new VertexArrayLayout().add(3).add(3));

        viewRot = new Matrix4f().lookAt(
                new Vector3f(0f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(0f,0f,1f)
        );
        shader = new Shader("shaders/mesh");

        GL33.glLineWidth(0.5f);

    }



    public static void setCamera(Camera camera) {
        shader.use();

        shader.setUniformMat4("perspective", perspective);
        shader.setUniformMat4("camera", camera.getView());
        shader.setUniformMat4("view_rot", viewRot);
    }


    public static void render(Matrix4f position, Vector3f s) {

        float[] v = new float[24 * 6];
        put(0, 0, 0, v, 0);
        put(0, s.y, 0, v, 1);
        put(0, s.y, 0, v, 2);
        put(s.x, s.y, 0, v, 3);
        put(s.x, s.y, 0, v, 4);
        put(s.x, 0, 0, v, 5);
        put(s.x, 0, 0, v, 6);
        put(0, 0, 0, v, 7);

        put(0, 0, s.z, v, 8);
        put(0, s.y, s.z, v, 9);
        put(0, s.y, s.z, v, 10);
        put(s.x, s.y, s.z, v, 11);
        put(s.x, s.y, s.z, v, 12);
        put(s.x, 0, s.z, v, 13);
        put(s.x, 0, s.z, v, 14);
        put(0, 0, s.z, v, 15);

        put(0, 0, 0, v, 16);
        put(0, 0, s.z, v, 17);
        put(0, s.y, 0, v, 18);
        put(0, s.y, s.z, v, 19);
        put(s.x, s.y, s.z, v, 20);
        put(s.x, s.y, 0, v, 21);
        put(s.x, 0, 0, v, 22);
        put(s.x, 0, s.z, v, 23);

        shader.use();
        buffer.bind();
        buffer.setVertices(v);

        //GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 3);
    }


    private static void put(float x, float y, float z, float[] v, int i) {
        i*=6;
        v[i+0] = x;
        v[i+1] = y;
        v[i+2] = z;
        v[i+3] = 0f;
        v[i+4] = 0f;
        v[i+5] = 0f;
    }


}
