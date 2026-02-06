package net.minecraft.src.vaderetro.container;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityBattery;

public class ContainerBattery extends Container {

    private TileEntityBattery tileEntity;
    private int lastVreStored = 0;

    public ContainerBattery(InventoryPlayer inventoryPlayer, TileEntityBattery tileEntity) {
        this.tileEntity = tileEntity;
        

        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlot(new Slot(inventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (int var3 = 0; var3 < 9; ++var3) {
            this.addSlot(new Slot(inventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public void updateCraftingResults() {
        super.updateCraftingResults();
        for (Object o : this.field_20121_g) {
            ICrafting var2 = (ICrafting) o;
            
            if (this.lastVreStored != this.tileEntity.vreStored) {
                var2.func_20158_a(this, 0, this.tileEntity.vreStored);
            }
        }
        this.lastVreStored = this.tileEntity.vreStored;
    }

    @Override
    public void func_20112_a(int var1, int var2) {
        if (var1 == 0) this.tileEntity.vreStored = var2;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer var1) {
        return this.tileEntity.canInteractWith(var1);
    }

    public ItemStack getStackInSlot(int var1) {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.slots.get(var1);
        if (var3 != null && var3.getHasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            
            if (var4.stackSize == 0) {
                var3.putStack((ItemStack)null);
            } else {
                var3.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize) {
                return null;
            }

            var3.onPickupFromSlot(var4);
        }

        return var2;
    }
}
