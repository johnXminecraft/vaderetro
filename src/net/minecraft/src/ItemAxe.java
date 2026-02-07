package net.minecraft.src;

public class ItemAxe extends ItemTool {

	private static Block[] blocksEffectiveAgainst = new Block[]{
			Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.pumpkin, Block.workbench, Block.doorWood,
			Block.slideDoor, Block.trapdoor, Block.parquet, Block.musicBlock, Block.jukebox, Block.stairCompactPlanks,
			Block.signWall, Block.ladder, Block.pressurePlatePlanks, Block.cactus, Block.fence, Block.pumpkinLantern,
			Block.lockedChest, Block.crtTvSetIdle, Block.crtTvSetActive, Block.johnMill, Block.millAxle,
			Block.waterWheel, Block.signPost, Block.axleRod, Block.gearbox, Block.radioactiveWood
	};

	protected ItemAxe(int var1, EnumToolMaterial var2) {
		super(var1, 3, var2, blocksEffectiveAgainst);
	}
}
