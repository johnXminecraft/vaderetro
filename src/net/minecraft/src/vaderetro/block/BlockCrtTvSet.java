package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityCrtTvSet;

import java.util.Random;

public class BlockCrtTvSet extends BlockContainer {

    private static final Random random = new Random();
    private final boolean isPowered;

    public BlockCrtTvSet(int id, boolean isPowered, int blockIndexInTexture) {
        super(id, Material.wood);
        this.isPowered = isPowered;
        this.blockIndexInTexture = blockIndexInTexture;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.crtTvSetIdle.blockID;
    }

    @Override
    public void onBlockAdded(World var1, int var2, int var3, int var4) {
        super.onBlockAdded(var1, var2, var3, var4);
        this.setDefaultDirection(var1, var2, var3, var4);
    }

    private void setDefaultDirection(World var1, int var2, int var3, int var4) {
        if(!var1.multiplayerWorld) {
            int var5 = var1.getBlockId(var2, var3, var4 - 1);
            int var6 = var1.getBlockId(var2, var3, var4 + 1);
            int var7 = var1.getBlockId(var2 - 1, var3, var4);
            int var8 = var1.getBlockId(var2 + 1, var3, var4);
            byte var9 = 3;

            if(Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6]) var9 = 3;
            if(Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5]) var9 = 2;
            if(Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8]) var9 = 5;
            if(Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7]) var9 = 4;

            var1.setBlockMetadataWithNotify(var2, var3, var4, var9);
        }
    }

    @Override
    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if(var5 == 1) {
            return this.blockIndexInTexture + 1;
        } else if(var5 == 0) {
            return 4;
        } else {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            if(random.nextInt(100) == 1) {
                return var5 != var6 ? this.blockIndexInTexture : (this.isPowered ? this.blockIndexInTexture + 2 : this.blockIndexInTexture - 16);
            }
            return var5 != var6 ? this.blockIndexInTexture : (this.isPowered ? this.blockIndexInTexture - 15 : this.blockIndexInTexture - 16);
        }
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        return var1 == 1 ? this.blockIndexInTexture + 1 : (var1 == 0 ? 4 : (var1 == 3 ? this.blockIndexInTexture - 16 : this.blockIndexInTexture));
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        if (!var1.multiplayerWorld) {
            TileEntityCrtTvSet var6 = (TileEntityCrtTvSet) var1.getBlockTileEntity(var2, var3, var4);
            var5.displayGUICrtTvSet(var6);
        }
        return true;
    }

    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
        if(var5 > 0 && Block.blocksList[var5].canProvidePower()) {
            boolean var6 = var1.isBlockGettingPowered(var2, var3, var4) || var1.isBlockIndirectlyGettingPowered(var2, var3, var4);
            TileEntityCrtTvSet var7 = (TileEntityCrtTvSet) var1.getBlockTileEntity(var2, var3, var4);
            var7.setPowered(var6);
        }
    }

    public static void updateBlockState(boolean var0, World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        if(var0) {
            var1.setBlockWithNotify(var2, var3, var4, Block.crtTvSetActive.blockID);
        } else {
            var1.setBlockWithNotify(var2, var3, var4, Block.crtTvSetIdle.blockID);
        }
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityCrtTvSet(isPowered);
    }

    @Override
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
        int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if(var6 == 0) var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
        if(var6 == 1) var1.setBlockMetadataWithNotify(var2, var3, var4, 5);
        if(var6 == 2) var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
        if(var6 == 3) var1.setBlockMetadataWithNotify(var2, var3, var4, 4);
    }
}
