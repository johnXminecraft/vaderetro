package net.minecraft.src.vaderetro.item;

import net.minecraft.src.*;

public class ItemNuclearBombPlacer extends Item {
	private final int placeBlockId;

	public ItemNuclearBombPlacer(int id) {
		super(id);
		this.placeBlockId = Block.nuclearBomb.blockID;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
		if(world.getBlockId(x, y, z) == Block.snow.blockID) {
			side = 0;
		} else {
			if(side == 0) --y;
			if(side == 1) ++y;
			if(side == 2) --z;
			if(side == 3) ++z;
			if(side == 4) --x;
			if(side == 5) ++x;
		}

		if(stack.stackSize == 0) {
			return false;
		} else if(y == 127 && Block.blocksList[this.placeBlockId].blockMaterial.isSolid()) {
			return false;
		} else if(world.canBlockBePlacedAt(this.placeBlockId, x, y, z, false, side)) {
			Block block = Block.blocksList[this.placeBlockId];
			if(world.setBlockAndMetadataWithNotify(x, y, z, this.placeBlockId, 0)) {
				block.onBlockPlaced(world, x, y, z, side);
				block.onBlockPlacedBy(world, x, y, z, player);
				world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.func_1145_d(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}
			return true;
		} else {
			return false;
		}
	}

	public Item setItemName(String name) {
		return super.setItemName(name);
	}

	public String getItemName() {
		return "Nuclear Bomb";
	}

	public String getItemNameIS(ItemStack stack) {
		return "Nuclear Bomb";
	}

	public String getStatName() {
		return "Nuclear Bomb";
	}
}
