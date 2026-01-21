package net.minecraft.src.vaderetro.gen.world;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.gen.structures.StructureGenChurch;

import java.util.Random;

public class WorldGenChurches extends WorldGenerator {

    private final int[][][] blocksToSpawn;

    public WorldGenChurches() {
        StructureGenChurch structureGenChurch = new StructureGenChurch();
        blocksToSpawn = structureGenChurch.getStructure();
    }

    @Override
    public boolean generate(World world, Random random, int posX, int posY, int posZ) {

        if(
                world.getBlockId(posX, posY - 1, posZ) == Block.grass.blockID &&
                random.nextInt(100) < 10 &&
                world.getWorldChunkManager().getBiomeGenAt(posX, posZ) == BiomeGenBase.taiga
        ) {

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
                            world.getBlockId(posX + x, y, posZ + z) != 0
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
        int lootItemId = random.nextInt(14);
        return lootItemId == 0 ? new ItemStack(Item.chain, random.nextInt(4) + 1) :
                (lootItemId == 1 ? new ItemStack(Item.canvas) :
                (lootItemId == 2 ? new ItemStack(Item.icon) :
                (lootItemId == 3 ? new ItemStack(Item.appleRed, random.nextInt(3) + 1) :
                (lootItemId == 4 ? new ItemStack(Item.appleGold) :
                (lootItemId == 5 ? new ItemStack(Item.charcoal, random.nextInt(4) + 1) :
                (lootItemId == 6 ? new ItemStack(Item.helmetChain) :
                (lootItemId == 7 ? new ItemStack(Item.plateChain) :
                (lootItemId == 8 ? new ItemStack(Item.legsChain) :
                (lootItemId == 9 ? new ItemStack(Item.bootsChain) :
                (lootItemId == 10 ? new ItemStack(Item.book, random.nextInt(2) + 1) :
                (lootItemId == 11 ? new ItemStack(Item.ingotGold, random.nextInt(4) + 1) :
                (lootItemId == 12 ? new ItemStack(Item.dyePowder, random.nextInt(4) + 1, 1) :
                (lootItemId == 13 ? new ItemStack(Item.dyePowder, random.nextInt(4) + 1, 4) : null)))))))))))));
    }
}
