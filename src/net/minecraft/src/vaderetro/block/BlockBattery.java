package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityBattery;

import java.util.Random;

public class BlockBattery extends BlockContainer {

    private static final int TEXTURE_IO = 302;
    private static final int TEXTURE_OTHER = 301;

    public BlockBattery(int id) {
        super(id, Material.iron);
        this.blockIndexInTexture = TEXTURE_OTHER;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return this.blockID;
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
        if (var5 == 1) return TEXTURE_IO; 
        int metadata = var1.getBlockMetadata(var2, var3, var4);
        if (var5 == metadata) return TEXTURE_IO; 
        return TEXTURE_OTHER;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 3) return TEXTURE_IO; 
        if (var1 == 1) return TEXTURE_IO; 
        return TEXTURE_OTHER;
    }

    @Override
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
        int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if(var6 == 0) var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
        if(var6 == 1) var1.setBlockMetadataWithNotify(var2, var3, var4, 5);
        if(var6 == 2) var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
        if(var6 == 3) var1.setBlockMetadataWithNotify(var2, var3, var4, 4);
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        if (!var1.multiplayerWorld) {
            if (var5.isSneaking()) {
                return false;
            }
            TileEntityBattery var6 = (TileEntityBattery) var1.getBlockTileEntity(var2, var3, var4);
            if (var6 != null) {
                var5.displayGUIBattery(var6);
            }
            return true;
        }
        return false;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityBattery();
    }
}
