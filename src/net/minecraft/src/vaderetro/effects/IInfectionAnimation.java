package net.minecraft.src.vaderetro.disease;

public interface IInfectionAnimation {

    int getDurationTicks();

    void applyArmTransform(float progress, float partialTick);
}