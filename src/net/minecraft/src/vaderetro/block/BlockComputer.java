package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityComputer;

public class BlockComputer extends BlockContainer {

    private static final int TEXTURE_FACE_IDLE = 281;
    private static final int TEXTURE_FACE_BLINK = 265;  
    private static final int TEXTURE_SIDES = 282;
    private static final long BLINK_PERIOD_MS = 1000;

    public BlockComputer(int id, int blockIndexInTexture) {
        super(id, blockIndexInTexture, Material.iron);
    }

    @Override
    public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        if (side == meta) {
            long t = System.currentTimeMillis() % BLINK_PERIOD_MS;
            boolean blink = t < (BLINK_PERIOD_MS / 2);
            return blink ? TEXTURE_FACE_IDLE : TEXTURE_FACE_BLINK;
        }
        return TEXTURE_SIDES;
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        return side == 3 ? TEXTURE_FACE_IDLE : TEXTURE_SIDES;
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if (!world.multiplayerWorld) {
            TileEntityComputer te = (TileEntityComputer) world.getBlockTileEntity(x, y, z);
            player.displayGUIComputer(te);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving placer) {
        int facing = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (facing == 0) world.setBlockMetadataWithNotify(x, y, z, 2);
        if (facing == 1) world.setBlockMetadataWithNotify(x, y, z, 5);
        if (facing == 2) world.setBlockMetadataWithNotify(x, y, z, 3);
        if (facing == 3) world.setBlockMetadataWithNotify(x, y, z, 4);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityComputer();
    }
}
