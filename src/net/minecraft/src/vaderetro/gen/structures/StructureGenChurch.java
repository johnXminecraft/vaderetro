package net.minecraft.src.vaderetro.gen.structures;

import net.minecraft.src.Block;

public class StructureGenChurch implements IStructure {

    private final int[][][] blocksToSpawn = new int[7][12][7];
    private boolean isInit = false;

    public int[][][] getStructure() {
        if (!isInit) {
            initStructure();
        }
        return blocksToSpawn;
    }

    public void initStructure() {

        isInit = true;

        // y = 0
        blocksToSpawn[0][0][0] = Block.grass.blockID;
        blocksToSpawn[1][0][0] = Block.grass.blockID;
        blocksToSpawn[2][0][0] = Block.grass.blockID;
        blocksToSpawn[3][0][0] = Block.grass.blockID;
        blocksToSpawn[4][0][0] = Block.grass.blockID;
        blocksToSpawn[5][0][0] = Block.grass.blockID;
        blocksToSpawn[6][0][0] = Block.grass.blockID;

        blocksToSpawn[0][0][1] = Block.grass.blockID;
        blocksToSpawn[1][0][1] = Block.grass.blockID;
        blocksToSpawn[2][0][1] = Block.parquet.blockID;
        blocksToSpawn[3][0][1] = Block.parquet.blockID;
        blocksToSpawn[4][0][1] = Block.parquet.blockID;
        blocksToSpawn[5][0][1] = Block.grass.blockID;
        blocksToSpawn[6][0][1] = Block.grass.blockID;

        blocksToSpawn[0][0][2] = Block.grass.blockID;
        blocksToSpawn[1][0][2] = Block.parquet.blockID;
        blocksToSpawn[2][0][2] = Block.parquet.blockID;
        blocksToSpawn[3][0][2] = Block.parquet.blockID;
        blocksToSpawn[4][0][2] = Block.parquet.blockID;
        blocksToSpawn[5][0][2] = Block.parquet.blockID;
        blocksToSpawn[6][0][2] = Block.grass.blockID;

        blocksToSpawn[0][0][3] = Block.grass.blockID;
        blocksToSpawn[1][0][3] = Block.parquet.blockID;
        blocksToSpawn[2][0][3] = Block.parquet.blockID;
        blocksToSpawn[3][0][3] = Block.parquet.blockID;
        blocksToSpawn[4][0][3] = Block.parquet.blockID;
        blocksToSpawn[5][0][3] = Block.parquet.blockID;
        blocksToSpawn[6][0][3] = Block.grass.blockID;

        blocksToSpawn[0][0][4] = Block.grass.blockID;
        blocksToSpawn[1][0][4] = Block.parquet.blockID;
        blocksToSpawn[2][0][4] = Block.parquet.blockID;
        blocksToSpawn[3][0][4] = Block.parquet.blockID;
        blocksToSpawn[4][0][4] = Block.parquet.blockID;
        blocksToSpawn[5][0][4] = Block.parquet.blockID;
        blocksToSpawn[6][0][4] = Block.grass.blockID;

        blocksToSpawn[0][0][5] = Block.grass.blockID;
        blocksToSpawn[1][0][5] = Block.grass.blockID;
        blocksToSpawn[2][0][5] = Block.parquet.blockID;
        blocksToSpawn[3][0][5] = Block.parquet.blockID;
        blocksToSpawn[4][0][5] = Block.parquet.blockID;
        blocksToSpawn[5][0][5] = Block.grass.blockID;
        blocksToSpawn[6][0][5] = Block.grass.blockID;

        blocksToSpawn[0][0][6] = Block.grass.blockID;
        blocksToSpawn[1][0][6] = Block.grass.blockID;
        blocksToSpawn[2][0][6] = Block.parquet.blockID;
        blocksToSpawn[3][0][6] = Block.parquet.blockID;
        blocksToSpawn[4][0][6] = Block.parquet.blockID;
        blocksToSpawn[5][0][6] = Block.grass.blockID;
        blocksToSpawn[6][0][6] = Block.grass.blockID;

        // y = 1
        blocksToSpawn[0][1][0] = 0;
        blocksToSpawn[1][1][0] = 0;
        blocksToSpawn[2][1][0] = 0;
        blocksToSpawn[3][1][0] = 0;
        blocksToSpawn[4][1][0] = 0;
        blocksToSpawn[5][1][0] = 0;
        blocksToSpawn[6][1][0] = 0;

        blocksToSpawn[0][1][1] = 0;
        blocksToSpawn[1][1][1] = 0;
        blocksToSpawn[2][1][1] = Block.wood.blockID;
        blocksToSpawn[3][1][1] = Block.wood.blockID;
        blocksToSpawn[4][1][1] = Block.wood.blockID;
        blocksToSpawn[5][1][1] = 0;
        blocksToSpawn[6][1][1] = 0;

        blocksToSpawn[0][1][2] = 0;
        blocksToSpawn[1][1][2] = Block.wood.blockID;
        blocksToSpawn[2][1][2] = Block.planks.blockID;
        blocksToSpawn[3][1][2] = Block.planks.blockID;
        blocksToSpawn[4][1][2] = Block.planks.blockID;
        blocksToSpawn[5][1][2] = Block.wood.blockID;
        blocksToSpawn[6][1][2] = 0;

        blocksToSpawn[0][1][3] = 0;
        blocksToSpawn[1][1][3] = Block.wood.blockID;
        blocksToSpawn[2][1][3] = 0;
        blocksToSpawn[3][1][3] = 0;
        blocksToSpawn[4][1][3] = 0;
        blocksToSpawn[5][1][3] = Block.wood.blockID;
        blocksToSpawn[6][1][3] = 0;

        blocksToSpawn[0][1][4] = 0;
        blocksToSpawn[1][1][4] = Block.wood.blockID;
        blocksToSpawn[2][1][4] = Block.chest.blockID;
        blocksToSpawn[3][1][4] = 0;
        blocksToSpawn[4][1][4] = Block.planks.blockID;
        blocksToSpawn[5][1][4] = Block.wood.blockID;
        blocksToSpawn[6][1][4] = 0;

        blocksToSpawn[0][1][5] = 0;
        blocksToSpawn[1][1][5] = 0;
        blocksToSpawn[2][1][5] = Block.wood.blockID;
        blocksToSpawn[3][1][5] = 0;
        blocksToSpawn[4][1][5] = Block.wood.blockID;
        blocksToSpawn[5][1][5] = 0;
        blocksToSpawn[6][1][5] = 0;

        blocksToSpawn[0][1][6] = 0;
        blocksToSpawn[1][1][6] = 0;
        blocksToSpawn[2][1][6] = 0;
        blocksToSpawn[3][1][6] = 0;
        blocksToSpawn[4][1][6] = 0;
        blocksToSpawn[5][1][6] = 0;
        blocksToSpawn[6][1][6] = 0;

        // y = 2
        blocksToSpawn[0][2][0] = 0;
        blocksToSpawn[1][2][0] = 0;
        blocksToSpawn[2][2][0] = 0;
        blocksToSpawn[3][2][0] = 0;
        blocksToSpawn[4][2][0] = 0;
        blocksToSpawn[5][2][0] = 0;
        blocksToSpawn[6][2][0] = 0;

        blocksToSpawn[0][2][1] = 0;
        blocksToSpawn[1][2][1] = 0;
        blocksToSpawn[2][2][1] = Block.cloth.blockID;
        blocksToSpawn[3][2][1] = Block.cloth.blockID;
        blocksToSpawn[4][2][1] = Block.cloth.blockID;
        blocksToSpawn[5][2][1] = 0;
        blocksToSpawn[6][2][1] = 0;

        blocksToSpawn[0][2][2] = 0;
        blocksToSpawn[1][2][2] = Block.cloth.blockID;
        blocksToSpawn[2][2][2] = Block.fence.blockID;
        blocksToSpawn[3][2][2] = 0;
        blocksToSpawn[4][2][2] = Block.fence.blockID;
        blocksToSpawn[5][2][2] = Block.cloth.blockID;
        blocksToSpawn[6][2][2] = 0;

        blocksToSpawn[0][2][3] = 0;
        blocksToSpawn[1][2][3] = Block.cloth.blockID;
        blocksToSpawn[2][2][3] = 0;
        blocksToSpawn[3][2][3] = 0;
        blocksToSpawn[4][2][3] = 0;
        blocksToSpawn[5][2][3] = Block.cloth.blockID;
        blocksToSpawn[6][2][3] = 0;

        blocksToSpawn[0][2][4] = 0;
        blocksToSpawn[1][2][4] = Block.cloth.blockID;
        blocksToSpawn[2][2][4] = 0;
        blocksToSpawn[3][2][4] = 0;
        blocksToSpawn[4][2][4] = Block.lampKeroseneActive.blockID;
        blocksToSpawn[5][2][4] = Block.cloth.blockID;
        blocksToSpawn[6][2][4] = 0;

        blocksToSpawn[0][2][5] = 0;
        blocksToSpawn[1][2][5] = 0;
        blocksToSpawn[2][2][5] = Block.cloth.blockID;
        blocksToSpawn[3][2][5] = 0;
        blocksToSpawn[4][2][5] = Block.cloth.blockID;
        blocksToSpawn[5][2][5] = 0;
        blocksToSpawn[6][2][5] = 0;

        blocksToSpawn[0][2][6] = 0;
        blocksToSpawn[1][2][6] = 0;
        blocksToSpawn[2][2][6] = 0;
        blocksToSpawn[3][2][6] = 0;
        blocksToSpawn[4][2][6] = 0;
        blocksToSpawn[5][2][6] = 0;
        blocksToSpawn[6][2][6] = 0;

        // y = 3
        blocksToSpawn[0][3][0] = 0;
        blocksToSpawn[1][3][0] = 0;
        blocksToSpawn[2][3][0] = 0;
        blocksToSpawn[3][3][0] = 0;
        blocksToSpawn[4][3][0] = 0;
        blocksToSpawn[5][3][0] = 0;
        blocksToSpawn[6][3][0] = 0;

        blocksToSpawn[0][3][1] = 0;
        blocksToSpawn[1][3][1] = 0;
        blocksToSpawn[2][3][1] = Block.cloth.blockID;
        blocksToSpawn[3][3][1] = Block.cloth.blockID;
        blocksToSpawn[4][3][1] = Block.cloth.blockID;
        blocksToSpawn[5][3][1] = 0;
        blocksToSpawn[6][3][1] = 0;

        blocksToSpawn[0][3][2] = 0;
        blocksToSpawn[1][3][2] = Block.cloth.blockID;
        blocksToSpawn[2][3][2] = Block.fence.blockID;
        blocksToSpawn[3][3][2] = 0;
        blocksToSpawn[4][3][2] = Block.fence.blockID;
        blocksToSpawn[5][3][2] = Block.cloth.blockID;
        blocksToSpawn[6][3][2] = 0;

        blocksToSpawn[0][3][3] = 0;
        blocksToSpawn[1][3][3] = Block.cloth.blockID;
        blocksToSpawn[2][3][3] = 0;
        blocksToSpawn[3][3][3] = 0;
        blocksToSpawn[4][3][3] = 0;
        blocksToSpawn[5][3][3] = Block.cloth.blockID;
        blocksToSpawn[6][3][3] = 0;

        blocksToSpawn[0][3][4] = 0;
        blocksToSpawn[1][3][4] = Block.cloth.blockID;
        blocksToSpawn[2][3][4] = 0;
        blocksToSpawn[3][3][4] = 0;
        blocksToSpawn[4][3][4] = 0;
        blocksToSpawn[5][3][4] = Block.cloth.blockID;
        blocksToSpawn[6][3][4] = 0;

        blocksToSpawn[0][3][5] = 0;
        blocksToSpawn[1][3][5] = 0;
        blocksToSpawn[2][3][5] = Block.cloth.blockID;
        blocksToSpawn[3][3][5] = Block.cloth.blockID;
        blocksToSpawn[4][3][5] = Block.cloth.blockID;
        blocksToSpawn[5][3][5] = 0;
        blocksToSpawn[6][3][5] = 0;

        blocksToSpawn[0][3][6] = 0;
        blocksToSpawn[1][3][6] = 0;
        blocksToSpawn[2][3][6] = 0;
        blocksToSpawn[3][3][6] = 0;
        blocksToSpawn[4][3][6] = 0;
        blocksToSpawn[5][3][6] = 0;
        blocksToSpawn[6][3][6] = 0;

        // y = 4
        blocksToSpawn[0][4][0] = 0;
        blocksToSpawn[1][4][0] = 0;
        blocksToSpawn[2][4][0] = Block.tilesGreen.blockID;
        blocksToSpawn[3][4][0] = Block.tilesGreen.blockID;
        blocksToSpawn[4][4][0] = Block.tilesGreen.blockID;
        blocksToSpawn[5][4][0] = 0;
        blocksToSpawn[6][4][0] = 0;

        blocksToSpawn[0][4][1] = 0;
        blocksToSpawn[1][4][1] = Block.tilesGreen.blockID;
        blocksToSpawn[2][4][1] = Block.tilesGreen.blockID;
        blocksToSpawn[3][4][1] = Block.tilesGreen.blockID;
        blocksToSpawn[4][4][1] = Block.tilesGreen.blockID;
        blocksToSpawn[5][4][1] = Block.tilesGreen.blockID;
        blocksToSpawn[6][4][1] = 0;

        blocksToSpawn[0][4][2] = Block.tilesGreen.blockID;
        blocksToSpawn[1][4][2] = Block.tilesGreen.blockID;
        blocksToSpawn[2][4][2] = Block.wood.blockID;
        blocksToSpawn[3][4][2] = Block.wood.blockID;
        blocksToSpawn[4][4][2] = Block.wood.blockID;
        blocksToSpawn[5][4][2] = Block.tilesGreen.blockID;
        blocksToSpawn[6][4][2] = Block.tilesGreen.blockID;

        blocksToSpawn[0][4][3] = Block.tilesGreen.blockID;
        blocksToSpawn[1][4][3] = Block.tilesGreen.blockID;
        blocksToSpawn[2][4][3] = 0;
        blocksToSpawn[3][4][3] = Block.blockGold.blockID;
        blocksToSpawn[4][4][3] = 0;
        blocksToSpawn[5][4][3] = Block.tilesGreen.blockID;
        blocksToSpawn[6][4][3] = Block.tilesGreen.blockID;

        blocksToSpawn[0][4][4] = Block.tilesGreen.blockID;
        blocksToSpawn[1][4][4] = Block.tilesGreen.blockID;
        blocksToSpawn[2][4][4] = 0;
        blocksToSpawn[3][4][4] = 0;
        blocksToSpawn[4][4][4] = 0;
        blocksToSpawn[5][4][4] = Block.tilesGreen.blockID;
        blocksToSpawn[6][4][4] = Block.tilesGreen.blockID;

        blocksToSpawn[0][4][5] = 0;
        blocksToSpawn[1][4][5] = Block.tilesGreen.blockID;
        blocksToSpawn[2][4][5] = Block.tilesGreen.blockID;
        blocksToSpawn[3][4][5] = Block.tilesGreen.blockID;
        blocksToSpawn[4][4][5] = Block.tilesGreen.blockID;
        blocksToSpawn[5][4][5] = Block.tilesGreen.blockID;
        blocksToSpawn[6][4][5] = 0;

        blocksToSpawn[0][4][6] = 0;
        blocksToSpawn[1][4][6] = 0;
        blocksToSpawn[2][4][6] = Block.tilesGreen.blockID;
        blocksToSpawn[3][4][6] = Block.tilesGreen.blockID;
        blocksToSpawn[4][4][6] = Block.tilesGreen.blockID;
        blocksToSpawn[5][4][6] = 0;
        blocksToSpawn[6][4][6] = 0;

        // y = 5
        blocksToSpawn[0][5][0] = 0;
        blocksToSpawn[1][5][0] = 0;
        blocksToSpawn[2][5][0] = 0;
        blocksToSpawn[3][5][0] = 0;
        blocksToSpawn[4][5][0] = 0;
        blocksToSpawn[5][5][0] = 0;
        blocksToSpawn[6][5][0] = 0;

        blocksToSpawn[0][5][1] = 0;
        blocksToSpawn[1][5][1] = 0;
        blocksToSpawn[2][5][1] = Block.tilesGreen.blockID;
        blocksToSpawn[3][5][1] = Block.tilesGreen.blockID;
        blocksToSpawn[4][5][1] = Block.tilesGreen.blockID;
        blocksToSpawn[5][5][1] = 0;
        blocksToSpawn[6][5][1] = 0;

        blocksToSpawn[0][5][2] = 0;
        blocksToSpawn[1][5][2] = Block.tilesGreen.blockID;
        blocksToSpawn[2][5][2] = 0;
        blocksToSpawn[3][5][2] = 0;
        blocksToSpawn[4][5][2] = 0;
        blocksToSpawn[5][5][2] = Block.tilesGreen.blockID;
        blocksToSpawn[6][5][2] = 0;

        blocksToSpawn[0][5][3] = 0;
        blocksToSpawn[1][5][3] = Block.tilesGreen.blockID;
        blocksToSpawn[2][5][3] = 0;
        blocksToSpawn[3][5][3] = Block.fence.blockID;
        blocksToSpawn[4][5][3] = 0;
        blocksToSpawn[5][5][3] = Block.tilesGreen.blockID;
        blocksToSpawn[6][5][3] = 0;

        blocksToSpawn[0][5][4] = 0;
        blocksToSpawn[1][5][4] = Block.tilesGreen.blockID;
        blocksToSpawn[2][5][4] = 0;
        blocksToSpawn[3][5][4] = 0;
        blocksToSpawn[4][5][4] = 0;
        blocksToSpawn[5][5][4] = Block.tilesGreen.blockID;
        blocksToSpawn[6][5][4] = 0;

        blocksToSpawn[0][5][5] = 0;
        blocksToSpawn[1][5][5] = 0;
        blocksToSpawn[2][5][5] = Block.tilesGreen.blockID;
        blocksToSpawn[3][5][5] = Block.tilesGreen.blockID;
        blocksToSpawn[4][5][5] = Block.tilesGreen.blockID;
        blocksToSpawn[5][5][5] = 0;
        blocksToSpawn[6][5][5] = 0;

        blocksToSpawn[0][5][6] = 0;
        blocksToSpawn[1][5][6] = 0;
        blocksToSpawn[2][5][6] = 0;
        blocksToSpawn[3][5][6] = 0;
        blocksToSpawn[4][5][6] = 0;
        blocksToSpawn[5][5][6] = 0;
        blocksToSpawn[6][5][6] = 0;

        // y = 6
        blocksToSpawn[0][6][0] = 0;
        blocksToSpawn[1][6][0] = 0;
        blocksToSpawn[2][6][0] = 0;
        blocksToSpawn[3][6][0] = 0;
        blocksToSpawn[4][6][0] = 0;
        blocksToSpawn[5][6][0] = 0;
        blocksToSpawn[6][6][0] = 0;

        blocksToSpawn[0][6][1] = 0;
        blocksToSpawn[1][6][1] = 0;
        blocksToSpawn[2][6][1] = 0;
        blocksToSpawn[3][6][1] = 0;
        blocksToSpawn[4][6][1] = 0;
        blocksToSpawn[5][6][1] = 0;
        blocksToSpawn[6][6][1] = 0;

        blocksToSpawn[0][6][2] = 0;
        blocksToSpawn[1][6][2] = 0;
        blocksToSpawn[2][6][2] = Block.tilesGreen.blockID;
        blocksToSpawn[3][6][2] = Block.tilesGreen.blockID;
        blocksToSpawn[4][6][2] = Block.tilesGreen.blockID;
        blocksToSpawn[5][6][2] = 0;
        blocksToSpawn[6][6][2] = 0;

        blocksToSpawn[0][6][3] = 0;
        blocksToSpawn[1][6][3] = 0;
        blocksToSpawn[2][6][3] = Block.tilesGreen.blockID;
        blocksToSpawn[3][6][3] = Block.tilesGreen.blockID;
        blocksToSpawn[4][6][3] = Block.tilesGreen.blockID;
        blocksToSpawn[5][6][3] = 0;
        blocksToSpawn[6][6][3] = 0;

        blocksToSpawn[0][6][4] = 0;
        blocksToSpawn[1][6][4] = 0;
        blocksToSpawn[2][6][4] = Block.tilesGreen.blockID;
        blocksToSpawn[3][6][4] = Block.tilesGreen.blockID;
        blocksToSpawn[4][6][4] = Block.tilesGreen.blockID;
        blocksToSpawn[5][6][4] = 0;
        blocksToSpawn[6][6][4] = 0;

        blocksToSpawn[0][6][5] = 0;
        blocksToSpawn[1][6][5] = 0;
        blocksToSpawn[2][6][5] = 0;
        blocksToSpawn[3][6][5] = 0;
        blocksToSpawn[4][6][5] = 0;
        blocksToSpawn[5][6][5] = 0;
        blocksToSpawn[6][6][5] = 0;

        blocksToSpawn[0][6][6] = 0;
        blocksToSpawn[1][6][6] = 0;
        blocksToSpawn[2][6][6] = 0;
        blocksToSpawn[3][6][6] = 0;
        blocksToSpawn[4][6][6] = 0;
        blocksToSpawn[5][6][6] = 0;
        blocksToSpawn[6][6][6] = 0;

        // y = 7
        blocksToSpawn[0][7][0] = 0;
        blocksToSpawn[1][7][0] = 0;
        blocksToSpawn[2][7][0] = 0;
        blocksToSpawn[3][7][0] = 0;
        blocksToSpawn[4][7][0] = 0;
        blocksToSpawn[5][7][0] = 0;
        blocksToSpawn[6][7][0] = 0;

        blocksToSpawn[0][7][1] = 0;
        blocksToSpawn[1][7][1] = 0;
        blocksToSpawn[2][7][1] = 0;
        blocksToSpawn[3][7][1] = 0;
        blocksToSpawn[4][7][1] = 0;
        blocksToSpawn[5][7][1] = 0;
        blocksToSpawn[6][7][1] = 0;

        blocksToSpawn[0][7][2] = 0;
        blocksToSpawn[1][7][2] = 0;
        blocksToSpawn[2][7][2] = 0;
        blocksToSpawn[3][7][2] = 0;
        blocksToSpawn[4][7][2] = 0;
        blocksToSpawn[5][7][2] = 0;
        blocksToSpawn[6][7][2] = 0;

        blocksToSpawn[0][7][3] = 0;
        blocksToSpawn[1][7][3] = 0;
        blocksToSpawn[2][7][3] = 0;
        blocksToSpawn[3][7][3] = Block.cloth.blockID;
        blocksToSpawn[4][7][3] = 0;
        blocksToSpawn[5][7][3] = 0;
        blocksToSpawn[6][7][3] = 0;

        blocksToSpawn[0][7][4] = 0;
        blocksToSpawn[1][7][4] = 0;
        blocksToSpawn[2][7][4] = 0;
        blocksToSpawn[3][7][4] = 0;
        blocksToSpawn[4][7][4] = 0;
        blocksToSpawn[5][7][4] = 0;
        blocksToSpawn[6][7][4] = 0;

        blocksToSpawn[0][7][5] = 0;
        blocksToSpawn[1][7][5] = 0;
        blocksToSpawn[2][7][5] = 0;
        blocksToSpawn[3][7][5] = 0;
        blocksToSpawn[4][7][5] = 0;
        blocksToSpawn[5][7][5] = 0;
        blocksToSpawn[6][7][5] = 0;

        blocksToSpawn[0][7][6] = 0;
        blocksToSpawn[1][7][6] = 0;
        blocksToSpawn[2][7][6] = 0;
        blocksToSpawn[3][7][6] = 0;
        blocksToSpawn[4][7][6] = 0;
        blocksToSpawn[5][7][6] = 0;
        blocksToSpawn[6][7][6] = 0;

        // y = 8
        blocksToSpawn[0][8][0] = 0;
        blocksToSpawn[1][8][0] = 0;
        blocksToSpawn[2][8][0] = 0;
        blocksToSpawn[3][8][0] = 0;
        blocksToSpawn[4][8][0] = 0;
        blocksToSpawn[5][8][0] = 0;
        blocksToSpawn[6][8][0] = 0;

        blocksToSpawn[0][8][1] = 0;
        blocksToSpawn[1][8][1] = 0;
        blocksToSpawn[2][8][1] = 0;
        blocksToSpawn[3][8][1] = 0;
        blocksToSpawn[4][8][1] = 0;
        blocksToSpawn[5][8][1] = 0;
        blocksToSpawn[6][8][1] = 0;

        blocksToSpawn[0][8][2] = 0;
        blocksToSpawn[1][8][2] = 0;
        blocksToSpawn[2][8][2] = 0;
        blocksToSpawn[3][8][2] = Block.tilesGreen.blockID;
        blocksToSpawn[4][8][2] = 0;
        blocksToSpawn[5][8][2] = 0;
        blocksToSpawn[6][8][2] = 0;

        blocksToSpawn[0][8][3] = 0;
        blocksToSpawn[1][8][3] = 0;
        blocksToSpawn[2][8][3] = Block.tilesGreen.blockID;
        blocksToSpawn[3][8][3] = Block.tilesGreen.blockID;
        blocksToSpawn[4][8][3] = Block.tilesGreen.blockID;
        blocksToSpawn[5][8][3] = 0;
        blocksToSpawn[6][8][3] = 0;

        blocksToSpawn[0][8][4] = 0;
        blocksToSpawn[1][8][4] = 0;
        blocksToSpawn[2][8][4] = 0;
        blocksToSpawn[3][8][4] = Block.tilesGreen.blockID;
        blocksToSpawn[4][8][4] = 0;
        blocksToSpawn[5][8][4] = 0;
        blocksToSpawn[6][8][4] = 0;

        blocksToSpawn[0][8][5] = 0;
        blocksToSpawn[1][8][5] = 0;
        blocksToSpawn[2][8][5] = 0;
        blocksToSpawn[3][8][5] = 0;
        blocksToSpawn[4][8][5] = 0;
        blocksToSpawn[5][8][5] = 0;
        blocksToSpawn[6][8][5] = 0;

        blocksToSpawn[0][8][6] = 0;
        blocksToSpawn[1][8][6] = 0;
        blocksToSpawn[2][8][6] = 0;
        blocksToSpawn[3][8][6] = 0;
        blocksToSpawn[4][8][6] = 0;
        blocksToSpawn[5][8][6] = 0;
        blocksToSpawn[6][8][6] = 0;

        // y = 9
        blocksToSpawn[0][9][0] = 0;
        blocksToSpawn[1][9][0] = 0;
        blocksToSpawn[2][9][0] = 0;
        blocksToSpawn[3][9][0] = 0;
        blocksToSpawn[4][9][0] = 0;
        blocksToSpawn[5][9][0] = 0;
        blocksToSpawn[6][9][0] = 0;

        blocksToSpawn[0][9][1] = 0;
        blocksToSpawn[1][9][1] = 0;
        blocksToSpawn[2][9][1] = 0;
        blocksToSpawn[3][9][1] = 0;
        blocksToSpawn[4][9][1] = 0;
        blocksToSpawn[5][9][1] = 0;
        blocksToSpawn[6][9][1] = 0;

        blocksToSpawn[0][9][2] = 0;
        blocksToSpawn[1][9][2] = 0;
        blocksToSpawn[2][9][2] = 0;
        blocksToSpawn[3][9][2] = 0;
        blocksToSpawn[4][9][2] = 0;
        blocksToSpawn[5][9][2] = 0;
        blocksToSpawn[6][9][2] = 0;

        blocksToSpawn[0][9][3] = 0;
        blocksToSpawn[1][9][3] = 0;
        blocksToSpawn[2][9][3] = 0;
        blocksToSpawn[3][9][3] = Block.wood.blockID;
        blocksToSpawn[4][9][3] = 0;
        blocksToSpawn[5][9][3] = 0;
        blocksToSpawn[6][9][3] = 0;

        blocksToSpawn[0][9][4] = 0;
        blocksToSpawn[1][9][4] = 0;
        blocksToSpawn[2][9][4] = 0;
        blocksToSpawn[3][9][4] = 0;
        blocksToSpawn[4][9][4] = 0;
        blocksToSpawn[5][9][4] = 0;
        blocksToSpawn[6][9][4] = 0;

        blocksToSpawn[0][9][5] = 0;
        blocksToSpawn[1][9][5] = 0;
        blocksToSpawn[2][9][5] = 0;
        blocksToSpawn[3][9][5] = 0;
        blocksToSpawn[4][9][5] = 0;
        blocksToSpawn[5][9][5] = 0;
        blocksToSpawn[6][9][5] = 0;

        blocksToSpawn[0][9][6] = 0;
        blocksToSpawn[1][9][6] = 0;
        blocksToSpawn[2][9][6] = 0;
        blocksToSpawn[3][9][6] = 0;
        blocksToSpawn[4][9][6] = 0;
        blocksToSpawn[5][9][6] = 0;
        blocksToSpawn[6][9][6] = 0;

        // y = 10
        blocksToSpawn[0][10][0] = 0;
        blocksToSpawn[1][10][0] = 0;
        blocksToSpawn[2][10][0] = 0;
        blocksToSpawn[3][10][0] = 0;
        blocksToSpawn[4][10][0] = 0;
        blocksToSpawn[5][10][0] = 0;
        blocksToSpawn[6][10][0] = 0;

        blocksToSpawn[0][10][1] = 0;
        blocksToSpawn[1][10][1] = 0;
        blocksToSpawn[2][10][1] = 0;
        blocksToSpawn[3][10][1] = 0;
        blocksToSpawn[4][10][1] = 0;
        blocksToSpawn[5][10][1] = 0;
        blocksToSpawn[6][10][1] = 0;

        blocksToSpawn[0][10][2] = 0;
        blocksToSpawn[1][10][2] = 0;
        blocksToSpawn[2][10][2] = 0;
        blocksToSpawn[3][10][2] = 0;
        blocksToSpawn[4][10][2] = 0;
        blocksToSpawn[5][10][2] = 0;
        blocksToSpawn[6][10][2] = 0;

        blocksToSpawn[0][10][3] = 0;
        blocksToSpawn[1][10][3] = 0;
        blocksToSpawn[2][10][3] = Block.wood.blockID;
        blocksToSpawn[3][10][3] = Block.wood.blockID;
        blocksToSpawn[4][10][3] = Block.wood.blockID;
        blocksToSpawn[5][10][3] = 0;
        blocksToSpawn[6][10][3] = 0;

        blocksToSpawn[0][10][4] = 0;
        blocksToSpawn[1][10][4] = 0;
        blocksToSpawn[2][10][4] = 0;
        blocksToSpawn[3][10][4] = 0;
        blocksToSpawn[4][10][4] = 0;
        blocksToSpawn[5][10][4] = 0;
        blocksToSpawn[6][10][4] = 0;

        blocksToSpawn[0][10][5] = 0;
        blocksToSpawn[1][10][5] = 0;
        blocksToSpawn[2][10][5] = 0;
        blocksToSpawn[3][10][5] = 0;
        blocksToSpawn[4][10][5] = 0;
        blocksToSpawn[5][10][5] = 0;
        blocksToSpawn[6][10][5] = 0;

        blocksToSpawn[0][10][6] = 0;
        blocksToSpawn[1][10][6] = 0;
        blocksToSpawn[2][10][6] = 0;
        blocksToSpawn[3][10][6] = 0;
        blocksToSpawn[4][10][6] = 0;
        blocksToSpawn[5][10][6] = 0;
        blocksToSpawn[6][10][6] = 0;

        // y = 11
        blocksToSpawn[0][11][0] = 0;
        blocksToSpawn[1][11][0] = 0;
        blocksToSpawn[2][11][0] = 0;
        blocksToSpawn[3][11][0] = 0;
        blocksToSpawn[4][11][0] = 0;
        blocksToSpawn[5][11][0] = 0;
        blocksToSpawn[6][11][0] = 0;

        blocksToSpawn[0][11][1] = 0;
        blocksToSpawn[1][11][1] = 0;
        blocksToSpawn[2][11][1] = 0;
        blocksToSpawn[3][11][1] = 0;
        blocksToSpawn[4][11][1] = 0;
        blocksToSpawn[5][11][1] = 0;
        blocksToSpawn[6][11][1] = 0;

        blocksToSpawn[0][11][2] = 0;
        blocksToSpawn[1][11][2] = 0;
        blocksToSpawn[2][11][2] = 0;
        blocksToSpawn[3][11][2] = 0;
        blocksToSpawn[4][11][2] = 0;
        blocksToSpawn[5][11][2] = 0;
        blocksToSpawn[6][11][2] = 0;

        blocksToSpawn[0][11][3] = 0;
        blocksToSpawn[1][11][3] = 0;
        blocksToSpawn[2][11][3] = 0;
        blocksToSpawn[3][11][3] = Block.wood.blockID;
        blocksToSpawn[4][11][3] = 0;
        blocksToSpawn[5][11][3] = 0;
        blocksToSpawn[6][11][3] = 0;

        blocksToSpawn[0][11][4] = 0;
        blocksToSpawn[1][11][4] = 0;
        blocksToSpawn[2][11][4] = 0;
        blocksToSpawn[3][11][4] = 0;
        blocksToSpawn[4][11][4] = 0;
        blocksToSpawn[5][11][4] = 0;
        blocksToSpawn[6][11][4] = 0;

        blocksToSpawn[0][11][5] = 0;
        blocksToSpawn[1][11][5] = 0;
        blocksToSpawn[2][11][5] = 0;
        blocksToSpawn[3][11][5] = 0;
        blocksToSpawn[4][11][5] = 0;
        blocksToSpawn[5][11][5] = 0;
        blocksToSpawn[6][11][5] = 0;

        blocksToSpawn[0][11][6] = 0;
        blocksToSpawn[1][11][6] = 0;
        blocksToSpawn[2][11][6] = 0;
        blocksToSpawn[3][11][6] = 0;
        blocksToSpawn[4][11][6] = 0;
        blocksToSpawn[5][11][6] = 0;
        blocksToSpawn[6][11][6] = 0;
    }
}
