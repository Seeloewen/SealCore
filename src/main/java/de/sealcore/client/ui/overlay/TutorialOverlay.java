package de.sealcore.client.ui.overlay;


import de.sealcore.client.ui.rendering.text.TextRenderer;

import java.util.ArrayList;
import java.util.function.Function;

public class TutorialOverlay {

    static ArrayList<Runnable> hints;

    public static int getCount() {
        return hints.size();
    }


    public static void init() {
        hints = new ArrayList<>();
        hints.add(() -> {
            TextRenderer.drawString(100, 100, 3, "Tutorial test hint", 0);
        });
    }

    public static void render(int i) {
        hints.get(i).run();
    }


    


}

