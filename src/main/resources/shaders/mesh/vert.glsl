#version 330 core
layout(location = 0) in vec3 i_pos;
layout(location = 1) in vec3 i_color;

uniform mat4 model;
uniform mat4 camera;
uniform mat4 perspective;
uniform mat4 view_rot;

out vec3 p_color;

void main() {
    gl_Position = perspective * view_rot * inverse(camera) * model* vec4(i_pos, 1.0);
    p_color = i_color;
}