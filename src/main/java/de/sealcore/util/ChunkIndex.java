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
            return - ring + index - lowest;
        } else if(index < lowest + 2*step) {
            return ring + 1;
        } else if(index < lowest + 3*step) {
            return ring + 1 - index + lowest + 2*step;
        } else {
            return -ring;
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

}
