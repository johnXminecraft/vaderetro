package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class WheatGrinderRecipes implements IProcessorRecipes {

    private static final WheatGrinderRecipes base = new WheatGrinderRecipes();
    private Map processingList = new HashMap();

    public static WheatGrinderRecipes processing() {
        return base;
    }

    public WheatGrinderRecipes() {
        this.addRecipe(Item.wheat.shiftedIndex, new ItemStack(Item.flour, 1));
        this.addRecipe(Item.cannabisLeaf.shiftedIndex, new ItemStack(Item.rope, 1));
    }

    public void addRecipe(int itemID, ItemStack itemStack) {
        this.processingList.put(itemID, itemStack);
    }

    public ItemStack getResult(int itemID) {
        return (ItemStack)this.processingList.get(itemID);
    }

    public Map getProcessingList() {
        return this.processingList;
    }
}
