package net.minecraft.src.vaderetro.render;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.EntityAirship;
import org.lwjgl.opengl.GL11;

public class RenderAirship extends Render {
	protected ModelBase field_20920_e;
	protected ModelBase renderPassModel;

	public RenderAirship(ModelBase modelbase, ModelBase modelbase1, float f) {
		this(modelbase, f);
		this.setRenderPassModel(modelbase1);
	}

	public RenderAirship(ModelBase modelbase, float f) {
		this.field_20920_e = modelbase;
		this.shadowSize = f;
	}

	public void setRenderPassModel(ModelBase modelbase) {
		this.renderPassModel = modelbase;
	}

	public void func_157_a(EntityAirship entityairship, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d, (float)d1, (float)d2);
		GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
		float f2 = (float)entityairship.field_806_b - f1;
		float f3 = (float)entityairship.field_807_a - f1;
		if(f3 < 0.0F) {
			f3 = 0.0F;
		}

		if(f2 > 0.0F) {
			GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0F * (float)entityairship.field_808_c, 1.0F, 0.0F, 0.0F);
		}

		this.loadTexture("/terrain.png");
		float f4 = 12.0F / 16.0F;
		GL11.glScalef(f4, f4, f4);
		GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
		this.loadTexture("/item/airship.png");
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		this.field_20920_e.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 1.0F / 16.0F);
		this.loadTexture("/item/balloon.png");
		this.renderPassModel.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 1.0F / 16.0F);
		GL11.glPopMatrix();
	}

	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		this.func_157_a((EntityAirship)entity, d, d1, d2, f, f1);
	}
}
