package net.minecraft.src.vaderetro.johnindustrialmod.block;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.Material;

import java.util.Random;

public class BlockUraniumOre extends Block {

    public BlockUraniumOre(int id, int textureId) {
        super(id, textureId, Material.rock);
    }

    public int idDropped(int id, Random random) {
        return Item.uraniumDust.shiftedIndex;
    }

    public int quantityDropped(Random random) {
        return 1 + random.nextInt(2);
    }
}
