package net.minecraft.src.vaderetro.item;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.block.BlockTerracottaColored;

public class ItemTerracottaColored extends ItemBlock {

    public ItemTerracottaColored(int id) {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getIconFromDamage(int damage) {
        BlockTerracottaColored block = (BlockTerracottaColored)Block.terracottaColored;
        return block.getBlockTextureFromDamage(damage);
    }

    public int getPlacedBlockMetadata(int i) {
        return i;
    }

    public String getItemNameIS(ItemStack itemStack) {
        return super.getItemName() + "." + ItemDye.dyeColors[itemStack.getItemDamage()];
    }
}
