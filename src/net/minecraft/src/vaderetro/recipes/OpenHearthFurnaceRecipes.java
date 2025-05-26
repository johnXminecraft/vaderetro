package net.minecraft.src.vaderetro.recipes;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class OpenHearthFurnaceRecipes {

    private static final OpenHearthFurnaceRecipes smeltingBase = new OpenHearthFurnaceRecipes();
    private Map smeltingList = new HashMap();

    public static OpenHearthFurnaceRecipes smelting() {
        return smeltingBase;
    }

    private OpenHearthFurnaceRecipes() {
        this.addSmelting(Item.stainedSteelCompound.shiftedIndex, new ItemStack(Item.ingotStainedSteel));
        this.addSmelting(Item.bucketOil.shiftedIndex, new ItemStack(Item.ingotPlastic, 1));
        this.addSmelting(Block.oreGoldHell.blockID, new ItemStack(Item.ingotGold, 1));
        this.addSmelting(Block.oreSulfurHell.blockID, new ItemStack(Item.ingotSulfur, 1));
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
