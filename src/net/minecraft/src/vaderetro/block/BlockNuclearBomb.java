package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityNuclearBomb;

import java.util.Random;

public class BlockNuclearBomb extends BlockContainer {
	private static final int INVENTORY_ICON_INDEX = 240;
	public BlockNuclearBomb(int id, int textureIndex) {
		super(id, textureIndex, Material.rock);
		this.setHardness(50.0F);
		this.blockResistance = 2000.0F;
		this.stepSound = soundStoneFootstep;
		this.setBlockName("nuclearBomb");
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
		return new TileEntityNuclearBomb();
	}

	public int idDropped(int meta, Random rand) {
		return 0; 
	}

	public int quantityDropped(Random rand) {
		return 0;
	}

	public int getBlockTextureFromSide(int side) {
		return INVENTORY_ICON_INDEX;
	}

	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		return false;
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity) {
		int facing = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if(facing == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2);
        }
        if(facing == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 1);
        }
        if(facing == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3);
        }
        if(facing == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 0);
        }
	}
}
