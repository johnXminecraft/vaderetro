package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.energy.IEnergyEmitter;
import net.minecraft.src.vaderetro.energy.IEnergySink;
import java.util.HashSet;
import java.util.Set;

public class TileEntityBattery extends TileEntity implements IInventory, IEnergyEmitter, IEnergySink {

    public int vreStored = 0;
    public int maxVre = 5000;

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
    }

    @Override
    public String getInvName() {
        return "Battery";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        this.vreStored = var1.getShort("VreStored");
    }

    @Override
    public void writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        var1.setShort("VreStored", (short) this.vreStored);
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && var1.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    public int getVreScaled(int var1) {
        return this.vreStored * var1 / this.maxVre;
    }

    @Override
    public boolean emitsEnergy(int side) {
        // Output from front side
        // Side 2: North, 3: South, 4: West, 5: East
        int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        // Metadata 2: North (Face 2)
        // Metadata 5: East (Face 5)
        // Metadata 3: South (Face 3)
        // Metadata 4: West (Face 4)
        return side == metadata;
    }

    @Override
    public boolean acceptsEnergy(int side) {
        return side == 1; // Input from above (Top)
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.multiplayerWorld) {
            if (this.vreStored > 0) {
                // Try to push energy to the front
                int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
                int x = xCoord, y = yCoord, z = zCoord;
                
                switch (metadata) {
                    case 2: z--; break; // North
                    case 3: z++; break; // South
                    case 4: x--; break; // West
                    case 5: x++; break; // East
                }

                TileEntity te = worldObj.getBlockTileEntity(x, y, z);
                if (te instanceof IEnergySink) {
                    IEnergySink sink = (IEnergySink) te;
                    // Calculate opposite side for injection
                    int oppositeSide = 0;
                    switch (metadata) {
                        case 2: oppositeSide = 3; break;
                        case 3: oppositeSide = 2; break;
                        case 4: oppositeSide = 5; break;
                        case 5: oppositeSide = 4; break;
                    }

                    if (sink.acceptsEnergy(oppositeSide)) {
                        int toPush = Math.min(this.vreStored, 32); // Max output rate? Assuming 32 like generator
                        Set<TileEntity> visited = new HashSet<TileEntity>();
                        visited.add(this);
                        int consumed = sink.injectEnergy(toPush, oppositeSide, visited);
                        this.vreStored -= consumed;
                    }
                }
            }
        }
    }

    @Override
    public int injectEnergy(int amount, int side, Set<TileEntity> visited) {
        if (visited.contains(this)) return 0;
        if (!acceptsEnergy(side)) return 0;
        
        visited.add(this);
        
        int space = maxVre - vreStored;
        int accepted = Math.min(space, amount);
        vreStored += accepted;
        return accepted;
    }

    public void openChest() {}
    public void closeChest() {}
}
