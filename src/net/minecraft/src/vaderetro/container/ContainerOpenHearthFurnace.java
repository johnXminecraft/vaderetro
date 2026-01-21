package net.minecraft.src.vaderetro.container;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.slot.SlotOpenHearthFurnace;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityOpenHearthFurnace;

public class ContainerOpenHearthFurnace extends Container {

    private TileEntityOpenHearthFurnace tileEntity;
    private int cookTime = 0;
    private int burnTime = 0;
    private int itemBurnTime = 0;

    public ContainerOpenHearthFurnace(InventoryPlayer inventoryPlayer, TileEntityOpenHearthFurnace tileEntity) {
        this.tileEntity = tileEntity;
        this.addSlot(new Slot(tileEntity, 0, 56, 17));
        this.addSlot(new Slot(tileEntity, 1, 56, 53));
        this.addSlot(new SlotOpenHearthFurnace(inventoryPlayer.player, this.tileEntity, 2, 116, 35));

        int var3;
        for(var3 = 0; var3 < 3; ++var3) {
            for(int var4 = 0; var4 < 9; ++var4) {
                this.addSlot(new Slot(inventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }
        for(var3 = 0; var3 < 9; ++var3) {
            this.addSlot(new Slot(inventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public void updateCraftingResults() {
        super.updateCraftingResults();
        for (Object o : this.field_20121_g) {
            ICrafting var2 = (ICrafting) o;
            if (this.cookTime != this.tileEntity.openHearthFurnaceCookTime) {
                var2.func_20158_a(this, 0, this.tileEntity.openHearthFurnaceCookTime);
            }
            if (this.burnTime != this.tileEntity.openHearthFurnaceBurnTime) {
                var2.func_20158_a(this, 1, this.tileEntity.openHearthFurnaceBurnTime);
            }
            if (this.itemBurnTime != this.tileEntity.currentItemBurnTime) {
                var2.func_20158_a(this, 2, this.tileEntity.currentItemBurnTime);
            }
        }
        this.cookTime = this.tileEntity.openHearthFurnaceCookTime;
        this.burnTime = this.tileEntity.openHearthFurnaceBurnTime;
        this.itemBurnTime = this.tileEntity.currentItemBurnTime;
    }

    @Override
    public void func_20112_a(int var1, int var2) {
        if(var1 == 0) this.tileEntity.openHearthFurnaceCookTime = var2;
        if(var1 == 1) this.tileEntity.openHearthFurnaceBurnTime = var2;
        if(var1 == 2) this.tileEntity.currentItemBurnTime = var2;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer var1) {
        return this.tileEntity.canInteractWith(var1);
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.slots.get(var1);
        if(var3 != null && var3.getHasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            if(var1 == 2) {
                this.func_28125_a(var4, 3, 39, true);
            } else if(var1 >= 3 && var1 < 30) {
                this.func_28125_a(var4, 30, 39, false);
            } else if(var1 >= 30 && var1 < 39) {
                this.func_28125_a(var4, 3, 30, false);
            } else {
                this.func_28125_a(var4, 3, 39, false);
            }

            if(var4.stackSize == 0) {
                var3.putStack((ItemStack)null);
            } else {
                var3.onSlotChanged();
            }

            if(var4.stackSize == var2.stackSize) {
                return null;
            }

            var3.onPickupFromSlot(var4);
        }

        return var2;
    }
}
