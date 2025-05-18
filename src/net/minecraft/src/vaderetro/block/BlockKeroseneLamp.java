package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityKeroseneLamp;
import java.util.Random;

public class BlockKeroseneLamp extends BlockContainer {

    private final Random random = new Random();
    private final boolean isLit;
    private static boolean keepLampInventory = false;

    public BlockKeroseneLamp(int var1, boolean isLit) {
        super(var1, Material.iron);
        this.blockIndexInTexture = 137;
        this.isLit = isLit;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityKeroseneLamp();
    }

    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.lampKeroseneIdle.blockID;
    }

    @Override
    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        return this.isLit ? this.blockIndexInTexture + 16: this.blockIndexInTexture;
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        if (!var1.multiplayerWorld) {
            TileEntityKeroseneLamp var6 = (TileEntityKeroseneLamp) var1.getBlockTileEntity(var2, var3, var4);
            var5.displayGUIKeroseneLamp(var6);
        }
        return true;
    }

    public static void updateBlockState(boolean var0, World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);
        keepLampInventory = true;
        if(var0) {
            var1.setBlockWithNotify(var2, var3, var4, Block.lampKeroseneActive.blockID);
        } else {
            var1.setBlockWithNotify(var2, var3, var4, Block.lampKeroseneActive.blockID);
        }
        keepLampInventory = false;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5);
        var6.func_31004_j();
        var1.setBlockTileEntity(var2, var3, var4, var6);
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
        if(!keepLampInventory) {
            TileEntityKeroseneLamp var5 = (TileEntityKeroseneLamp) var1.getBlockTileEntity(var2, var3, var4);

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
