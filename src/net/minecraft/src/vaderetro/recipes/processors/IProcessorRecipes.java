package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.ItemStack;
import java.util.HashMap;
import java.util.Map;

public interface IProcessorRecipes {

    Map processingList = new HashMap();

    default void addRecipe(int input, ItemStack output) {
        if(!processingList.containsKey(input)) {
            processingList.put(input, output);
        }
    }

    default ItemStack getResult(int itemId) {
        return (ItemStack) processingList.get(itemId);
    }

    default Map getProcessingList() {
        return processingList;
    }
}
