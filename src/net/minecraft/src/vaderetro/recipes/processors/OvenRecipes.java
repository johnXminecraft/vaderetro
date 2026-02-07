package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class OvenRecipes implements IProcessorRecipes {

    private static final OvenRecipes base = new OvenRecipes();

    public static OvenRecipes processing() {
        return base;
    }

    private OvenRecipes() {
        this.addRecipe(Item.porkRaw.shiftedIndex, new ItemStack(Item.porkCooked));
        this.addRecipe(Item.fishRaw.shiftedIndex, new ItemStack(Item.fishCooked));
        this.addRecipe(Item.egg.shiftedIndex, new ItemStack(Item.eggFried));
        this.addRecipe(Item.uncookedMeal.shiftedIndex, new ItemStack(Item.cookedMeal));
        this.addRecipe(Item.dough.shiftedIndex, new ItemStack(Item.bread));
        this.addRecipe(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2));
        this.addRecipe(Item.bucketWater.shiftedIndex, new ItemStack(Item.salt));
    }
}
