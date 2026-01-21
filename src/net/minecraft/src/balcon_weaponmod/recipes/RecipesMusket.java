package net.minecraft.src.balcon_weaponmod.recipes;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesMusket {

    public void addRecipes(CraftingManager var1) {
        var1.addRecipe(new ItemStack(Item.musketBullet, 16), new Object[]{
                "T", "G", "P",
                Character.valueOf('T'), Item.ingotTin,
                Character.valueOf('G'), Item.gunpowder,
                Character.valueOf('P'), Item.paper
        });
        var1.addRecipe(new ItemStack(Item.musketBarrel, 1), new Object[]{
                "SSS", "  F",
                Character.valueOf('S'), Item.ingotStainedSteel,
                Character.valueOf('F'), Item.flintAndSteel
        });
        var1.addRecipe(new ItemStack(Item.musketButt, 1), new Object[]{
                "SSP", "  P",
                Character.valueOf('S'), Item.stick,
                Character.valueOf('P'), Block.planks
        });
        var1.addRecipe(new ItemStack(Item.musket, 1), new Object[]{
                "RT",
                Character.valueOf('T'), Item.musketButt,
                Character.valueOf('R'), Item.musketBarrel
        });
        var1.addRecipe(new ItemStack(Item.gunpowder, 1), new Object[]{
                "S", "C", "G",
                Character.valueOf('S'), Item.ingotSulfur,
                Character.valueOf('C'), Item.coal,
                Character.valueOf('G'), Item.sugar,
        });
    }
}
