package net.minecraft.src.vaderetro.recipes.processors;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MaceratorRecipes implements IProcessorRecipes {

    private static final MaceratorRecipes base = new MaceratorRecipes();

    public static MaceratorRecipes processing() {
        return base;
    }
    
    private Map processingList = new HashMap();

    public MaceratorRecipes() {
        // to add: copper dust, tin dust, sulfur dust
        this.addRecipe(Block.oreGold.blockID, new ItemStack(Item.dustGold, 2));
        this.addRecipe(Block.oreGoldHell.blockID, new ItemStack(Item.dustGold, 4));
        this.addRecipe(Block.oreIron.blockID, new ItemStack(Item.dustIron, 2));
    }
    
    public void addRecipe(int itemID, ItemStack itemStack) {
        this.processingList.put(itemID, itemStack);
    }
    
    public ItemStack getResult(int itemID) {
        return (ItemStack)this.processingList.get(itemID);
    }
}
