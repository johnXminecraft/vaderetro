package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityCardReader;

public class BlockCardReader extends BlockContainer {

    private static final int TEXTURE_FACE = 266;
    private static final int TEXTURE_SIDES = 282;

    public BlockCardReader(int id, int blockIndexInTexture) {
        super(id, blockIndexInTexture, Material.iron);
    }

    @Override
    public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        return (side == meta) ? TEXTURE_FACE : TEXTURE_SIDES;
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        return side == 3 ? TEXTURE_FACE : TEXTURE_SIDES;
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if (world.multiplayerWorld) return true;
        TileEntityCardReader te = (TileEntityCardReader) world.getBlockTileEntity(x, y, z);
        if (te == null) return true;

        if (te.hasCard()) {
            ItemStack keycard = new ItemStack(TileEntityCardReader.KEYCARD_ITEM_ID, 1, 0);
            if (player.inventory.addItemStackToInventory(keycard)) {
                te.setHasCard(false);
            } else {
                player.dropPlayerItem(keycard);
                te.setHasCard(false);
            }
        } else {
            ItemStack held = player.inventory.getCurrentItem();
            if (held != null && held.itemID == TileEntityCardReader.KEYCARD_ITEM_ID) {
                held.stackSize--;
                if (held.stackSize <= 0) player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                te.setHasCard(true);
            }
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
        return new TileEntityCardReader();
    }
}
