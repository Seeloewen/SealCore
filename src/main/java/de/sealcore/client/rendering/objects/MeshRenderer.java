package de.sealcore.client.rendering.objects;

import de.sealcore.client.Camera;
import de.sealcore.client.rendering.abstractions.Shader;
import de.sealcore.util.timing.DeltaTimer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

public class MeshRenderer {

    private Mesh mesh;
    private Shader shader;

    Matrix4f perspective;

    Matrix4f viewRot;

    public MeshRenderer() {
        mesh = new Mesh();
        shader = new Shader("test_shader");




        perspective = new Matrix4f().perspective(0.5f, 4f/3, 0.1f, 20f);

        viewRot = new Matrix4f().lookAt(
                new Vector3f(0f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(0f,0f,1f)
        );

    }


    public void render(Camera camera) {

        mesh.bind();
        shader.use();

        shader.setUniformMat4("model", mesh.position);
        shader.setUniformMat4("perspective", perspective);
        shader.setUniformMat4("camera", camera.getView());
        shader.setUniformMat4("view_rot", viewRot);

        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 6*6);

    }





}
