package net.minecraft.src;

import java.util.Random;

public class BlockDirt extends Block {

	protected BlockDirt(int var1, int var2) {
		super(var1, var2, Material.ground);
	}

	public int idDropped(int var1, Random random) {
		return random.nextInt(20) == 0 ? Item.onion.shiftedIndex :
				random.nextInt(20) == 0 ? Item.carrot.shiftedIndex :
				random.nextInt(20) == 0 ? Item.potato.shiftedIndex : Block.dirt.blockID;
	}
}
