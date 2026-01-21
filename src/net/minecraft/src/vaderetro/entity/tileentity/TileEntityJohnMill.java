package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;

public class TileEntityJohnMill extends TileEntity {
    public float rotation;
    public float prevRotation;
    public float rotationSpeed = 1.0F;
    
    public int[] bladeColors = new int[4];
    public int currentBladeColoringIndex = 0;
    public int windMillCurrentDamage = 0;
    public int windMillTimeSinceHit = 0;
    public int windMillRockDirection = 1;
    public float windMillCurrentRotationSpeed = 0.0f;
    public boolean providingPower = false;
    public int overpowerTimer = -1;

    public TileEntityJohnMill() {
        for (int i = 0; i < 4; ++i) {
            this.bladeColors[i] = 0;
        }
    }

    public void updateEntity() {
        this.prevRotation = this.rotation;
        
        this.windMillCurrentRotationSpeed = computeRotationSpeed();
        this.rotation += this.windMillCurrentRotationSpeed;
        
        if (this.windMillTimeSinceHit > 0) {
            --this.windMillTimeSinceHit;
        }
        if (this.windMillCurrentDamage > 0) {
            --this.windMillCurrentDamage;
        }
        
        while(this.rotation >= 360.0F) {
            this.rotation -= 360.0F;
        }
        while(this.rotation < 0.0F) {
            this.rotation += 360.0F;
        }
    }
    
    private float computeRotationSpeed() {
        float rotationAmount = 0.0f;
        
        if (this.worldObj.isDaytime()) {
            rotationAmount = -0.125f;
            this.overpowerTimer = -1;
        } else {
            rotationAmount = -0.0675f;
            this.overpowerTimer = -1;
        }
        
        return rotationAmount;
    }
    
    public float getBrightness(float partialTicks) {
        return this.worldObj != null ? this.worldObj.getLightBrightness(this.xCoord, this.yCoord, this.zCoord) : 1.0f;
    }
    
    public int getBladeColor(int bladeIndex) {
        if (bladeIndex >= 0 && bladeIndex < 4) {
            return this.bladeColors[bladeIndex];
        }
        return 0; 
    }
    
    public void setBladeColor(int bladeIndex, int color) {
        if (bladeIndex >= 0 && bladeIndex < 4) {
            this.bladeColors[bladeIndex] = color;
        }
    }
    
    public void cycleBladeColoringIndex() {
        this.currentBladeColoringIndex++;
        if (this.currentBladeColoringIndex >= 4) {
            this.currentBladeColoringIndex = 0;
        }
    }
    
    public int getCurrentBladeColoringIndex() {
        return this.currentBladeColoringIndex;
    }

    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        this.rotation = var1.getFloat("Rot");
        this.prevRotation = var1.getFloat("PrevRot");
        
        this.bladeColors[0] = var1.getInteger("iBladeColors0");
        this.bladeColors[1] = var1.getInteger("iBladeColors1");
        this.bladeColors[2] = var1.getInteger("iBladeColors2");
        this.bladeColors[3] = var1.getInteger("iBladeColors3");
        this.currentBladeColoringIndex = var1.getInteger("iCurrentBladeColoringIndex");
        this.windMillCurrentDamage = var1.getInteger("iWindMillCurrentDamage");
        this.windMillTimeSinceHit = var1.getInteger("iWindMillTimeSinceHit");
        this.windMillRockDirection = var1.getInteger("iWindMillRockDirection");
        this.providingPower = var1.getBoolean("bProvidingPower");
        this.overpowerTimer = var1.getInteger("iOverpowerTimer");
    }

    public void writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        var1.setFloat("Rot", this.rotation);
        var1.setFloat("PrevRot", this.prevRotation);
        
        var1.setInteger("iBladeColors0", this.bladeColors[0]);
        var1.setInteger("iBladeColors1", this.bladeColors[1]);
        var1.setInteger("iBladeColors2", this.bladeColors[2]);
        var1.setInteger("iBladeColors3", this.bladeColors[3]);
        var1.setInteger("iCurrentBladeColoringIndex", this.currentBladeColoringIndex);
        var1.setInteger("iWindMillCurrentDamage", this.windMillCurrentDamage);
        var1.setInteger("iWindMillTimeSinceHit", this.windMillTimeSinceHit);
        var1.setInteger("iWindMillRockDirection", this.windMillRockDirection);
        var1.setBoolean("bProvidingPower", this.providingPower);
        var1.setInteger("iOverpowerTimer", this.overpowerTimer);
    }
}