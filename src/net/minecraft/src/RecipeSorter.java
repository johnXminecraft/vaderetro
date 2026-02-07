package net.minecraft.src;

import net.minecraft.src.vaderetro.recipes.creators.cooking.CookingManager;

import java.util.Comparator;

public class RecipeSorter implements Comparator {

	CraftingManager craftingManager;
	CookingManager cookingManager;

	public RecipeSorter(CraftingManager craftingManager) {
		this.craftingManager = craftingManager;
	}

	public RecipeSorter(CookingManager cookingManager) {
		this.cookingManager = cookingManager;
	}

	public int compareRecipes(IRecipe var1, IRecipe var2) {
		return var1 instanceof ShapelessRecipes && var2 instanceof ShapedRecipes ? 1 : (var2 instanceof ShapelessRecipes && var1 instanceof ShapedRecipes ? -1 : (var2.getRecipeSize() < var1.getRecipeSize() ? -1 : (var2.getRecipeSize() > var1.getRecipeSize() ? 1 : 0)));
	}

	public int compare(Object var1, Object var2) {
		return this.compareRecipes((IRecipe)var1, (IRecipe)var2);
	}
}
