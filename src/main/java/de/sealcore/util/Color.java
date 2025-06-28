package de.sealcore.util;

public record Color(float r, float g, float b, float a)
{

    public Color(float r, float g, float b)
    {
        this(r, g, b, 1f);
    }

    public Color(int r, int g, int b)
    {
        this(r / 256f, g / 256f, b / 256f);
    }

    public Color(float w)
    {
        this(w, w, w);
    }

    public Color(int w)
    {
        this(w, w, w);
    }

    public Color scale(float s)
    {
        return new Color(r * s, g * s, b * s);
    }

}
