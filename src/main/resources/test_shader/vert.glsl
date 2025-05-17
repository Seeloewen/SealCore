#version 330 core
layout(location = 0) in vec2 i_pos;
layout(location = 1) in vec3 i_color;

uniform vec3 offset;

out vec3 p_color;

void main() {
    gl_Position = vec4(i_pos.x + offset.x, i_pos.y + offset.y, 0.5, 1.0);
    p_color = i_color;
}