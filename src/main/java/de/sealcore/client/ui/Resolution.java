package de.sealcore.client.ui;

public class Resolution {

    public static int width = 800;
    public static int height = 600;
    public static float ratio = 3/4f;

    public static float xToScreen(int x) {
        return (float)x/(width/2)-1f;
    }

    public static float yToScreen(int y) {
        return -((float)y/(height/2)-1f);
    }

    public static int xToPixel(float x) {
        return (int)((x+1)*(width/2));
    }

    public static int yToPixel(float y) {
        return (int)((-y+1)*(height/2));
    }



}
