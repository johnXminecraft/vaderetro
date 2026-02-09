package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class OvenRecipes implements IProcessorRecipes {

    private static final OvenRecipes base = new OvenRecipes();
    private Map processingList = new HashMap();

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
