package de.sealcore.client.ui.rendering.mesh;

import de.sealcore.client.Camera;
import de.sealcore.client.input.InputHandler;
import de.sealcore.client.model.loading.MeshLoader;
import de.sealcore.client.model.mesh.Mesh;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.abstractions.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL33;

import java.util.HashMap;

public class MeshRenderer {

    private static Shader shader;

    private static Matrix4f perspective;

    private static Matrix4f viewRot;

    private static HashMap<String, Mesh> loadedMeshes;

    public static void init() {
        shader = new Shader("shaders/mesh");

        loadedMeshes = new HashMap<>();


        refreshProjection();

        viewRot = new Matrix4f().lookAt(
                new Vector3f(0f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(0f,0f,1f)
        );


        loadMesh( "f:grass");
        loadMesh( "e:player" );
        loadMesh( "e:grassling");
        loadMesh( "f:water" );
        loadMesh( "b:spruce_tree" );
        loadMesh( "b:oak_tree");


    }

    public static void refreshProjection() {
        perspective = new Matrix4f().perspective(1f, 1/Resolution.RATIO, 0.1f, 100f);
    }

    public static void setCamera(Camera camera) {
        shader.use();

        shader.setUniformMat4("perspective", perspective);
        shader.setUniformMat4("camera", camera.getView());
        shader.setUniformMat4("view_rot", viewRot);
    }

    public static void loadMesh(String meshID) {
        Mesh m = MeshLoader.loadMesh(meshID);
        loadedMeshes.put(meshID, m);
    }

    public static void render(String meshID, Matrix4f position) {

        var m = loadedMeshes.get(meshID);

        m.bind();

        shader.use();
        shader.setUniformMat4("model", position);

        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, m.size());
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
