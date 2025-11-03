package net.minecraft.src.vaderetro.johnmillmod.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.johnmillmod.model.ModelJohnMill;
import org.lwjgl.opengl.GL11;
import net.minecraft.src.Tessellator;

public class TileEntityJohnMillRenderer extends TileEntitySpecialRenderer {
    private ModelJohnMill model = new net.minecraft.src.vaderetro.johnmillmod.model.ModelJohnMill();

    public void renderTileEntityJohnMillAt(TileEntityJohnMill te, double x, double y, double z, float partial) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
        
        this.bindTextureByName("/mill/fcwindmillent.png");
        
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        
        float deltaTime = (float)te.windMillTimeSinceHit - partial;
        float deltaDamage = (float)te.windMillCurrentDamage - partial;
        float rotateForDamage = 0.0f;
        
        if (deltaDamage < 0.0f) {
            deltaDamage = 0.0f;
        }
        if (deltaDamage > 0.0f) {

            rotateForDamage = (float)Math.sin((double)deltaTime) * deltaTime * deltaDamage / 40.0f * (float)te.windMillRockDirection;
        }

        int meta = te.getBlockMetadata();
        int facing = meta & 7;

        float angle = te.prevRotation + (te.rotation - te.prevRotation) * partial;

        if (facing == 1) { 
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        } else if (facing == 2) { 
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
        } else if (facing == 3) { 
       
        } else if (facing == 4) { 
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        } else if (facing == 5) { 
            GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        } else if (facing == 6) { 
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        }

        GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(rotateForDamage, 1.0f, 0.0f, 0.0f);
        
        this.bindTextureByName("/terrain.png");
        renderWoodAxis(0.0625f);

        this.bindTextureByName("/mill/fcwindmillent.png");
        this.model.renderWithoutAxis(0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f, te);
        
        GL11.glPopMatrix();
    }

    private void renderWoodAxis(float scale) {
        float x0 = -2.0f * scale, y0 = -2.0f * scale, z0 = -12.0f * scale;
        float x1 =  2.0f * scale, y1 =  2.0f * scale, z1 =  0.0f * scale;

        int idx = Block.planks.blockIndexInTexture;
        float u0 = (idx % 16) / 16.0f;
        float v0 = (idx / 16) / 16.0f;
        float u1 = u0 + 1.0f / 16.0f;
        float v1 = v0 + 1.0f / 16.0f;

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

    public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
        this.renderTileEntityJohnMillAt((TileEntityJohnMill)var1, var2, var4, var6, var8);
    }
}




