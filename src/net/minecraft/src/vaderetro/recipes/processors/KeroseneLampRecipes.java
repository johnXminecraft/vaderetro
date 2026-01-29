package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class KeroseneLampRecipes implements IProcessorRecipes {

    private static final KeroseneLampRecipes base = new KeroseneLampRecipes();

    public static KeroseneLampRecipes processing() {
        return base;
    }

    private KeroseneLampRecipes() {
        this.addRecipe(Item.bucketKerosene.shiftedIndex, new ItemStack(Item.bucketEmpty));
    }
}
