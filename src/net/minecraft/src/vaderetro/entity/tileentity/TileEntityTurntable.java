package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.recipes.processors.TurntableRecipes;

public class TileEntityTurntable extends TileEntity implements IInventory {
    private ItemStack[] itemStacks = new ItemStack[2];
    public int burnTime = 0;
    public int currentItemBurnTime = 0;
    public int cooktime = 0;

    @Override
    public int getSizeInventory() {
        return this.itemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.itemStacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.itemStacks[slot] != null) {
            ItemStack itemStack;
            if (this.itemStacks[slot].stackSize <= amount) {
                itemStack = this.itemStacks[slot];
                this.itemStacks[slot] = null;
            } else {
                itemStack = this.itemStacks[slot].splitStack(amount);
                if (this.itemStacks[slot].stackSize == 0) {
                    this.itemStacks[slot] = null;
                }
            }
            return itemStack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.itemStacks[slot] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return "Turntable";
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList tagList = nbt.getTagList("Items");
        this.itemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < this.itemStacks.length) {
                this.itemStacks[slot] = new ItemStack(tagCompound);
            }
        }

        this.burnTime = nbt.getShort("BurnTime");
        this.cooktime = nbt.getShort("CookTime");
        this.currentItemBurnTime = 50;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setShort("BurnTime", (short) this.burnTime);
        nbt.setShort("CookTime", (short) this.cooktime);
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < this.itemStacks.length; i++) {
            if (this.itemStacks[i] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                this.itemStacks[i].writeToNBT(tagCompound);
                tagList.setTag(tagCompound);
            }
        }

        nbt.setTag("Items", tagList);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public int getCookProgressScaled(int scale) {
        int required = getRequiredCookTime();
        return required == 0 ? 0 : this.cooktime * scale / required;
    }

    public int getBurnTimeRemainingScaled(int scale) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 50;
        }
        return this.burnTime * scale / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    public int getPowerSourceType() {
        int id = this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord);
        if (id == Block.axleRod.blockID) {
            return getAxleRodPowerType(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord);
        }
        return 0;
    }

    public boolean isPowered() {
        return getPowerSourceType() != 0;
    }

    public boolean isPoweredByWaterWheel() {
        return getPowerSourceType() == 2;
    }

    public int getRequiredCookTime() {
        return isPoweredByWaterWheel() ? 100 : 50;
    }

    private int getAxleRodPowerType(World world, int x, int y, int z) {
        return getAxleRodPowerType(world, x, y, z, new java.util.HashSet());
    }

    private int getAxleRodPowerType(World world, int x, int y, int z, java.util.Set visited) {
        String key = x + "," + y + "," + z;
        if (visited.contains(key)) {
            return 0;
        }
        visited.add(key);

        int axis = world.getBlockMetadata(x, y, z) & 3;
        int[][] dirs;
        if (axis == 1) dirs = new int[][] { {0,-1,0,0}, {0,1,0,1} };
        else if (axis == 2) dirs = new int[][] { {0,0,-1,2}, {0,0,1,3} };
        else dirs = new int[][] { {-1,0,0,4}, {1,0,0,5} };

        for (int d = 0; d < 2; d++) {
            int dx = dirs[d][0], dy = dirs[d][1], dz = dirs[d][2];
            int sideToward = dirs[d][3];
            int cx = x, cy = y, cz = z;
            while (true) {
                cx += dx; cy += dy; cz += dz;
                int id = world.getBlockId(cx, cy, cz);
                if (id == Block.axleRod.blockID) {
                    continue;
                } else if (id == Block.millAxle.blockID) {
                    int inputSide = world.getBlockMetadata(cx, cy, cz) & 7;
                    int outputSide = getOpposite(inputSide);
                    int fromAxleTowardRod = getOpposite(sideToward);
                    if (fromAxleTowardRod == outputSide) {
                        int powerType = getMillAxlePowerType(world, cx, cy, cz);
                        if (powerType != 0) return powerType;
                    }
                    break;
                } else if (id == Block.gearbox.blockID) {
                    int powerType = getGearboxPowerTypeAt(world, cx, cy, cz, visited);
                    if (powerType != 0) return powerType;
                    break;
                } else {
                    break;
                }
            }
        }
        return 0;
    }

    private int getMillAxlePowerType(World world, int ax, int ay, int az) {
        int millMeta = world.getBlockMetadata(ax, ay, az) & 7;
        int inputSide = millMeta;
        int ox = ax + (inputSide == 5 ? 1 : inputSide == 4 ? -1 : 0);
        int oy = ay + (inputSide == 1 ? 1 : inputSide == 0 ? -1 : 0);
        int oz = az + (inputSide == 3 ? 1 : inputSide == 2 ? -1 : 0);
        int nid = world.getBlockId(ox, oy, oz);
        if (nid == Block.johnMill.blockID) return 1;
        if (nid == Block.waterWheel.blockID) {
            if (world.getBlockId(ox, oy, oz) != Block.waterWheel.blockID) return 0;
            TileEntity te = world.getBlockTileEntity(ox, oy, oz);
            if (te instanceof TileEntityWaterWheel && ((TileEntityWaterWheel) te).providingPower) {
                return 2;
            }
        }
        return 0;
    }

    private int getGearboxPowerTypeAt(World world, int gx, int gy, int gz) {
        return getGearboxPowerTypeAt(world, gx, gy, gz, new java.util.HashSet());
    }

    private int getGearboxPowerTypeAt(World world, int gx, int gy, int gz, java.util.Set visited) {
        String key = gx + "," + gy + "," + gz;
        if (visited.contains(key)) {
            return 0;
        }
        visited.add(key);

        int meta = world.getBlockMetadata(gx, gy, gz) & 3;
        int inputSide = meta == 0 ? 2 : meta == 1 ? 5 : meta == 2 ? 3 : 4;

        int nx = gx + (inputSide == 4 ? 1 : inputSide == 5 ? -1 : 0);
        int ny = gy + (inputSide == 1 ? -1 : inputSide == 0 ? 1 : 0);
        int nz = gz + (inputSide == 2 ? 1 : inputSide == 3 ? -1 : 0);

        int nid = world.getBlockId(nx, ny, nz);
        if (nid == Block.axleRod.blockID) {
            return getAxleRodPowerType(world, nx, ny, nz, visited);
        }
        return 0;
    }

    private int getOpposite(int side) {
        switch (side) {
            case 0: return 1;
            case 1: return 0;
            case 2: return 3;
            case 3: return 2;
            case 4: return 5;
            case 5: return 4;
            default: return side;
        }
    }

    @Override
    public void updateEntity() {
        boolean wasBurning = this.isBurning();
        boolean powered = this.isPowered();

        if (powered && this.itemStacks[0] != null && canProcess()) {
            this.cooktime++;
            int required = getRequiredCookTime();
            if (this.cooktime >= required) {
                if(canProcess()) {
                    this.cooktime = 0;
                    this.processItem();
                }
            }
        } else {
            this.cooktime = 0;
        }

        if (wasBurning != this.isBurning()) {
            this.onInventoryChanged();
        }
    }

    private boolean canProcess() {
        if(this.itemStacks[0] == null) {
            return false;
        } else {
            ItemStack output = TurntableRecipes.processing().getResult(this.itemStacks[0].getItem().shiftedIndex);
            if (output == null) return false;
            if (this.itemStacks[1] == null) return true;
            if (!this.itemStacks[1].isItemEqual(output)) return false;
            int result = this.itemStacks[1].stackSize + output.stackSize;
            return result <= this.getInventoryStackLimit() && result <= this.itemStacks[1].getMaxStackSize();
        }
    }

    public void processItem() {
        if(this.canProcess()) {
            ItemStack var1 = TurntableRecipes.processing().getResult(this.itemStacks[0].getItem().shiftedIndex);
            if(this.itemStacks[1] == null) {
                this.itemStacks[1] = var1.copy();
            } else if(this.itemStacks[1].itemID == var1.itemID) {
                this.itemStacks[1].stackSize += var1.stackSize;
            }
            --this.itemStacks[0].stackSize;
            if(this.itemStacks[0].stackSize <= 0) {
                this.itemStacks[0] = null;
            }
        }
    }

    public boolean canInteractWith(EntityPlayer player) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
    }
}
