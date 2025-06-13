package de.sealcore.client.ui.rendering.texture;

import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.abstractions.Shader;
import de.sealcore.client.ui.rendering.abstractions.Texture;
import de.sealcore.client.ui.rendering.abstractions.VertexArrayLayout;
import de.sealcore.client.ui.rendering.abstractions.VertexBuffer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import org.lwjgl.opengl.GL33;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class TextureRenderer {

    private static HashMap<String, Texture> loadedTextures;
    private static Shader shader;
    private static VertexBuffer buffer;

    public static void init() {
        shader = new Shader("shaders/texture");
        buffer = new VertexBuffer(new float[5*6], new VertexArrayLayout().add(3).add(2));
        loadedTextures = new HashMap<>();

        try {
            TextureRenderer.loadTexture("missing_texture", "Missing_Texture.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void loadTexture(String id, String path) throws IOException {
        BufferedImage image = ImageIO.read(TextureRenderer.class.getClassLoader().getResource(path));
        Texture texture = new Texture(image);
        loadedTextures.put(id, texture);
    }

    public static void drawTexture(String id, Rectangle rec, float z) {
        var v = new float[5*6];
        put(rec.x1(), rec.y1(), z, 0f, 1f, v ,0);
        put(rec.x2(), rec.y1(), z, 1f, 1f, v ,1);
        put(rec.x1(), rec.y2(), z, 0f, 0f, v ,2);
        put(rec.x2(), rec.y1(), z, 1f, 1f, v ,3);
        put(rec.x1(), rec.y2(), z, 0f, 0f, v ,4);
        put(rec.x2(), rec.y2(), z, 1f, 0f, v ,5);
        shader.use();
        buffer.setVertices(v);
        buffer.bind();
        loadedTextures.get(id).bind();
        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 6);
    }

    public static void drawTexture(String id, Rectangle rec, float s1, float t1, float s2, float t2, float z) {
        var v = new float[5*6];
        put(rec.x1(), rec.y1(), z, s1, t1, v ,0);
        put(rec.x2(), rec.y1(), z, s2, t1, v ,1);
        put(rec.x1(), rec.y2(), z, s1, t2, v ,2);
        put(rec.x2(), rec.y1(), z, s2, t1, v ,3);
        put(rec.x1(), rec.y2(), z, s1, t2, v ,4);
        put(rec.x2(), rec.y2(), z, s2, t2, v ,5);
        shader.use();
        buffer.setVertices(v);
        buffer.bind();
        loadedTextures.get(id).bind();
        GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 6);
    }

    private static void put(int x, int y, float z, float s, float t, float[] v, int i) {
        i *= 5;
        v[i+0] = Resolution.xToScreen(x);
        v[i+1] = Resolution.yToScreen(y);
        v[i+2] = z;
        v[i+3] = s;
        v[i+4] = 1-t;
    }


}
