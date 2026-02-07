package net.minecraft.src.vaderetro.block;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockCookingTable extends Block {

    public BlockCookingTable(int id) {
        super(id, Material.wood);
        this.blockIndexInTexture = 251;
    }

    public int getBlockTextureFromSide(int sideId) {
        return sideId == 1 ? this.blockIndexInTexture :
                (sideId == 0 ? 6 :
                (sideId != 5 && sideId != 4 ? 252 : 253));
    }

    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if(world.multiplayerWorld) {
            return true;
        } else {
            player.displayCookingTableGUI(x, y, z);
            return true;
        }
    }
}
