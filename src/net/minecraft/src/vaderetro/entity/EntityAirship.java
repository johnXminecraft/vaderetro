package net.minecraft.src.vaderetro.entity;

import net.minecraft.src.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.vaderetro.__config.mod_airship;
import org.lwjgl.input.Keyboard;

public class EntityAirship extends Entity implements IInventory {
	boolean hasOpened;
	Minecraft mc;
	int fuelTime;
	int maxProxies;
	boolean checked;
	int currentFuelTime;
	int count;
	private ItemStack[] cargoItems;
	private int field_9394_d;
	private double field_9393_e;
	private double field_9392_f;
	private double field_9391_g;
	private double field_9390_h;
	private double field_9389_i;
	private double field_9388_j;
	private double field_9387_k;
	private double field_9386_l;
	public int field_807_a;
	public int field_806_b;
	public int field_808_c;
	public int PetrolFuel;
	public GuiIngame chat;
	boolean hasFired;

	public EntityAirship(World world) {
		super(world);
		this.hasOpened = false;
		this.fuelTime = 0;
		this.maxProxies = 1024;
		this.checked = false;
		this.count = 0;
		this.PetrolFuel = 0;
		this.hasFired = false;
		this.field_807_a = 0;
		this.field_806_b = 0;
		this.field_808_c = 1;
		this.preventEntitySpawning = true;
		this.setSize(1.5F, 1.7F);
		this.yOffset = this.height / 2.0F;
		this.cargoItems = new ItemStack[36];
		this.mc = null;
	}

	public void setMinecraftInstance(Minecraft minecraft) {
		this.mc = minecraft;
	}

	protected void fall(float f) {
	}

	public EntityAirship(World world, double d, double d1, double d2) {
		this(world);
		this.setPosition(d, d1 + (double)this.yOffset, d2);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = d;
		this.prevPosY = d1;
		this.prevPosZ = d2;
	}

	public void setEntityDead() {
		for(int random = 0; random < this.getSizeInventory(); ++random) {
			ItemStack i = this.getStackInSlot(random);
			if(i != null) {
				float f = this.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

				while(i.stackSize > 0) {
					int j = this.rand.nextInt(21) + 10;
					if(j > i.stackSize) {
						j = i.stackSize;
					}

					i.stackSize -= j;
					EntityItem entityitem = new EntityItem(this.worldObj, this.posX + (double)f, this.posY + (double)f1, this.posZ + (double)f2, new ItemStack(i.itemID, j, i.getItemDamage()));
					float f3 = 0.05F;
					entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
					entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
					entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
					this.worldObj.entityJoinedWorld(entityitem);
				}
			}
		}

		Random var9 = new Random();

		Minecraft mc = this.mc != null ? this.mc : mod_airship.getMinecraftInstance();
		if(mc != null) {
			for(int var10 = 1; var10 < 30; ++var10) {
				if(var10 % 2 == 0) {
					mc.effectRenderer.addEffect(new EntitySteamExplode(this.worldObj, this.posX + (double)(var9.nextInt(var10) / 8), this.posY, this.posZ - (double)(var9.nextInt(var10) / 8), 0.0D, 0.0D, 0.0D));
					mc.effectRenderer.addEffect(new EntitySteamExplode(this.worldObj, this.posX + (double)(var9.nextInt(var10) / 8), this.posY, this.posZ + (double)(var9.nextInt(var10) / 8), 0.0D, 0.0D, 0.0D));
				} else {
					mc.effectRenderer.addEffect(new EntitySteamExplode(this.worldObj, this.posX - (double)(var9.nextInt(var10) / 8), this.posY, this.posZ + (double)(var9.nextInt(var10) / 8), 0.0D, 0.0D, 0.0D));
					mc.effectRenderer.addEffect(new EntitySteamExplode(this.worldObj, this.posX - (double)(var9.nextInt(var10) / 8), this.posY, this.posZ - (double)(var9.nextInt(var10) / 8), 0.0D, 0.0D, 0.0D));
				}
			}
		}

		super.setEntityDead();
	}

	public AxisAlignedBB func_383_b_(Entity entity) {
		return entity.boundingBox;
	}

	public AxisAlignedBB func_372_f_() {
		return this.boundingBox;
	}

	public boolean canBePushed() {
		return true;
	}

	public String getInvName() {
		return "Airship";
	}

	public void onInventoryChanged() {
	}

	public double getMountedYOffset() {
		return (double)this.height * 0.0D - (double)0.3F;
	}

	public boolean attackEntityFrom(Entity entity, int i) {
		if(entity == null) {
			return true;
		} else if(!this.worldObj.multiplayerWorld && !this.isDead) {
			double d1 = entity.posX - this.posX;
			double d2 = entity.posY - this.posY;
			double d3 = entity.posZ - this.posZ;
			double d4 = entity.posX - d1 / 2.0D;
			double d5 = entity.posY - d2 / 2.0D;
			double d6 = entity.posZ - d3 / 2.0D;
			Minecraft mc = this.mc != null ? this.mc : mod_airship.getMinecraftInstance();
			if(mc != null) {
				mc.effectRenderer.addEffect(new EntitySteamExplode(this.worldObj, d4, d5, d6, 0.0D, 0.0D, 0.0D));
			}
			this.field_808_c = -this.field_808_c;
			this.field_806_b = 1;
			this.field_807_a += i * 10;
			this.setBeenAttacked();
			if(this.field_807_a > 300) {
				this.dropItemWithOffset(mod_airship.airShip.shiftedIndex, 1, 0.0F);
				this.setEntityDead();
			}

			return true;
		} else {
			return true;
		}
	}

	public void performHurtAnimation() {
		this.field_808_c = -this.field_808_c;
		this.field_806_b = 1;
		this.field_807_a += this.field_807_a * 2;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public void setPositionAndRotation2(double d, double d1, double d2, float f, float f1, int i) {
		this.field_9393_e = d;
		this.field_9392_f = d1;
		this.field_9391_g = d2;
		this.field_9390_h = (double)f;
		this.field_9389_i = (double)f1;
		this.field_9394_d = i + 4;
		this.motionX = this.field_9388_j;
		this.motionY = this.field_9387_k;
		this.motionZ = this.field_9386_l;
	}

	public void setVelocity(double d, double d1, double d2) {
		this.field_9388_j = this.motionX = d;
		this.field_9387_k = this.motionY = d1;
		this.field_9386_l = this.motionZ = d2;
	}

    private int getFuelTime(ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        }
        int i = itemstack.getItem().shiftedIndex;
        if (i == Item.bucketKerosene.shiftedIndex) {
            return 10000;
        } else {
            return 0;
        }
    }

	public int getFuelScaled(int i) {
		if (this.currentFuelTime == 0) {
			return 0;
		}
		return this.fuelTime * i / this.currentFuelTime;
	}

	public void onUpdate() {
		super.onUpdate();

		if (this.fuelTime > 0) {
			this.fuelTime--;
		}

		if (!this.worldObj.multiplayerWorld) {
			if (this.fuelTime <= 0) {
				ItemStack fuelStack = this.cargoItems[13];
				if (fuelStack != null) {
					int burnValue = this.getFuelTime(fuelStack);
					if (burnValue > 0) {
						this.fuelTime = burnValue;
						this.currentFuelTime = burnValue;

						if (fuelStack.getItem().hasContainerItem()) {
							this.cargoItems[13] = new ItemStack(fuelStack.getItem().getContainerItem());
						} else {
							fuelStack.stackSize--;
						}

						if (fuelStack.stackSize == 0) {
							this.cargoItems[13] = null;
						}
					}
				}
			}
		}

		if(this.field_806_b > 0) {
			--this.field_806_b;
		}

		if(this.field_807_a > 0) {
			--this.field_807_a;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		byte i = 5;
		double d = 0.0D;

		int d7;
		double d4;
		double d8;
		AxisAlignedBB axisalignedbb;
		for(d7 = 0; d7 < i; ++d7) {
			d4 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(d7 + 0) / (double)i - 0.125D;
			d8 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(d7 + 1) / (double)i - 0.125D;
			axisalignedbb = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, d4, this.boundingBox.minZ, this.boundingBox.maxX, d8, this.boundingBox.maxZ);
			if(this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
				d += 1.0D / (double)i;
			}
		}

		double d11;
		double d14;
		double d16;
		double var27;
		if(this.worldObj.multiplayerWorld) {
			if(this.field_9394_d > 0) {
				var27 = this.posX + (this.field_9393_e - this.posX) / (double)this.field_9394_d;
				d11 = this.posY + (this.field_9392_f - this.posY) / (double)this.field_9394_d;
				d14 = this.posZ + (this.field_9391_g - this.posZ) / (double)this.field_9394_d;

				for(d16 = this.field_9390_h - (double)this.rotationYaw; d16 < -180.0D; d16 += 360.0D) {
				}

				while(d16 >= 180.0D) {
					d16 -= 360.0D;
				}

				this.rotationYaw = (float)((double)this.rotationYaw + d16 / (double)this.field_9394_d);
				this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9389_i - (double)this.rotationPitch) / (double)this.field_9394_d);
				--this.field_9394_d;
				this.setPosition(var27, d11, d14);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				var27 = this.posX + this.motionX;
				d11 = this.posY + this.motionY;
				d14 = this.posZ + this.motionZ;
				this.setPosition(var27, d11, d14);
				if(this.onGround) {
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
					this.posY += 3.0D;
				}

				this.motionX *= (double)0.99F;
				this.motionY *= (double)0.95F;
				this.motionZ *= (double)0.99F;
			}

		} else {
			if(this.riddenByEntity != null && this.fuelTime > 0) {
				this.motionX += this.riddenByEntity.motionX * 0.25D;
				this.motionZ += this.riddenByEntity.motionZ * 0.25D;
				if(Keyboard.isKeyDown(mod_airship.KEY_UP)) {
					this.motionY -= this.riddenByEntity.motionY * 0.04000000000000001D;
				}

				if(Keyboard.isKeyDown(mod_airship.KEY_DOWN)) {
					for(d7 = 0; d7 < i; ++d7) {
						d4 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(d7 - 2) / (double)i - 0.125D;
						d8 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(d7 - 4) / (double)i - 0.125D;
						axisalignedbb = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, d4, this.boundingBox.minZ, this.boundingBox.maxX, d8, this.boundingBox.maxZ);
						if(!this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
							this.motionY += this.riddenByEntity.motionY * 0.01000000000000001D;
						} else {
							this.posY += 5.0D;
							this.motionY = 0.0D;
						}
					}
				}
			}

			if(this.riddenByEntity == null || this.fuelTime == 0) {
				this.motionY -= 0.006666666666666667D;
			}

			var27 = 1.0D;
			if(this.motionX < -var27) {
				this.motionX = -var27;
			}

			if(this.motionX > var27) {
				this.motionX = var27;
			}

			if(this.motionZ < -var27) {
				this.motionZ = -var27;
			}

			if(this.motionZ > var27) {
				this.motionZ = var27;
			}

			if(this.onGround) {
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			d11 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			if(d11 > 0.15D) {
				d14 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D);
				d16 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D);

				for(int d17 = 0; (double)d17 < 1.0D + d11 * 60.0D; ++d17) {
					double d18 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
					double d20 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
					double player = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(d17 + 0) / (double)i - 0.125D;
					double d81 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(d17 + 1) / (double)i - 0.125D;
					AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, player, this.boundingBox.minZ, this.boundingBox.maxX, d81, this.boundingBox.maxZ);
					double d22;
					double d24;
					if(this.rand.nextBoolean()) {
						d22 = this.posX - d14 * d18 * 0.8D + d16 * d20;
						d24 = this.posZ - d16 * d18 * 0.8D - d14 * d20;
						if(this.worldObj.isAABBInMaterial(axisalignedbb1, Material.water)) {
							this.worldObj.spawnParticle("splash", d22, this.posY - 0.125D, d24, this.motionX, this.motionY, this.motionZ);
						}
					} else {
						d22 = this.posX + d14 + d16 * d18 * 0.7D;
						d24 = this.posZ + d16 - d14 * d18 * 0.7D;
						if(this.worldObj.isAABBInMaterial(axisalignedbb1, Material.water)) {
							this.worldObj.spawnParticle("splash", d22, this.posY - 0.125D, d24, this.motionX, this.motionY, this.motionZ);
						}
					}
				}
			}

			if(mod_airship.SHOW_BOILER) {
				d14 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
				if(d14 > (double)0.65F) {
					Minecraft mc = this.mc != null ? this.mc : mod_airship.getMinecraftInstance();
					if(mc != null) {
						mc.effectRenderer.addEffect(new EntitySteamFX(this.worldObj, this.posX, this.posY + 0.9D, this.posZ, 0.0D, 0.0D, 0.0D));
					}
				}
			}

			if(!this.isCollidedHorizontally || d11 <= 0.15D) {
				this.motionX *= (double)0.99F;
				this.motionY *= (double)0.95F;
				this.motionZ *= (double)0.99F;
			}

			this.rotationPitch = 0.0F;
			d14 = (double)this.rotationYaw;
			d16 = this.prevPosX - this.posX;
			double var28 = this.prevPosZ - this.posZ;
			if(d16 * d16 + var28 * var28 > 0.001D) {
				d14 = (double)((float)(Math.atan2(var28, d16) * 180.0D / Math.PI));
			}

			double d19;
			for(d19 = d14 - (double)this.rotationYaw; d19 >= 180.0D; d19 -= 360.0D) {
			}

			while(d19 < -180.0D) {
				d19 += 360.0D;
			}

			if(d19 > 30.0D) {
				d19 = 30.0D;
			}

			if(d19 < -30.0D) {
				d19 = -30.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + d19);
			this.setRotation(this.rotationYaw, this.rotationPitch);
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand((double)0.2F, 0.0D, (double)0.2F));
			if(list != null && list.size() > 0) {
				for(int var29 = 0; var29 < list.size(); ++var29) {
					Entity entity = (Entity)list.get(var29);
					if(entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityAirship) {
						entity.applyEntityCollision(this);
					}
				}
			}

			if(this.riddenByEntity != null && this.riddenByEntity.isDead) {
				this.riddenByEntity = null;
			}

			if(this.count == 20) {
				this.hasFired = false;
			}

			++this.count;
			if(Keyboard.isKeyDown(mod_airship.KEY_FIRE) && this.riddenByEntity != null && !this.hasFired) {
				EntityPlayer var30 = (EntityPlayer)this.riddenByEntity;
				this.FireArrow(var30);
				this.count = 0;
			}
		}
	}

	public void updateRiderPosition() {
		if(this.riddenByEntity != null) {
			double d = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			double d1 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			this.riddenByEntity.setPosition(this.posX + d, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
		}
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("BurnTime", (short)this.fuelTime);
        nbttagcompound.setShort("MaxBurnTime", (short)this.currentFuelTime);

		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < this.cargoItems.length; ++i) {
			if(this.cargoItems[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.cargoItems[i].writeToNBT(nbttagcompound1);
				nbttaglist.setTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        this.fuelTime = nbttagcompound.getShort("BurnTime");
        this.currentFuelTime = nbttagcompound.getShort("MaxBurnTime");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		this.cargoItems = new ItemStack[this.getSizeInventory()];

		for(int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;
			if(j >= 0 && j < this.cargoItems.length) {
				this.cargoItems[j] = new ItemStack(nbttagcompound1);
			}
		}

		if(this.mc == null) {
			this.mc = mod_airship.getMinecraftInstance();
		}
	}

	public int getSizeInventory() {
		return 14;
	}

	public ItemStack getStackInSlot(int i) {
		return this.cargoItems[i];
	}

	public ItemStack decrStackSize(int i, int j) {
		if(this.cargoItems[i] != null) {
			ItemStack itemstack1;
			if(this.cargoItems[i].stackSize <= j) {
				itemstack1 = this.cargoItems[i];
				this.cargoItems[i] = null;
				return itemstack1;
			} else {
				itemstack1 = this.cargoItems[i].splitStack(j);
				if(this.cargoItems[i].stackSize == 0) {
					this.cargoItems[i] = null;
				}

				return itemstack1;
			}
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.cargoItems[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public float getShadowSize() {
		return 0.0F;
	}

	public boolean interact(EntityPlayer entityplayer) {
		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityplayer) {
			return true;
		} else {
			if(!this.worldObj.multiplayerWorld) {
				ItemStack itemstack = entityplayer.inventory.getCurrentItem();
				if(itemstack != null && itemstack.itemID == Item.bow.shiftedIndex) {
					return false;
				}

				entityplayer.mountEntity(this);
			}

			return true;
		}
	}

	protected void entityInit() {
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.isDead ? false : entityplayer.getDistanceSqToEntity(this) <= 64.0D;
	}

	private void FireArrow(EntityPlayer entityplayer) {
		World world = this.worldObj;
		ItemStack itemstack = this.getStackInSlot(12);
		double d8;
		double d1;
		if(itemstack != null && !this.hasFired && itemstack.getItem().getItemName() == Item.arrow.getItemName()) {
			Vec3D e = entityplayer.getLook(1.0F);
			double args2 = 4.0D;
			double constructor = this.posX + e.xCoord * args2;
			d8 = this.posY + (double)(this.height / 4.0F);
			d1 = this.posZ + e.zCoord * args2;
			EntityArrow d2 = new EntityArrow(world, constructor, d8, d1);
			this.decrStackSize(12, 1);
			world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
			d2.setArrowHeading(e.xCoord, e.yCoord, e.zCoord, 2.6F, 6.0F);
			world.entityJoinedWorld(d2);
			this.hasFired = true;
		}

		if(itemstack != null && !this.hasFired) {
			Class[] args3;
			Vec3D vec;
			double d3;
			Object[] args;
			Object arrow;
			Object[] argshead;
			Method setHeading;
			Class e1;
			Class[] args21;
			Constructor constructor1;
			double d21;
			if(itemstack.getItem().shiftedIndex == 384) {
				try {
					e1 = Class.forName("EntityArrowExplosive");
					args21 = new Class[]{World.class, Double.TYPE, Double.TYPE, Double.TYPE};
					args3 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE};
					constructor1 = e1.getConstructor(args21);
					vec = entityplayer.getLook(1.0F);
					d8 = 4.0D;
					d1 = this.posX + vec.xCoord * d8;
					d21 = this.posY + (double)(this.height / 4.0F);
					d3 = this.posZ + vec.zCoord * d8;
					args = new Object[]{world, Double.valueOf(d1), Double.valueOf(d21), Double.valueOf(d3)};
					arrow = constructor1.newInstance(args);
					this.decrStackSize(12, 1);
					world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
					argshead = new Object[]{Double.valueOf(vec.xCoord), Double.valueOf(vec.yCoord), Double.valueOf(vec.zCoord), Float.valueOf(2.6F), Float.valueOf(6.0F)};
					setHeading = e1.getMethod("a", args3);
					setHeading.invoke(arrow, argshead);
					world.entityJoinedWorld((Entity)arrow);
					this.hasFired = true;
				} catch (Throwable var25) {
					System.out.println(var25);
				}
			}

			if(itemstack.getItem().shiftedIndex == 385) {
				try {
					e1 = Class.forName("EntityArrowFire");
					args21 = new Class[]{World.class, Double.TYPE, Double.TYPE, Double.TYPE};
					args3 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE};
					constructor1 = e1.getConstructor(args21);
					vec = entityplayer.getLook(1.0F);
					d8 = 4.0D;
					d1 = this.posX + vec.xCoord * d8;
					d21 = this.posY + (double)(this.height / 4.0F);
					d3 = this.posZ + vec.zCoord * d8;
					args = new Object[]{world, Double.valueOf(d1), Double.valueOf(d21), Double.valueOf(d3)};
					arrow = constructor1.newInstance(args);
					this.decrStackSize(12, 1);
					world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
					argshead = new Object[]{Double.valueOf(vec.xCoord), Double.valueOf(vec.yCoord), Double.valueOf(vec.zCoord), Float.valueOf(2.6F), Float.valueOf(6.0F)};
					setHeading = e1.getMethod("a", args3);
					setHeading.invoke(arrow, argshead);
					world.entityJoinedWorld((Entity)arrow);
					this.hasFired = true;
				} catch (Throwable var24) {
					System.out.println(var24);
				}
			}

			if(itemstack.getItem().shiftedIndex == 386) {
				try {
					e1 = Class.forName("EntityArrowIce");
					args21 = new Class[]{World.class, Double.TYPE, Double.TYPE, Double.TYPE};
					args3 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE};
					constructor1 = e1.getConstructor(args21);
					vec = entityplayer.getLook(1.0F);
					d8 = 4.0D;
					d1 = this.posX + vec.xCoord * d8;
					d21 = this.posY + (double)(this.height / 4.0F);
					d3 = this.posZ + vec.zCoord * d8;
					args = new Object[]{world, Double.valueOf(d1), Double.valueOf(d21), Double.valueOf(d3)};
					arrow = constructor1.newInstance(args);
					this.decrStackSize(12, 1);
					world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
					argshead = new Object[]{Double.valueOf(vec.xCoord), Double.valueOf(vec.yCoord), Double.valueOf(vec.zCoord), Float.valueOf(2.6F), Float.valueOf(6.0F)};
					setHeading = e1.getMethod("a", args3);
					setHeading.invoke(arrow, argshead);
					world.entityJoinedWorld((Entity)arrow);
					this.hasFired = true;
				} catch (Throwable var23) {
					System.out.println(var23);
				}
			}

			if(itemstack.getItem().shiftedIndex == 387) {
				try {
					e1 = Class.forName("EntityArrowEgg");
					args21 = new Class[]{World.class, Double.TYPE, Double.TYPE, Double.TYPE};
					args3 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE};
					constructor1 = e1.getConstructor(args21);
					vec = entityplayer.getLook(1.0F);
					d8 = 4.0D;
					d1 = this.posX + vec.xCoord * d8;
					d21 = this.posY + (double)(this.height / 4.0F);
					d3 = this.posZ + vec.zCoord * d8;
					args = new Object[]{world, Double.valueOf(d1), Double.valueOf(d21), Double.valueOf(d3)};
					arrow = constructor1.newInstance(args);
					this.decrStackSize(12, 1);
					world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
					argshead = new Object[]{Double.valueOf(vec.xCoord), Double.valueOf(vec.yCoord), Double.valueOf(vec.zCoord), Float.valueOf(2.6F), Float.valueOf(6.0F)};
					setHeading = e1.getMethod("a", args3);
					setHeading.invoke(arrow, argshead);
					world.entityJoinedWorld((Entity)arrow);
					this.hasFired = true;
				} catch (Throwable var22) {
					System.out.println(var22);
				}
			}

			if(itemstack.getItem().shiftedIndex == 388) {
				try {
					e1 = Class.forName("EntityArrowLightning");
					args21 = new Class[]{World.class, Double.TYPE, Double.TYPE, Double.TYPE};
					args3 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE};
					constructor1 = e1.getConstructor(args21);
					vec = entityplayer.getLook(1.0F);
					d8 = 4.0D;
					d1 = this.posX + vec.xCoord * d8;
					d21 = this.posY + (double)(this.height / 4.0F);
					d3 = this.posZ + vec.zCoord * d8;
					args = new Object[]{world, Double.valueOf(d1), Double.valueOf(d21), Double.valueOf(d3)};
					arrow = constructor1.newInstance(args);
					this.decrStackSize(12, 1);
					world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
					argshead = new Object[]{Double.valueOf(vec.xCoord), Double.valueOf(vec.yCoord), Double.valueOf(vec.zCoord), Float.valueOf(2.6F), Float.valueOf(6.0F)};
					setHeading = e1.getMethod("a", args3);
					setHeading.invoke(arrow, argshead);
					world.entityJoinedWorld((Entity)arrow);
					this.hasFired = true;
				} catch (Throwable var21) {
					System.out.println(var21);
				}
			}
		}
	}
}