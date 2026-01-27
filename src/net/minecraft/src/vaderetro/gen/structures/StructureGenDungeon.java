package net.minecraft.src.vaderetro.gen.structures;

import net.minecraft.src.Block;

import java.util.Random;

public class StructureGenDungeon implements IStructure {

    private int[][][] blocksToSpawn;
    private boolean isInit = false;
    private Random random = new Random();

    public int[][][] getStructure() {
        if (!isInit) {
            initStructure();
        }
        return blocksToSpawn;
    }

    @Override
    public void initStructure() {

        int sizeX = 7 + random.nextInt(6);
        if((sizeX % 2) == 0) sizeX++;
        int sizeY = 7 + random.nextInt(6);
        int sizeZ = sizeX;

        blocksToSpawn = new int[sizeX][sizeY][sizeZ];

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    boolean isWall =
                            x == 0 || x == sizeX - 1 ||
                                    y == 0 || y == sizeY - 1 ||
                                    z == 0 || z == sizeZ - 1;
                    if (isWall) {
                        if(random.nextInt(100) <= 10) {
                            blocksToSpawn[x][y][z] = Block.cobblestoneMossy.blockID;
                        } else {
                            blocksToSpawn[x][y][z] = Block.cobblestone.blockID;
                        }
                    } else {
                        blocksToSpawn[x][y][z] = 0;
                    }
                }
            }
        }

        blocksToSpawn[sizeX / 2][1][sizeZ / 2] = Block.mobSpawner.blockID;

        isInit = true;
    }
}
