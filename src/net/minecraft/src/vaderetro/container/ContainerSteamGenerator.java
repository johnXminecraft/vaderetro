package net.minecraft.src.vaderetro.container;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntitySteamGenerator;

public class ContainerSteamGenerator extends Container {

    private TileEntitySteamGenerator tileEntity;
    private int lastBurnTime = 0;
    private int lastItemBurnTime = 0;
    private int lastWaterStored = 0;
    private int lastVreStored = 0;

    public ContainerSteamGenerator(InventoryPlayer inventoryPlayer, TileEntitySteamGenerator tileEntity) {
        this.tileEntity = tileEntity;
        
        this.addSlot(new Slot(tileEntity, 0, 65, 52));
        this.addSlot(new Slot(tileEntity, 1, 65, 17));

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
            
            if (this.lastBurnTime != this.tileEntity.generatorBurnTime) {
                var2.func_20158_a(this, 0, this.tileEntity.generatorBurnTime);
            }
            if (this.lastItemBurnTime != this.tileEntity.currentItemBurnTime) {
                var2.func_20158_a(this, 1, this.tileEntity.currentItemBurnTime);
            }
            if (this.lastWaterStored != this.tileEntity.waterStored) {
                var2.func_20158_a(this, 2, this.tileEntity.waterStored);
            }
            if (this.lastVreStored != this.tileEntity.vreStored) {
                var2.func_20158_a(this, 3, this.tileEntity.vreStored);
            }
        }
        this.lastBurnTime = this.tileEntity.generatorBurnTime;
        this.lastItemBurnTime = this.tileEntity.currentItemBurnTime;
        this.lastWaterStored = this.tileEntity.waterStored;
        this.lastVreStored = this.tileEntity.vreStored;
    }

    @Override
    public void func_20112_a(int var1, int var2) {
        if (var1 == 0) this.tileEntity.generatorBurnTime = var2;
        if (var1 == 1) this.tileEntity.currentItemBurnTime = var2;
        if (var1 == 2) this.tileEntity.waterStored = var2;
        if (var1 == 3) this.tileEntity.vreStored = var2;
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
            
            if (var1 < 2) {
                this.func_28125_a(var4, 2, 38, true);
            } else {
                this.func_28125_a(var4, 0, 2, false);
            }

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