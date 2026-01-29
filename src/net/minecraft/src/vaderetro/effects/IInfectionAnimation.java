package net.minecraft.src.vaderetro.effects;

public interface IInfectionAnimation {

    int getDurationTicks();

    void applyArmTransform(float progress, float partialTick);
}