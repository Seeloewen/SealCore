package de.sealcore.util;

public class Maths
{
    public static int safeMod(int a, int m)
    {
        return ((a % m) + m) % m;
    }
}
