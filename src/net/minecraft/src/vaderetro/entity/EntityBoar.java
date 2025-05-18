package net.minecraft.src.vaderetro.entity;

import net.minecraft.src.*;

public class EntityBoar extends EntityWild {

    public EntityBoar(World var1) {
        super(var1);
        this.texture = "/mob/boar.png";
        this.setSize(0.9F, 0.9F);
        this.moveSpeed = 3;
    }

    protected void entityInit() {
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    protected String getLivingSound() {
        return "mob.pig";
    }

    protected String getHurtSound() {
        return "mob.pig";
    }

    protected String getDeathSound() {
        return "mob.pigdeath";
    }

    protected int getDropItemId() {
        return this.fire > 0 ? Item.porkCooked.shiftedIndex : Item.porkRaw.shiftedIndex;
    }

    public void onStruckByLightning(EntityLightningBolt var1) {
        if(!this.worldObj.multiplayerWorld) {
            EntityPigZombie var2 = new EntityPigZombie(this.worldObj);
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.entityJoinedWorld(var2);
            this.setEntityDead();
        }
    }
}
