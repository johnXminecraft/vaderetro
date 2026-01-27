package net.minecraft.src.vaderetro.entity.mob.undead.zombie;

import net.minecraft.src.*;

import java.util.Random;

// It's a class for all types of Zombies, including:
// Normal Zombies,
// Captain Zombies,
// Runner Zombies,
// Tank Zombies
public class EntityZombie extends EntityMob {

	private static final Random random = new Random();
	private final int type = getType();

	public EntityZombie(World world) {
		super(world);
		// Normal Zombie
		if(type == 0) {
			int randomColor = random.nextInt(100);
			if(randomColor <= 18) {
				this.texture = "/mob/zombie_red.png";
			}
			if(randomColor > 18 && randomColor <= 32) {
				this.texture = "/mob/zombie_orange.png";
			}
			if(randomColor > 32 && randomColor <= 50) {
				this.texture = "/mob/zombie_yellow.png";
			}
			if(randomColor > 50 && randomColor <= 68) {
				this.texture = "/mob/zombie_green.png";
			}
			if(randomColor > 68 && randomColor <= 82) {
				this.texture = "/mob/zombie.png";
			}
			if(randomColor > 82) {
				this.texture = "/mob/zombie_purple.png";
			}
			this.moveSpeed = 0.5F;
			this.attackStrength = 5;
		}
		// Captain Zombie
		if(type == 1) {
			this.texture = "/mob/zombie_captain.png";
			this.moveSpeed = 1.0F;
			this.attackStrength = 10;
			this.health = random.nextInt(60) + 40;
		}
		// Runner Zombie
		if(type == 2) {
			this.texture = "/mob/zombie_runner.png";
			this.moveSpeed = 1.5F;
			this.attackStrength = 3;
		}
		// Tank Zombie
		if(type == 3) {
			this.texture = "/mob/zombie_tank.png";
			this.moveSpeed = 0.5F;
			this.attackStrength = 5;
			this.health = random.nextInt(50) + 100;
		}
	}

	private int getType() {
		int randomType = random.nextInt(100);
		if(randomType <= 2) {
			return 1;
		}
		if(randomType <= 20) {
			int randomSubType = random.nextInt(100);
			if(randomSubType <= 50) {
				return 2;
			}
			return 3;
		}
		return 0;
	}

	public void onLivingUpdate() {
		if(this.worldObj.isDaytime()) {
			float var1 = this.getEntityBrightness(1.0F);
			if(var1 > 0.5F && type != 3 && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
				this.fire = 300;
			}
		}

		super.onLivingUpdate();
	}

	protected String getLivingSound() {
		return "mob.zombie";
	}

	protected String getHurtSound() {
		return "mob.zombiehurt";
	}

	protected String getDeathSound() {
		return "mob.zombiedeath";
	}

	protected int getDropItemId() {
		return Item.rottenFlesh.shiftedIndex;
	}

	protected void dropFewItems() {

		int drop = rand.nextInt(100);

		if(drop > 50) {
			this.dropItem(Item.rottenFlesh.shiftedIndex, random.nextInt(4) + 1);
		} else {
			if(type == 0) {
				if(drop <= 10) {
					this.dropItem(Item.chain.shiftedIndex, random.nextInt(3) + 1);
				}
				if(drop > 10 && drop <= 40) {
					this.dropItem(Item.cloth.shiftedIndex, random.nextInt(3) + 1);
				}
				if(drop > 40) {
					this.dropItem(Item.eggFried.shiftedIndex, 1);
				}
			}
			if(type == 1) {
				this.dropItem(Item.appleGold.shiftedIndex, 1);
			}
			if(type == 2) {
				this.dropItem(Item.cigarette.shiftedIndex, random.nextInt(3) + 1);
			}
			if(type == 3) {
				this.dropItem(Item.ar15Bullet.shiftedIndex, random.nextInt(3) + 1);
			}
		}
	}
}
