package net.minecraft.src.vaderetro.recipes;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class DryerRecipes {

    private static final DryerRecipes dryingBase = new DryerRecipes();
    private Map dryingList = new HashMap();

    public static DryerRecipes drying() {
        return dryingBase;
    }

    private DryerRecipes() {
        this.addRecipe(Item.bucketWater.shiftedIndex, new ItemStack(Item.salt, 4));
        this.addRecipe(Item.rottenFlesh.shiftedIndex, new ItemStack(Item.jerky, 1));
        this.addRecipe(Item.tobaccoLeaf.shiftedIndex, new ItemStack(Item.tobacco, 1));
        this.addRecipe(Item.leather.shiftedIndex, new ItemStack(Item.driedLeather, 1));
    }

    public void addRecipe(int var1, ItemStack var2) {
        this.dryingList.put(Integer.valueOf(var1), var2);
    }

    public ItemStack getDryingResult(int var1) {
        return (ItemStack)this.dryingList.get(Integer.valueOf(var1));
    }

    public Map getDryingList() {
        return this.dryingList;
    }
}
