package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityWaterWheel;

public class BlockWaterWheel extends BlockContainer {
    public BlockWaterWheel(int id, int textureIndex) {
        super(id, textureIndex, Material.wood);
        this.setHardness(2.0F);
        this.blockResistance = 5.0F * 3.0F;
        this.stepSound = soundWoodFootstep;
        this.setBlockName("waterWheel");
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return -1;
    }

    protected TileEntity getBlockEntity() {
        return new TileEntityWaterWheel();
    }

    private boolean isAttachable(World world, int x, int y, int z) {
        int id = world.getBlockId(x, y, z);
        if (id == 0) return false;
        Material m = world.getBlockMaterial(x, y, z);
        return !m.getIsLiquid();
    }

    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        return (side == 1 && isAttachable(world, x, y - 1, z)) ||
               (side == 2 && isAttachable(world, x, y, z + 1)) ||
               (side == 3 && isAttachable(world, x, y, z - 1)) ||
               (side == 4 && isAttachable(world, x + 1, y, z)) ||
               (side == 5 && isAttachable(world, x - 1, y, z)) ||
               (side == 0 && isAttachable(world, x, y + 1, z));
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return isAttachable(world, x - 1, y, z) ||
               isAttachable(world, x + 1, y, z) ||
               isAttachable(world, x, y, z - 1) ||
               isAttachable(world, x, y, z + 1) ||
               isAttachable(world, x, y - 1, z) ||
               isAttachable(world, x, y + 1, z);
    }

    public void onBlockPlaced(World world, int x, int y, int z, int side) {
        int meta = 0;
        if (side == 5 && isAttachable(world, x - 1, y, z)) {
            meta = 1;
        } else if (side == 4 && isAttachable(world, x + 1, y, z)) {
            meta = 2;
        } else if (side == 3 && isAttachable(world, x, y, z - 1)) {
            meta = 3;
        } else if (side == 2 && isAttachable(world, x, y, z + 1)) {
            meta = 4;
        } else if (side == 1 && isAttachable(world, x, y - 1, z)) {
            meta = 5;
        } else if (side == 0 && isAttachable(world, x, y + 1, z)) {
            meta = 6;
        }
        world.setBlockMetadataWithNotify(x, y, z, meta);
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0) {
            int dir = MathHelper.floor_double((double)(entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            if (dir == 0) world.setBlockMetadataWithNotify(x, y, z, 3);
            if (dir == 1) world.setBlockMetadataWithNotify(x, y, z, 2);
            if (dir == 2) world.setBlockMetadataWithNotify(x, y, z, 4);
            if (dir == 3) world.setBlockMetadataWithNotify(x, y, z, 1);
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborId) {
        int meta = world.getBlockMetadata(x, y, z) & 7;
        boolean attached = false;
        if (meta == 1 && world.isBlockNormalCube(x - 1, y, z)) attached = true;
        if (meta == 2 && world.isBlockNormalCube(x + 1, y, z)) attached = true;
        if (meta == 3 && world.isBlockNormalCube(x, y, z - 1)) attached = true;
        if (meta == 4 && world.isBlockNormalCube(x, y, z + 1)) attached = true;
        if (meta == 5 && world.isBlockNormalCube(x, y - 1, z)) attached = true;
        if (meta == 6 && world.isBlockNormalCube(x, y + 1, z)) attached = true;

        if (!attached) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
            world.setBlockWithNotify(x, y, z, 0);
        }
    }
}
