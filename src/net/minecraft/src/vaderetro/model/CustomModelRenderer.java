package net.minecraft.src.vaderetro.model;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.texture.CustomTexturedQuad;
import org.lwjgl.opengl.GL11;

public class CustomModelRenderer {
	private PositionTextureVertex[] corners;
	private CustomTexturedQuad[] faces;
	private int textureOffsetX;
	private int textureOffsetY;
	public float offsetX;
	public float offsetY;
	public float offsetZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	private boolean compiled = false;
	private int displayList = 0;
	public boolean mirror = false;
	public boolean field_1403_h = true;
	public boolean field_1402_i = false;
	private int texWidth;
	private int texHeight;

	public CustomModelRenderer(int i, int j, int w, int h) {
		this.textureOffsetX = i;
		this.textureOffsetY = j;
		this.texWidth = w;
		this.texHeight = h;
	}

	public void func_921_a(float f, float f1, float f2, int i, int j, int k) {
		this.addBox(f, f1, f2, i, j, k, 0.0F);
	}

	public void addBox(float f, float f1, float f2, int i, int j, int k, float f3) {
		this.corners = new PositionTextureVertex[8];
		this.faces = new CustomTexturedQuad[6];
		float f4 = f + (float)i;
		float f5 = f1 + (float)j;
		float f6 = f2 + (float)k;
		f -= f3;
		f1 -= f3;
		f2 -= f3;
		f4 += f3;
		f5 += f3;
		f6 += f3;
		if(this.mirror) {
			float PositionTextureVertex = f4;
			f4 = f;
			f = PositionTextureVertex;
		}

		PositionTextureVertex var20 = new PositionTextureVertex(f, f1, f2, 0.0F, 0.0F);
		PositionTextureVertex PositionTextureVertex1 = new PositionTextureVertex(f4, f1, f2, 0.0F, 8.0F);
		PositionTextureVertex PositionTextureVertex2 = new PositionTextureVertex(f4, f5, f2, 8.0F, 8.0F);
		PositionTextureVertex PositionTextureVertex3 = new PositionTextureVertex(f, f5, f2, 8.0F, 0.0F);
		PositionTextureVertex PositionTextureVertex4 = new PositionTextureVertex(f, f1, f6, 0.0F, 0.0F);
		PositionTextureVertex PositionTextureVertex5 = new PositionTextureVertex(f4, f1, f6, 0.0F, 8.0F);
		PositionTextureVertex PositionTextureVertex6 = new PositionTextureVertex(f4, f5, f6, 8.0F, 8.0F);
		PositionTextureVertex PositionTextureVertex7 = new PositionTextureVertex(f, f5, f6, 8.0F, 0.0F);
		this.corners[0] = var20;
		this.corners[1] = PositionTextureVertex1;
		this.corners[2] = PositionTextureVertex2;
		this.corners[3] = PositionTextureVertex3;
		this.corners[4] = PositionTextureVertex4;
		this.corners[5] = PositionTextureVertex5;
		this.corners[6] = PositionTextureVertex6;
		this.corners[7] = PositionTextureVertex7;
		this.faces[0] = new CustomTexturedQuad(new PositionTextureVertex[]{PositionTextureVertex5, PositionTextureVertex1, PositionTextureVertex2, PositionTextureVertex6}, this.textureOffsetX + k + i, this.textureOffsetY + k, this.textureOffsetX + k + i + k, this.textureOffsetY + k + j, this.texWidth, this.texHeight);
		this.faces[1] = new CustomTexturedQuad(new PositionTextureVertex[]{var20, PositionTextureVertex4, PositionTextureVertex7, PositionTextureVertex3}, this.textureOffsetX + 0, this.textureOffsetY + k, this.textureOffsetX + k, this.textureOffsetY + k + j, this.texWidth, this.texHeight);
		this.faces[2] = new CustomTexturedQuad(new PositionTextureVertex[]{PositionTextureVertex5, PositionTextureVertex4, var20, PositionTextureVertex1}, this.textureOffsetX + k, this.textureOffsetY + 0, this.textureOffsetX + k + i, this.textureOffsetY + k, this.texWidth, this.texHeight);
		this.faces[3] = new CustomTexturedQuad(new PositionTextureVertex[]{PositionTextureVertex2, PositionTextureVertex3, PositionTextureVertex7, PositionTextureVertex6}, this.textureOffsetX + k + i, this.textureOffsetY + 0, this.textureOffsetX + k + i + i, this.textureOffsetY + k, this.texWidth, this.texHeight);
		this.faces[4] = new CustomTexturedQuad(new PositionTextureVertex[]{PositionTextureVertex1, var20, PositionTextureVertex3, PositionTextureVertex2}, this.textureOffsetX + k, this.textureOffsetY + k, this.textureOffsetX + k + i, this.textureOffsetY + k + j, this.texWidth, this.texHeight);
		this.faces[5] = new CustomTexturedQuad(new PositionTextureVertex[]{PositionTextureVertex4, PositionTextureVertex5, PositionTextureVertex6, PositionTextureVertex7}, this.textureOffsetX + k + i + k, this.textureOffsetY + k, this.textureOffsetX + k + i + k + i, this.textureOffsetY + k + j, this.texWidth, this.texHeight);
		if(this.mirror) {
			for(int l = 0; l < this.faces.length; ++l) {
				this.faces[l].func_809_a();
			}
		}

	}

	public void setPosition(float f, float f1, float f2) {
		this.offsetX = f;
		this.offsetY = f1;
		this.offsetZ = f2;
	}

	public void render(float f) {
		if(!this.field_1402_i) {
			if(this.field_1403_h) {
				if(!this.compiled) {
					this.compileDisplayList(f);
				}

				if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if(this.offsetX == 0.0F && this.offsetY == 0.0F && this.offsetZ == 0.0F) {
						GL11.glCallList(this.displayList);
					} else {
						GL11.glTranslatef(this.offsetX * f, this.offsetY * f, this.offsetZ * f);
						GL11.glCallList(this.displayList);
						GL11.glTranslatef(-this.offsetX * f, -this.offsetY * f, -this.offsetZ * f);
					}
				} else {
					GL11.glPushMatrix();
					GL11.glTranslatef(this.offsetX * f, this.offsetY * f, this.offsetZ * f);
					if(this.rotateAngleZ != 0.0F) {
						GL11.glRotatef(this.rotateAngleZ * 57.29578F, 0.0F, 0.0F, 1.0F);
					}

					if(this.rotateAngleY != 0.0F) {
						GL11.glRotatef(this.rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
					}

					if(this.rotateAngleX != 0.0F) {
						GL11.glRotatef(this.rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
					}

					GL11.glCallList(this.displayList);
					GL11.glPopMatrix();
				}

			}
		}
	}

	public void func_926_b(float f) {
		if(!this.field_1402_i) {
			if(this.field_1403_h) {
				if(!this.compiled) {
					this.compileDisplayList(f);
				}

				if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if(this.offsetX != 0.0F || this.offsetY != 0.0F || this.offsetZ != 0.0F) {
						GL11.glTranslatef(this.offsetX * f, this.offsetY * f, this.offsetZ * f);
					}
				} else {
					GL11.glTranslatef(this.offsetX * f, this.offsetY * f, this.offsetZ * f);
					if(this.rotateAngleZ != 0.0F) {
						GL11.glRotatef(this.rotateAngleZ * 57.29578F, 0.0F, 0.0F, 1.0F);
					}

					if(this.rotateAngleY != 0.0F) {
						GL11.glRotatef(this.rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
					}

					if(this.rotateAngleX != 0.0F) {
						GL11.glRotatef(this.rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
					}
				}

			}
		}
	}

	private void compileDisplayList(float f) {
		this.displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(this.displayList, GL11.GL_COMPILE);
		Tessellator tessellator = Tessellator.instance;

		for(int i = 0; i < this.faces.length; ++i) {
			this.faces[i].func_808_a(tessellator, f);
		}

		GL11.glEndList();
		this.compiled = true;
	}
}
