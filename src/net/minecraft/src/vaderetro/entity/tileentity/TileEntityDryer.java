package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.block.BlockDryer;
import net.minecraft.src.vaderetro.recipes.processors.DryerRecipes;

public class TileEntityDryer extends TileEntity implements IInventory {

    private ItemStack[] dryerItemStacks = new ItemStack[5];
    public int dryerBurnTime = 0;
    public int dryerCookTime = 0;

    @Override
    public int getSizeInventory() {
        return this.dryerItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotId) {
        return dryerItemStacks[slotId];
    }

    @Override
    public ItemStack decrStackSize(int slotId, int var2) {
        if(this.dryerItemStacks[slotId] != null) {
            ItemStack var3;
            if(this.dryerItemStacks[slotId].stackSize <= var2) {
                var3 = this.dryerItemStacks[slotId];
                this.dryerItemStacks[slotId] = null;
            } else {
                var3 = this.dryerItemStacks[slotId].splitStack(var2);
                if(this.dryerItemStacks[slotId].stackSize == 0) {
                    this.dryerItemStacks[slotId] = null;
                }
            }
            return var3;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotId, ItemStack itemStack) {
        this.dryerItemStacks[slotId] = itemStack;
        if(itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return "Dryer";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && var1.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        NBTTagList var2 = nbtTagCompound.getTagList("Items");
        this.dryerItemStacks = new ItemStack[this.getSizeInventory()];

        for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");
            if(var5 >= 0 && var5 < this.dryerItemStacks.length) {
                this.dryerItemStacks[var5] = new ItemStack(var4);
            }
        }

        this.dryerBurnTime = nbtTagCompound.getShort("BurnTime");
        this.dryerCookTime = nbtTagCompound.getShort("CookTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort("BurnTime", (short)this.dryerBurnTime);
        nbtTagCompound.setShort("CookTime", (short)this.dryerCookTime);
        NBTTagList var2 = new NBTTagList();

        for(int var3 = 0; var3 < this.dryerItemStacks.length; ++var3) {
            if(this.dryerItemStacks[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.dryerItemStacks[var3].writeToNBT(var4);
                var2.setTag(var4);
            }
        }

        nbtTagCompound.setTag("Items", var2);
    }

    public int getCookProgressScaled(int var1) {
        return this.dryerCookTime * var1 / 200;
    }

    public boolean isActive() {
        return this.dryerBurnTime > 0;
    }

    @Override
    public void updateEntity() {
        boolean var1 = this.dryerBurnTime > 0;
        boolean var2 = false;
        if(!this.worldObj.multiplayerWorld) {
            if(this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord) == Block.fire.blockID) {
                this.dryerBurnTime = 1;
            } else {
                this.dryerBurnTime = 0;
            }
            if(this.dryerBurnTime > 0) {
                var2 = true;
            }
            if(this.isActive() && this.canDry()) {
                ++this.dryerCookTime;
                if(this.dryerCookTime == 200) {
                    this.dryerCookTime = 0;
                    this.dryItem();
                    var2 = true;
                }
            } else {
                this.dryerCookTime = 0;
            }
            if(var1 != this.dryerBurnTime > 0) {
                var2 = true;
                BlockDryer.updateBlockState(this.dryerBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if(var2) {
            this.onInventoryChanged();
        }
    }

    private boolean canDry() {
        if(this.dryerItemStacks[0] == null) {
            return false;
        } else {
            ItemStack var1 = DryerRecipes.processing().getResult(this.dryerItemStacks[0].getItem().shiftedIndex);
            return var1 != null
                    && (this.dryerItemStacks[2] == null
                    || (this.dryerItemStacks[2].isItemEqual(var1)
                    && (this.dryerItemStacks[2].stackSize < this.getInventoryStackLimit()
                    && this.dryerItemStacks[2].stackSize < this.dryerItemStacks[2].getMaxStackSize()
                    || this.dryerItemStacks[2].stackSize < var1.getMaxStackSize())))
                    && this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord) == Block.fire.blockID;
        }
    }

    public void dryItem() {
        if(this.canDry()) {
            ItemStack var1 = DryerRecipes.processing().getResult(this.dryerItemStacks[0].getItem().shiftedIndex);
            if(this.dryerItemStacks[2] == null) {
                this.dryerItemStacks[2] = var1.copy();
            } else if(this.dryerItemStacks[2].itemID == var1.itemID) {
                ++this.dryerItemStacks[2].stackSize;
            }
            --this.dryerItemStacks[0].stackSize;
            if(this.dryerItemStacks[0].stackSize <= 0) {
                this.dryerItemStacks[0] = null;
            }
        }
    }
}
