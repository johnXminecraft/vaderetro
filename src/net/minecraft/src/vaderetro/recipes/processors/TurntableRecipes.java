package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TurntableRecipes implements IProcessorRecipes {

    private static final TurntableRecipes base = new TurntableRecipes();

    public static TurntableRecipes processing() {
        return base;
    }

    private Map processingList = new HashMap();

    public TurntableRecipes() {
        this.addRecipe(Item.clay.shiftedIndex, new ItemStack(Item.rawClayPlate, 1));
    }

    public void addRecipe(int itemID, ItemStack itemStack) {
        this.processingList.put(itemID, itemStack);
    }

    public ItemStack getResult(int itemID) {
        return (ItemStack)this.processingList.get(itemID);
    }
}
