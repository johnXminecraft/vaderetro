package net.minecraft.src.vaderetro.item;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.EntityAirship;
import net.minecraft.src.vaderetro.__config.mod_airship;

public class ItemAirship extends Item {
	public ItemAirship(int itemIndex) {
		super(itemIndex);
		this.maxStackSize = 1;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		float f = 1.0F;
		float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
		float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
		double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)f;
		double d1 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)f + 1.62D - (double)entityplayer.yOffset;
		double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)f;
		Vec3D vec3d = Vec3D.createVector(d, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
		float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-f1 * 0.01745329F);
		float f6 = MathHelper.sin(-f1 * 0.01745329F);
		float f7 = f4 * f5;
		float f9 = f3 * f5;
		double d3 = 5.0D;
		Vec3D vec3d1 = vec3d.addVector((double)f7 * d3, (double)f6 * d3, (double)f9 * d3);
		MovingObjectPosition movingobjectposition = world.rayTraceBlocks_do(vec3d, vec3d1, true);
		if(movingobjectposition == null) {
			return itemstack;
		} else {
			if(movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;
				if(!world.multiplayerWorld) {
					EntityAirship airship = new EntityAirship(world, (double)((float)i + 0.5F), (double)((float)j + 1.5F), (double)((float)k + 0.5F));
					if(mod_airship.getMinecraftInstance() != null) {
						airship.setMinecraftInstance(mod_airship.getMinecraftInstance());
					}
					world.entityJoinedWorld(airship);
				}

				--itemstack.stackSize;
			}

			return itemstack;
		}
	}

	public String getItemNameIS(ItemStack itemstack) {
		return this.getItemName();
	}
}
