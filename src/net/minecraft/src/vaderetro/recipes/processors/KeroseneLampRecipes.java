package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class KeroseneLampRecipes implements IProcessorRecipes {

    private static final KeroseneLampRecipes base = new KeroseneLampRecipes();
    private Map processingList = new HashMap();

    public static KeroseneLampRecipes processing() {
        return base;
    }

    private KeroseneLampRecipes() {
        this.addRecipe(Item.bucketKerosene.shiftedIndex, new ItemStack(Item.bucketEmpty));
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
