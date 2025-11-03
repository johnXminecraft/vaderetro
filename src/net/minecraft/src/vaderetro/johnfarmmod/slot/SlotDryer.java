package net.minecraft.src.vaderetro.johnfarmmod.slot;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotDryer extends Slot {

    private final EntityPlayer player;

    public SlotDryer(EntityPlayer player, IInventory iInventory, int id, int x, int y) {
        super(iInventory, id, x, y);
        this.player = player;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    @Override
    public void onPickupFromSlot(ItemStack itemStack) {
        itemStack.onCrafting(this.player.worldObj, this.player);
        super.onPickupFromSlot(itemStack);
    }
}
