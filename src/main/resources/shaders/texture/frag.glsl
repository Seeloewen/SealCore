#version 330 core

out vec4 FragColor;

in vec2 p_texCoords;

uniform sampler2D tex;

void main() {
    FragColor = texture(tex, p_texCoords);
}

