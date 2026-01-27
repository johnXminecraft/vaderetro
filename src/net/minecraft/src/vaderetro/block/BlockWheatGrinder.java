package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityWheatGrinder;

public class BlockWheatGrinder extends BlockContainer {
    public BlockWheatGrinder(int id, int textureIndex) {
        super(id, textureIndex, Material.wood);
        this.setHardness(2.0F);
        this.blockResistance = 5.0F;
        this.stepSound = soundWoodFootstep;
        this.setBlockName("wheatGrinder");
    }
    
    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityWheatGrinder();
    }
    
    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if (!world.multiplayerWorld) {
            TileEntityWheatGrinder tileEntity = (TileEntityWheatGrinder) world.getBlockTileEntity(x, y, z);
            player.displayGUIWheatGrinder(tileEntity);
        }
        return true;
    }
    
    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        if (side == 1) {
            return 235;    
        }
        return 236;    
    }
    
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        int nx = x + (side == 4 ? 1 : side == 5 ? -1 : 0);
        int ny = y + (side == 1 ? -1 : side == 0 ? 1 : 0);
        int nz = z + (side == 2 ? 1 : side == 3 ? -1 : 0);
        int nid = world.getBlockId(nx, ny, nz);
        
        if (nid == Block.axleRod.blockID) {
            return true;
        }
        
        return world.isBlockNormalCube(nx, ny, nz);
    }
    
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if (world.isBlockNormalCube(x, y - 1, z)) {
            return true;
        }
        
        int[][] neighbors = new int[][] { {-1,0,0}, {1,0,0}, {0,-1,0}, {0,1,0}, {0,0,-1}, {0,0,1} };
        for (int i = 0; i < neighbors.length; i++) {
            int nx = x + neighbors[i][0];
            int ny = y + neighbors[i][1];
            int nz = z + neighbors[i][2];
            if (world.getBlockId(nx, ny, nz) == Block.axleRod.blockID) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void onBlockRemoval(World world, int x, int y, int z) {
        TileEntityWheatGrinder tileEntity = (TileEntityWheatGrinder) world.getBlockTileEntity(x, y, z);
        if (tileEntity != null) {
            for (int i = 0; i < tileEntity.getSizeInventory(); i++) {
                ItemStack itemStack = tileEntity.getStackInSlot(i);
                if (itemStack != null) {
                    float f = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
                    
                    while (itemStack.stackSize > 0) {
                        int j = world.rand.nextInt(21) + 10;
                        if (j > itemStack.stackSize) {
                            j = itemStack.stackSize;
                        }
                        
                        itemStack.stackSize -= j;
                        EntityItem entityItem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemStack.itemID, j, itemStack.getItemDamage()));
                        float f3 = 0.05F;
                        entityItem.motionX = (double)((float)world.rand.nextGaussian() * f3);
                        entityItem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
                        entityItem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
                        world.entityJoinedWorld(entityItem);
                    }
                }
            }
        }
        super.onBlockRemoval(world, x, y, z);
    }
}