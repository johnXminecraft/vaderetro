package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityOpenHearthFurnace;

import java.util.Random;

public class BlockOpenHearthFurnace extends BlockContainer {

    private final Random random = new Random();
    private final boolean isLit;
    private static boolean keepFurnaceInventory = false;

    public BlockOpenHearthFurnace(int id, boolean isLit) {
        super(id, Material.rock);
        this.isLit = isLit;
        this.blockIndexInTexture = 111;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.openHearthFurnaceIdle.blockID;
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
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
        if(this.isLit) {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            float var7 = (float)var2 + 0.5F;
            float var8 = (float)var3 + 0.0F + var5.nextFloat() * 10.0F / 16.0F;
            float var9 = (float)var4 + 0.5F;
            float var10 = 0.52F;
            float var11 = var5.nextFloat() * 0.6F - 0.3F;

            if(var6 == 4) {
                var1.spawnParticle("flame", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            } else if(var6 == 5) {
                var1.spawnParticle("flame", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            } else if(var6 == 2) {
                var1.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
            } else if(var6 == 3) {
                var1.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if(var5 == 1) {
            return this.isLit ? this.blockIndexInTexture + 32 : this.blockIndexInTexture + 16;
        } else if(var5 == 0) {
            return this.isLit ? this.blockIndexInTexture + 31 : this.blockIndexInTexture + 15;
        } else {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            return var5 != var6 ? this.blockIndexInTexture : (this.isLit ? this.blockIndexInTexture + 30 : this.blockIndexInTexture + 14);
        }
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        return var1 == 1 ? this.blockIndexInTexture + 16 : (var1 == 0 ? this.blockIndexInTexture + 15 : (var1 == 3 ? this.blockIndexInTexture + 14 : this.blockIndexInTexture));
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        if (!var1.multiplayerWorld) {
            TileEntityOpenHearthFurnace var6 = (TileEntityOpenHearthFurnace) var1.getBlockTileEntity(var2, var3, var4);
            var5.displayGUIOpenHearthFurnace(var6);
        }
        return true;
    }

    public static void updateBlockState(boolean var0, World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);
        keepFurnaceInventory = true;
        if(var0) {
            var1.setBlockWithNotify(var2, var3, var4, Block.openHearthFurnaceActive.blockID);
        } else {
            var1.setBlockWithNotify(var2, var3, var4, Block.openHearthFurnaceIdle.blockID);
        }
        keepFurnaceInventory = false;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5);
        var6.func_31004_j();
        var1.setBlockTileEntity(var2, var3, var4, var6);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityOpenHearthFurnace();
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
    public void onBlockRemoval(World var1, int var2, int var3, int var4) {
        if(!keepFurnaceInventory) {
            TileEntityOpenHearthFurnace var5 = (TileEntityOpenHearthFurnace)var1.getBlockTileEntity(var2, var3, var4);

            for(int var6 = 0; var6 < var5.getSizeInventory(); ++var6) {
                ItemStack var7 = var5.getStackInSlot(var6);

                if(var7 != null) {
                    float var8 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var9 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var10 = this.random.nextFloat() * 0.8F + 0.1F;

                    while(var7.stackSize > 0) {
                        int var11 = this.random.nextInt(21) + 10;
                        if(var11 > var7.stackSize) {
                            var11 = var7.stackSize;
                        }

                        var7.stackSize -= var11;
                        EntityItem var12 = new EntityItem(var1, (double)((float)var2 + var8), (double)((float)var3 + var9), (double)((float)var4 + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));
                        float var13 = 0.05F;
                        var12.motionX = (double)((float)this.random.nextGaussian() * var13);
                        var12.motionY = (double)((float)this.random.nextGaussian() * var13 + 0.2F);
                        var12.motionZ = (double)((float)this.random.nextGaussian() * var13);
                        var1.entityJoinedWorld(var12);
                    }
                }
            }
        }

        super.onBlockRemoval(var1, var2, var3, var4);
    }
}
