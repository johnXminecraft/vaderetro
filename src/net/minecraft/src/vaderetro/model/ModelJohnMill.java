package net.minecraft.src.vaderetro.model;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityJohnMill;
import org.lwjgl.opengl.GL11;

public class ModelJohnMill extends ModelBase {
    public ModelRenderer[] windMillComponents = new ModelRenderer[8];
    public ModelRenderer supportAxis;
    private final int iNumWindMillComponents = 8;
    private final float fLocalPi = 3.141593f;
    private final float fBladeOffsetFromCenter = 15.0f;
    private final int iBladeLength = 84;
    private final int iBladeWidth = 16;
    private final float fShaftOffsetFromCenter = 2.5f;
    private final int iShaftLength = 97;
    private final int iShaftWidth = 4;

    public ModelJohnMill() {
        int i;

        this.supportAxis = new ModelRenderer(0, 40);
        this.supportAxis.addBox(-2.0F, -2.0F, -12.0F, 4, 4, 12, 0.0F);
        this.supportAxis.setRotationPoint(0.0F, 0.0F, 0.0F);
        for (i = 0; i < 4; ++i) {
            this.windMillComponents[i] = new ModelRenderer(0, 0);
            this.windMillComponents[i].addBox(2.5f, -2.0f, -2.0f, 97, 4, 4, 0.0F);
            this.windMillComponents[i].setRotationPoint(0.0f, 0.0f, 0.0f);
            this.windMillComponents[i].rotateAngleZ = 3.141593f * (float)(i - 4) / 2.0f;
        }
        for (i = 4; i < 8; ++i) {
            this.windMillComponents[i] = new ModelRenderer(0, 15);
            this.windMillComponents[i].addBox(15.0f, -8.0f, -0.5f, 84, 16, 1, 0.0F);
            this.windMillComponents[i].setRotationPoint(0.0f, 0.0f, 0.0f);
            this.windMillComponents[i].rotateAngleX = 0.0f;
            this.windMillComponents[i].rotateAngleZ = 3.141593f * (float)i / 2.0f;
        }
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5, TileEntityJohnMill windMillEnt) {

        for (int i = 0; i < 4; ++i) {
            this.windMillComponents[i].render(f5);
        }
        
        float fBrightness = windMillEnt.getBrightness(f);
        
        for (int i = 4; i < 8; ++i) {
            int iBladeColor = windMillEnt.getBladeColor(i - 4);
            if (iBladeColor > 0) {
                float[] color = getColorFromIndex(iBladeColor);
                GL11.glColor3f(fBrightness * color[0], fBrightness * color[1], fBrightness * color[2]);
            }
            this.windMillComponents[i].render(f5);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
        }
    }

    public void renderAll() {
        this.supportAxis.render(0.0625F);
        for (int i = 0; i < 4; ++i) {
            this.windMillComponents[i].render(0.0625F);
        }
        for (int i = 4; i < 8; ++i) {
            this.windMillComponents[i].render(0.0625F);
        }
    }

    public void renderWithoutAxis(float f, float f1, float f2, float f3, float f4, float f5, TileEntityJohnMill windMillEnt) {
        for (int i = 0; i < 4; ++i) {
            this.windMillComponents[i].render(f5);
        }
        float fBrightness = windMillEnt.getBrightness(f);
        for (int i = 4; i < 8; ++i) {
            int iBladeColor = windMillEnt.getBladeColor(i - 4);
            if (iBladeColor > 0) {
                float[] color = getColorFromIndex(iBladeColor);
                GL11.glColor3f(fBrightness * color[0], fBrightness * color[1], fBrightness * color[2]);
            }
            this.windMillComponents[i].render(f5);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
        }
    }

    private float[] getColorFromIndex(int colorIndex) {
        switch (colorIndex) {
            case 0: return new float[]{1.0f, 1.0f, 1.0f}; // White
            case 1: return new float[]{0.8f, 0.8f, 0.8f}; // Light Gray
            case 2: return new float[]{0.4f, 0.4f, 0.4f}; // Dark Gray
            case 3: return new float[]{0.2f, 0.2f, 0.2f}; // Black
            case 4: return new float[]{0.8f, 0.4f, 0.4f}; // Red
            case 5: return new float[]{0.4f, 0.8f, 0.4f}; // Green
            case 6: return new float[]{0.4f, 0.4f, 0.8f}; // Blue
            case 7: return new float[]{0.8f, 0.8f, 0.4f}; // Yellow
            case 8: return new float[]{0.8f, 0.4f, 0.8f}; // Magenta
            case 9: return new float[]{0.4f, 0.8f, 0.8f}; // Cyan
            case 10: return new float[]{0.8f, 0.6f, 0.4f}; // Orange
            case 11: return new float[]{0.6f, 0.4f, 0.8f}; // Purple
            case 12: return new float[]{0.6f, 0.4f, 0.2f}; // Brown
            case 13: return new float[]{0.6f, 0.6f, 0.6f}; // Light Gray
            case 14: return new float[]{0.2f, 0.2f, 0.2f}; // Dark Gray
            case 15: return new float[]{0.1f, 0.1f, 0.1f}; // Black
            default: return new float[]{1.0f, 1.0f, 1.0f}; // Default white
        }
    }
}



