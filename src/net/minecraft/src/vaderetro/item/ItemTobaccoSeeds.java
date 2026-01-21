package net.minecraft.src.vaderetro.item;

import net.minecraft.src.*;

public class ItemTobaccoSeeds extends Item {

    private int blockId;

    public ItemTobaccoSeeds(int id, int blockId) {
        super(id);
        this.blockId = blockId;
    }

    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int var7) {
        if(var7 != 1) {
            return false;
        } else {
            int var8 = world.getBlockId(x, y, z);
            if(var8 == Block.tilledField.blockID && world.isAirBlock(x, y + 1, z)) {
                world.setBlockWithNotify(x, y + 1, z, this.blockId);
                --itemStack.stackSize;
                return true;
            } else {
                return false;
            }
        }
    }
}
