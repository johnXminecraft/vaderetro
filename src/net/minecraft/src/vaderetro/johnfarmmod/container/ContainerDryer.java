package net.minecraft.src.vaderetro.johnfarmmod.container;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.johnfarmmod.entity.tileentity.TileEntityDryer;
import net.minecraft.src.vaderetro.johnfarmmod.slot.SlotDryer;

public class ContainerDryer extends Container {

    private final TileEntityDryer tileEntity;
    private int cookTime = 0;
    private int burnTime = 0;

    public ContainerDryer(InventoryPlayer inventoryPlayer, TileEntityDryer tileEntity) {
        this.tileEntity = tileEntity;
        this.addSlot(new Slot(tileEntity, 0, 56, 35));
        this.addSlot(new SlotDryer(inventoryPlayer.player, this.tileEntity, 2, 116, 35));

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
            if (this.cookTime != this.tileEntity.dryerCookTime) {
                var2.func_20158_a(this, 0, this.tileEntity.dryerCookTime);
            }
            if (this.burnTime != this.tileEntity.dryerBurnTime) {
                var2.func_20158_a(this, 1, this.tileEntity.dryerBurnTime);
            }
        }
        this.cookTime = this.tileEntity.dryerCookTime;
        this.burnTime = this.tileEntity.dryerBurnTime;
    }

    @Override
    public void func_20112_a(int var1, int var2) {
        if(var1 == 0) this.tileEntity.dryerCookTime = var2;
        if(var1 == 1) this.tileEntity.dryerBurnTime = var2;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.tileEntity.canInteractWith(player);
    }

    @Override
    public ItemStack getStackInSlot(int slotId) {
        ItemStack itemStack = null;
        Slot slot = (Slot)this.slots.get(slotId);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemStackFromSlot = slot.getStack();
            itemStack = itemStackFromSlot.copy();
            // Slots layout in this container:
            // 0 - dryer input
            // 1 - dryer output
            // 2..28 - player inventory (27 slots)
            // 29..37 - player hotbar (9 slots)
            if (slotId == 1) {
                // Shift output into the whole player inventory (2..37)
                this.func_28125_a(itemStackFromSlot, 2, 38, true);
            } else if (slotId >= 2 && slotId < 29) {
                // From main inventory to hotbar
                this.func_28125_a(itemStackFromSlot, 29, 38, false);
            } else if (slotId >= 29 && slotId < 38) {
                // From hotbar to main inventory
                this.func_28125_a(itemStackFromSlot, 2, 29, false);
            } else {
                // From dryer slots (0 or 1) to the whole player inventory
                this.func_28125_a(itemStackFromSlot, 2, 38, false);
            }

            if(itemStackFromSlot.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }

            if(itemStackFromSlot.stackSize == itemStack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(itemStackFromSlot);
        }
        return itemStack;
    }
}