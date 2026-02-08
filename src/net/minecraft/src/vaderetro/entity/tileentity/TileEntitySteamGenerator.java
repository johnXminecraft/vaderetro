package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.block.BlockSteamGenerator;
import net.minecraft.src.vaderetro.energy.IEnergyEmitter;
import net.minecraft.src.vaderetro.energy.IEnergySink;
import java.util.HashSet;
import java.util.Set;

public class TileEntitySteamGenerator extends TileEntity implements IInventory, IEnergyEmitter {

    private ItemStack[] steamGenItemStacks = new ItemStack[2];
    public int generatorBurnTime = 0;
    public int currentItemBurnTime = 0;
    public int waterStored = 0;
    public int maxWater = 10000;
    public int vreStored = 0;
    public int maxVre = 2000;

    @Override
    public int getSizeInventory() {
        return this.steamGenItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return this.steamGenItemStacks[var1];
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        if (this.steamGenItemStacks[var1] != null) {
            ItemStack var3;
            if (this.steamGenItemStacks[var1].stackSize <= var2) {
                var3 = this.steamGenItemStacks[var1];
                this.steamGenItemStacks[var1] = null;
            } else {
                var3 = this.steamGenItemStacks[var1].splitStack(var2);
                if (this.steamGenItemStacks[var1].stackSize == 0) {
                    this.steamGenItemStacks[var1] = null;
                }
            }
            return var3;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.steamGenItemStacks[var1] = var2;
        if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
            var2.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return "Steam Generator";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public int getBurnTimeRemainingScaled(int var1) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }
        return this.generatorBurnTime * var1 / this.currentItemBurnTime;
    }

    public int getVreScaled(int var1) {
        return this.vreStored * var1 / this.maxVre;
    }

    public boolean isBurning() {
        return this.generatorBurnTime > 0;
    }

    @Override
    public boolean emitsEnergy(int side) {
        return side == 1;
    }

    @Override
    public void updateEntity() {
        boolean var1 = this.generatorBurnTime > 0 && this.waterStored > 0;
        boolean var2 = false;

        if (this.generatorBurnTime > 0) {
            --this.generatorBurnTime;
        }

        if (this.waterStored > 0) {
            if (this.generatorBurnTime > 0) {
                this.waterStored--;
            } else {
                this.waterStored--;
            }
            if (this.waterStored < 0) this.waterStored = 0;
        }

        // Energy generation or decay depending on water availability
        if (!this.worldObj.multiplayerWorld) {
            if (this.waterStored > 0 && this.generatorBurnTime > 0) {
                this.vreStored += 5;
                if (this.vreStored > this.maxVre) this.vreStored = this.maxVre;
            } else {
                if (this.vreStored > 0) {
                    this.vreStored -= 5;
                    if (this.vreStored < 0) this.vreStored = 0;
                }
            }

            if (this.vreStored > 0) {
                TileEntity te = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                if (te instanceof IEnergySink) {
                    IEnergySink sink = (IEnergySink) te;
                    if (sink.acceptsEnergy(0)) {
                        int toPush = Math.min(this.vreStored, 32);
                        Set<TileEntity> visited = new HashSet<TileEntity>();
                        visited.add(this);
                        int consumed = sink.injectEnergy(toPush, 0, visited);
                        this.vreStored -= consumed;
                    }
                }
            }
        }

        if (!this.worldObj.multiplayerWorld) {
            if (this.steamGenItemStacks[1] != null && this.steamGenItemStacks[1].itemID == Item.bucketWater.shiftedIndex) {
                if (this.waterStored + 1000 <= this.maxWater) {
                    this.waterStored += 1000;
                    this.steamGenItemStacks[1] = new ItemStack(Item.bucketEmpty);
                    var2 = true;
                }
            }

            if (this.generatorBurnTime == 0 && this.canGenerate()) {
                this.currentItemBurnTime = this.generatorBurnTime = this.getItemBurnTime(this.steamGenItemStacks[0]);
                if (this.generatorBurnTime > 0) {
                    var2 = true;
                    if (this.steamGenItemStacks[0] != null) {
                        --this.steamGenItemStacks[0].stackSize;
                        if (this.steamGenItemStacks[0].stackSize == 0) {
                            this.steamGenItemStacks[0] = null;
                        }
                    }
                }
            }

            boolean isGeneratingNow = this.generatorBurnTime > 0 && this.waterStored > 0;
            if (var1 != isGeneratingNow) {
                var2 = true;
                BlockSteamGenerator.updateBlockState(isGeneratingNow, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (var2) {
            this.onInventoryChanged();
        }
    }

    private boolean canGenerate() {
        if (this.waterStored <= 0) return false;
        return this.getItemBurnTime(this.steamGenItemStacks[0]) > 0;
    }

    private int getItemBurnTime(ItemStack var1) {
        if (var1 == null) {
            return 0;
        } else {
            int var2 = var1.getItem().shiftedIndex;
            if (var2 < 256 && Block.blocksList[var2].blockMaterial == Material.wood) {
                return 300;
            } else if (var2 == Item.stick.shiftedIndex) {
                return 100;
            } else if (var2 == Item.coal.shiftedIndex) {
                return 1600;
            } else if (var2 == Item.bucketLava.shiftedIndex) {
                return 20000;
            } else if (var2 == Block.sapling.blockID) {
                return 100;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && var1.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        NBTTagList var2 = var1.getTagList("Items");
        this.steamGenItemStacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.steamGenItemStacks.length) {
                this.steamGenItemStacks[var5] = new ItemStack(var4);
            }
        }

        this.generatorBurnTime = var1.getShort("BurnTime");
        this.waterStored = var1.getShort("WaterStored");
        this.vreStored = var1.getShort("VreStored");
        this.currentItemBurnTime = this.getItemBurnTime(this.steamGenItemStacks[0]);
    }

    @Override
    public void writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        var1.setShort("BurnTime", (short) this.generatorBurnTime);
        var1.setShort("WaterStored", (short) this.waterStored);
        var1.setShort("VreStored", (short) this.vreStored);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.steamGenItemStacks.length; ++var3) {
            if (this.steamGenItemStacks[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.steamGenItemStacks[var3].writeToNBT(var4);
                var2.setTag(var4);
            }
        }

        var1.setTag("Items", var2);
    }
}
