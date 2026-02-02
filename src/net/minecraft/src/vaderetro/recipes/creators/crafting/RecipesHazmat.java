package net.minecraft.src.vaderetro.recipes.creators.crafting;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;


public class RecipesHazmat {

    public void addRecipes(CraftingManager craftingManager) {

        craftingManager.addRecipe(new ItemStack(Item.rawLatex, 5), new Object[]{
                " SR", "SBS", "R  ",
                Character.valueOf('S'), Item.slimeBall,
                Character.valueOf('R'), Item.reed,
                Character.valueOf('B'), Item.bucketWater
        });
        craftingManager.addRecipe(new ItemStack(Item.rawLatex, 1), new Object[]{
                "S", "R",
                Character.valueOf('S'), Item.slimeBall,
                Character.valueOf('R'), Item.reed
        });

        craftingManager.addRecipe(new ItemStack(Item.oxygenTank, 1, 0), new Object[]{
                " I ", "RGR", " I ",
                Character.valueOf('I'), Item.ingotIron,
                Character.valueOf('R'), Item.rubber,
                Character.valueOf('G'), Block.glass
        });

        craftingManager.addRecipe(new ItemStack(Item.helmetHazmat, 1), new Object[]{
                "RCR", "RGR", " I ",
                Character.valueOf('R'), Item.rubber,
                Character.valueOf('C'), Item.cloth,
                Character.valueOf('G'), Block.glass,
                Character.valueOf('I'), Item.ingotStainedSteel
        });
        craftingManager.addRecipe(new ItemStack(Item.plateHazmat, 1), new Object[]{
                "R R", "RCR", "RLR",
                Character.valueOf('R'), Item.rubber,
                Character.valueOf('C'), Item.cloth,
                Character.valueOf('L'), Item.leather
        });
        craftingManager.addRecipe(new ItemStack(Item.legsHazmat, 1), new Object[]{
                "RCR", "R R", "RLR",
                Character.valueOf('R'), Item.rubber,
                Character.valueOf('C'), Item.cloth,
                Character.valueOf('L'), Item.leather
        });
        craftingManager.addRecipe(new ItemStack(Item.bootsHazmat, 1), new Object[]{
                "R R", "L L",
                Character.valueOf('R'), Item.rubber,
                Character.valueOf('L'), Item.leather
        });
    }
}
