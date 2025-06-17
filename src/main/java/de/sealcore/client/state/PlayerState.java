package de.sealcore.client.state;


import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.PlayerInteractPacket;
import de.sealcore.util.Color;
import org.lwjgl.glfw.GLFW;

public class PlayerState {


    int selectedSlot = 5;

    public double cooldownProgress = 1;
    public double cooldownTotal = 1;

    public int targetEntity;
    public double distTargetEntity;
    public int targetBlockX;
    public int targetBlockY;
    public double distTargetBlock;
    public int targetFloorX;
    public int targetFloorY;
    public double distTargetFloor;

    public int hp = 10;

    public void handleMousePress(int button) {
        if(cooldownProgress < cooldownTotal) return;
        NetworkHandler.send(new PlayerInteractPacket(selectedSlot, button == GLFW.GLFW_MOUSE_BUTTON_LEFT,
                targetEntity, distTargetEntity,
                targetBlockX, targetBlockY, distTargetBlock,
                targetFloorX, targetFloorY, distTargetFloor)
        );


    }

    public void update(double dt) {
        if(cooldownProgress < cooldownTotal) {
            cooldownProgress += dt;
        }
    }

    public void render() {
        double hpRatio = hp/15.0;
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 200, 50, Resolution.WIDTH / 2 + 200, 90),
                new Color(0), -0.5f);
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 200, 50, (int) (Resolution.WIDTH / 2 -200 + 400*hpRatio), 90),
                new Color(1f,0f,0f), -0.6f);


        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 100, 100, Resolution.WIDTH / 2 + 100, 115),
                new Color(0.7f), -0.5f);
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 100, 100, (int) (Resolution.WIDTH / 2 -100 + 200*(cooldownProgress/cooldownTotal)), 115),
                new Color(0.2f), -0.6f);

        drawCrosshair();
    }

    private void drawCrosshair() {
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 2, Resolution.HEIGHT / 2 - 2, Resolution.WIDTH / 2 + 2, Resolution.HEIGHT / 2 + 2),
                new Color(0), -0.5f);

    }

    public void setSelectedSlot(int i) {
        selectedSlot = i;
        String id = ""; //=inventorystate.getItem(i);


    }

}
