package net.minecraft.src.vaderetro.gen.world;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.gen.structures.StructureGenRadioTower;

import java.util.Random;

public class WorldGenRadioTower extends WorldGenerator {

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        if (world.getBlockId(x, y - 1, z) != Block.grass.blockID)
            return false;

        if (random.nextInt(3000) != 0)
            return false;

        if (world.getWorldChunkManager().getBiomeGenAt(x, z) != BiomeGenBase.taiga)
            return false;

        int[][][] blocks = StructureGenRadioTower.BLOCKS;

        for (int i = 0; i < blocks[0].length; i++) {
            if (!world.isAirBlock(x + 1, y + i, z + 2)) {
                return false;
            }
        }

        generateGround(world, x, y, z);

        for (int yy = 0; yy < blocks[0].length; yy++) {
            for (int xx = 0; xx < blocks.length; xx++) {
                for (int zz = 0; zz < blocks[0][0].length; zz++) {

                    int id = blocks[xx][yy][zz];
                    if (id != 0) {
                        world.setBlock(x + xx, y + yy, z + zz, id);
                    }
                }
            }
        }

        return true;
    }

    private void generateGround(World world, int x, int y, int z) {
        for (int xx = 0; xx < 7; xx++) {
            for (int zz = 0; zz < 7; zz++) {
                for (int yy = y - 1; yy > 0; yy--) {
                    if (world.getBlockId(x + xx, yy, z + zz) != 0) break;
                    world.setBlock(x + xx, yy, z + zz, Block.dirt.blockID);
                }
            }
        }
    }
}
