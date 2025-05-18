package de.sealcore.client.rendering.objects;

import de.sealcore.client.rendering.abstractions.Shader;
import de.sealcore.util.timing.DeltaTimer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

public class MeshRenderer {

    private Mesh mesh;
    private Shader shader;

    Matrix4f view;
    Matrix4f perspective;

    public MeshRenderer() {
        mesh = new Mesh();
        shader = new Shader("test_shader");


        view = new Matrix4f().lookAt(
                new Vector3f(5, 3, 5),
                new Vector3f(-0.5f,1,0),
                new Vector3f(0, 1, 0)
        );

        perspective = new Matrix4f().perspective(0.5f, 4f/3, 0.1f, 20f);


    }


    public void render() {

        mesh.bind();
        shader.use();

        shader.setUniformMat4("model", mesh.position);
        shader.setUniformMat4("perspective", perspective);
        shader.setUniformMat4("view", view);

        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 6*6);

    }





}
