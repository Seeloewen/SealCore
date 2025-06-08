#version 330 core

layout (location = 0) in vec3 i_pos;
layout (location = 1) in vec2 i_texCoords;

out vec2 p_texCoords;

void main() {
    gl_Position = vec4(i_pos, 1.0);
    p_texCoords = i_texCoords;
}


