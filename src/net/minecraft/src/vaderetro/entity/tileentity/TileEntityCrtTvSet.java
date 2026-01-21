package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.block.BlockCrtTvSet;

public class TileEntityCrtTvSet extends TileEntity {

    private int field_31025_c;
    private boolean isPowered;

    public TileEntityCrtTvSet() {}

    public TileEntityCrtTvSet(boolean isPowered) {
        this.isPowered = isPowered;
    }

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        this.field_31025_c = var1.getInteger("facing");
        this.isPowered = var1.getBoolean("powered");
    }

    @Override
    public void writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        var1.setInteger("facing", this.field_31025_c);
        var1.setBoolean("powered", this.isPowered);
    }

    public boolean isPowered() {
        return isPowered;
    }

    public void switchOnOff() {
        this.isPowered = !this.isPowered;
    }

    public void setPowered(boolean powered) {
        this.isPowered = powered;
    }

    @Override
    public void updateEntity() {
        BlockCrtTvSet.updateBlockState(isPowered(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }
}
