package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import java.util.Random;

public class BlockRadioactiveWood extends Block {
	public BlockRadioactiveWood(int id) {
		super(id, 171, Material.wood);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setStepSound(Block.soundWoodFootstep);
		this.setBlockName("radioactiveWood");
	}

	public int getBlockTextureFromSide(int side) {
		if(side == 1 || side == 0) {
			return 189;
		}
		return 188;
	}

	public int quantityDropped(Random var1) {
		return 1;
	}

	public int idDropped(int var1, Random var2) {
		return Block.wood.blockID;
	}
}
