package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.EntityObjModel;
import net.minecraft.src.vaderetro.render.NukeEffectsManager;

public class TileEntityNuclearBomb extends TileEntity {
	public float rotation;
	public float prevRotation;
	public float scale = 1.0f;

	public int countdownTicks = -1;

	public void updateEntity() {
		this.prevRotation = this.rotation;
		if (worldObj == null) return;
		if (countdownTicks > 0) {
			countdownTicks--;
			if (countdownTicks == 0) {
				triggerExplosion();
			}
		}
	}

	private static final double BIOME_RADIUS = 200.0D;

	private void triggerExplosion() {
		double x = (double) xCoord + 0.5D;
		double y = (double) yCoord + 0.5D;
		double z = (double) zCoord + 0.5D;
		double bombY = (double) yCoord + 1.0D;
		double killRadius = BIOME_RADIUS;
		float explosionRadius = 20.0F;

		NukeEffectsManager.startNuke(x, y, z, 20 * 120);
		NukeEffectsManager.startFlash(60);

		java.util.List entities = worldObj.getEntitiesWithinAABBExcludingEntity(null,
			AxisAlignedBB.getBoundingBoxFromPool(
				x - killRadius - 2, y - killRadius - 2, z - killRadius - 2,
				x + killRadius + 2, y + killRadius + 2, z + killRadius + 2));
		if (entities != null) {
			for (int i = 0; i < entities.size(); i++) {
				Entity e = (Entity) entities.get(i);
				if (e instanceof EntityLiving && !e.isDead) {
					double dist = e.getDistance(x, y, z);
					if (dist <= killRadius) {
						EntityLiving living = (EntityLiving) e;
						living.health = 0;
						living.onDeath(null);
					}
				}
			}
		}

		worldObj.createExplosion(null, x, y, z, explosionRadius);

		EntityObjModel mushroom = new EntityObjModel(worldObj, x, bombY, z, "/models/test.obj", "/textures/test.png");
		int growTicks = 20 * 5;
		int holdTicks = 20 * 5;
		mushroom.enableGrowAndHoldAnimation(0.1f, 1.0f, growTicks, holdTicks);
		mushroom.setRotationSpeed(0.0f);
		worldObj.entityJoinedWorld(mushroom);

		try {
			NukeEffectsManager.persistToWorldInfo(worldObj);
		} catch (Throwable t) {}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.rotation = nbt.getFloat("Rot");
		this.prevRotation = nbt.getFloat("PrevRot");
		this.scale = nbt.getFloat("Scale");
		this.countdownTicks = nbt.hasKey("Countdown") ? nbt.getInteger("Countdown") : -1;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("Rot", this.rotation);
		nbt.setFloat("PrevRot", this.prevRotation);
		nbt.setFloat("Scale", this.scale);
		if (countdownTicks >= 0) nbt.setInteger("Countdown", this.countdownTicks);
	}
}
