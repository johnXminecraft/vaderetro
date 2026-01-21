package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.block.BlockOpenHearthFurnace;
import net.minecraft.src.vaderetro.recipes.OpenHearthFurnaceRecipes;

public class TileEntityOpenHearthFurnace extends TileEntity implements IInventory {

    private ItemStack[] openHearthFurnaceItemStacks = new ItemStack[5];
    public int openHearthFurnaceBurnTime = 0;
    public int currentItemBurnTime = 0;
    public int openHearthFurnaceCookTime = 0;

    @Override
    public int getSizeInventory() {
        return this.openHearthFurnaceItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return this.openHearthFurnaceItemStacks[var1];
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        if(this.openHearthFurnaceItemStacks[var1] != null) {
            ItemStack var3;
            if(this.openHearthFurnaceItemStacks[var1].stackSize <= var2) {
                var3 = this.openHearthFurnaceItemStacks[var1];
                this.openHearthFurnaceItemStacks[var1] = null;
            } else {
                var3 = this.openHearthFurnaceItemStacks[var1].splitStack(var2);
                if(this.openHearthFurnaceItemStacks[var1].stackSize == 0) {
                    this.openHearthFurnaceItemStacks[var1] = null;
                }
            }
            return var3;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.openHearthFurnaceItemStacks[var1] = var2;
        if(var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
            var2.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return "Open Hearth Furnace";
    }

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        NBTTagList var2 = var1.getTagList("Items");
        this.openHearthFurnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");
            if(var5 >= 0 && var5 < this.openHearthFurnaceItemStacks.length) {
                this.openHearthFurnaceItemStacks[var5] = new ItemStack(var4);
            }
        }

        this.openHearthFurnaceBurnTime = var1.getShort("BurnTime");
        this.openHearthFurnaceCookTime = var1.getShort("CookTime");
        this.currentItemBurnTime = this.getItemBurnTime(this.openHearthFurnaceItemStacks[1]);
    }

    @Override
    public void writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        var1.setShort("BurnTime", (short)this.openHearthFurnaceBurnTime);
        var1.setShort("CookTime", (short)this.openHearthFurnaceCookTime);
        NBTTagList var2 = new NBTTagList();

        for(int var3 = 0; var3 < this.openHearthFurnaceItemStacks.length; ++var3) {
            if(this.openHearthFurnaceItemStacks[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.openHearthFurnaceItemStacks[var3].writeToNBT(var4);
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
        return this.openHearthFurnaceCookTime * var1 / 200;
    }

    public int getBurnTimeRemainingScaled(int var1) {
        if(this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }
        return this.openHearthFurnaceBurnTime * var1 / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.openHearthFurnaceBurnTime > 0;
    }

    @Override
    public void updateEntity() {
        boolean var1 = this.openHearthFurnaceBurnTime > 0;
        boolean var2 = false;
        if(this.openHearthFurnaceBurnTime > 0) {
            --this.openHearthFurnaceBurnTime;
        }
        if(!this.worldObj.multiplayerWorld) {
            if(this.openHearthFurnaceBurnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.openHearthFurnaceBurnTime = this.getItemBurnTime(this.openHearthFurnaceItemStacks[1]);
                if(this.openHearthFurnaceBurnTime > 0) {
                    var2 = true;
                    if(this.openHearthFurnaceItemStacks[1] != null) {
                        --this.openHearthFurnaceItemStacks[1].stackSize;
                        if(this.openHearthFurnaceItemStacks[1].stackSize == 0) {
                            this.openHearthFurnaceItemStacks[1] = null;
                        }
                    }
                }
            }
            if(this.isBurning() && this.canSmelt()) {
                ++this.openHearthFurnaceCookTime;
                if(this.openHearthFurnaceCookTime == 200) {
                    this.openHearthFurnaceCookTime = 0;
                    this.smeltItem();
                    var2 = true;
                }
            } else {
                this.openHearthFurnaceCookTime = 0;
            }
            if(var1 != this.openHearthFurnaceBurnTime > 0) {
                var2 = true;
                BlockOpenHearthFurnace.updateBlockState(this.openHearthFurnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if(var2) {
            this.onInventoryChanged();
        }
    }

    private boolean canSmelt() {
        if(this.openHearthFurnaceItemStacks[0] == null) {
            return false;
        } else {
            ItemStack var1 = OpenHearthFurnaceRecipes.smelting().getSmeltingResult(this.openHearthFurnaceItemStacks[0].getItem().shiftedIndex);
            return var1 != null
                    && (this.openHearthFurnaceItemStacks[2] == null
                    || (this.openHearthFurnaceItemStacks[2].isItemEqual(var1)
                    && (this.openHearthFurnaceItemStacks[2].stackSize < this.getInventoryStackLimit()
                    && this.openHearthFurnaceItemStacks[2].stackSize < this.openHearthFurnaceItemStacks[2].getMaxStackSize()
                    || this.openHearthFurnaceItemStacks[2].stackSize < var1.getMaxStackSize())));
        }
    }

    public void smeltItem() {
        if(this.canSmelt()) {
            ItemStack var1 = OpenHearthFurnaceRecipes.smelting().getSmeltingResult(this.openHearthFurnaceItemStacks[0].getItem().shiftedIndex);
            if(this.openHearthFurnaceItemStacks[2] == null) {
                this.openHearthFurnaceItemStacks[2] = var1.copy();
            } else if(this.openHearthFurnaceItemStacks[2].itemID == var1.itemID) {
                ++this.openHearthFurnaceItemStacks[2].stackSize;
            }
            --this.openHearthFurnaceItemStacks[0].stackSize;
            if(this.openHearthFurnaceItemStacks[0].stackSize <= 0) {
                this.openHearthFurnaceItemStacks[0] = null;
            }
        }
    }

    private int getItemBurnTime(ItemStack var1) {
        if(var1 == null) {
            return 0;
        } else {
            int var2 = var1.getItem().shiftedIndex;
            return var2 == Item.bucketLava.shiftedIndex ? 20000 : 0;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && var1.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }
}
