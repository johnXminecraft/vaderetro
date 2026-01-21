package net.minecraft.src.balcon_weaponmod.item;

import net.minecraft.src.*;
import net.minecraft.src.balcon_weaponmod.entity.EntityAR15Bullet;

public class ItemAR15 extends Item {
    private boolean isReloading = false;
    private int reloadTime = 0;
    private int shotsFired = 0;
    private boolean isRecoiling = false;
    private int recoilTime = 0;
    private boolean hasPlayedEquipSound = false;
    private static final int RELOAD_DURATION = 90; // AR15 reload duration
    private static final int RECOIL_DURATION = 8; // AR15 recoil duration
    private static final int MAGAZINE_SIZE = 7; // AR15 magazine size

    public ItemAR15(int var1) {
        super(var1);
        this.maxStackSize = 1;
    }

    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        if(this.isReloading || this.isRecoiling) {
            return var1;
        }
        
        if(this.shotsFired >= MAGAZINE_SIZE) {
            this.startReload(var2, var3);
            return var1;
        }
        
        boolean hasBullets = false;
        for(int i = 0; i < var3.inventory.mainInventory.length; i++) {
            if(var3.inventory.mainInventory[i] != null && var3.inventory.mainInventory[i].itemID == Item.ar15Bullet.shiftedIndex) {
                hasBullets = true;
                break;
            }
        }
        
        if(!hasBullets) {
            var2.playSoundAtEntity(var3, "random.click", 0.5F, 1.0F);
            return var1;
        }
        
        if(var3.inventory.consumeInventoryItem(Item.ar15Bullet.shiftedIndex)) {
            var2.playSoundAtEntity(var3, "weaponmod.ar15shoot", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            this.startRecoil();
            if(!var2.multiplayerWorld) {
                var2.entityJoinedWorld(new EntityAR15Bullet(var2, var3));
            }
            
            this.shotsFired++;
            
            if(this.shotsFired >= MAGAZINE_SIZE) {
                this.startReload(var2, var3);
            }
        }
        return var1;
    }
    
    private void startRecoil() {
        this.isRecoiling = true;
        this.recoilTime = RECOIL_DURATION;
    }
    
    private void startReload(World var2, EntityPlayer var3) {
        this.isReloading = true;
        this.reloadTime = RELOAD_DURATION;
        this.shotsFired = 0; 
        var2.playSoundAtEntity(var3, "weaponmod.ar15reload", 1.5F, 1.0F);
        var3.swingItem(); 
    }
    
    public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
        if(var3 instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)var3;
            if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == this.shiftedIndex) {
                if(!this.hasPlayedEquipSound) {
                    var2.playSoundAtEntity(player, "weaponmod.ar15hnd", 1.0F, 1.0F);
                    this.hasPlayedEquipSound = true;
                }
                
                if(this.isReloading) {
                    this.reloadTime--;
                    if(this.reloadTime <= 0) {
                        this.isReloading = false;
                        this.reloadTime = 0;
                    }
                }
                if(this.isRecoiling) {
                    this.recoilTime--;
                    if(this.recoilTime <= 0) {
                        this.isRecoiling = false;
                        this.recoilTime = 0;
                    }
                }
            } else {
                this.hasPlayedEquipSound = false;
            }
        }
    }
    
    public boolean isReloading() {
        return this.isReloading;
    }
    
    public boolean isRecoiling() {
        return this.isRecoiling;
    }
    
    public int getReloadProgress() {
        return RELOAD_DURATION - this.reloadTime;
    }
    
    public int getRecoilProgress() {
        return RECOIL_DURATION - this.recoilTime;
    }
    
    public int getReloadDuration() {
        return RELOAD_DURATION;
    }
    
    public int getRecoilDuration() {
        return RECOIL_DURATION;
    }
    
    public int getShotsFired() {
        return this.shotsFired;
    }
    
    public int getMagazineSize() {
        return MAGAZINE_SIZE;
    }
    
    public int getRemainingShots() {
        return MAGAZINE_SIZE - this.shotsFired;
    }
}
