package net.minecraft.src.vaderetro.recipes;

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

        // easter eggs recipes
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 0), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 0),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 1), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 1),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 2), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 2),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 3), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 3),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 4), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 4),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 5), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 5),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 6), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 6),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 7), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 7),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 8), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 8),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 9), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 9),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 10), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 10),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 11), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 11),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 12), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 12),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 13), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 13),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 14), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 14),
                Character.valueOf('E'), Item.egg
        });
        craftingManager.addRecipe(new ItemStack(Item.easterEgg, 1, 15), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 15),
                Character.valueOf('E'), Item.egg
        });
    }
}
