package net.minecraft.src;

public class BlockWorkbench extends Block {

	protected BlockWorkbench(int id) {
		super(id, Material.wood);
		this.blockIndexInTexture = 59;
	}

	public int getBlockTextureFromSide(int sideId) {
		return sideId == 1 ? this.blockIndexInTexture - 16 : (sideId == 0 ? Block.planks.getBlockTextureFromSide(0) : (sideId != 2 && sideId != 4 ? this.blockIndexInTexture : this.blockIndexInTexture + 1));
	}

	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if(world.multiplayerWorld) {
			return true;
		} else {
			player.displayWorkbenchGUI(x, y, z);
			return true;
		}
	}
}
