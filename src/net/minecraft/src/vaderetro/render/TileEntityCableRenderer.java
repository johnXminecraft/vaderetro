package net.minecraft.src.vaderetro.render;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityCable;
import org.lwjgl.opengl.GL11;

public class TileEntityCableRenderer extends TileEntitySpecialRenderer {
    
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        if (!(tileEntity instanceof TileEntityCable)) return;
        TileEntityCable cable = (TileEntityCable) tileEntity;
        
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

        this.bindTextureByName("/terrain.png");
        GL11.glColor3f(1.0f, 1.0f, 1.0f);

        int idx = Block.cable.blockIndexInTexture;
        float ts = (float) TerrainTextureManager.getTerrainTextureSize();
        float eps = 0.5f / ts;
        float px = (idx % 16) * 16.0f;
        float py = (idx / 16) * 16.0f;
        float u0 = (px + 0.0f) / ts + eps;
        float v0 = (py + 0.0f) / ts + eps;
        float u1 = (px + 16.0f) / ts - eps;
        float v1 = (py + 16.0f) / ts - eps;

        drawBox(-0.15f, -0.15f, -0.15f, 0.15f, 0.15f, 0.15f, u0, u1, v0, v1);
        
        if (cable.connections[0]) drawBox(-0.1f, -0.5f, -0.1f, 0.1f, -0.15f, 0.1f, u0, u1, v0, v1);
        if (cable.connections[1]) drawBox(-0.1f, 0.15f, -0.1f, 0.1f, 0.5f, 0.1f, u0, u1, v0, v1);
        if (cable.connections[2]) drawBox(-0.1f, -0.1f, -0.5f, 0.1f, 0.1f, -0.15f, u0, u1, v0, v1);
        if (cable.connections[3]) drawBox(-0.1f, -0.1f, 0.15f, 0.1f, 0.1f, 0.5f, u0, u1, v0, v1);
        if (cable.connections[4]) drawBox(-0.5f, -0.1f, -0.1f, -0.15f, 0.1f, 0.1f, u0, u1, v0, v1);
        if (cable.connections[5]) drawBox(0.15f, -0.1f, -0.1f, 0.5f, 0.1f, 0.1f, u0, u1, v0, v1);

        GL11.glPopMatrix();
    }
    
    private void drawBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float u0, float u1, float v0, float v1) {
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        
        t.addVertexWithUV(minX, minY, minZ, u0, v1);
        t.addVertexWithUV(maxX, minY, minZ, u1, v1);
        t.addVertexWithUV(maxX, minY, maxZ, u1, v0);
        t.addVertexWithUV(minX, minY, maxZ, u0, v0);
        
        t.addVertexWithUV(minX, maxY, minZ, u0, v1);
        t.addVertexWithUV(minX, maxY, maxZ, u0, v0);
        t.addVertexWithUV(maxX, maxY, maxZ, u1, v0);
        t.addVertexWithUV(maxX, maxY, minZ, u1, v1);
        
        t.addVertexWithUV(minX, minY, minZ, u0, v1);
        t.addVertexWithUV(minX, minY, maxZ, u1, v1);
        t.addVertexWithUV(minX, maxY, maxZ, u1, v0);
        t.addVertexWithUV(minX, maxY, minZ, u0, v0);
        
        t.addVertexWithUV(maxX, minY, minZ, u1, v1);
        t.addVertexWithUV(maxX, maxY, minZ, u1, v0);
        t.addVertexWithUV(maxX, maxY, maxZ, u0, v0);
        t.addVertexWithUV(maxX, minY, maxZ, u0, v1);
        
        t.addVertexWithUV(minX, minY, minZ, u1, v1);
        t.addVertexWithUV(minX, maxY, minZ, u1, v0);
        t.addVertexWithUV(maxX, maxY, minZ, u0, v0);
        t.addVertexWithUV(maxX, minY, minZ, u0, v1);
        
        t.addVertexWithUV(minX, minY, maxZ, u0, v1);
        t.addVertexWithUV(maxX, minY, maxZ, u1, v1);
        t.addVertexWithUV(maxX, maxY, maxZ, u1, v0);
        t.addVertexWithUV(minX, maxY, maxZ, u0, v0);
        
        t.draw();
    }
}
