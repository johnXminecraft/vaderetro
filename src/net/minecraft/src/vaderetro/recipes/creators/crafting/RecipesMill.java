package net.minecraft.src.vaderetro.recipes.creators.crafting;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesMill {

    public void addRecipes(CraftingManager craftingManager) {
        craftingManager.addRecipe(new ItemStack(Block.johnMill), new Object[]{
                "WSW", "SPS", "WSW",
                Character.valueOf('P'), Block.planks,
                Character.valueOf('W'), Block.cloth,
                Character.valueOf('S'), Item.stick
        });
        craftingManager.addRecipe(new ItemStack(Block.waterWheel), new Object[]{
                " P ", "PSP", " P ",
                Character.valueOf('P'), Block.planks,
                Character.valueOf('S'), Item.stick
        });
    }
}
