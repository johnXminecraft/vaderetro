package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityCable;
import java.util.HashSet;
import java.util.Set;

public class BlockCable extends BlockContainer {
    private static final int INVENTORY_TEXTURE = 298;

    private static ThreadLocal<Set<String>> updatingCoords = new ThreadLocal<Set<String>>() {
        @Override
        protected Set<String> initialValue() {
            return new HashSet<String>();
        }
    };

    public BlockCable(int id) {
        super(id, Material.cloth);
        this.setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
        this.setHardness(0.1F);
        this.setStepSound(Block.soundMetalFootstep);
        this.blockIndexInTexture = 282;
        this.setBlockName("cable");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return this.blockIndexInTexture;
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        return INVENTORY_TEXTURE;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityCable();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        updateConnections(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborId) {
        super.onNeighborBlockChange(world, x, y, z, neighborId);
        updateConnections(world, x, y, z);
    }
    
    private void updateConnections(World world, int x, int y, int z) {
        String key = x + "," + y + "," + z;
        Set<String> updating = updatingCoords.get();
        if (updating.contains(key)) return;
        
        updating.add(key);
        try {
            TileEntity te = world.getBlockTileEntity(x, y, z);
            if (te instanceof TileEntityCable) {
                ((TileEntityCable) te).updateConnections();
            }
        } finally {
            updating.remove(key);
        }
    }
}
