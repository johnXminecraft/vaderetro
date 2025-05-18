package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.block.BlockKeroseneLamp;
import net.minecraft.src.vaderetro.recipes.KeroseneLampRecipes;

public class TileEntityKeroseneLamp extends TileEntity implements IInventory {

    private ItemStack[] keroseneLampItemStacks = new ItemStack[3];
    public int keroseneLampBurnTime = 0;
    public int currentItemBurnTime = 0;
    public int keroseneLampCookTime = 0;

    @Override
    public int getSizeInventory() {
        return this.keroseneLampItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return this.keroseneLampItemStacks[var1];
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        if(this.keroseneLampItemStacks[var1] != null) {
            ItemStack var3;
            if(this.keroseneLampItemStacks[var1].stackSize <= var2) {
                var3 = this.keroseneLampItemStacks[var1];
                this.keroseneLampItemStacks[var1] = null;
            } else {
                var3 = this.keroseneLampItemStacks[var1].splitStack(var2);
                if(this.keroseneLampItemStacks[var1].stackSize == 0) {
                    this.keroseneLampItemStacks[var1] = null;
                }
            }
            return var3;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.keroseneLampItemStacks[var1] = var2;
        if(var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
            var2.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return "Kerosene Lamp";
    }

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        NBTTagList var2 = var1.getTagList("Items");
        this.keroseneLampItemStacks = new ItemStack[this.getSizeInventory()];

        for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");
            if(var5 >= 0 && var5 < this.keroseneLampItemStacks.length) {
                this.keroseneLampItemStacks[var5] = new ItemStack(var4);
            }
        }

        this.keroseneLampBurnTime = var1.getShort("BurnTime");
        this.keroseneLampCookTime = var1.getShort("CookTime");
        this.currentItemBurnTime = this.getItemBurnTime(this.keroseneLampItemStacks[1]);
    }

    @Override
    public void writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        var1.setShort("BurnTime", (short)this.keroseneLampBurnTime);
        var1.setShort("CookTime", (short)this.keroseneLampCookTime);
        NBTTagList var2 = new NBTTagList();

        for(int var3 = 0; var3 < this.keroseneLampItemStacks.length; ++var3) {
            if(this.keroseneLampItemStacks[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.keroseneLampItemStacks[var3].writeToNBT(var4);
                var2.setTag(var4);
            }
        }

        var1.setTag("Items", var2);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public int getCookProgressScaled(int var1) {
        return this.keroseneLampCookTime * var1 / 200;
    }

    public int getBurnTimeRemainingScaled(int var1) {
        if(this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }
        return this.keroseneLampBurnTime * var1 / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.keroseneLampBurnTime > 0;
    }

    @Override
    public void updateEntity() {
        boolean var1 = this.keroseneLampBurnTime > 0;
        boolean var2 = false;
        if(this.keroseneLampBurnTime > 0) {
            --this.keroseneLampBurnTime;
        }
        if(!this.worldObj.multiplayerWorld) {
            if(this.keroseneLampBurnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.keroseneLampBurnTime = this.getItemBurnTime(this.keroseneLampItemStacks[1]);
                if(this.keroseneLampBurnTime > 0) {
                    var2 = true;
                    if(this.keroseneLampItemStacks[1] != null) {
                        --this.keroseneLampItemStacks[1].stackSize;
                        if(this.keroseneLampItemStacks[1].stackSize == 0) {
                            this.keroseneLampItemStacks[1] = null;
                        }
                    }
                }
            }
            if(this.isBurning() && this.canSmelt()) {
                ++this.keroseneLampCookTime;
                if(this.keroseneLampCookTime == 200) {
                    this.keroseneLampCookTime = 0;
                    this.smeltItem();
                    var2 = true;
                }
            } else {
                this.keroseneLampCookTime = 0;
            }
            if(var1 != this.keroseneLampBurnTime > 0) {
                var2 = true;
                BlockKeroseneLamp.updateBlockState(this.keroseneLampBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if(var2) {
            this.onInventoryChanged();
        }
    }

    private boolean canSmelt() {
        if(this.keroseneLampItemStacks[0] == null) {
            return false;
        } else {
            ItemStack var1 = KeroseneLampRecipes.smelting().getSmeltingResult(this.keroseneLampItemStacks[0].getItem().shiftedIndex);
            return var1 != null
                    && (this.keroseneLampItemStacks[2] == null
                    || (this.keroseneLampItemStacks[2].isItemEqual(var1)
                    && (this.keroseneLampItemStacks[2].stackSize < this.getInventoryStackLimit()
                    && this.keroseneLampItemStacks[2].stackSize < this.keroseneLampItemStacks[2].getMaxStackSize()
                    || this.keroseneLampItemStacks[2].stackSize < var1.getMaxStackSize())));
        }
    }

    public void smeltItem() {
        if(this.canSmelt()) {
            ItemStack var1 = KeroseneLampRecipes.smelting().getSmeltingResult(this.keroseneLampItemStacks[0].getItem().shiftedIndex);
            if(this.keroseneLampItemStacks[2] == null) {
                this.keroseneLampItemStacks[2] = var1.copy();
            } else if(this.keroseneLampItemStacks[2].itemID == var1.itemID) {
                ++this.keroseneLampItemStacks[2].stackSize;
            }
            --this.keroseneLampItemStacks[0].stackSize;
            if(this.keroseneLampItemStacks[0].stackSize <= 0) {
                this.keroseneLampItemStacks[0] = null;
            }
        }
    }

    private int getItemBurnTime(ItemStack var1) {
        if(var1 == null) {
            return 0;
        } else {
            int var2 = var1.getItem().shiftedIndex;
            return var2 == Item.silk.shiftedIndex ? 24000 : 0;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && var1.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }
}
