package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.block.BlockOven;
import net.minecraft.src.vaderetro.recipes.processors.OvenRecipes;

public class TileEntityOven extends TileEntity implements IInventory {

    private ItemStack[] itemStacks = new ItemStack[5];
    public int burnTime = 0;
    public int currentItemBurnTime = 0;
    public int cookTime = 0;

    @Override
    public int getSizeInventory() {
        return this.itemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotId) {
        return this.itemStacks[slotId];
    }

    @Override
    public ItemStack decrStackSize(int slotId1, int var2) {
        if(this.itemStacks[slotId1] != null) {
            ItemStack itemStack;
            if(this.itemStacks[slotId1].stackSize <= var2) {
                itemStack = this.itemStacks[slotId1];
                this.itemStacks[slotId1] = null;
            } else {
                itemStack = this.itemStacks[slotId1].splitStack(var2);
                if(this.itemStacks[slotId1].stackSize == 0) {
                    this.itemStacks[slotId1] = null;
                }
            }
            return itemStack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotId, ItemStack itemStack) {
        this.itemStacks[slotId] = itemStack;
        if(itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return "Oven";
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        NBTTagList items = tagCompound.getTagList("Items");
        this.itemStacks = new ItemStack[this.getSizeInventory()];

        for(int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound taggedAt = (NBTTagCompound)items.tagAt(i);
            byte slotId = taggedAt.getByte("Slot");
            if(slotId >= 0 && slotId < this.itemStacks.length) {
                this.itemStacks[slotId] = new ItemStack(taggedAt);
            }
        }

        this.burnTime = tagCompound.getShort("BurnTime");
        this.cookTime = tagCompound.getShort("CookTime");
        this.currentItemBurnTime = this.getItemBurnTime(this.itemStacks[1]);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setShort("BurnTime", (short)this.burnTime);
        tagCompound.setShort("CookTime", (short)this.cookTime);
        NBTTagList tagList = new NBTTagList();

        for(int i = 0; i < this.itemStacks.length; ++i) {
            if(this.itemStacks[i] != null) {
                NBTTagCompound slot = new NBTTagCompound();
                slot.setByte("Slot", (byte)i);
                this.itemStacks[i].writeToNBT(slot);
                tagList.setTag(slot);
            }
        }

        tagCompound.setTag("Items", tagList);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public int getCookProgressScaled(int progress) {
        return this.cookTime * progress / 100;
    }

    public int getBurnTimeRemainingScaled(int progress) {
        if(this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 100;
        }
        return this.burnTime * progress / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void updateEntity() {
        boolean isBurning = this.burnTime > 0;
        boolean isCookable = false;
        if(this.burnTime > 0) {
            --this.burnTime;
        }
        if(!this.worldObj.multiplayerWorld) {
            if(this.burnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.burnTime = this.getItemBurnTime(this.itemStacks[1]);
                if(this.burnTime > 0) {
                    isCookable = true;
                    if(this.itemStacks[1] != null) {
                        --this.itemStacks[1].stackSize;
                        if(this.itemStacks[1].stackSize == 0) {
                            this.itemStacks[1] = null;
                        }
                    }
                }
            }
            if(this.isBurning() && this.canSmelt()) {
                ++this.cookTime;
                if(this.cookTime == 100) {
                    this.cookTime = 0;
                    this.smeltItem();
                    isCookable = true;
                }
            } else {
                this.cookTime = 0;
            }
            if(isBurning != this.burnTime > 0) {
                isCookable = true;
                BlockOven.updateBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if(isCookable) {
            this.onInventoryChanged();
        }
    }

    private boolean canSmelt() {
        if(this.itemStacks[0] == null) {
            return false;
        } else {
            ItemStack itemStack = OvenRecipes.processing().getResult(this.itemStacks[0].getItem().shiftedIndex);
            return itemStack != null
                    && (this.itemStacks[2] == null
                    || (this.itemStacks[2].isItemEqual(itemStack)
                    && (this.itemStacks[2].stackSize < this.getInventoryStackLimit()
                    && this.itemStacks[2].stackSize < this.itemStacks[2].getMaxStackSize()
                    || this.itemStacks[2].stackSize < itemStack.getMaxStackSize())));
        }
    }

    public void smeltItem() {
        if(this.canSmelt()) {
            ItemStack itemStack = OvenRecipes.processing().getResult(this.itemStacks[0].getItem().shiftedIndex);
            if(this.itemStacks[2] == null) {
                this.itemStacks[2] = itemStack.copy();
            } else if(this.itemStacks[2].itemID == itemStack.itemID) {
                ++this.itemStacks[2].stackSize;
            }
            --this.itemStacks[0].stackSize;
            if(this.itemStacks[0].stackSize <= 0) {
                this.itemStacks[0] = null;
            }
        }
    }

    private int getItemBurnTime(ItemStack itemStack) {
        if(itemStack == null) {
            return 0;
        } else {
            int index = itemStack.getItem().shiftedIndex;
            return index < 256 && Block.blocksList[index].blockMaterial == Material.wood ? 300 :
                    (index == Item.stick.shiftedIndex ? 100 :
                    (index == Item.coal.shiftedIndex ? 1600 :
                    (index == Item.charcoal.shiftedIndex ? 800 :
                    (index == Block.sapling.blockID ? 100 :
                    (index == Block.blockCoal.blockID ? 16000 : 0)))));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }
}
