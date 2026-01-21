package net.minecraft.src.vaderetro.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemCigarette extends Item {

    public ItemCigarette(int id) {
        super(id);
        setMaxStackSize(20);
    }

    // perhaps something more interesting
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        --itemstack.stackSize;
        player.heal(-1);
        return itemstack;
    }
}
