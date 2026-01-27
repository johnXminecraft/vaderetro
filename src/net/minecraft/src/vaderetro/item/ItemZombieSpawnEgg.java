package net.minecraft.src.vaderetro.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.Item;
import net.minecraft.src.vaderetro.entity.mob.undead.zombie.EntityZombie;

public class ItemZombieSpawnEgg extends Item {

    public ItemZombieSpawnEgg(int id) {
        super(id);
        this.setMaxStackSize(1);
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        EntityZombie zombie = new EntityZombie(world);
        zombie.setLocationAndAngles(
                player.posX + 5,
                player.posY,
                player.posZ,
                world.rand.nextFloat() * 360.0F,
                0.0F
        );
        world.entityJoinedWorld(zombie);
        return itemStack;
    }
}
