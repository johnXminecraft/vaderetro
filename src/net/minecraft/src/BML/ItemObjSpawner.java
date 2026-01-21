package net.minecraft.src.BML;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.EntityObjModel;

public class ItemObjSpawner extends Item {
    public ItemObjSpawner(int id) {
        super(id);
        this.setIconCoord(6, 8);
        this.setItemName("objSpawner");
        this.setMaxStackSize(1);
    }
    
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
        if (world.multiplayerWorld) {
            return true;
        }
        
        EntityObjModel objEntity = new EntityObjModel(world, 
            (double)i + 0.5D, 
            (double)j + 1.0D, 
            (double)k + 0.5D,
            "/models/test.obj",
            "/textures/test.png"
        );
        
        world.entityJoinedWorld(objEntity);
        
        --itemstack.stackSize;
        
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (world.multiplayerWorld) {
            return itemstack;
        }
        
        double x = entityplayer.posX + MathHelper.cos(entityplayer.rotationYaw / 180.0F * (float)Math.PI) * 2.0D;
        double y = entityplayer.posY + entityplayer.getEyeHeight();
        double z = entityplayer.posZ + MathHelper.sin(entityplayer.rotationYaw / 180.0F * (float)Math.PI) * 2.0D;
        
        EntityObjModel objEntity = new EntityObjModel(world, x, y, z,
            "/models/test.obj",
            "/textures/test.png"
        );
        
        world.entityJoinedWorld(objEntity);
        
        return itemstack;
    }
}
