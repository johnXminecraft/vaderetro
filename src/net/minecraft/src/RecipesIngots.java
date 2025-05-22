package net.minecraft.src;

public class RecipesIngots {
	private Object[][] recipeItems = new Object[][]{
			{Block.blockGold, new ItemStack(Item.ingotGold, 9)},
			{Block.blockSteel, new ItemStack(Item.ingotIron, 9)},
			{Block.blockDiamond, new ItemStack(Item.diamond, 9)},
			{Block.blockLapis, new ItemStack(Item.dyePowder, 9, 4)},
			{Block.blockTin, new ItemStack(Item.ingotTin, 9)},
			{Block.blockCopper, new ItemStack(Item.ingotCopper, 9)},
			{Block.blockRedstone, new ItemStack(Item.redstone, 9)},
			{Block.blockCoal, new ItemStack(Item.coal, 9)},
			{Block.blockSulfur, new ItemStack(Item.ingotSulfur, 9)},
			{Block.blockPlastic, new ItemStack(Item.ingotPlastic, 9)},
			{Block.blockStainedSteel, new ItemStack(Item.ingotStainedSteel, 9)}
	};

	public void addRecipes(CraftingManager var1) {
		for(int var2 = 0; var2 < this.recipeItems.length; ++var2) {
			Block var3 = (Block)this.recipeItems[var2][0];
			ItemStack var4 = (ItemStack)this.recipeItems[var2][1];
			var1.addRecipe(new ItemStack(var3), new Object[]{"###", "###", "###", Character.valueOf('#'), var4});
			var1.addRecipe(var4, new Object[]{"#", Character.valueOf('#'), var3});
		}
	}
}
