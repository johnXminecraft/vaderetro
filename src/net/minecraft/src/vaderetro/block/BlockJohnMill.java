package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityJohnMill;

public class BlockJohnMill extends BlockContainer {
    public BlockJohnMill(int id, int textureIndex) {
        super(id, textureIndex, Material.wood);
        this.setHardness(2.0F);
        this.blockResistance = 5.0F * 3.0F; 
        this.stepSound = soundWoodFootstep;
        this.setBlockName("johnMill");
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return -1;
    }

    protected TileEntity getBlockEntity() {
        return new TileEntityJohnMill();
    }

    private void log(String msg) {
        System.out.println("[JohnMill] " + msg);
    }

	private boolean isAttachable(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		if (id == 0) return false;
		Material m = world.getBlockMaterial(x, y, z);
		return !m.getIsLiquid();
	}

    private void logNeighbors(World world, int x, int y, int z) {
        int idN = world.getBlockId(x, y, z - 1);
        int idS = world.getBlockId(x, y, z + 1);
        int idW = world.getBlockId(x - 1, y, z);
        int idE = world.getBlockId(x + 1, y, z);
        int idD = world.getBlockId(x, y - 1, z);
        int idU = world.getBlockId(x, y + 1, z);
        log("neighbors N:"+idN+" S:"+idS+" W:"+idW+" E:"+idE+" D:"+idD+" U:"+idU);
        log("normal N:"+world.isBlockNormalCube(x, y, z - 1)
            +" S:"+world.isBlockNormalCube(x, y, z + 1)
            +" W:"+world.isBlockNormalCube(x - 1, y, z)
            +" E:"+world.isBlockNormalCube(x + 1, y, z)
            +" D:"+world.isBlockNormalCube(x, y - 1, z)
            +" U:"+world.isBlockNormalCube(x, y + 1, z));
    }

    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		boolean can = (side == 1 && isAttachable(world, x, y - 1, z)) ||
		       (side == 2 && isAttachable(world, x, y, z + 1)) ||
		       (side == 3 && isAttachable(world, x, y, z - 1)) ||
		       (side == 4 && isAttachable(world, x + 1, y, z)) ||
		       (side == 5 && isAttachable(world, x - 1, y, z)) ||
		       (side == 0 && isAttachable(world, x, y + 1, z));
        log("canPlaceBlockOnSide side=" + side + " can=" + can + " at ("+x+","+y+","+z+")");
        if (!can) {
            logNeighbors(world, x, y, z);
        }
        return can;
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		boolean can = isAttachable(world, x - 1, y, z) ||
		       isAttachable(world, x + 1, y, z) ||
		       isAttachable(world, x, y, z - 1) ||
		       isAttachable(world, x, y, z + 1) ||
		       isAttachable(world, x, y - 1, z) ||
		       isAttachable(world, x, y + 1, z);
        log("canPlaceBlockAt can=" + can + " at ("+x+","+y+","+z+")");
        if (!can) {
            logNeighbors(world, x, y, z);
        }
        return can;
    }

    public void onBlockPlaced(World world, int x, int y, int z, int side) {
        int meta = 0;
        // 1=W,2=E,3=N,4=S,5=UP,6=DOWN
		if (side == 5 && isAttachable(world, x - 1, y, z)) { 
            meta = 1;
		} else if (side == 4 && isAttachable(world, x + 1, y, z)) { // east face
            meta = 2;
		} else if (side == 3 && isAttachable(world, x, y, z - 1)) { // north face
            meta = 3;
		} else if (side == 2 && isAttachable(world, x, y, z + 1)) { // south face
            meta = 4;
		} else if (side == 1 && isAttachable(world, x, y - 1, z)) { // top of block below
            meta = 5;
		} else if (side == 0 && isAttachable(world, x, y + 1, z)) { // bottom of block above
            meta = 6;
        }
        log("onBlockPlaced side=" + side + " -> meta=" + meta + " at ("+x+","+y+","+z+")");
        logNeighbors(world, x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, meta);
    }

    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
        int meta = var1.getBlockMetadata(var2, var3, var4);
        if (meta == 0) {
            int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            if(var6 == 0) {
                var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
            }

            if(var6 == 1) {
                var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
            }

            if(var6 == 2) {
                var1.setBlockMetadataWithNotify(var2, var3, var4, 4);
            }

            if(var6 == 3) {
                var1.setBlockMetadataWithNotify(var2, var3, var4, 1);
            }
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborId) {
        int meta = world.getBlockMetadata(x, y, z) & 7;
        boolean attached = false;
        if (meta == 1 && world.isBlockNormalCube(x - 1, y, z)) attached = true;
        if (meta == 2 && world.isBlockNormalCube(x + 1, y, z)) attached = true;
        if (meta == 3 && world.isBlockNormalCube(x, y, z - 1)) attached = true;
        if (meta == 4 && world.isBlockNormalCube(x, y, z + 1)) attached = true;
        if (meta == 5 && world.isBlockNormalCube(x, y - 1, z)) attached = true;
        if (meta == 6 && world.isBlockNormalCube(x, y + 1, z)) attached = true;

        if (!attached) {
            log("Detached from support (meta="+meta+") -> dropping at ("+x+","+y+","+z+")");
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z));
            world.setBlockWithNotify(x, y, z, 0);
        }
    }
}