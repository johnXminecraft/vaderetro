package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class DryerRecipes implements IProcessorRecipes {

    private static final DryerRecipes base = new DryerRecipes();

    public static DryerRecipes processing() {
        return base;
    }

    public DryerRecipes() {
        this.addRecipe(Item.bucketWater.shiftedIndex, new ItemStack(Item.salt, 4));
        this.addRecipe(Item.rottenFlesh.shiftedIndex, new ItemStack(Item.jerky, 1));
        this.addRecipe(Item.tobaccoLeaf.shiftedIndex, new ItemStack(Item.tobacco, 1));
        this.addRecipe(Item.leather.shiftedIndex, new ItemStack(Item.driedLeather, 1));
    }
}
