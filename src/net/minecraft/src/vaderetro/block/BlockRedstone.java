package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;

import java.util.Random;

public class BlockRedstone extends BlockOreStorage {

    public BlockRedstone(int var1, int var2) {
        super(var1, var2);
        this.setTickOnLoad(true);
    }

    public int tickRate() {
        return 2;
    }

    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        if(var1.getBlockMetadata(var2, var3, var4) == 0) {
            super.onBlockAdded(var1, var2, var3, var4);
        }
        var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
    }

    public void onBlockRemoval(World var1, int var2, int var3, int var4) {
        var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
        var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
    }

    public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        return true;
    }

    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        super.onNeighborBlockChange(var1, var2, var3, var4, var5);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
        return this.isPoweringTo(var1, var2, var3, var4, var5);
    }

    public boolean canProvidePower() {
        return true;
    }

    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        double var7 = (double)((float)var2 + 0.5F) + (double)(var5.nextFloat() - 0.5F) * 0.2D;
        double var9 = (double)((float)var3 + 0.7F) + (double)(var5.nextFloat() - 0.5F) * 0.2D;
        double var11 = (double)((float)var4 + 0.5F) + (double)(var5.nextFloat() - 0.5F) * 0.2D;
        double var13 = (double)0.22F;
        double var15 = (double)0.27F;
        if(var6 == 1) {
            var1.spawnParticle("reddust", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        } else if(var6 == 2) {
            var1.spawnParticle("reddust", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        } else if(var6 == 3) {
            var1.spawnParticle("reddust", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
        } else if(var6 == 4) {
            var1.spawnParticle("reddust", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
        } else {
            var1.spawnParticle("reddust", var7, var9, var11, 0.0D, 0.0D, 0.0D);
        }
    }
}
