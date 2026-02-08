package net.minecraft.src.vaderetro.recipes.creators.crafting;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesMill {

    public void addRecipes(CraftingManager craftingManager) {

        craftingManager.addRecipe(new ItemStack(Block.johnMill), new Object[]{
                "WSW", "SPS", "WSW",
                'P', Block.planks,
                'W', Block.cloth,
                'S', Item.stick
        });

        craftingManager.addRecipe(new ItemStack(Block.waterWheel), new Object[]{
                " P ", "PSP", " P ",
                'P', Block.planks,
                'S', Item.stick
        });

        craftingManager.addRecipe(new ItemStack(Block.millAxle, 1), new Object[]{
                "###", "#X#", "###",
                '#', Block.planks,
                'X', Item.stick
        });

        craftingManager.addRecipe(new ItemStack(Block.axleRod, 4), new Object[]{
                "#", "#", "#",
                '#', Item.stick
        });

        craftingManager.addRecipe(new ItemStack(Block.gearbox, 1), new Object[]{
                "###", "#X#", "###",
                '#', Block.planks,
                'X', Item.ingotIron
        });

        craftingManager.addRecipe(new ItemStack(Block.wheatGrinder, 1), new Object[]{
                "###", "#X#", "###",
                '#', Block.cobblestone,
                'X', Item.ingotIron
        });

        craftingManager.addRecipe(new ItemStack(Block.macerator), new Object[]{
                "FIF", "SMS", "FFF",
                'F', Item.flint,
                'I', Item.ingotIron,
                'S', Block.stone,
                'M', Item.ingotStainedSteel
        });
    }
}
