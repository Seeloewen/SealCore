package de.sealcore.client.rendering.ui.text;

import de.sealcore.client.rendering.Resolution;
import de.sealcore.client.rendering.abstractions.Shader;
import de.sealcore.client.rendering.abstractions.Texture;
import de.sealcore.client.rendering.abstractions.VertexArrayLayout;
import de.sealcore.client.rendering.abstractions.VertexBuffer;
import de.sealcore.client.rendering.ui.primitives.Rectangle;
import de.sealcore.client.rendering.ui.texture.TextureRenderer;
import de.sealcore.util.ResourceManager;
import de.sealcore.util.json.JsonArray;
import de.sealcore.util.json.JsonObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class TextRenderer {

    static private HashMap<Character, Glyph> mappings;

    private static Shader shader;
    private static VertexBuffer buffer;
    private static Texture texture;

    static private float[] vertices;
    static private int index;

    public static void init() {
        shader = new Shader("shaders/texture");
        buffer = new VertexBuffer(new float[0], new VertexArrayLayout().add(3).add(2));

        try {

            TextureRenderer.loadTexture("font", "font/block font - final3.png");

            mappings = new HashMap<>();
            String jsonContent = ResourceManager.getResourceFileAsString("font/font-map.json");
            var o = JsonObject.fromString(jsonContent);
            JsonArray charObjects = o.getArray("characters");
            for (Object obj : charObjects) {
                JsonObject charObject = (JsonObject) obj;
                char c = charObject.getString("character").charAt(0);
                int x1 = charObject.getInt("offsetX");
                int y1 = charObject.getInt("offsetY");
                int w = charObject.getInt("width");
                int x2 = x1 + w;
                int y2 = y1 + 8;
                mappings.put(c, new Glyph(x1/256f,y1/256f,x2/256f,y2/256f, w));

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void drawString(int x, int y, int size, String text, float z) {
        vertices = new float[text.length() * 6 * 5];
        index = 0;
        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            var glyph = mappings.get(c);
            int w = glyph.width();
            w *= size;
            float s1 = glyph.s1();
            float t1 = glyph.t1();
            float s2 = glyph.s2();
            float t2 = glyph.t2();
            TextureRenderer.drawTexture("font", new Rectangle(x, y, x+w, y+8*size), s1, 1-t1, s2, 1-t2, z);
            x += w;
            x += size;
        }
    }






}
