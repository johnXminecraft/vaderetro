package net.minecraft.src.vaderetro.container;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityComputer;

public class ContainerComputer extends Container {

    private TileEntityComputer tileEntity;

    public ContainerComputer(InventoryPlayer inventoryPlayer, TileEntityComputer tileEntity) {
        this.tileEntity = tileEntity;
    }

    public TileEntityComputer getTileEntity() {
        return tileEntity;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return tileEntity != null && tileEntity.worldObj != null
                && tileEntity.worldObj.getBlockTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord) == tileEntity
                && player.getDistanceSq((double) tileEntity.xCoord + 0.5D, (double) tileEntity.yCoord + 0.5D, (double) tileEntity.zCoord + 0.5D) <= 64.0D;
    }
}
