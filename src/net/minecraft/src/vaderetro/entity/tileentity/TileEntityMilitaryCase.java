package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

import java.util.Random;

public class TileEntityMilitaryCase extends TileEntity {

    public boolean isOpen = false;
    public boolean isEmpty = false;
    public float lidAngle = 0.0F;
    public float prevLidAngle = 0.0F;
    public float randomRotation;

    private static final float LID_OPEN_TARGET = 1.9F;
    private static final float LID_SPEED = 0.1F;
    private static final Random rand = new Random();

    public TileEntityMilitaryCase() {
        this.randomRotation = rand.nextFloat() * 360.0F;
    }

    @Override
    public void updateEntity() {
        this.prevLidAngle = this.lidAngle;

        if (this.isOpen) {
            if (this.lidAngle < LID_OPEN_TARGET) {
                this.lidAngle += LID_SPEED;
                if (this.lidAngle > LID_OPEN_TARGET) {
                    this.lidAngle = LID_OPEN_TARGET;
                }
            }
        } else {
            if (this.lidAngle > 0.0F) {
                this.lidAngle -= LID_SPEED;
                if (this.lidAngle < 0.0F) {
                    this.lidAngle = 0.0F;
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.isOpen = nbt.getBoolean("IsOpen");
        this.isEmpty = nbt.getBoolean("IsEmpty");
        this.randomRotation = nbt.getFloat("RandomRotation");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("IsOpen", this.isOpen);
        nbt.setBoolean("IsEmpty", this.isEmpty);
        nbt.setFloat("RandomRotation", this.randomRotation);
    }
}
