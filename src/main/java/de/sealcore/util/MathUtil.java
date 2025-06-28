package de.sealcore.util;

public class MathUtil
{
    public static int safeMod(int a, int m)
    {
        return ((a % m) + m) % m;
    }

    public static int toChunk(int block) {
        return block < 0 ? (block - 7) / 8 : block / 8;
    }


    public static int toBlock(double pos) {
        return pos >= 0 ? (int)pos : ((int)pos)-1;
    }

}
