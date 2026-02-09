package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class CeramicFurnaceRecipes implements IProcessorRecipes {

    private static final CeramicFurnaceRecipes base = new CeramicFurnaceRecipes();
    private Map processingList = new HashMap();

    public static CeramicFurnaceRecipes processing() {
        return base;
    }

    private CeramicFurnaceRecipes() {
        this.addRecipe(Item.clay.shiftedIndex, new ItemStack(Item.brick));
        this.addRecipe(Item.rawClayPlate.shiftedIndex, new ItemStack(Item.ceramicPlate));
        this.addRecipe(Block.sand.blockID, new ItemStack(Block.glass));
        this.addRecipe(Item.helmetClay.shiftedIndex, new ItemStack(Item.helmetCeramic));
        this.addRecipe(Item.plateClay.shiftedIndex, new ItemStack(Item.plateCeramic));
        this.addRecipe(Item.legsClay.shiftedIndex, new ItemStack(Item.legsCeramic));
        this.addRecipe(Item.bootsClay.shiftedIndex, new ItemStack(Item.bootsCeramic));
        this.addRecipe(Block.blockClay.blockID, new ItemStack(Block.terracotta));
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
