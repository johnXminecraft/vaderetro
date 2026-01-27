package net.minecraft.src;

public class RecipesArmor {
	private String[][] recipePatterns = new String[][]{
			{"XXX", "X X"},
			{"X X", "XXX", "XXX"},
			{"XXX", "X X", "X X"},
			{"X X", "X X"}};
	private Object[][] recipeItems = new Object[][]{
			{Item.leather, Item.chain, Item.ingotIron, Item.diamond, Item.ingotGold, Item.ingotStainedSteel, Item.unobtainium},
			{Item.helmetLeather, Item.helmetChain, Item.helmetSteel, Item.helmetDiamond, Item.helmetGold, Item.helmetStainedSteel, Item.helmetSchema},
			{Item.plateLeather, Item.plateChain, Item.plateSteel, Item.plateDiamond, Item.plateGold, Item.plateStainedSteel, Item.plateSchema},
			{Item.legsLeather, Item.legsChain, Item.legsSteel, Item.legsDiamond, Item.legsGold, Item.legsStainedSteel, Item.legsSchema},
			{Item.bootsLeather, Item.bootsChain, Item.bootsSteel, Item.bootsDiamond, Item.bootsGold, Item.bootsStainedSteel, Item.bootsSchema},
	};

	public void addRecipes(CraftingManager craftingManager) {
		for(int i = 0; i < this.recipeItems[0].length; ++i) {
			Object material = this.recipeItems[0][i];
			for(int j = 0; j < this.recipeItems.length - 1; ++j) {
				Item armorPiece = (Item)this.recipeItems[j + 1][i];
				craftingManager.addRecipe(new ItemStack(armorPiece), new Object[]{this.recipePatterns[j], Character.valueOf('X'), material});
				craftingManager.addRecipe(new ItemStack(armorPiece), new Object[]{"X ", " X", Character.valueOf('X'), new ItemStack(armorPiece, 1, -1)});
			}
		}
	}
}
