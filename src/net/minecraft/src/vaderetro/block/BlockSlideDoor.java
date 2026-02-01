package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntitySlideDoor;

import java.util.Random;

public class BlockSlideDoor extends BlockContainer {

    private static final float THICKNESS = 3.0F / 16.0F;
    private static final float SLIDE = 1.0F - THICKNESS;

    public BlockSlideDoor(int id, Material material) {
        super(id, material);
        this.blockIndexInTexture = 280;
        float half = 0.5F;
        this.setBlockBounds(0.5F - half, 0.0F, 0.5F - half, 0.5F + half, 1.0F, 0.5F + half);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntitySlideDoor();
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        if (side != 0 && side != 1) {
            int state = getState(meta);
            if ((state == 0 || state == 2) ^ side <= 3) {
                return this.blockIndexInTexture;
            }
            int v = state / 2 + (side & 1 ^ state);
            int tex = this.blockIndexInTexture - (meta & 8) * 2;
            return Math.abs(tex);
        }
        return this.blockIndexInTexture;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 18;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        int facing = meta & 3;
        float progress;

        TileEntity te = blockAccess.getBlockTileEntity(x, y, z);
        if (te instanceof TileEntitySlideDoor) {
            progress = ((TileEntitySlideDoor) te).slideProgress;
        } else {
            progress = (meta & 4) != 0 ? 1.0F : 0.0F;
        }

        this.setSlideDoorBounds(facing, progress);
    }

    private void setSlideDoorBounds(int facing, float progress) {
        float minX, maxX, minZ, maxZ;
        switch (facing) {
            case 0:
                minX = progress * SLIDE;
                maxX = 1.0F;
                minZ = 0.0F;
                maxZ = THICKNESS;
                break;
            case 1:
                minX = SLIDE;
                maxX = 1.0F;
                minZ = progress * SLIDE;
                maxZ = 1.0F;
                break;
            case 2:
                minX = 0.0F;
                maxX = 1.0F - progress * SLIDE;
                minZ = SLIDE;
                maxZ = 1.0F;
                break;
            default:
                minX = 0.0F;
                maxX = THICKNESS;
                minZ = progress * SLIDE;
                maxZ = 1.0F;
                break;
        }
        this.setBlockBounds(minX, 0.0F, minZ, maxX, 1.0F, maxZ);
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        this.blockActivated(world, x, y, z, player);
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        int meta = world.getBlockMetadata(x, y, z);
        if ((meta & 8) != 0) {
            if (world.getBlockId(x, y - 1, z) == this.blockID) {
                this.blockActivated(world, x, y - 1, z, player);
            }
            return true;
        }
        if (world.getBlockId(x, y + 1, z) == this.blockID) {
            world.setBlockMetadataWithNotify(x, y + 1, z, (meta ^ 4) + 8);
        }
        world.setBlockMetadataWithNotify(x, y, z, meta ^ 4);
        world.markBlocksDirty(x, y - 1, z, x, y, z);
        world.func_28107_a(player, 1003, x, y, z, 0);
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborId) {
        int meta = world.getBlockMetadata(x, y, z);
        if ((meta & 8) != 0) {
            if (world.getBlockId(x, y - 1, z) != this.blockID) {
                world.setBlockWithNotify(x, y, z, 0);
            }
            return;
        }
        if (world.getBlockId(x, y + 1, z) != this.blockID) {
            world.setBlockWithNotify(x, y, z, 0);
            if (!world.multiplayerWorld) {
                this.dropBlockAsItem(world, x, y, z, meta);
            }
            return;
        }
        if (!world.isBlockNormalCube(x, y - 1, z)) {
            world.setBlockWithNotify(x, y, z, 0);
            if (world.getBlockId(x, y + 1, z) == this.blockID) {
                world.setBlockWithNotify(x, y + 1, z, 0);
            }
            if (!world.multiplayerWorld) {
                this.dropBlockAsItem(world, x, y, z, meta);
            }
        }
    }

    @Override
    public int idDropped(int meta, Random random) {
        return (meta & 8) != 0 ? 0 : Item.doorSlideWood.shiftedIndex;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3D start, Vec3D end) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, start, end);
    }

    public static int getState(int meta) {
        return (meta & 4) == 0 ? (meta - 1) & 3 : meta & 3;
    }

    public static boolean isOpen(int meta) {
        return (meta & 4) != 0;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return y < 127 && world.isBlockNormalCube(x, y - 1, z)
                && super.canPlaceBlockAt(world, x, y, z)
                && super.canPlaceBlockAt(world, x, y + 1, z);
    }

    @Override
    public int getMobilityFlag() {
        return 1;
    }
}
