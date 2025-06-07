package de.sealcore.client.rendering.meshes;

import de.sealcore.client.Camera;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.rendering.abstractions.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL33;

public class MeshRenderer {

    private Shader shader;

    Matrix4f perspective;

    Matrix4f viewRot;


    public MeshRenderer() {
        shader = new Shader("shaders/mesh");




        perspective = new Matrix4f().perspective(0.5f, 4f/3, 0.5f, 100f);

        viewRot = new Matrix4f().lookAt(
                new Vector3f(0f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(0f,0f,1f)
        );

    }

    public void setCamera(Camera camera) {
        shader.use();

        shader.setUniformMat4("perspective", perspective);
        shader.setUniformMat4("camera", camera.getView());
        shader.setUniformMat4("view_rot", viewRot);
    }

    public void render(Mesh mesh) {

        mesh.bind();

        shader.setUniformMat4("model", mesh.position);

        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, mesh.size());

    }





}
