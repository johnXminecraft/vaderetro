package net.minecraft.src.vaderetro.container;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.recipes.creators.cooking.CookingManager;

public class ContainerCooking extends Container {

    public InventoryCrafting matrix = new InventoryCrafting(this, 3, 3);
    public IInventory result = new InventoryCraftResult();
    private World world;
    private int x;
    private int y;
    private int z;

    public ContainerCooking(InventoryPlayer player, World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.addSlot(new SlotCrafting(player.player, this.matrix, this.result, 0, 124, 35));

        int var6;
        int var7;
        for(var6 = 0; var6 < 3; ++var6) {
            for(var7 = 0; var7 < 3; ++var7) {
                this.addSlot(new Slot(this.matrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
            }
        }

        for(var6 = 0; var6 < 3; ++var6) {
            for(var7 = 0; var7 < 9; ++var7) {
                this.addSlot(new Slot(player, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }

        for(var6 = 0; var6 < 9; ++var6) {
            this.addSlot(new Slot(player, var6, 8 + var6 * 18, 142));
        }

        this.onCraftMatrixChanged(this.matrix);
    }

    public void onCraftMatrixChanged(IInventory var1) {
        this.result.setInventorySlotContents(0, CookingManager.getInstance().findMatchingRecipe(this.matrix));
    }

    public void onCraftGuiClosed(EntityPlayer var1) {
        super.onCraftGuiClosed(var1);
        if(!this.world.multiplayerWorld) {
            for(int var2 = 0; var2 < 9; ++var2) {
                ItemStack var3 = this.matrix.getStackInSlot(var2);
                if(var3 != null) {
                    var1.dropPlayerItem(var3);
                }
            }

        }
    }

    public boolean isUsableByPlayer(EntityPlayer var1) {
        return this.world.getBlockId(this.x, this.y, this.z) != Block.cookingTable.blockID ? false : var1.getDistanceSq((double)this.x + 0.5D, (double)this.y + 0.5D, (double)this.z + 0.5D) <= 64.0D;
    }

    public ItemStack getStackInSlot(int var1) {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.slots.get(var1);
        if(var3 != null && var3.getHasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            if(var1 == 0) {
                this.func_28125_a(var4, 10, 46, true);
            } else if(var1 >= 10 && var1 < 37) {
                this.func_28125_a(var4, 37, 46, false);
            } else if(var1 >= 37 && var1 < 46) {
                this.func_28125_a(var4, 10, 37, false);
            } else {
                this.func_28125_a(var4, 10, 46, false);
            }

            if(var4.stackSize == 0) {
                var3.putStack((ItemStack)null);
            } else {
                var3.onSlotChanged();
            }

            if(var4.stackSize == var2.stackSize) {
                return null;
            }

            var3.onPickupFromSlot(var4);
        }

        return var2;
    }
}
