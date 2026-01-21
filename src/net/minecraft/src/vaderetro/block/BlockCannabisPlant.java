package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;

import java.util.Random;

public class BlockCannabisPlant extends BlockFlower {

    public BlockCannabisPlant(int id, int blockId) {
        super(id, blockId);
        this.blockIndexInTexture = blockId;
        this.setTickOnLoad(true);
        float var3 = 0.5F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
    }

    protected boolean canThisPlantGrowOnThisBlockID(int blockIdUnderneath) {
        return blockIdUnderneath == Block.tilledField.blockID;
    }

    public void updateTick(World world, int x, int y, int z, Random random) {
        super.updateTick(world, x, y, z, random);
        if(world.getBlockLightValue(x, y + 1, z) >= 9) {
            int blockTobaccoPlant = world.getBlockMetadata(x, y, z);
            if(blockTobaccoPlant < 3) {
                float growthRate = this.getGrowthRate(world, x, y, z);
                if(random.nextInt((int)(100.0F / growthRate)) == 0) {
                    ++blockTobaccoPlant;
                    world.setBlockMetadataWithNotify(x, y, z, blockTobaccoPlant);
                }
            }
        }
    }

    public void fertilize(World world, int x, int y, int z) {
        world.setBlockMetadataWithNotify(x, y, z, 3);
    }

    private float getGrowthRate(World world, int x, int y, int z) {
        float var5 = 1.0F;
        int var6 = world.getBlockId(x, y, z - 1);
        int var7 = world.getBlockId(x, y, z + 1);
        int var8 = world.getBlockId(x - 1, y, z);
        int var9 = world.getBlockId(x + 1, y, z);
        int var10 = world.getBlockId(x - 1, y, z - 1);
        int var11 = world.getBlockId(x + 1, y, z - 1);
        int var12 = world.getBlockId(x + 1, y, z + 1);
        int var13 = world.getBlockId(x - 1, y, z + 1);
        boolean var14 = var8 == this.blockID || var9 == this.blockID;
        boolean var15 = var6 == this.blockID || var7 == this.blockID;
        boolean var16 = var10 == this.blockID || var11 == this.blockID || var12 == this.blockID || var13 == this.blockID;

        for(int var17 = x - 1; var17 <= x + 1; ++var17) {
            for(int var18 = z - 1; var18 <= z + 1; ++var18) {
                int var19 = world.getBlockId(var17, y - 1, var18);
                float var20 = 0.0F;
                if(var19 == Block.tilledField.blockID) {
                    var20 = 1.0F;
                    if(world.getBlockMetadata(var17, y - 1, var18) > 0) {
                        var20 = 3.0F;
                    }
                }
                if(var17 != x || var18 != z) {
                    var20 /= 4.0F;
                }
                var5 += var20;
            }
        }
        if(var16 || var14 && var15) {
            var5 /= 2.0F;
        }
        return var5;
    }

    public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
        if(var2 < 0) {
            var2 = 3;
        }
        return this.blockIndexInTexture + var2;
    }

    public int getRenderType() {
        return 6;
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int var5, float var6) {
        super.dropBlockAsItemWithChance(world, x, y, z, var5, var6);
        if(!world.multiplayerWorld) {
            for(int var7 = 0; var7 < 3; ++var7) {
                if(world.rand.nextInt(15) <= var5) {
                    float var8 = 0.7F;
                    float var9 = world.rand.nextFloat() * var8 + (1.0F - var8) * 0.5F;
                    float var10 = world.rand.nextFloat() * var8 + (1.0F - var8) * 0.5F;
                    float var11 = world.rand.nextFloat() * var8 + (1.0F - var8) * 0.5F;
                    EntityItem var12 = new EntityItem(world, (double)((float)x + var9), (double)((float)y + var10), (double)((float)z + var11), new ItemStack(Item.cannabisSeeds));
                    var12.delayBeforeCanPickup = 10;
                    world.entityJoinedWorld(var12);
                }
            }
        }
    }

    public int idDropped(int stage, Random random) {
        return stage == 3 ? Item.cannabisLeaf.shiftedIndex : -1;
    }

    public int quantityDropped(Random random) {
        return 1;
    }
}
