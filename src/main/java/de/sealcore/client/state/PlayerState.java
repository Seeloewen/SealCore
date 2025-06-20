package de.sealcore.client.state;


import de.sealcore.client.Client;
import de.sealcore.client.config.Blocks;
import de.sealcore.client.config.Items;
import de.sealcore.client.ui.Resolution;
import de.sealcore.client.ui.rendering.primitives.PrimitiveRenderer;
import de.sealcore.client.ui.rendering.primitives.Rectangle;
import de.sealcore.client.ui.rendering.text.TextRenderer;
import de.sealcore.networking.NetworkHandler;
import de.sealcore.networking.packets.PlayerInteractPacket;
import de.sealcore.util.ChunkIndex;
import de.sealcore.util.Color;
import de.sealcore.util.MathUtil;
import org.lwjgl.glfw.GLFW;

public class PlayerState {

    public static int playerChunkX = 0;
    public static int playerChunkY = 0;

    public int selectedSlot = 5;

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
    public int coreHP = 19;

    private boolean targeting;

    public String text1 = "-0:00";
    public String text2 = "Game started";


    public void handleMousePress(int button) {
        if(cooldownProgress < cooldownTotal) return;
        NetworkHandler.send(new PlayerInteractPacket(selectedSlot, button == GLFW.GLFW_MOUSE_BUTTON_LEFT,
                targetEntity, distTargetEntity,
                targetBlockX, targetBlockY, distTargetBlock,
                targetFloorX, targetFloorY, distTargetFloor)
        );


    }

    public void update(double dt) {
        var p = Client.instance.gameState.loadedMeshes.get(Client.instance.camera.following);
        if(p != null) {
            playerChunkX = MathUtil.toChunk(MathUtil.toBlock(p.posX));
            playerChunkY = MathUtil.toChunk(MathUtil.toBlock(p.posY));
        }

        targeting = false;
        var itemID = Client.instance.inventoryState.getSelectedItem(selectedSlot);
        var item = Items.get(itemID);
        switch (item.getString("type")) {
            case "it:weapon" -> {
                if(distTargetEntity >= 0
                        && (distTargetEntity < distTargetBlock || distTargetBlock <= 0)
                        && (distTargetEntity < distTargetFloor || distTargetFloor <= 0)
                        && item.getDouble("range") >= distTargetEntity) {
                    targeting = true;
                }
            }
            case "it:tool" -> {
                if(distTargetBlock >= 0
                        && (distTargetBlock < distTargetFloor || distTargetFloor <= 0)
                        && (distTargetBlock < distTargetEntity || distTargetEntity <= 0)
                        && item.getDouble("range") >= distTargetBlock) {
                    var chunk = Client.instance.gameState.loadedChunks.get(ChunkIndex.toI(MathUtil.toChunk(targetBlockX),MathUtil.toChunk( targetBlockY)));
                    var blockState = chunk.blocks[MathUtil.safeMod(targetBlockX, 8) + 8* MathUtil.safeMod(targetBlockY, 8)];
                    targeting = Blocks.get(blockState.type).getString("tt").equals(item.getString("tt"));
                }
            }
            case "it:placeable" -> {
                if(distTargetFloor >= 0
                        && (distTargetFloor < distTargetBlock || distTargetBlock <= 0)
                        && (distTargetFloor < distTargetEntity || distTargetEntity <= 0)
                        && item.getDouble("range") >= distTargetFloor) {
                    var chunk = Client.instance.gameState.loadedChunks.get(ChunkIndex.toI(MathUtil.toChunk(targetFloorX),MathUtil.toChunk( targetFloorY)));
                    var floorState = chunk.floors[MathUtil.safeMod(targetFloorX, 8) + 8* MathUtil.safeMod(targetFloorY, 8)];
                    targeting = floorState.type.equals("f:grass");
                }
            }
        }

        if(cooldownProgress < cooldownTotal) {
            cooldownProgress += dt;
        }
    }

    public void render() {

        TextRenderer.drawString(15, Resolution.HEIGHT-180, 3, text1, -0.5f);
        TextRenderer.drawString(15, Resolution.HEIGHT-150, 4, text2, -0.5f);

        double hpRatio = hp/15.0;
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 200, 50, Resolution.WIDTH / 2 + 200, 90),
                new Color(0), -0.5f);
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 200, 50, (int) (Resolution.WIDTH / 2 -200 + 400*hpRatio), 90),
                new Color(1f,0f,0f), -0.6f);

        double coreHpRatio = coreHP/20.0;
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 200, 10, Resolution.WIDTH / 2 + 200, 50),
                new Color(0), -0.5f);
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 200, 10, (int) (Resolution.WIDTH / 2 -200 + 400*coreHpRatio), 50),
                new Color(0.2f,0.2f,1f), -0.6f);


        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 100, 100, Resolution.WIDTH / 2 + 100, 115),
                new Color(targeting ?  0.9f : 0.7f), -0.5f);
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 100, 100, (int) (Resolution.WIDTH / 2 -100 + 200*(cooldownProgress/cooldownTotal)), 115),
                new Color(targeting ? 0.4f : 0.2f), -0.6f);

        drawCrosshair();
    }

    private void drawCrosshair() {
        PrimitiveRenderer.drawRectangle(
                new Rectangle(Resolution.WIDTH / 2 - 2, Resolution.HEIGHT / 2 - 2, Resolution.WIDTH / 2 + 2, Resolution.HEIGHT / 2 + 2),
                new Color(0), -0.5f);

    }

    public void setSelectedHotbarSlot(int i) {
       selectedSlot = Client.instance.inventoryState.hotbarToSlot(i);
    }

}
