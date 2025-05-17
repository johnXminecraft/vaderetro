package net.minecraft.src.vaderetro.slot;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotKeroseneLamp extends Slot {

    private final EntityPlayer player;

    public SlotKeroseneLamp(EntityPlayer player, IInventory var2, int var3, int var4, int var5) {
        super(var2, var3, var4, var5);
        this.player = player;
    }

    @Override
    public boolean isItemValid(ItemStack var1) {
        return false;
    }

    @Override
    public void onPickupFromSlot(ItemStack var1) {
        var1.onCrafting(this.player.worldObj, this.player);
        super.onPickupFromSlot(var1);
    }
}
