package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class WheatGrinderRecipes implements IProcessorRecipes {

    private static final WheatGrinderRecipes base = new WheatGrinderRecipes();

    public static WheatGrinderRecipes processing() {
        return base;
    }

    public WheatGrinderRecipes() {
        this.addRecipe(Item.wheat.shiftedIndex, new ItemStack(Item.flour, 1));
        this.addRecipe(Item.cannabisLeaf.shiftedIndex, new ItemStack(Item.rope, 1));
    }
}
