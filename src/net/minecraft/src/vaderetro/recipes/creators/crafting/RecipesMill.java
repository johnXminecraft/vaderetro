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
        craftingManager.addRecipe(new ItemStack(Block.millAxle, 1), new Object[]{
                "###", "#X#", "###",
                Character.valueOf('#'), Block.planks,
                Character.valueOf('X'), Item.stick
        });
        craftingManager.addRecipe(new ItemStack(Block.axleRod, 4), new Object[]{
                "#", "#", "#",
                Character.valueOf('#'), Item.stick
        });
        craftingManager.addRecipe(new ItemStack(Block.gearbox, 1), new Object[]{
                "###", "#X#", "###",
                Character.valueOf('#'), Block.planks,
                Character.valueOf('X'), Item.ingotIron
        });
        craftingManager.addRecipe(new ItemStack(Block.wheatGrinder, 1), new Object[]{
                "###", "#X#", "###",
                Character.valueOf('#'), Block.cobblestone,
                Character.valueOf('X'), Item.ingotIron
        });
        craftingManager.addRecipe(new ItemStack(Block.macerator), new Object[]{
                "FIF", "SMS", "FFF",
                Character.valueOf('F'), Item.flint,
                Character.valueOf('I'), Item.ingotIron,
                Character.valueOf('S'), Block.stone,
                Character.valueOf('M'), Item.ingotStainedSteel,
        });
    }
}
