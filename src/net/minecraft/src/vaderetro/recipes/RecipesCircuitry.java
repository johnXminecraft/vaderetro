package net.minecraft.src.vaderetro.recipes;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesCircuitry {

    public void addRecipes(CraftingManager var1) {
        var1.addRecipe(new ItemStack(Item.wire, 32), new Object[]{"#", Character.valueOf('#'), Item.ingotCopper});
        var1.addRecipe(new ItemStack(Item.detailPlastic, 32), new Object[]{"#", Character.valueOf('#'), Item.ingotPlastic});
        var1.addRecipe(new ItemStack(Item.stainedSteelCompound), new Object[]{
                "CDI", "CDI", " B ",
                Character.valueOf('C'), Item.coal,
                Character.valueOf('D'), Item.diamond,
                Character.valueOf('I'), Item.ingotIron,
                Character.valueOf('B'), Item.bucketEmpty
        });
        var1.addRecipe(new ItemStack(Item.chain, 32), new Object[]{
                " # ", "# #", " # ",
                Character.valueOf('#'), Item.ingotStainedSteel
        });
        var1.addRecipe(new ItemStack(Item.anodeCathode), new Object[]{
                "TRT", "WSW",
                Character.valueOf('T'), Block.torchRedstoneActive,
                Character.valueOf('R'), Item.redstone,
                Character.valueOf('W'), Item.wire,
                Character.valueOf('S'), Block.stone
        });
        var1.addRecipe(new ItemStack(Item.resistor), new Object[]{
                "PRP", "W W",
                Character.valueOf('P'), Item.detailPlastic,
                Character.valueOf('R'), Item.redstoneRepeater,
                Character.valueOf('W'), Item.wire
        });
        var1.addRecipe(new ItemStack(Item.capacitor), new Object[]{
                "PSP", "W W",
                Character.valueOf('P'), Item.detailPlastic,
                Character.valueOf('S'), Item.ingotStainedSteel,
                Character.valueOf('W'), Item.wire
        });
        var1.addRecipe(new ItemStack(Item.vacuumTube), new Object[]{
                "SSS", "GAG", "SWS",
                Character.valueOf('S'), Item.ingotStainedSteel,
                Character.valueOf('G'), Block.glass,
                Character.valueOf('A'), Item.anodeCathode,
                Character.valueOf('W'), Item.wire
        });
        var1.addRecipe(new ItemStack(Item.circuitryPlateEmpty), new Object[]{
                "DDD", "WWW", "SSS",
                Character.valueOf('D'), Item.detailPlastic,
                Character.valueOf('W'), Item.wire,
                Character.valueOf('S'), Block.stone
        });
        var1.addRecipe(new ItemStack(Item.circuitryPlate), new Object[]{
                "RRR", "CCC", "VPV",
                Character.valueOf('R'), Item.resistor,
                Character.valueOf('C'), Item.capacitor,
                Character.valueOf('V'), Item.vacuumTube,
                Character.valueOf('P'), Item.circuitryPlateEmpty
        });
        var1.addRecipe(new ItemStack(Item.cathodeRayTube), new Object[]{
                "PPP", "DAG", "PPP",
                Character.valueOf('P'), Item.detailPlastic,
                Character.valueOf('D'), Block.dispenser,
                Character.valueOf('A'), Item.anodeCathode,
                Character.valueOf('G'), Block.glass
        });
        var1.addRecipe(new ItemStack(Block.crtTvSetIdle), new Object[]{
                "PPP", "JKJ", "WCW",
                Character.valueOf('P'), Item.detailPlastic,
                Character.valueOf('J'), Block.jukebox,
                Character.valueOf('K'), Item.cathodeRayTube,
                Character.valueOf('W'), Block.planks,
                Character.valueOf('C'), Item.circuitryPlate
        });
        var1.addRecipe(new ItemStack(Block.lampKeroseneIdle), new Object[]{
                "SSS", "G G", "SSS",
                Character.valueOf('S'), Item.ingotStainedSteel,
                Character.valueOf('G'), Block.glass
        });
    }
}
