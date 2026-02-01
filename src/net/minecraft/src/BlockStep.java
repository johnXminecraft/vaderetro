package net.minecraft.src;

import java.util.Random;

public class BlockStep extends Block {
	public static final String[] field_22037_a = new String[]{"stone", "sand", "wood", "cobble"};
	private boolean blockType;
	private boolean isReversed;

	public BlockStep(int id, boolean isFullBlock, boolean isReversed) {
		super(id, 6, Material.rock);
		this.blockType = isFullBlock;
		this.isReversed = isReversed;
		if(!this.blockType) {
            if(this.isReversed) {
                this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
        }

		this.setLightOpacity(0);
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return var2 == 0 ? (var1 <= 1 ? 6 : 5) : (var2 == 1 ? (var1 == 0 ? 208 : (var1 == 1 ? 176 : 192)) : (var2 == 2 ? 4 : (var2 == 3 ? 16 : 6)));
	}

	public int getBlockTextureFromSide(int var1) {
		return this.getBlockTextureFromSideAndMetadata(var1, 0);
	}

	public boolean isOpaqueCube() {
		return this.blockType;
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		if(this != Block.stairSingle) {
			super.onBlockAdded(var1, var2, var3, var4);
		}

		int var5 = var1.getBlockId(var2, var3 - 1, var4);
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		int var7 = var1.getBlockMetadata(var2, var3 - 1, var4);
		if(var6 == var7) {
			if(var5 == stairSingle.blockID) {
				var1.setBlockWithNotify(var2, var3, var4, 0);
				var1.setBlockAndMetadataWithNotify(var2, var3 - 1, var4, Block.stairDouble.blockID, var6);
			}

		}
	}

	public int idDropped(int var1, Random var2) {
		return Block.stairSingle.blockID;
	}

	public int quantityDropped(Random var1) {
		return this.blockType ? 2 : 1;
	}

	protected int damageDropped(int var1) {
		return var1;
	}

	public boolean renderAsNormalBlock() {
		return this.blockType;
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		if(this != Block.stairSingle) {
			super.shouldSideBeRendered(var1, var2, var3, var4, var5);
		}

		return var5 == 1 ? true : (!super.shouldSideBeRendered(var1, var2, var3, var4, var5) ? false : (var5 == 0 ? true : var1.getBlockId(var2, var3, var4) != this.blockID));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		if(this.isReversed) {
			return AxisAlignedBB.getBoundingBox(x, y + 0.5F, z, x + 1, y + 1, z + 1);
		}
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.5F, z + 1);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
		return getCollisionBoundingBoxFromPool(w, x, y, z);
	}
}
