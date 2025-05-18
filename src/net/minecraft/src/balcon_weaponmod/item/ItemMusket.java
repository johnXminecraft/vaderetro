package net.minecraft.src.balcon_weaponmod.item;

import net.minecraft.src.*;
import net.minecraft.src.balcon_weaponmod.entity.EntityMusketBullet;

public class ItemMusket extends Item {

    public ItemMusket(int var1) {
        super(var1);
        this.maxStackSize = 1;
    }

    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        if(var3.inventory.consumeInventoryItem(Item.musketBullet.shiftedIndex)) {
            var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if(!var2.multiplayerWorld) {
                var2.entityJoinedWorld(new EntityMusketBullet(var2, var3));
            }
        }
        return var1;
    }
}
