package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityOven;

import java.util.Random;

public class BlockOven extends BlockContainer {

    private final Random random = new Random();
    private final boolean isLit;
    private static boolean keepInventory = false;

    public BlockOven(int id, boolean isLit) {
        super(id, Material.rock);
        this.isLit = isLit;
        this.blockIndexInTexture = 7;
    }

    @Override
    public int idDropped(int id, Random random) {
        return Block.ovenIdle.blockID;
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
        if(this.isLit) {
            int var6 = world.getBlockMetadata(x, y, z);
            float var7 = (float)x + 0.5F;
            float var8 = (float)y + 0.0F + random.nextFloat() * 10.0F / 16.0F;
            float var9 = (float)z + 0.5F;
            float var10 = 0.52F;
            float var11 = random.nextFloat() * 0.6F - 0.3F;

            if(var6 == 4) {
                world.spawnParticle("flame", (double)(var7 - var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            } else if(var6 == 5) {
                world.spawnParticle("flame", (double)(var7 + var10), (double)var8, (double)(var9 + var11), 0.0D, 0.0D, 0.0D);
            } else if(var6 == 2) {
                world.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 - var10), 0.0D, 0.0D, 0.0D);
            } else if(var6 == 3) {
                world.spawnParticle("flame", (double)(var7 + var11), (double)var8, (double)(var9 + var10), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int sideId) {
        if(sideId == 1) {
            return 270;
        } else if(sideId == 0) {
            return 7;
        } else {
            int var6 = blockAccess.getBlockMetadata(x, y, z);
            return sideId != var6 ? 269 : (this.isLit ? 268 : 267);
        }
    }

    @Override
    public int getBlockTextureFromSide(int sideId) {
        return sideId == 1 ? 270 : (sideId == 0 ? 7 : (sideId == 3 ? 267 : 269));
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if (!world.multiplayerWorld) {
            TileEntityOven tileEntity = (TileEntityOven) world.getBlockTileEntity(x, y, z);
            player.displayGUIOven(tileEntity);
        }
        return true;
    }

    public static void updateBlockState(boolean isActive, World world, int x, int y, int z) {
        int blockMetadata = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        keepInventory = true;
        if(isActive) {
            world.setBlockWithNotify(x, y, z, Block.ovenActive.blockID);
        } else {
            world.setBlockWithNotify(x, y, z, Block.ovenIdle.blockID);
        }
        keepInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, blockMetadata);
        tileEntity.func_31004_j();
        world.setBlockTileEntity(x, y, z, tileEntity);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityOven();
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
            TileEntityOven tileEntity = (TileEntityOven)world.getBlockTileEntity(x, y, z);

            for(int i = 0; i < tileEntity.getSizeInventory(); ++i) {
                ItemStack stackInSlot = tileEntity.getStackInSlot(i);

                if(stackInSlot != null) {
                    float var8 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var9 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var10 = this.random.nextFloat() * 0.8F + 0.1F;

                    while(stackInSlot.stackSize > 0) {
                        int var11 = this.random.nextInt(21) + 10;
                        if(var11 > stackInSlot.stackSize) {
                            var11 = stackInSlot.stackSize;
                        }

                        stackInSlot.stackSize -= var11;
                        EntityItem item = new EntityItem(world, (double)((float)x + var8), (double)((float)y + var9), (double)((float)z + var10), new ItemStack(stackInSlot.itemID, var11, stackInSlot.getItemDamage()));
                        float var13 = 0.05F;
                        item.motionX = (double)((float)this.random.nextGaussian() * var13);
                        item.motionY = (double)((float)this.random.nextGaussian() * var13 + 0.2F);
                        item.motionZ = (double)((float)this.random.nextGaussian() * var13);
                        world.entityJoinedWorld(item);
                    }
                }
            }
        }

        super.onBlockRemoval(world, x, y, z);
    }
}
