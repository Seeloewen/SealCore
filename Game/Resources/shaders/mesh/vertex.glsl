#version 330 core

layout(location = 0) in vec3 i_pos;
layout(location = 1) in float i_color;


out vec3 p_color;

uniform mat4 perspective;
uniform mat4 camera;

void main() {
    p_color = vec3(0, i_color, 0);
    gl_Position = perspective * camera * vec4(i_pos, 1.0);
}