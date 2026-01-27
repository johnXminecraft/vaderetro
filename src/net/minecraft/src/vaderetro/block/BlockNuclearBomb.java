package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityNuclearBomb;

import java.util.Random;

public class BlockNuclearBomb extends BlockContainer {
	private static final int INVENTORY_ICON_INDEX = 240;
	public BlockNuclearBomb(int id, int textureIndex) {
		super(id, textureIndex, Material.rock);
		this.setHardness(5.0F);
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
		if (world.multiplayerWorld) {
			return true;
		}

		net.minecraft.src.vaderetro.entity.EntityObjModel mush = new net.minecraft.src.vaderetro.entity.EntityObjModel(
			world,
			(double)x + 0.5D,
			(double)y + 1.0D,
			(double)z + 0.5D,
			"/models/test.obj",
			"/textures/test.png"
		);
		int growTicks = 20 * 5; 
		int holdTicks = 20 * 5; 
		mush.enableGrowAndHoldAnimation(0.1f, 1.0f, growTicks, holdTicks);
		mush.setRotationSpeed(0.0f);
		world.entityJoinedWorld(mush);
		return true;
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
