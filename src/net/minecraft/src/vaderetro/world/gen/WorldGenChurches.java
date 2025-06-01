package net.minecraft.src.vaderetro.world.gen;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.world.gen.structures.StructureChurch;

import java.util.Random;

public class WorldGenChurches extends WorldGenerator {

    private final int[][][] blocksToSpawn;

    public WorldGenChurches() {
        StructureChurch structureChurch = new StructureChurch();
        blocksToSpawn = structureChurch.getStructure();
    }

    @Override
    public boolean generate(World world, Random random, int posX, int posY, int posZ) {

        if(world.getBlockId(posX, posY - 1, posZ) == Block.grass.blockID && random.nextInt(100) < 50) {

            generateGround(world, random, posX, posY, posZ);
            placeBlocks(world, random, posX, posY, posZ);

            System.out.println("Generated church!");

            return true;
        }

        return false;
    }

    private void generateGround(World world, Random random, int posX, int posY, int posZ) {
        for(int x = 0; x < 7; x++) {
            for(int z = 0; z < 7; z++) {
                for(int y = posY - 2; y > 0; y--) {
                    if(
                            world.getBlockMaterial(posX + x, y, posZ + z) != Material.air ||
                            world.getBlockMaterial(posX + x, y, posZ + z) != Material.water ||
                            world.getBlockMaterial(posX + x, y, posZ + z) != Material.lava ||
                            world.getBlockMaterial(posX + x, y, posZ + z) != Material.oil
                    ) {
                        break;
                    } else {
                        world.setBlock(posX + x, y, posZ + z, Block.dirt.blockID);
                    }
                }
            }
        }
    }

    private void placeBlocks(World world, Random random, int posX, int posY, int posZ) {
        for(int y = 0; y < 12; y++) {
            for(int z = 0; z < 7; z++) {
                label:
                for(int x = 0; x < 7; x++) {
                    if(blocksToSpawn[x][y][z] == Block.chest.blockID) {
                        world.setBlockWithNotify(posX + x, posY + y - 1, posZ + z, this.blocksToSpawn[x][y][z]);
                        TileEntityChest tileEntityChest = (TileEntityChest)world.getBlockTileEntity(posX + x, posY + y - 1, posZ + z);
                        int itemStackNumber = 0;
                        while(true) {
                            if(itemStackNumber >= 8) {
                                continue label;
                            }
                            ItemStack itemStack = this.getLoot(random);
                            if(itemStack != null) {
                                tileEntityChest.setInventorySlotContents(random.nextInt(tileEntityChest.getSizeInventory()), itemStack);
                            }
                            ++itemStackNumber;
                        }
                    } else {
                        world.setBlock(posX + x, posY + y - 1, posZ + z, this.blocksToSpawn[x][y][z]);
                    }
                }
            }
        }
    }

    private ItemStack getLoot(Random random) {
        int var2 = random.nextInt(11);
        return var2 == 0 ? new ItemStack(Item.saddle) : (var2 == 1 ? new ItemStack(Item.ingotIron, random.nextInt(4) + 1) : (var2 == 2 ? new ItemStack(Item.bread) : (var2 == 3 ? new ItemStack(Item.wheat, random.nextInt(4) + 1) : (var2 == 4 ? new ItemStack(Item.gunpowder, random.nextInt(4) + 1) : (var2 == 5 ? new ItemStack(Item.silk, random.nextInt(4) + 1) : (var2 == 6 ? new ItemStack(Item.bucketEmpty) : (var2 == 7 && random.nextInt(100) == 0 ? new ItemStack(Item.appleGold) : (var2 == 8 && random.nextInt(2) == 0 ? new ItemStack(Item.redstone, random.nextInt(4) + 1) : (var2 == 9 && random.nextInt(10) == 0 ? new ItemStack(Item.itemsList[Item.record13.shiftedIndex + random.nextInt(2)]) : (var2 == 10 ? new ItemStack(Item.dyePowder, 1, 3) : null))))))))));
    }
}
