package net.minecraft.src.vaderetro.item;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.EntityIcon;

public class ItemIcon extends Item {

    public ItemIcon(int var1) {
        super(var1);
    }

    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        if(var7 == 0) {
            return false;
        } else if(var7 == 1) {
            return false;
        } else {
            byte var8 = 0;
            if(var7 == 4) {
                var8 = 1;
            }

            if(var7 == 3) {
                var8 = 2;
            }

            if(var7 == 5) {
                var8 = 3;
            }

            EntityIcon var9 = new EntityIcon(var3, var4, var5, var6, var8);
            if(var9.func_410_i()) {
                if(!var3.multiplayerWorld) {
                    var3.entityJoinedWorld(var9);
                }
                --var1.stackSize;
            }

            return true;
        }
    }
}
