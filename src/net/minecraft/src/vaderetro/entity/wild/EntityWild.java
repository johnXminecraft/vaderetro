package net.minecraft.src.vaderetro.entity.wild;

import net.minecraft.src.*;

public class EntityWild extends EntityCreature {

    protected int attackStrength = 4;

    public EntityWild(World var1) {
        super(var1);
        this.health = 10;
    }

    @Override
    protected float getBlockPathWeight(int var1, int var2, int var3) {
        return this.worldObj.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID ? 10.0F : this.worldObj.getLightBrightness(var1, var2, var3) - 0.5F;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
    }

    @Override
    public boolean getCanSpawnHere() {
        int posXInt = MathHelper.floor_double(this.posX);
        int posYInt = MathHelper.floor_double(this.boundingBox.minY);
        int posZInt = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockId(posXInt, posYInt - 1, posZInt) == Block.grass.blockID &&
                this.worldObj.getFullBlockLightValue(posXInt, posYInt, posZInt) > 4 &&
                this.worldObj.getFullBlockLightValue(posXInt, posYInt, posZInt) < 15 &&
                (
                        this.worldObj.getWorldChunkManager().getBiomeGenAt(posXInt, posZInt) == BiomeGenBase.forest ||
                                this.worldObj.getWorldChunkManager().getBiomeGenAt(posXInt, posZInt) == BiomeGenBase.rainforest ||
                                this.worldObj.getWorldChunkManager().getBiomeGenAt(posXInt, posZInt) == BiomeGenBase.seasonalForest ||
                                this.worldObj.getWorldChunkManager().getBiomeGenAt(posXInt, posZInt) == BiomeGenBase.taiga
                ) &&
                super.getCanSpawnHere();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!this.worldObj.multiplayerWorld && this.worldObj.difficultySetting == 0) {
            this.setEntityDead();
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
        return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
    }

    @Override
    protected void attackEntity(Entity var1, float var2) {
        if(this.attackTime <= 0 && var2 < 2.0F && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            var1.attackEntityFrom(this, this.attackStrength);
        }
    }

    @Override
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

    @Override
    public int getTalkInterval() {
        return 120;
    }
}
