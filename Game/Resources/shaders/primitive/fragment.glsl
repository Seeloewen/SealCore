#version 330 core

in vec3 p_color;

void main() {
    gl_FragColor = vec4(p_color, 1.0);
}