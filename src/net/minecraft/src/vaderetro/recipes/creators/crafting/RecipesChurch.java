package net.minecraft.src.vaderetro.recipes.creators.crafting;

import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesChurch {

    public void addRecipes(CraftingManager craftingManager) {
        craftingManager.addRecipe(new ItemStack(Item.canvas, 1), new Object[]{
                "RGB", "LLL",
                Character.valueOf('R'), new ItemStack(Item.dyePowder, 1, 1),
                Character.valueOf('G'), Item.ingotGold,
                Character.valueOf('B'), new ItemStack(Item.dyePowder, 1, 4),
                Character.valueOf('L'), Item.leather
        });
        craftingManager.addRecipe(new ItemStack(Item.icon, 1), new Object[]{
                "SSS", "SCS", "SSS",
                Character.valueOf('S'), Item.stick,
                Character.valueOf('C'), Item.canvas
        });
        craftingManager.addRecipe(new ItemStack(Item.bowlOfGold), new Object[]{
                "CGC", " C ",
                Character.valueOf('C'), Item.ingotCopper,
                Character.valueOf('G'), Item.ingotGold
        });
    }
}