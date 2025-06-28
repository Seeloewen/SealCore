#version 330 core

out vec4 FragColor;


in vec3 p_color;

void main() {
    FragColor = vec4(p_color, 1.0);
}

