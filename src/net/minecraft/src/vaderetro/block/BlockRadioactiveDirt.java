package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import java.util.Random;

public class BlockRadioactiveDirt extends Block {
	public BlockRadioactiveDirt(int id) {
		super(id, 203, Material.ground);
		this.setHardness(0.5F);
		this.setStepSound(Block.soundGravelFootstep);
		this.setBlockName("radioactiveDirt");
	}

	public int getBlockTextureFromSide(int side) {
		if(side == 1 || side == 0) {
			return 220;
		}
		return 221;
	}

	public int idDropped(int var1, Random random) {
		return Block.dirt.blockID;
	}
}
