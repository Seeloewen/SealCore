package de.sealcore.client.ui.rendering.mesh;

import de.sealcore.client.Camera;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.model.loading.MeshLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.abstractions.Shader;
import de.sealcore.client.ui.rendering.abstractions.VertexArray;
import de.sealcore.client.ui.rendering.abstractions.VertexBufferLayout;
import de.sealcore.util.logging.Log;
import de.sealcore.util.logging.LogType;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;

public class MeshRenderer {

    static class Buffer {
        static final int SIZE = 256;
        int count;
        float[] vertices;

        String id;

        Buffer(String id) {
            vertices = new float[SIZE * 16];
            count = 0;
            this.id = id;
        }

        void addMatrix(Matrix4f value) {
            if(count >= 64) flush();
            try (MemoryStack stack = MemoryStack.stackPush()) {
                var f = value.get(stack.mallocFloat(16));
                for(int i = 0; i < 16; i++) {
                    vertices[count*16 + i] = f.get(i);
                }
                count++;
            }
        }


        void flush() {
            MeshRenderer.drawMesh(id, vertices, count);
            vertices = new float[SIZE*16];
            count = 0;
        }
    }

    private static HashMap<String, Buffer> instanceBuffers;

    private static Shader shader;

    private static Matrix4f perspective;

    private static Matrix4f viewRot;

    private static HashMap<String, Mesh> loadedMeshes;

    private static VertexArray vertexArray;

    public static void init() {
        shader = new Shader("shaders/mesh");

        loadedMeshes = new HashMap<>();

        instanceBuffers = new HashMap<>();

        refreshProjection();

        viewRot = new Matrix4f().lookAt(
                new Vector3f(0f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(0f,0f,1f)
        );

        MeshLoader.loadMeshes();

        vertexArray = new VertexArray(new VertexBufferLayout[]{
                new VertexBufferLayout(new int[]{3, 3}, false),
                new VertexBufferLayout(new int[]{4, 4, 4, 4}, true)
        });

    }

    public static void refreshProjection() {
        perspective = new Matrix4f().perspective(1f, 1/Resolution.RATIO, 0.1f, 150f);
    }

    public static void setCamera(Camera camera) {
        shader.use();

        shader.setUniformMat4("perspective", perspective);
        shader.setUniformMat4("camera", camera.getView());
        shader.setUniformMat4("view_rot", viewRot);
    }

    public static void addMesh(String meshID, Mesh mesh) {
        loadedMeshes.put(meshID, mesh);
    }

    private static void drawMesh(String meshID, float[] positionMatrices, int count) {
        Mesh m = loadedMeshes.get(meshID);
        vertexArray.setVertices(0, m.getVertices());
        vertexArray.setVertices(1, positionMatrices);
        vertexArray.bind();
        shader.use();
        GL33.glDrawArraysInstanced(GL33.GL_TRIANGLES, 0, m.getSize(), count);
    }

    public static void render(String meshID, Matrix4f position) {
        var buffer = instanceBuffers.get(meshID);
        if(buffer == null) {
            instanceBuffers.put(meshID, new Buffer(meshID));
            render(meshID, position);
        } else {
            buffer.addMatrix(position);
        }
    }

    public static void flush() {
        for(Buffer buffer : instanceBuffers.values()) {
            buffer.flush();
        }
    }

    public static Vector3f getMouseRayDir() {
        Vector4f ray_clip = new Vector4f(
                Resolution.xToScreen((int) InputHandler.mouseX),
                Resolution.yToScreen((int) InputHandler.mouseY),
                -1f, 1f
        );
        Vector4f ray_eye = new Matrix4f(perspective).invert().transform(ray_clip);
        return null;
    }



}
