package net.minecraft.src.vaderetro.entity.tileentity;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
public class TileEntityWaterWheelRenderer extends TileEntitySpecialRenderer {
    private static final float PI = 3.141593F;
    private static final float SCALE = 0.0625F;
    public void renderTileEntityWaterWheelAt(TileEntityWaterWheel te, double x, double y, double z, float partial) {
        if (te.worldObj != null && te.worldObj.getBlockId(te.xCoord, te.yCoord, te.zCoord) != Block.waterWheel.blockID) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        int meta = te.getBlockMetadata() & 7;
        float angle = te.prevRotation + (te.rotation - te.prevRotation) * partial;
        if (meta == 1) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        } else if (meta == 2) {
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        } else if (meta == 4) {
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        } else if (meta == 5) {
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        } else if (meta == 6) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        }
        GL11.glRotatef(angle, 0.0F, 0.0F, 1.0F);
        this.bindTextureByName("/terrain.png");
        renderWoodAxis(SCALE);
        renderWheelWithPlanksUV(SCALE);
        GL11.glPopMatrix();
    }
    private void renderWheelWithPlanksUV(float scale) {
        for (int i = 0; i < 8; i++) {
            GL11.glPushMatrix();
            GL11.glRotatef((float) i * 45.0F, 0.0F, 0.0F, 1.0F);
            drawBoxPlanks(4.0F * scale, -1.0F * scale, -6.0F * scale, 30.0F * scale, 1.0F * scale, -4.0F * scale);
            drawBoxPlanks(4.0F * scale, -1.0F * scale, 4.0F * scale, 30.0F * scale, 1.0F * scale, 6.0F * scale);
            drawBoxPlanks(28.0F * scale, -1.5F * scale, -6.0F * scale, 30.0F * scale, 1.5F * scale, 6.0F * scale);
            drawBoxPlanks(30.0F * scale, -4.0F * scale, -6.0F * scale, 32.0F * scale, 4.0F * scale, 6.0F * scale);
            GL11.glPopMatrix();
        }
    }
    private void drawBoxPlanks(float x0, float y0, float z0, float x1, float y1, float z1) {
        int idx = Block.planks.blockIndexInTexture;
        float ts = (float) TerrainTextureManager.getTerrainTextureSize();
        float eps = 0.5f / ts;
        float px = (idx % 16) * 16.0f;
        float py = (idx / 16) * 16.0f;
        float u0 = (px + 0.0f) / ts + eps;
        float v0 = (py + 0.0f) / ts + eps;
        float u1 = (px + 16.0f) / ts - eps;
        float v1 = (py + 16.0f) / ts - eps;
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV(x0, y0, z1, u0, v1);
        t.addVertexWithUV(x1, y0, z1, u1, v1);
        t.addVertexWithUV(x1, y1, z1, u1, v0);
        t.addVertexWithUV(x0, y1, z1, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x1, y0, z0, u0, v1);
        t.addVertexWithUV(x0, y0, z0, u1, v1);
        t.addVertexWithUV(x0, y1, z0, u1, v0);
        t.addVertexWithUV(x1, y1, z0, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x0, y0, z0, u0, v1);
        t.addVertexWithUV(x0, y0, z1, u1, v1);
        t.addVertexWithUV(x0, y1, z1, u1, v0);
        t.addVertexWithUV(x0, y1, z0, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x1, y0, z1, u0, v1);
        t.addVertexWithUV(x1, y0, z0, u1, v1);
        t.addVertexWithUV(x1, y1, z0, u1, v0);
        t.addVertexWithUV(x1, y1, z1, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x0, y0, z0, u0, v1);
        t.addVertexWithUV(x1, y0, z0, u1, v1);
        t.addVertexWithUV(x1, y0, z1, u1, v0);
        t.addVertexWithUV(x0, y0, z1, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x0, y1, z1, u0, v1);
        t.addVertexWithUV(x1, y1, z1, u1, v1);
        t.addVertexWithUV(x1, y1, z0, u1, v0);
        t.addVertexWithUV(x0, y1, z0, u0, v0);
        t.draw();
    }
    private void renderWoodAxis(float scale) {
        float x0 = -3.0f * scale, y0 = -3.0f * scale, z0 = -12.0f * scale;
        float x1 = 3.0f * scale, y1 = 3.0f * scale, z1 = 6.0f * scale;
        int idx = Block.planks.blockIndexInTexture;
        float ts = (float) TerrainTextureManager.getTerrainTextureSize();
        float eps = 0.5f / ts;
        float px = (idx % 16) * 16.0f;
        float py = (idx / 16) * 16.0f;
        float u0 = (px + 0.0f) / ts + eps;
        float v0 = (py + 0.0f) / ts + eps;
        float u1 = (px + 16.0f) / ts - eps;
        float v1 = (py + 16.0f) / ts - eps;
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV(x0, y0, z1, u0, v1);
        t.addVertexWithUV(x1, y0, z1, u1, v1);
        t.addVertexWithUV(x1, y1, z1, u1, v0);
        t.addVertexWithUV(x0, y1, z1, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x1, y0, z0, u0, v1);
        t.addVertexWithUV(x0, y0, z0, u1, v1);
        t.addVertexWithUV(x0, y1, z0, u1, v0);
        t.addVertexWithUV(x1, y1, z0, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x0, y0, z0, u0, v1);
        t.addVertexWithUV(x0, y0, z1, u1, v1);
        t.addVertexWithUV(x0, y1, z1, u1, v0);
        t.addVertexWithUV(x0, y1, z0, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x1, y0, z1, u0, v1);
        t.addVertexWithUV(x1, y0, z0, u1, v1);
        t.addVertexWithUV(x1, y1, z0, u1, v0);
        t.addVertexWithUV(x1, y1, z1, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x0, y0, z0, u0, v1);
        t.addVertexWithUV(x1, y0, z0, u1, v1);
        t.addVertexWithUV(x1, y0, z1, u1, v0);
        t.addVertexWithUV(x0, y0, z1, u0, v0);
        t.draw();
        t.startDrawingQuads();
        t.addVertexWithUV(x0, y1, z1, u0, v1);
        t.addVertexWithUV(x1, y1, z1, u1, v1);
        t.addVertexWithUV(x1, y1, z0, u1, v0);
        t.addVertexWithUV(x0, y1, z0, u0, v0);
        t.draw();
        drawBoxPlanks(-4.0F * scale, -4.0F * scale, -2.0F * scale, 4.0F * scale, 4.0F * scale, 2.0F * scale);
    }
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partial) {
        this.renderTileEntityWaterWheelAt((TileEntityWaterWheel) te, x, y, z, partial);
    }
}