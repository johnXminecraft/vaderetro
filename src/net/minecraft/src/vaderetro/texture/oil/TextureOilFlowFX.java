package net.minecraft.src.vaderetro.texture.oil;

import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.TextureFX;

public class TextureOilFlowFX extends TextureFX {

    private int tickCounter = 0;
    private int[] baseTextureData;
    private final int TEXTURE_WIDTH = 16;
    private final int TEXTURE_HEIGHT = 16;
    private final int NUM_FRAMES = 4;
    private byte[][] frameData = new byte[NUM_FRAMES][];

    public TextureOilFlowFX() {
        super(Block.oilMoving.blockIndexInTexture + 1);
        this.tileSize = 2;

        frameData[0] = new byte[imageData.length];
        frameData[1] = new byte[imageData.length];
        frameData[2] = new byte[imageData.length];
        frameData[3] = new byte[imageData.length];
    }


    private boolean textureLoaded = false;
    public void bindImage(RenderEngine renderEngine) {
        super.bindImage(renderEngine);

        if (!textureLoaded) {

            baseTextureData = renderEngine.func_28149_a("/terrain.png");
            if (baseTextureData != null) {

                int textureIndex = this.iconIndex;
                int texturePosX = textureIndex % 16;
                int texturePosY = textureIndex / 16;


                prepareFrames(texturePosX, texturePosY);
                textureLoaded = true;
            }
        }
    }

    private void prepareFrames(int texturePosX, int texturePosY) {
        if (baseTextureData == null) return;

        int terrainWidth = 16 * TEXTURE_WIDTH;
        int baseOffsetX = texturePosX * TEXTURE_WIDTH;
        int baseOffsetY = texturePosY * TEXTURE_HEIGHT;


        for (int y = 0; y < TEXTURE_HEIGHT; y++) {
            for (int x = 0; x < TEXTURE_WIDTH; x++) {
                int terrainPos = (baseOffsetY + y) * terrainWidth + (baseOffsetX + x);
                if (terrainPos >= 0 && terrainPos < baseTextureData.length) {
                    int pixel = baseTextureData[terrainPos];
                    int r = (pixel >> 16) & 0xFF;
                    int g = (pixel >> 8) & 0xFF;
                    int b = pixel & 0xFF;

                    int imagePos = (y * TEXTURE_WIDTH + x) * 4;
                    frameData[0][imagePos + 0] = (byte)r;
                    frameData[0][imagePos + 1] = (byte)g;
                    frameData[0][imagePos + 2] = (byte)b;
                    frameData[0][imagePos + 3] = -1;
                }
            }
        }


        createFlowFrame1();
        createFlowFrame2();
        createFlowFrame3();
    }


    private void createFlowFrame1() {
        for (int y = 0; y < TEXTURE_HEIGHT; y++) {
            for (int x = 0; x < TEXTURE_WIDTH; x++) {
                int srcY = (y + 2) % TEXTURE_HEIGHT;
                int srcPos = (srcY * TEXTURE_WIDTH + x) * 4;
                int dstPos = (y * TEXTURE_WIDTH + x) * 4;

                frameData[1][dstPos + 0] = frameData[0][srcPos + 0];
                frameData[1][dstPos + 1] = frameData[0][srcPos + 1];
                frameData[1][dstPos + 2] = frameData[0][srcPos + 2];
                frameData[1][dstPos + 3] = -1;
            }
        }

        adjustBrightness(frameData[1], 0.97f);
    }


    private void createFlowFrame2() {
        for (int y = 0; y < TEXTURE_HEIGHT; y++) {
            for (int x = 0; x < TEXTURE_WIDTH; x++) {
                int srcY = (y + 3) % TEXTURE_HEIGHT;
                int srcX = (x - 1 + TEXTURE_WIDTH) % TEXTURE_WIDTH;
                int srcPos = (srcY * TEXTURE_WIDTH + srcX) * 4;
                int dstPos = (y * TEXTURE_WIDTH + x) * 4;

                frameData[2][dstPos + 0] = frameData[0][srcPos + 0];
                frameData[2][dstPos + 1] = frameData[0][srcPos + 1];
                frameData[2][dstPos + 2] = frameData[0][srcPos + 2];
                frameData[2][dstPos + 3] = -1;
            }
        }

        adjustBrightness(frameData[2], 1.03f);
    }


    private void createFlowFrame3() {
        for (int y = 0; y < TEXTURE_HEIGHT; y++) {
            for (int x = 0; x < TEXTURE_WIDTH; x++) {
                int srcY = (y + 4) % TEXTURE_HEIGHT;
                int srcX = (x + 1) % TEXTURE_WIDTH;
                int srcPos = (srcY * TEXTURE_WIDTH + srcX) * 4;
                int dstPos = (y * TEXTURE_WIDTH + x) * 4;

                frameData[3][dstPos + 0] = frameData[0][srcPos + 0];
                frameData[3][dstPos + 1] = frameData[0][srcPos + 1];
                frameData[3][dstPos + 2] = frameData[0][srcPos + 2];
                frameData[3][dstPos + 3] = -1;
            }
        }

        adjustBrightness(frameData[3], 0.95f);
    }


    private void adjustBrightness(byte[] data, float factor) {
        for (int i = 0; i < data.length; i += 4) {
            int r = data[i] & 0xFF;
            int g = data[i+1] & 0xFF;
            int b = data[i+2] & 0xFF;

            r = Math.min(255, Math.max(0, (int)(r * factor)));
            g = Math.min(255, Math.max(0, (int)(g * factor)));
            b = Math.min(255, Math.max(0, (int)(b * factor)));

            data[i] = (byte)r;
            data[i+1] = (byte)g;
            data[i+2] = (byte)b;
        }
    }

    public void onTick() {
        if (!textureLoaded) return;

        tickCounter++;


        int currentFrame = (tickCounter / 20) % NUM_FRAMES;


        System.arraycopy(frameData[currentFrame], 0, imageData, 0, imageData.length);
    }
}
