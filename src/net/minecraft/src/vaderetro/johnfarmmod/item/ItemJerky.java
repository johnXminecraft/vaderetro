package net.minecraft.src.vaderetro.johnfarmmod.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemJerky extends Item {

    public ItemJerky(int id) {
        super(id);
        setMaxStackSize(64);
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        --itemstack.stackSize;
        player.heal(2);
        return itemstack;
    }
}
