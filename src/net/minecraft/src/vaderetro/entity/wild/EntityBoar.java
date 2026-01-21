package net.minecraft.src.vaderetro.entity.wild;

import net.minecraft.src.*;

import java.util.Random;

public class EntityBoar extends EntityWild {

    private static final Random random = new Random();

    public EntityBoar(World var1) {
        super(var1);
        this.texture = "/mob/boar.png";
        this.setSize(0.9F, 0.9F);
        this.moveSpeed = 3;
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    @Override
    protected String getLivingSound() {
        return "mob.pig";
    }

    @Override
    protected String getHurtSound() {
        return "mob.pig";
    }

    @Override
    protected String getDeathSound() {
        return "mob.pigdeath";
    }

    @Override
    protected int getDropItemId() {
        if(random.nextInt(20) <= 5) {
            return Item.leather.shiftedIndex;
        }
        return this.fire > 0 ? Item.porkCooked.shiftedIndex : Item.porkRaw.shiftedIndex;
    }

    @Override
    protected void dropFewItems() {
        for(int i = 0; i < random.nextInt(3); ++i) {
            if(this.fire > 1) {
                this.dropItem(Item.porkCooked.shiftedIndex, 1);
            } else {
                this.dropItem(Item.porkRaw.shiftedIndex, 1);
            }
        }
        for(int i = 0; i < random.nextInt(3); ++i) {
            this.dropItem(Item.leather.shiftedIndex, 1);
        }
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt var1) {
        if(!this.worldObj.multiplayerWorld) {
            EntityPigZombie var2 = new EntityPigZombie(this.worldObj);
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.entityJoinedWorld(var2);
            this.setEntityDead();
        }
    }
}
