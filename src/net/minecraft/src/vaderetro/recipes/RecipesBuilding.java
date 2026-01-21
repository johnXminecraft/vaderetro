package net.minecraft.src.vaderetro.recipes;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesBuilding {

    public void addRecipes(CraftingManager craftingManager) {
        craftingManager.addRecipe(new ItemStack(Block.parquet, 3), new Object[]{
                "PPP", "PPP", "PPP",
                Character.valueOf('P'), Block.planks
        });
        craftingManager.addRecipe(new ItemStack(Block.tiles, 1), new Object[]{
                "BBB", "BBB", "BBB",
                Character.valueOf('B'), Item.brick
        });
        craftingManager.addRecipe(new ItemStack(Block.tilesGreen, 8), new Object[]{
                "TTT", "TGT", "TTT",
                Character.valueOf('T'), Block.tiles,
                Character.valueOf('G'), new ItemStack(Item.dyePowder, 1, 2)
        });
    }
}
