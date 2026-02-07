package net.minecraft.src.vaderetro.recipes.creators.crafting;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesFarming {

    public void addRecipes(CraftingManager craftingManager) {
        craftingManager.addRecipe(new ItemStack(Item.cigarette, 20), new Object[]{
                "PPP", "WTT", "PPP",
                Character.valueOf('P'), Item.paper,
                Character.valueOf('W'), Block.cloth,
                Character.valueOf('T'), Item.tobacco
        });
        craftingManager.addRecipe(new ItemStack(Item.cloth), new Object[]{
                "RR", "RR",
                Character.valueOf('R'), Item.rope
        });
        craftingManager.addRecipe(new ItemStack(Block.dryerIdle), new Object[]{
                "BBB", "B B", "BBB",
                Character.valueOf('B'), Block.brick
        });
        craftingManager.addRecipe(new ItemStack(Item.belt, 3), new Object[]{
                "D",
                Character.valueOf('D'), Item.driedLeather
        });
        craftingManager.addRecipe(new ItemStack(Block.ovenIdle), new Object[]{
                "BB", "BB",
                Character.valueOf('B'), Block.brick
        });
        craftingManager.addRecipe(new ItemStack(Block.cookingTable), new Object[]{
                "PP", "CC",
                Character.valueOf('P'), Block.planks,
                Character.valueOf('C'), Block.cobblestone,
        });
    }
}
