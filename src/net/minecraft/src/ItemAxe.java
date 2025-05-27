package net.minecraft.src;

public class ItemAxe extends ItemTool {

	private static Block[] blocksEffectiveAgainst = new Block[]{
			Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.pumpkin, Block.workbench, Block.doorWood,
			Block.trapdoor, Block.parquet, Block.musicBlock, Block.jukebox, Block.stairCompactPlanks, Block.signPost,
			Block.signWall, Block.ladder, Block.pressurePlatePlanks, Block.cactus, Block.fence, Block.pumpkinLantern,
			Block.lockedChest, Block.crtTvSetIdle, Block.crtTvSetActive
	};

	protected ItemAxe(int var1, EnumToolMaterial var2) {
		super(var1, 3, var2, blocksEffectiveAgainst);
	}
}
