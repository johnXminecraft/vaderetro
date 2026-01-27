package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;

public class TileEntityNuclearBomb extends TileEntity {
	public float rotation;
	public float prevRotation;
	public float scale = 1.0f;

	public void updateEntity() {
		this.prevRotation = this.rotation;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.rotation = nbt.getFloat("Rot");
		this.prevRotation = nbt.getFloat("PrevRot");
		this.scale = nbt.getFloat("Scale");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("Rot", this.rotation);
		nbt.setFloat("PrevRot", this.prevRotation);
		nbt.setFloat("Scale", this.scale);
	}
}
