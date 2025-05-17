package de.sealcore.client.rendering.abstractions;

import org.joml.Vector3f;
import org.lwjgl.system.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL33.*;

public class Shader {

    private int program;

    private HashMap<String, Integer> uniformCache;

    public Shader(String srcFolder) {
        init(srcFolder);

        uniformCache = new HashMap<>();
    }


    private int getUniformLocation(String name) {
        if(uniformCache.containsKey(name)) {
            return uniformCache.get(name);
        } else {
            int l = glGetUniformLocation(program, name);
            if(l == -1) throw new IllegalArgumentException(String.format("uniform name \"%s\"  does not exist", name));
            uniformCache.put(name, l);
            return l;
        }
    }

    public void setUniformFloat(String name, float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    public void setUniformVec3(String name, Vector3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniform3fv(getUniformLocation(name), value.get(stack.mallocFloat(3)));
        }
    }


    public void use() {
        glUseProgram(program);
    }

    private void init(String srcFolder) {
        int vert = createShader(GL_VERTEX_SHADER, srcFolder + "\\vert.glsl");
        int frag = createShader(GL_FRAGMENT_SHADER, srcFolder + "\\frag.glsl");

        program = glCreateProgram();
        glAttachShader(program, vert);
        glAttachShader(program, frag);
        glLinkProgram(program);

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer buffer = stack.mallocInt(1);
            glGetProgramiv(program, GL_LINK_STATUS, buffer);
            int linked = buffer.get(0);
            if(linked != GL_TRUE) {
                System.out.println(glGetProgramInfoLog(program));
                throw new RuntimeException();
            }
        }

    }

    private int createShader(int type, String path) {
        String source = null;
        try {
            source = getResourceFileAsString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer buffer = stack.mallocInt(1);
            glGetShaderiv(shader, GL_COMPILE_STATUS, buffer);
            int compiled = buffer.get(0);
            if(compiled != GL_TRUE) {
                throw new RuntimeException("shader compilation failed: " + glGetShaderInfoLog(shader));
            }
        }
        return shader;
    }

    /**
     * Reads given resource file as a string.
     *
     * @param fileName path to the resource file
     * @return the file's contents
     * @throws IOException if read fails for any reason
     */
    static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

}
