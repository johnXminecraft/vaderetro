package net.minecraft.src.vaderetro.entity;

import net.minecraft.src.*;

public class EntitySteamExplode extends EntityFX {
	public EntitySteamExplode(World world, double d, double d1, double d2, double d3, double d4, double d5) {
		super(world, d, d1, d2, d3, d4, d5);
		this.motionX = d3 + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.05F);
		this.motionY = d4 + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.05F);
		this.motionZ = d5 + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.05F);
		this.particleRed = 230.0F;
		this.particleGreen = 230.0F;
		this.particleBlue = 230.0F;
		this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F;
		this.particleMaxAge = (int)(16.0D / ((double)this.rand.nextFloat() * 0.8D + 0.2D)) + 2;
	}

	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
		super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
	}

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if(this.particleAge++ >= this.particleMaxAge) {
			this.setEntityDead();
		}

		this.particleTextureIndex = 7 - this.particleAge * 8 / this.particleMaxAge;
		this.motionY += 0.008D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= (double)0.9F;
		this.motionY *= (double)0.9F;
		this.motionZ *= (double)0.9F;
		if(this.onGround) {
			this.motionX *= (double)0.7F;
			this.motionZ *= (double)0.7F;
		}

	}
}
