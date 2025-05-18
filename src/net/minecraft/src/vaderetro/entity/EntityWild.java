package net.minecraft.src.vaderetro.entity;

import net.minecraft.src.*;

public class EntityWild extends EntityCreature {

    protected int attackStrength = 4;

    public EntityWild(World var1) {
        super(var1);
        this.health = 10;
    }

    protected float getBlockPathWeight(int var1, int var2, int var3) {
        return this.worldObj.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID ? 10.0F : this.worldObj.getLightBrightness(var1, var2, var3) - 0.5F;
    }

    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
    }

    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
    }

    public boolean getCanSpawnHere() {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 4 && super.getCanSpawnHere();
    }

    public void onUpdate() {
        super.onUpdate();
        if(!this.worldObj.multiplayerWorld && this.worldObj.difficultySetting == 0) {
            this.setEntityDead();
        }
    }

    protected Entity findPlayerToAttack() {
        EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
        return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
    }

    protected void attackEntity(Entity var1, float var2) {
        if(this.attackTime <= 0 && var2 < 2.0F && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            var1.attackEntityFrom(this, this.attackStrength);
        }
    }

    public boolean attackEntityFrom(Entity var1, int var2) {
        if(super.attackEntityFrom(var1, var2)) {
            if(this.riddenByEntity != var1 && this.ridingEntity != var1) {
                if(var1 != this) {
                    this.playerToAttack = var1;
                }
                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public int getTalkInterval() {
        return 120;
    }
}
