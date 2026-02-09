package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class OpenHearthFurnaceRecipes implements IProcessorRecipes {

    private static final OpenHearthFurnaceRecipes base = new OpenHearthFurnaceRecipes();
    private Map processingList = new HashMap();

    public static OpenHearthFurnaceRecipes processing() {
        return base;
    }

    private OpenHearthFurnaceRecipes() {
        this.addRecipe(Item.stainedSteelCompound.shiftedIndex, new ItemStack(Item.ingotStainedSteel, 1));
        this.addRecipe(Item.bucketOil.shiftedIndex, new ItemStack(Item.ingotPlastic, 1));
        this.addRecipe(Block.oreGoldHell.blockID, new ItemStack(Item.ingotGold, 1));
        this.addRecipe(Block.oreSulfurHell.blockID, new ItemStack(Item.ingotSulfur, 1));
        this.addRecipe(Item.dustIron.shiftedIndex, new ItemStack(Item.ingotIron, 1));
        this.addRecipe(Item.dustGold.shiftedIndex, new ItemStack(Item.ingotGold, 1));
        this.addRecipe(Item.dustCopper.shiftedIndex, new ItemStack(Item.ingotCopper, 1));
        this.addRecipe(Item.dustSulfur.shiftedIndex, new ItemStack(Item.ingotSulfur, 1));
        this.addRecipe(Item.dustTin.shiftedIndex, new ItemStack(Item.ingotTin, 1));
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
