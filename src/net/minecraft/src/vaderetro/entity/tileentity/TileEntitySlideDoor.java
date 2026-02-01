package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntitySlideDoor extends TileEntity {

	public float slideProgress = 0.0F;

	private static final float SLIDE_SPEED = 0.05F;

	@Override
	public void updateEntity() {
		if (this.worldObj == null) {
			return;
		}
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		boolean targetOpen = (meta & 4) != 0;
		float target = targetOpen ? 1.0F : 0.0F;
		float prev = this.slideProgress;

		if (this.slideProgress < target) {
			this.slideProgress += SLIDE_SPEED;
			if (this.slideProgress > target) {
				this.slideProgress = target;
			}
		} else if (this.slideProgress > target) {
			this.slideProgress -= SLIDE_SPEED;
			if (this.slideProgress < target) {
				this.slideProgress = target;
			}
		}

		if (this.slideProgress != prev) {
			this.worldObj.markBlocksDirty(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
			if ((meta & 8) != 0) {
				this.worldObj.markBlocksDirty(this.xCoord, this.yCoord - 1, this.zCoord, this.xCoord, this.yCoord - 1, this.zCoord);
			} else {
				this.worldObj.markBlocksDirty(this.xCoord, this.yCoord + 1, this.zCoord, this.xCoord, this.yCoord + 1, this.zCoord);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound var1) {
		super.readFromNBT(var1);
		this.slideProgress = var1.getFloat("SlideProgress");
	}

	@Override
	public void writeToNBT(NBTTagCompound var1) {
		super.writeToNBT(var1);
		var1.setFloat("SlideProgress", this.slideProgress);
	}
}
