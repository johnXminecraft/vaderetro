package net.minecraft.src.vaderetro.recipes.creators.crafting;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesCeramic {

    public void addRecipes(CraftingManager craftingManager) {
        craftingManager.addRecipe(new ItemStack(Block.blockClay, 1), new Object[]{
                "##", "##",
                Character.valueOf('#'), Item.clay}
        );
        craftingManager.addRecipe(new ItemStack(Block.brick, 1), new Object[]{
                "##", "##",
                Character.valueOf('#'), Item.brick
        });
        craftingManager.addRecipe(new ItemStack(Block.ceramicFurnaceIdle), new Object[]{
                "SS", "SS",
                Character.valueOf('S'), Block.stone
        });
        craftingManager.addRecipe(new ItemStack(Block.stairSingle, 3, 1), new Object[]{
                "###",
                Character.valueOf('#'), Block.sandStone
        });
        craftingManager.addRecipe(new ItemStack(Item.helmetClay), new Object[]{
                "CCC", "C C",
                Character.valueOf('C'), Item.clay
        });
        craftingManager.addRecipe(new ItemStack(Item.plateClay), new Object[]{
                "C C", "CCC", "CCC",
                Character.valueOf('C'), Item.clay
        });
        craftingManager.addRecipe(new ItemStack(Item.legsClay), new Object[]{
                "CCC", "C C", "C C",
                Character.valueOf('C'), Item.clay
        });
        craftingManager.addRecipe(new ItemStack(Item.bootsClay), new Object[]{
                "C C", "C C",
                Character.valueOf('C'), Item.clay
        });
        craftingManager.addRecipe(new ItemStack(Item.legsClay), new Object[]{
                "PPP", "CIC", "CIC",
                Character.valueOf('P'), Block.planks,
                Character.valueOf('C'), Block.cobblestone,
                Character.valueOf('I'), Item.ingotIron
        });

        // Colored Terracotta
        for (int i = 0; i < 16; i++) {
            craftingManager.addRecipe(new ItemStack(Block.terracottaColored, 8, i), new Object[]{
                    "TTT", "TDT", "TTT",
                    Character.valueOf('T'), Block.terracotta,
                    Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, i)
            });
        }
    }
}
