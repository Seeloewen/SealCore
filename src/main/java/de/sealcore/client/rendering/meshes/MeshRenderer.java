package de.sealcore.client.rendering.meshes;

import de.sealcore.client.Camera;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.rendering.Resolution;
import de.sealcore.client.rendering.abstractions.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL33;

public class MeshRenderer {

    private static Shader shader;

    private static Matrix4f perspective;

    private static Matrix4f viewRot;


    public static void init() {
        shader = new Shader("shaders/mesh");




        perspective = new Matrix4f().perspective(0.5f, 4f/3, 0.5f, 100f);

        viewRot = new Matrix4f().lookAt(
                new Vector3f(0f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(0f,0f,1f)
        );

    }

    public static void setCamera(Camera camera) {
        shader.use();

        shader.setUniformMat4("perspective", perspective);
        shader.setUniformMat4("camera", camera.getView());
        shader.setUniformMat4("view_rot", viewRot);
    }

    public static void render(Mesh mesh) {

        mesh.bind();

        shader.setUniformMat4("model", mesh.position);

        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, mesh.size());

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
