package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;

public class BlockTest extends Block {
    private static boolean debugPrinted = false;
    
    public BlockTest(int id, int textureIndex) {
        super(id, textureIndex, Material.rock); 
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.stepSound = soundStoneFootstep;
        this.setBlockName("testBlock");
    }

    public int getBlockTextureFromSide(int side) {
        if (!debugPrinted) {
            System.out.println("[BlockTest] getBlockTextureFromSide called, returning 273");
            debugPrinted = true;
        }
        return 511;
    }
    
    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        if (!debugPrinted) {
            System.out.println("[BlockTest] getBlockTextureFromSideAndMetadata called, returning 273");
            debugPrinted = true;
        }
        return 511;
    }
}