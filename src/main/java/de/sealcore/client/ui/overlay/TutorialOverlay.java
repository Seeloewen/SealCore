package de.sealcore.client.ui.overlay;


import de.sealcore.client.ui.rendering.text.TextRenderer;

import java.util.ArrayList;
import java.util.function.Function;

public class TutorialOverlay {

    static ArrayList<Runnable> hints;
    public static boolean disabled;

    public static int getCount() {
        return hints.size();
    }


    public static void init() {
        hints = new ArrayList<>();
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to continue", -0.3f);
            TextRenderer.drawString(50, 180, 3, "Welcome to SealCore! Use the WASD keys to move and your mouse to look around!", -0.3f);
        });
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to continue", -0.3f);
            TextRenderer.drawString(50, 180, 3, "You can open your inventory with the E key!", -0.3f);
        });
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to continue", -0.3f);
            TextRenderer.drawString(50, 180, 3, "At the bottom left you can find your hotbar!", -0.3f);
        });
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to continue", -0.3f);
            TextRenderer.drawString(50, 180, 3, "Click on an item in the inventory and then on another slot to move it", -0.3f);
        });
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to continue", -0.3f);
            TextRenderer.drawString(50, 180, 3, "Use your axe and pickaxe to farm trees and stones", -0.3f);
        });
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to continue", -0.3f);
            TextRenderer.drawString(50, 180, 3, "These can be used to craft weapons and ammo in the inventory!", -0.3f);
        });
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to continue", -0.3f);
            TextRenderer.drawString(50, 180, 3, "At the top of the screen is your health in red.", -0.3f);
        });
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to continue", -0.3f);
            TextRenderer.drawString(50, 180, 3, "The blue bar is the health of your core.", -0.3f);
        });
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to continue", -0.3f);
            TextRenderer.drawString(50, 180, 3, "The waves will start when you're ready - try to defend the core!", -0.3f);
        });
        hints.add(() -> {
            TextRenderer.drawString(50, 150, 3, "SealCore Tutorial - Press T to close", -0.3f);
            TextRenderer.drawString(50, 180, 3, "Have fun!", -0.3f);
        });
    }

    public static void render(int i)
    {
        if(disabled) return;
        hints.get(i).run();
    }


    


}

