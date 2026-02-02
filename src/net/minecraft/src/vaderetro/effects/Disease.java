package net.minecraft.src.vaderetro.effects;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public abstract class Disease {
    
    protected int ticksInfected = 0;
    protected int maxDurationTicks;
    protected boolean active = false;
    protected String infectionMessage;
    
    public abstract String onInfected(EntityPlayer player);
    
    public abstract void onTick(EntityPlayer player);
    
    public abstract void onFinalStage(EntityPlayer player, World world);
    
    public abstract float getRedOverlayIntensity();
    
    public float getOverlayRed() { return 0.8f; }
    public float getOverlayGreen() { return 0f; }
    public float getOverlayBlue() { return 0f; }
    
    public abstract float getBlurIntensity();
    
    public abstract float getCameraShakeIntensity();
    
    public abstract String getDiseaseId();
    
    public abstract String getDiseaseName();
    
    public void startInfection(EntityPlayer player) {
        this.active = true;
        this.ticksInfected = 0;
        this.infectionMessage = onInfected(player);
    }

    public void restoreState(int ticksInfected, int maxDurationTicks) {
        this.active = true;
        this.ticksInfected = ticksInfected;
        this.maxDurationTicks = maxDurationTicks;
        this.infectionMessage = "You are still infected.";
    }
    
    public void update(EntityPlayer player) {
        if (!active) return;

        ticksInfected++;
        onTick(player);
    }
    
    public float getProgress() {
        if (maxDurationTicks <= 0) return 0.0f;
        return (float) ticksInfected / (float) maxDurationTicks;
    }
    
    public int getTicksRemaining() {
        return Math.max(0, maxDurationTicks - ticksInfected);
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void cure() {
        this.active = false;
        this.ticksInfected = 0;
    }
    
    public String getInfectionMessage() {
        return infectionMessage;
    }
    
    public boolean shouldCauseDeath() {
        return ticksInfected >= maxDurationTicks && maxDurationTicks > 0;
    }
}