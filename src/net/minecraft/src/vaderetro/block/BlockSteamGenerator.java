package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntitySteamGenerator;

import java.util.Random;

public class BlockSteamGenerator extends BlockContainer {

    private final Random random = new Random();
    private final boolean isLit;
    private static boolean keepGeneratorInventory = false;
    private static final int TEXTURE_FRONT_IDLE = 287;
    private static final int TEXTURE_TOP = 271;
    private static final int TEXTURE_FRONT_ACTIVE = 303;
    private static final int TEXTURE_SIDES = 286;

    public BlockSteamGenerator(int id, boolean isLit) {
        super(id, Material.iron);
        this.isLit = isLit;
        this.blockIndexInTexture = 62; // Default furnace texture for now
    }

    @Override
    public int idDropped(int var1, Random var2) {
        return Block.steamGeneratorIdle.blockID;
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
        if (var5 == 1) return TEXTURE_TOP;
        if (var5 == 0) return TEXTURE_SIDES;
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        if (var5 != var6) return TEXTURE_SIDES;
        return this.isLit ? TEXTURE_FRONT_ACTIVE : TEXTURE_FRONT_IDLE;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        if (var1 == 3) return TEXTURE_FRONT_IDLE;
        if (var1 == 1) return TEXTURE_TOP;
        if (var1 == 0) return TEXTURE_SIDES;
        return TEXTURE_SIDES;
    }

    @Override
    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
        if (!var1.multiplayerWorld) {
            ItemStack held = var5.getCurrentEquippedItem();
            if (var5.isSneaking()) {
                return false;
            }
            TileEntitySteamGenerator var6 = (TileEntitySteamGenerator) var1.getBlockTileEntity(var2, var3, var4);
            var5.displayGUISteamGenerator(var6);
            return true;
        }
        return false;
    }

    public static void updateBlockState(boolean var0, World var1, int var2, int var3, int var4) {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);
        keepGeneratorInventory = true;
        if(var0) {
            var1.setBlockWithNotify(var2, var3, var4, Block.steamGeneratorActive.blockID);
        } else {
            var1.setBlockWithNotify(var2, var3, var4, Block.steamGeneratorIdle.blockID);
        }
        keepGeneratorInventory = false;
        var1.setBlockMetadataWithNotify(var2, var3, var4, var5);
        var6.func_31004_j();
        var1.setBlockTileEntity(var2, var3, var4, var6);
        var1.notifyBlocksOfNeighborChange(var2, var3, var4, var1.getBlockId(var2, var3, var4));
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntitySteamGenerator();
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
        if(!keepGeneratorInventory) {
            TileEntitySteamGenerator var5 = (TileEntitySteamGenerator)var1.getBlockTileEntity(var2, var3, var4);

            if (var5 != null) {
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
        }

        super.onBlockRemoval(var1, var2, var3, var4);
    }
}
