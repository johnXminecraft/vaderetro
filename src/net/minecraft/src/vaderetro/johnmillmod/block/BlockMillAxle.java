package net.minecraft.src.vaderetro.johnmillmod.block;

import net.minecraft.src.*;

public class BlockMillAxle extends Block {
	public BlockMillAxle(int id, int textureIndex) {
		super(id, textureIndex, Material.wood);
		this.setHardness(2.0F);
		this.blockResistance = 5.0F;
		this.stepSound = soundWoodFootstep;
		this.setBlockName("millAxle");
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving placer) {
		int yawIdx = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int meta = 2;
		if (yawIdx == 0) meta = 2; 
		if (yawIdx == 1) meta = 5; 
		if (yawIdx == 2) meta = 3; 
		if (yawIdx == 3) meta = 4; 
		world.setBlockMetadataWithNotify(x, y, z, meta);
	}

	public void onBlockPlaced(World world, int x, int y, int z, int side) {
		int meta = side; 
		world.setBlockMetadataWithNotify(x, y, z, meta);
	}

	public int getOpposite(int side) {
		switch (side) {
			case 0: return 1;
			case 1: return 0;
			case 2: return 3;
			case 3: return 2;
			case 4: return 5;
			case 5: return 4;
			default: return side;
		}
	}

	public int getBlockTextureFromSideAndMetadata(int side, int meta) {

		if (side == meta) {
			return 1; 
		}
		if (side == getOpposite(meta)) {
			return 2; 
		}
		return 4;
	}
}
