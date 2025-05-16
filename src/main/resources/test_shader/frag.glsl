#version 330 core

out vec4 FragColor;

uniform float brightness;

in vec3 p_color;

void main() {
    FragColor = vec4(p_color * brightness, 1.0);
}

