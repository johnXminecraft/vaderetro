package net.minecraft.src.vaderetro.entity.mob;

import net.minecraft.src.*;

import java.util.Random;

public class EntityMiner extends EntityMob {

    private static final Random random = new Random();

    public EntityMiner(World world) {
        super(world);
        this.texture = "/mob/miner.png";
        this.moveSpeed = 1.0F;
        this.attackStrength = 7;
    }

    @Override
    public boolean getCanSpawnHere() {
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        if(var2 > 32) {
            return false;
        } else {
            return super.getCanSpawnHere();
        }
    }

    @Override
    protected String getLivingSound() {
        return "random.hurt";
    }

    @Override
    protected int getDropItemId() {
        return Item.coal.shiftedIndex;
    }

    @Override
    protected void dropFewItems() {

        int drop;

        drop = rand.nextInt(50);

        if(drop >= 40) {
            for(int i = 0; i < random.nextInt(3); ++i) {
                this.dropItem(Item.coal.shiftedIndex, 1);
            }
        }
        if(drop >= 30 && drop < 40) {
            for(int i = 0; i < random.nextInt(2); ++i) {
                this.dropItem(Item.redstone.shiftedIndex, 1);
            }
        }
        if(drop >= 15 && drop < 30) {
            for(int i = 0; i < random.nextInt(4); ++i) {
                this.dropItem(Item.stick.shiftedIndex, 1);
            }
        }
        if(drop >= 10 && drop < 15) {
            this.dropItem(Item.eggFried.shiftedIndex, 1);
        }
        if(drop < 5) {
            this.dropItem(Item.pickaxeSteel.shiftedIndex, 1);
        }
    }

    @Override
    public ItemStack getHeldItem() {
        return new ItemStack(Item.pickaxeSteel);
    }
}
