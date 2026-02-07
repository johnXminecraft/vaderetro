package net.minecraft.src.vaderetro.block;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockTerracottaColored extends Block {

    public BlockTerracottaColored(int id, int blockIndexInTexture) {
        super(id, blockIndexInTexture, Material.rock);
    }

    public int getBlockTextureFromSideAndMetadata(int var1, int damage) {
        return damage == 0 ? this.blockIndexInTexture :
                (damage == 1 ? this.blockIndexInTexture + 1 :
                (damage == 2 ? this.blockIndexInTexture + 16 :
                (damage == 3 ? this.blockIndexInTexture + 17 :
                (damage == 4 ? this.blockIndexInTexture + 32 :
                (damage == 5 ? this.blockIndexInTexture + 33 :
                (damage == 6 ? this.blockIndexInTexture + 48 :
                (damage == 7 ? this.blockIndexInTexture + 49 :
                (damage == 8 ? this.blockIndexInTexture + 64 :
                (damage == 9 ? this.blockIndexInTexture + 65 :
                (damage == 10 ? this.blockIndexInTexture + 80 :
                (damage == 11 ? this.blockIndexInTexture + 81 :
                (damage == 12 ? this.blockIndexInTexture + 96 :
                (damage == 13 ? this.blockIndexInTexture + 97 :
                (damage == 14 ? this.blockIndexInTexture + 112 :
                (damage == 15 ? this.blockIndexInTexture + 113 : 288)))))))))))))));
    }

    public int getBlockTextureFromDamage(int damage) {
        return damage == 0 ? this.blockIndexInTexture :
                (damage == 1 ? this.blockIndexInTexture + 1 :
                (damage == 2 ? this.blockIndexInTexture + 16 :
                (damage == 3 ? this.blockIndexInTexture + 17 :
                (damage == 4 ? this.blockIndexInTexture + 32 :
                (damage == 5 ? this.blockIndexInTexture + 33 :
                (damage == 6 ? this.blockIndexInTexture + 48 :
                (damage == 7 ? this.blockIndexInTexture + 49 :
                (damage == 8 ? this.blockIndexInTexture + 64 :
                (damage == 9 ? this.blockIndexInTexture + 65 :
                (damage == 10 ? this.blockIndexInTexture + 80 :
                (damage == 11 ? this.blockIndexInTexture + 81 :
                (damage == 12 ? this.blockIndexInTexture + 96 :
                (damage == 13 ? this.blockIndexInTexture + 97 :
                (damage == 14 ? this.blockIndexInTexture + 112 :
                (damage == 15 ? this.blockIndexInTexture + 113 : 288)))))))))))))));
    }

    protected int damageDropped(int var1) {
        return var1;
    }
}