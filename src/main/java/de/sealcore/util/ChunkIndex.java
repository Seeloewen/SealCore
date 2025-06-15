package de.sealcore.util;

//im sorry if someone ever has to debug this
public class ChunkIndex {

    public static int toX(int index) {
        int ring = (int)(Math.sqrt(index)/2);
        int lowest = 4*ring*ring;
        int highest = 4*(ring+1)*(ring+1);
        int diff = highest - lowest;
        int step = diff / 4;

        if(index < lowest + step) {
            return - ring + index - lowest -1;
        } else if(index < lowest + 2*step) {
            return ring + 1-1;
        } else if(index < lowest + 3*step) {
            return ring + 1 - index + lowest + 2*step-1;
        } else {
            return -ring-1;
        }
    }

    public static int toY(int index) {
        int ring = (int)(Math.sqrt(index)/2);
        int lowest = 4*ring*ring;
        int highest = 4*(ring+1)*(ring+1);
        int diff = highest - lowest;
        int step = diff / 4;

        if(index < lowest + step) {
            return ring;
        } else if(index < lowest + 2*step) {
            return ring - index + lowest + step;
        } else if(index < lowest + 3*step) {
            return -ring - 1;
        } else {
            return -ring - 1 + index - lowest - 3*step;
        }
    }

    public static int toI(int x, int y) {
        x++;
        int rl = -x;
        int rr = x-1;
        int ru = y;
        int rd = -y-1;

        int r = max(rl, rr, ru, rd);

        int s = 4 * r * r;
        int q = 2*r + 1;

        if(ru == r) {
            return s + r + x;
        } else if(rr == r) {
            return s + q - y + r;
        } else if(rd == r){
            return s + 2 * q - x + 1 + r;
        } else {
            return s + 3*q + y + r + 1;
        }

    }

    public static int blockToI(int x, int y) {
        return toI(MathUtil.toChunk(x), MathUtil.toChunk(y));
    }

    private static int max(int... values) {
        int max = 0;
        for(int v : values) {
            if(v > max) {
                max = v;
            }
        }
        return max;
    }

}
