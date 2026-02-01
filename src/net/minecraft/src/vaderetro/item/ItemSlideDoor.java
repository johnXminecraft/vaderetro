package net.minecraft.src.vaderetro.item;

import net.minecraft.src.*;

public class ItemSlideDoor extends Item {

	public ItemSlideDoor(int id) {
		super(id);
		this.maxStackSize = 1;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
		if (side != 1) {
			return false;
		}
		++y;
		Block doorBlock = Block.slideDoor;
		if (!doorBlock.canPlaceBlockAt(world, x, y, z)) {
			return false;
		}
		int rotation = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
		int offsetX = 0;
		int offsetZ = 0;
		if (rotation == 0) {
			offsetZ = 1;
		}
		if (rotation == 1) {
			offsetX = -1;
		}
		if (rotation == 2) {
			offsetZ = -1;
		}
		if (rotation == 3) {
			offsetX = 1;
		}
		int leftBlocks = (world.isBlockNormalCube(x - offsetX, y, z - offsetZ) ? 1 : 0) + (world.isBlockNormalCube(x - offsetX, y + 1, z - offsetZ) ? 1 : 0);
		int rightBlocks = (world.isBlockNormalCube(x + offsetX, y, z + offsetZ) ? 1 : 0) + (world.isBlockNormalCube(x + offsetX, y + 1, z + offsetZ) ? 1 : 0);
		boolean hasLeftDoor = world.getBlockId(x - offsetX, y, z - offsetZ) == doorBlock.blockID || world.getBlockId(x - offsetX, y + 1, z - offsetZ) == doorBlock.blockID;
		boolean hasRightDoor = world.getBlockId(x + offsetX, y, z + offsetZ) == doorBlock.blockID || world.getBlockId(x + offsetX, y + 1, z + offsetZ) == doorBlock.blockID;
		boolean useLeftHinge = false;
		if (hasLeftDoor && !hasRightDoor) {
			useLeftHinge = true;
		} else if (rightBlocks > leftBlocks) {
			useLeftHinge = true;
		}
		if (useLeftHinge) {
			rotation = (rotation - 1) & 3;
			rotation += 4;
		}
		world.editingBlocks = true;
		world.setBlockAndMetadataWithNotify(x, y, z, doorBlock.blockID, rotation);
		world.setBlockAndMetadataWithNotify(x, y + 1, z, doorBlock.blockID, rotation + 8);
		world.editingBlocks = false;
		world.notifyBlocksOfNeighborChange(x, y, z, doorBlock.blockID);
		world.notifyBlocksOfNeighborChange(x, y + 1, z, doorBlock.blockID);
		--stack.stackSize;
		return true;
	}
}
