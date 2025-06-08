#version 330 core

layout(location = 0) in vec3 i_pos;
layout(location = 1) in vec3 i_color;


out vec3 p_color;

void main() {
    gl_Position = vec4(i_pos, 1.0);
    p_color = i_color;
}



