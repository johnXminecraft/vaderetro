package net.minecraft.src.vaderetro.texture;

import net.minecraft.src.*;

public class CustomTexturedQuad {
	public PositionTextureVertex[] field_1195_a;
	public int field_1194_b;
	private boolean field_1196_c;
	private int texWidth;
	private int texHeight;

	public CustomTexturedQuad(PositionTextureVertex[] aPositionTextureVertex) {
		this.field_1194_b = 0;
		this.field_1196_c = false;
		this.field_1195_a = aPositionTextureVertex;
		this.field_1194_b = aPositionTextureVertex.length;
	}

	public CustomTexturedQuad(PositionTextureVertex[] aPositionTextureVertex, int i, int j, int k, int l) {
		this(aPositionTextureVertex);
		float f = 0.0015625F;
		float f1 = 0.003125F;
		aPositionTextureVertex[0] = aPositionTextureVertex[0].setTexturePosition((float)k / 64.0F - f, (float)j / 32.0F + f1);
		aPositionTextureVertex[1] = aPositionTextureVertex[1].setTexturePosition((float)i / 64.0F + f, (float)j / 32.0F + f1);
		aPositionTextureVertex[2] = aPositionTextureVertex[2].setTexturePosition((float)i / 64.0F + f, (float)l / 32.0F - f1);
		aPositionTextureVertex[3] = aPositionTextureVertex[3].setTexturePosition((float)k / 64.0F - f, (float)l / 32.0F - f1);
	}

	public CustomTexturedQuad(PositionTextureVertex[] aPositionTextureVertex, int i, int j, int k, int l, int texWidth, int texHeight) {
		this(aPositionTextureVertex);
		float f = 0.0015625F;
		float f1 = 0.003125F;
		float w = (float)texWidth;
		float h = (float)texHeight;
		aPositionTextureVertex[0] = aPositionTextureVertex[0].setTexturePosition((float)k / w - f, (float)j / h + f1);
		aPositionTextureVertex[1] = aPositionTextureVertex[1].setTexturePosition((float)i / w + f, (float)j / h + f1);
		aPositionTextureVertex[2] = aPositionTextureVertex[2].setTexturePosition((float)i / w + f, (float)l / h - f1);
		aPositionTextureVertex[3] = aPositionTextureVertex[3].setTexturePosition((float)k / w - f, (float)l / h - f1);
	}

	public void func_809_a() {
		PositionTextureVertex[] aPositionTextureVertex = new PositionTextureVertex[this.field_1195_a.length];

		for(int i = 0; i < this.field_1195_a.length; ++i) {
			aPositionTextureVertex[i] = this.field_1195_a[this.field_1195_a.length - i - 1];
		}

		this.field_1195_a = aPositionTextureVertex;
	}

	public void func_808_a(Tessellator tessellator, float f) {
		Vec3D vec3d = this.field_1195_a[1].vector3D.subtract(this.field_1195_a[0].vector3D);
		Vec3D vec3d1 = this.field_1195_a[1].vector3D.subtract(this.field_1195_a[2].vector3D);
		Vec3D vec3d2 = vec3d1.crossProduct(vec3d).normalize();
		tessellator.startDrawingQuads();
		if(this.field_1196_c) {
			tessellator.setNormal(-((float)vec3d2.xCoord), -((float)vec3d2.yCoord), -((float)vec3d2.zCoord));
		} else {
			tessellator.setNormal((float)vec3d2.xCoord, (float)vec3d2.yCoord, (float)vec3d2.zCoord);
		}

		for(int i = 0; i < 4; ++i) {
			PositionTextureVertex PositionTextureVertex = this.field_1195_a[i];
			tessellator.addVertexWithUV((double)((float)PositionTextureVertex.vector3D.xCoord * f), (double)((float)PositionTextureVertex.vector3D.yCoord * f), (double)((float)PositionTextureVertex.vector3D.zCoord * f), (double)PositionTextureVertex.texturePositionX, (double)PositionTextureVertex.texturePositionY);
		}

		tessellator.draw();
	}
}
