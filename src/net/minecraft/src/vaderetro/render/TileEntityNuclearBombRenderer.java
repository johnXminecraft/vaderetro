package net.minecraft.src.vaderetro.render;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import net.minecraft.src.vaderetro._BML.RenderObj;

public class TileEntityNuclearBombRenderer extends TileEntitySpecialRenderer {
    private RenderObj renderObj = new RenderObj("/models/nuclear_bomb.obj");

    public void renderTileEntityNuclearBombAt(net.minecraft.src.vaderetro.entity.TileEntityNuclearBomb te, double x, double y, double z, float partial) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);

		int meta = te.getBlockMetadata();
		float yaw = 0.0f;
        if (meta == 0) yaw = 270.0f;
        else if (meta == 1) yaw = 180.0f;
        else if (meta == 2) yaw = 0.0f;
        else if (meta == 3) yaw = 90.0f;
		GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);

		GL11.glScalef(te.scale, te.scale, te.scale);
		this.bindTextureByName("/textures/nuclear_bomb.png");
		renderObj.doRenderAtPosition(0.0, 0.0, 0.0, 0.0f, 0.0f, 1.0f);
		GL11.glPopMatrix();
	}

	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partial) {
		this.renderTileEntityNuclearBombAt((net.minecraft.src.vaderetro.entity.TileEntityNuclearBomb)te, x, y, z, partial);
	}
}
