package net.minecraft.src.vaderetro.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.vaderetro.effects.DiseaseManager;

public class ItemCigarette extends Item {

    private final DiseaseManager diseaseManager = DiseaseManager.getInstance();

    public ItemCigarette(int id) {
        super(id);
        setMaxStackSize(20);
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        --itemstack.stackSize;
        player.heal(-1);
        if(player.health <= 0) {
            player.onDeath(null);
        }
        diseaseManager.cureZombieDisease();
        return itemstack;
    }
}
