package net.minecraft.src.vaderetro.recipes.creators.crafting;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesFarming {

    public void addRecipes(CraftingManager craftingManager) {

        craftingManager.addRecipe(new ItemStack(Item.cigarette, 20), new Object[]{
                "PPP", "WTT", "PPP",
                'P', Item.paper,
                'W', Block.cloth,
                'T', Item.tobacco
        });

        craftingManager.addRecipe(new ItemStack(Block.cloth, 1), new Object[]{
                "RR", "RR",
                'R', Item.rope
        });

        craftingManager.addRecipe(new ItemStack(Block.dryerIdle, 1), new Object[]{
                "BBB", "B B", "BBB",
                'B', Block.brick
        });

        craftingManager.addRecipe(new ItemStack(Item.belt, 3), new Object[]{
                "D",
                'D', Item.driedLeather
        });

        craftingManager.addRecipe(new ItemStack(Block.ovenIdle, 1), new Object[]{
                "BB", "BB",
                'B', Block.brick
        });

        craftingManager.addRecipe(new ItemStack(Block.cookingTable, 1), new Object[]{
                "PP", "CC",
                'P', Block.planks,
                'C', Block.cobblestone
        });
    }
}
