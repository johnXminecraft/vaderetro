package net.minecraft.src.vaderetro.container;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityTurntable;

public class ContainerTurntable extends Container {
    private final int m_iNumSlotRows = 2;
    private final int m_iNumSlotColumns = 1;

    private final int m_iNumSlots = (m_iNumSlotRows * m_iNumSlotColumns);

    private TileEntityTurntable tileEntity;

    public ContainerTurntable(InventoryPlayer inventoryplayer, TileEntityTurntable tileEntity) {
        this.tileEntity = tileEntity;

        for (int iRow = 0; iRow < m_iNumSlotRows; iRow++) {
            for(int iColumn = 0; iColumn < m_iNumSlotColumns; iColumn++) {
                this.addSlot(new Slot(tileEntity,
                        iColumn + iRow * m_iNumSlotColumns,
                        80 + (iColumn) * 18,
                        43 + iRow * 18));
            }
        }

        for (int iRow = 0; iRow < 3; iRow++) {
            for (int iColumn = 0; iColumn < 9; iColumn++) {
                this.addSlot(new Slot(inventoryplayer,
                        iColumn + iRow * 9 + 9,
                        8 + iColumn * 18,
                        93 + iRow * 18));
            }
        }

        for (int iColumn = 0; iColumn < 9; iColumn++) {
            this.addSlot(new Slot(inventoryplayer, iColumn, 8 + iColumn * 18, 151));
        }
    }

    public boolean isUsableByPlayer(EntityPlayer entityplayer) {
        return tileEntity.canInteractWith(entityplayer);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int iSlotIndex) {

        ItemStack itemstack = null;
        Slot slot = (Slot)this.slots.get(iSlotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (iSlotIndex < m_iNumSlots) {
                this.func_28125_a(itemstack1, m_iNumSlots, this.slots.size(), true);
            }
            else {
                this.func_28125_a(itemstack1, 0, m_iNumSlots, false);
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
}
