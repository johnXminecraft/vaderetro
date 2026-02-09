package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityMilitaryCase;

import java.util.Random;

public class BlockMilitaryCase extends BlockContainer {

    private Random random = new Random();
    private final boolean isSupply;

    public BlockMilitaryCase(int id, Material material, boolean isSupply) {
        super(id, material);
        this.blockIndexInTexture = 0;
        this.isSupply = isSupply;
    }

    public String getTexturePath() {
        return this.isSupply ? "/terrain/military_supcase.png" : "/terrain/military_case.png";
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityMilitaryCase();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public int idDropped(int par1, Random par2Random) {
    return -1;
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
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (!(te instanceof TileEntityMilitaryCase)) {
            return false;
        }

        TileEntityMilitaryCase militaryCase = (TileEntityMilitaryCase) te;

        if (!militaryCase.isOpen) {
            militaryCase.isOpen = true;
            world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.chestopen", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
            return true;
        }

        if (militaryCase.isOpen && !militaryCase.isEmpty) {
            if (this.isSupply) {
                Item dropItem = this.random.nextFloat() < 0.3F ? Item.ar15 : Item.antiRadin;
                float offsetX = this.random.nextFloat() * 0.8F + 0.1F;
                float offsetY = this.random.nextFloat() * 0.8F + 0.1F;
                float offsetZ = this.random.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, (double) ((float) x + offsetX), (double) ((float) y + offsetY), (double) ((float) z + offsetZ), new ItemStack(dropItem, 1, 0));
                entityItem.motionX = (double) ((float) this.random.nextGaussian() * 0.05F);
                entityItem.motionY = (double) ((float) this.random.nextGaussian() * 0.05F + 0.2F);
                entityItem.motionZ = (double) ((float) this.random.nextGaussian() * 0.05F);
                world.entityJoinedWorld(entityItem);
            } else {
                float offsetX = this.random.nextFloat() * 0.8F + 0.1F;
                float offsetY = this.random.nextFloat() * 0.8F + 0.1F;
                float offsetZ = this.random.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, (double) ((float) x + offsetX), (double) ((float) y + offsetY), (double) ((float) z + offsetZ), new ItemStack(Item.militaryKeycard, 1, 0));
                entityItem.motionX = (double) ((float) this.random.nextGaussian() * 0.05F);
                entityItem.motionY = (double) ((float) this.random.nextGaussian() * 0.05F + 0.2F);
                entityItem.motionZ = (double) ((float) this.random.nextGaussian() * 0.05F);

                world.entityJoinedWorld(entityItem);
            }
            militaryCase.isEmpty = true;
            return true;
        }

        return true;
    }
}
