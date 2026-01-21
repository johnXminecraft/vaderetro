package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityDryer;

import java.util.Random;

public class BlockDryer extends BlockContainer {

    private final Random random = new Random();
    private final boolean isActive;
    private static boolean keepInventory = false;

    public BlockDryer(int id, boolean isActive) {
        super(id, Material.rock);
        this.isActive = isActive;
        this.blockIndexInTexture = 171;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        this.setLightOpacity(255);
    }

    @Override
    public int idDropped(int id, Random random) {
        return Block.dryerIdle.blockID;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityDryer();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        this.setDefaultDirection(world, x, y, z);
    }

    private void setDefaultDirection(World world, int x, int y, int z) {
        if(!world.multiplayerWorld) {
            int var5 = world.getBlockId(x, y, z - 1);
            int var6 = world.getBlockId(x, y, z + 1);
            int var7 = world.getBlockId(x - 1, y, z);
            int var8 = world.getBlockId(x + 1, y, z);
            byte var9 = 3;

            if(Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6]) var9 = 3;
            if(Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5]) var9 = 2;
            if(Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8]) var9 = 5;
            if(Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7]) var9 = 4;

            world.setBlockMetadataWithNotify(x, y, z, var9);
        }
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if(this.isActive) {
            int var6 = world.getBlockMetadata(x, y, z);
            float var7 = (float)x + 0.5F;
            float var8 = (float)y + 0.0F + random.nextFloat() * 10.0F / 16.0F;
            float var9 = (float)z + 0.5F;
            float var10 = 0.52F;
            float var11 = random.nextFloat() * 0.6F - 0.3F;

            if(var6 == 4) {
                world.spawnParticle("smoke", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            } else if(var6 == 5) {
                world.spawnParticle("smoke", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            } else if(var6 == 2) {
                world.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
            } else if(var6 == 3) {
                world.spawnParticle("smoke", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        if(var5 == 1) {
            return this.isActive ? this.blockIndexInTexture + 16 : this.blockIndexInTexture + 1;
        } else if(var5 == 0) {
            return 7;
        } else {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            return var5 != var6 ? this.blockIndexInTexture : (this.isActive ? this.blockIndexInTexture - 1 : this.blockIndexInTexture - 2);
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getBlockTextureFromSide(int var1) {
        return var1 == 1 ? this.blockIndexInTexture + 1 : (var1 == 0 ? 7 : (var1 == 3 ? this.blockIndexInTexture -2 : this.blockIndexInTexture));
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if (!world.multiplayerWorld) {
            TileEntityDryer tileEntity = (TileEntityDryer) world.getBlockTileEntity(x, y, z);
            player.displayGUIDryer(tileEntity);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving) {
        int var6 = MathHelper.floor_double((double)(entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if(var6 == 0) world.setBlockMetadataWithNotify(x, y, z, 2);
        if(var6 == 1) world.setBlockMetadataWithNotify(x, y, z, 5);
        if(var6 == 2) world.setBlockMetadataWithNotify(x, y, z, 3);
        if(var6 == 3) world.setBlockMetadataWithNotify(x, y, z, 4);
    }

    @Override
    public void onBlockRemoval(World world, int x, int y, int z) {
        if(!keepInventory) {
            TileEntityDryer var5 = (TileEntityDryer) world.getBlockTileEntity(x, y, z);
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
                        EntityItem var12 = new EntityItem(world, (double)((float)x + var8), (double)((float)y + var9), (double)((float)z + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));
                        float var13 = 0.05F;
                        var12.motionX = (double)((float)this.random.nextGaussian() * var13);
                        var12.motionY = (double)((float)this.random.nextGaussian() * var13 + 0.2F);
                        var12.motionZ = (double)((float)this.random.nextGaussian() * var13);
                        world.entityJoinedWorld(var12);
                    }
                }
            }
        }
        super.onBlockRemoval(world, x, y, z);
    }

    public static void updateBlockState(boolean isActive, World world, int x, int y, int z) {
        int var5 = world.getBlockMetadata(x, y, z);
        TileEntity var6 = world.getBlockTileEntity(x, y, z);
        keepInventory = true;
        if(isActive) {
            world.setBlockWithNotify(x, y, z, Block.dryerActive.blockID);
        } else {
            world.setBlockWithNotify(x, y, z, Block.dryerIdle.blockID);
        }
        keepInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, var5);
        var6.func_31004_j();
        world.setBlockTileEntity(x, y, z, var6);
    }
}
