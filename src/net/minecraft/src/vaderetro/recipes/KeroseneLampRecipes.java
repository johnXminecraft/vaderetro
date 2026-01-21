package net.minecraft.src.vaderetro.recipes;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class KeroseneLampRecipes {

    private static final KeroseneLampRecipes smeltingBase = new KeroseneLampRecipes();
    private Map smeltingList = new HashMap();

    public static KeroseneLampRecipes smelting() {
        return smeltingBase;
    }

    private KeroseneLampRecipes() {
        this.addSmelting(Item.bucketKerosene.shiftedIndex, new ItemStack(Item.bucketEmpty));
    }

    public void addSmelting(int var1, ItemStack var2) {
        this.smeltingList.put(Integer.valueOf(var1), var2);
    }

    public ItemStack getSmeltingResult(int var1) {
        return (ItemStack)this.smeltingList.get(Integer.valueOf(var1));
    }

    public Map getSmeltingList() {
        return this.smeltingList;
    }
}
