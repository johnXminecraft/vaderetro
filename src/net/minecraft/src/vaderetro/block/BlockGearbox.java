package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;

public class BlockGearbox extends Block {
    public BlockGearbox(int id, int textureIndex) {
        super(id, textureIndex, Material.wood);
        this.setHardness(2.0F);
        this.blockResistance = 5.0F;
        this.stepSound = soundWoodFootstep;
        this.setBlockName("gearbox");
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving placer) {
        int yawIdx = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int meta = yawIdx;
        world.setBlockMetadataWithNotify(x, y, z, meta);
    }

    public void onBlockPlaced(World world, int x, int y, int z, int side) {
        int meta = 0;
        if (side == 2) meta = 0;
        if (side == 5) meta = 1;
        if (side == 3) meta = 2;
        if (side == 4) meta = 3;
        world.setBlockMetadataWithNotify(x, y, z, meta);
    }

    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        int frontSide = meta == 0 ? 2 : meta == 1 ? 5 : meta == 2 ? 3 : 4;
        int inputSide = getOppositeSide(frontSide);

        if (side == 0) {
            return 235;
        }
        if (side == 1) {
            return 166;
        }
        if (side == inputSide) {
            return 235;
        }
        return 219;
    }

    private int getOppositeSide(int side) {
        switch (side) {
            case 2: return 3;
            case 3: return 2;
            case 4: return 5;
            case 5: return 4;
            default: return side;
        }
    }

    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        int nx = x + (side == 4 ? 1 : side == 5 ? -1 : 0);
        int ny = y + (side == 1 ? -1 : side == 0 ? 1 : 0);
        int nz = z + (side == 2 ? 1 : side == 3 ? -1 : 0);
        int nid = world.getBlockId(nx, ny, nz);

        if (nid == Block.axleRod.blockID) {
            return true;
        }

        return world.isBlockNormalCube(nx, ny, nz);
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if (world.isBlockNormalCube(x, y - 1, z)) {
            return true;
        }

        int[][] neighbors = new int[][] {
            {-1,0,0}, {1,0,0},
            {0,-1,0}, {0,1,0},
            {0,0,-1}, {0,0,1}
        };

        for (int i = 0; i < neighbors.length; i++) {
            int nx = x + neighbors[i][0];
            int ny = y + neighbors[i][1];
            int nz = z + neighbors[i][2];
            if (world.getBlockId(nx, ny, nz) == Block.axleRod.blockID) {
                return true;
            }
        }
        return false;
    }
}