package net.minecraft.src.vaderetro.container;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityCrtTvSet;

public class ContainerCrtTvSet extends Container {

    private TileEntityCrtTvSet tileEntity;

    public ContainerCrtTvSet(InventoryPlayer inventoryPlayer, TileEntityCrtTvSet tileEntity) {
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer var1) {
        return true;
    }
}
