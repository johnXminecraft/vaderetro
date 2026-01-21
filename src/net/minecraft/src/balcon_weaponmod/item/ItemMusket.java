package net.minecraft.src.balcon_weaponmod.item;

import net.minecraft.src.*;
import net.minecraft.src.balcon_weaponmod.entity.EntityMusketBullet;

public class ItemMusket extends Item {
    private boolean isReloading = false;
    private int reloadTime = 0;
    private static final int RELOAD_DURATION = 60; 

    public ItemMusket(int var1) {
        super(var1);
        this.maxStackSize = 1;
    }

    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        if(this.isReloading) {
            return var1;
        }
        
        boolean hasBullets = false;
        for(int i = 0; i < var3.inventory.mainInventory.length; i++) {
            if(var3.inventory.mainInventory[i] != null && var3.inventory.mainInventory[i].itemID == Item.musketBullet.shiftedIndex) {
                hasBullets = true;
                break;
            }
        }
        
        if(!hasBullets) {
            var2.playSoundAtEntity(var3, "random.click", 0.5F, 1.0F);
            return var1;
        }
        
        if(var3.inventory.consumeInventoryItem(Item.musketBullet.shiftedIndex)) {
            var2.playSoundAtEntity(var3, "random.explode", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            var3.swingItem();
            if(!var2.multiplayerWorld) {
                var2.entityJoinedWorld(new EntityMusketBullet(var2, var3));
            }
            
            this.isReloading = true;
            this.reloadTime = RELOAD_DURATION;
            var2.playSoundAtEntity(var3, "random.reload", 2F, 1.0F);
            var3.swingItem();
        }
        return var1;
    }
    
    public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
        if(this.isReloading) {
            this.reloadTime--;
            if(this.reloadTime <= 0) {
                this.isReloading = false;
                this.reloadTime = 0;
            }
        }
    }
    
    public boolean isReloading() {
        return this.isReloading;
    }
    
    public int getReloadProgress() {
        return RELOAD_DURATION - this.reloadTime;
    }
    
    public int getReloadDuration() {
        return RELOAD_DURATION;
    }
}
