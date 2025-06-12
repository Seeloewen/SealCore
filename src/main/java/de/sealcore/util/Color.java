package de.sealcore.util;

public record Color(float r, float g, float b, float a) {

    public Color(float r, float g, float b) {
        this(r, g, b, 1f);
    }

    public Color scale(float s) {
        return new Color(r*s, g*s, b*s);
    }

}
