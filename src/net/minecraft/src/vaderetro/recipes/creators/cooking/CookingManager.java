package net.minecraft.src.vaderetro.recipes.creators.cooking;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CookingManager {

    private static final CookingManager instance = new CookingManager();
    private List recipes = new ArrayList();

    public static final CookingManager getInstance() {
        return instance;
    }

    private CookingManager() {

        // Cake
        this.addRecipe(new ItemStack(Item.cake, 1), new Object[]{
                "AAA", "BEB", "CCC",
                Character.valueOf('A'), Item.bucketMilk,
                Character.valueOf('B'), Item.sugar,
                Character.valueOf('C'), Item.flour,
                Character.valueOf('E'), Item.egg
        });
        // Sugar
        this.addRecipe(new ItemStack(Item.sugar, 1), new Object[]{
                "#",
                Character.valueOf('#'), Item.reed
        });
        // Dough
        this.addRecipe(new ItemStack(Item.dough, 1), new Object[]{
                "FS",
                Character.valueOf('F'), Item.flour,
                Character.valueOf('S'), Item.salt,
        });
        // Golden Apple
        this.addRecipe(new ItemStack(Item.appleGold, 1), new Object[]{
                "###", "#X#", "###",
                Character.valueOf('#'), Block.blockGold,
                Character.valueOf('X'), Item.appleRed
        });
        // Mushroom Stew
        this.addShapelessRecipe(new ItemStack(Item.bowlSoup), new Object[]{
                Block.mushroomBrown, Block.mushroomRed, Item.bowlEmpty
        });
        // Cookie
        this.addRecipe(new ItemStack(Item.cookie, 8), new Object[]{
                "#X#",
                Character.valueOf('X'), new ItemStack(Item.dyePowder, 1, 3),
                Character.valueOf('#'), Item.flour
        });
        // Uncooked Meal
        this.addShapelessRecipe(new ItemStack(Item.uncookedMeal), new Object[]{
                Item.potato, Item.carrot, Item.porkRaw, Item.onion, Item.ceramicPlate,
        });

        // easter eggs recipes
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 0), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 0),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 1), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 1),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 2), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 2),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 3), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 3),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 4), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 4),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 5), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 5),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 6), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 6),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 7), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 7),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 8), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 8),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 9), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 9),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 10), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 10),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 11), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 11),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 12), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 12),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 13), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 13),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 14), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 14),
                Character.valueOf('E'), Item.egg
        });
        this.addRecipe(new ItemStack(Item.easterEgg, 1, 15), new Object[]{
                "OBD", "OED", "OBD",
                Character.valueOf('O'), Item.onion,
                Character.valueOf('B'), Item.bowlOfGold,
                Character.valueOf('D'), new ItemStack(Item.dyePowder, 1, 15),
                Character.valueOf('E'), Item.egg
        });

        Collections.sort(this.recipes, new RecipeSorter(this));
        System.out.println(this.recipes.size() + " cooking recipes");
    }

    public void addRecipe(ItemStack var1, Object... var2) {
        StringBuilder var3 = new StringBuilder();
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        if(var2[var4] instanceof String[]) {
            String[] var11 = (String[])((String[])var2[var4++]);

            for (String var9 : var11) {
                ++var6;
                var5 = var9.length();
                var3.append(var9);
            }
        } else {
            while(var2[var4] instanceof String) {
                String var7 = (String)var2[var4++];
                ++var6;
                var5 = var7.length();
                var3.append(var7);
            }
        }

        HashMap var12;
        for(var12 = new HashMap(); var4 < var2.length; var4 += 2) {
            Character var13 = (Character)var2[var4];
            ItemStack var15 = null;
            if(var2[var4 + 1] instanceof Item) {
                var15 = new ItemStack((Item)var2[var4 + 1]);
            } else if(var2[var4 + 1] instanceof Block) {
                var15 = new ItemStack((Block)var2[var4 + 1], 1, -1);
            } else if(var2[var4 + 1] instanceof ItemStack) {
                var15 = (ItemStack)var2[var4 + 1];
            }

            var12.put(var13, var15);
        }

        ItemStack[] var14 = new ItemStack[var5 * var6];

        for(int var16 = 0; var16 < var5 * var6; ++var16) {
            char var10 = var3.charAt(var16);
            if(var12.containsKey(Character.valueOf(var10))) {
                var14[var16] = ((ItemStack)var12.get(Character.valueOf(var10))).copy();
            } else {
                var14[var16] = null;
            }
        }

        this.recipes.add(new ShapedRecipes(var5, var6, var14, var1));
    }

    void addShapelessRecipe(ItemStack var1, Object... var2) {
        ArrayList var3 = new ArrayList();
        Object[] var4 = var2;
        int var5 = var2.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Object var7 = var4[var6];
            if(var7 instanceof ItemStack) {
                var3.add(((ItemStack)var7).copy());
            } else if(var7 instanceof Item) {
                var3.add(new ItemStack((Item)var7));
            } else {
                if(!(var7 instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                var3.add(new ItemStack((Block)var7));
            }
        }

        this.recipes.add(new ShapelessRecipes(var1, var3));
    }

    public ItemStack findMatchingRecipe(InventoryCrafting var1) {
        for(int var2 = 0; var2 < this.recipes.size(); ++var2) {
            IRecipe var3 = (IRecipe)this.recipes.get(var2);
            if(var3.matches(var1)) {
                return var3.getCraftingResult(var1);
            }
        }

        return null;
    }

    public List getRecipeList() {
        return this.recipes;
    }
}
