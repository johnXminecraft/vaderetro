package net.minecraft.src;

public class ItemBlock extends Item {
	private int blockID;

	public ItemBlock(int var1) {
		super(var1);
		this.blockID = var1 + 256;
		this.setIconIndex(Block.blocksList[var1 + 256].getBlockTextureFromSide(2));
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side) {
		if(world.getBlockId(x, y, z) == Block.snow.blockID) {
			side = 0;
		} else {
			if(side == 0) {
				--y;
			}

			if(side == 1) {
				++y;
			}

			if(side == 2) {
				--z;
			}

			if(side == 3) {
				++z;
			}

			if(side == 4) {
				--x;
			}

			if(side == 5) {
				++x;
			}
		}

		if(itemStack.stackSize == 0) {
			return false;
		} else if(y == 255 && Block.blocksList[this.blockID].blockMaterial.isSolid()) {
			return false;
		} else if(world.canBlockBePlacedAt(this.blockID, x, y, z, false, side)) {
			Block block = Block.blocksList[this.blockID];
			if(world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, this.getPlacedBlockMetadata(itemStack.getItemDamage()))) {
				if(this.blockID == Block.stairSingle.blockID || this.blockID == Block.slabReversed.blockID) {
					if(side == 0 || player.isSneaking()) {
						this.blockID = Block.slabReversed.blockID;
					} else {
						this.blockID = Block.stairSingle.blockID;
					}
				}
				Block.blocksList[this.blockID].onBlockPlaced(world, x, y, z, side);
				Block.blocksList[this.blockID].onBlockPlacedBy(world, x, y, z, player);
				world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.func_1145_d(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				--itemStack.stackSize;
			}
			return true;
		} else {
			return false;
		}
	}

	public String getItemNameIS(ItemStack var1) {
		return Block.blocksList[this.blockID].getBlockName();
	}

	public String getItemName() {
		return Block.blocksList[this.blockID].getBlockName();
	}
}
