package net.minecraft.src.vaderetro.effects;

import net.minecraft.src.MathHelper;
import org.lwjgl.opengl.GL11;

public class ZombieBiteAnimation implements IInfectionAnimation {

    public static final String ID = "zombie_bite";
    private static final int DURATION_TICKS = 28;

    @Override
    public int getDurationTicks() {
        return DURATION_TICKS;
    }

    @Override
    public void applyArmTransform(float progress, float partialTick) {
        if (progress <= 0f || progress >= 1f) return;

        float t = progress;
        float easeOut = 1f - (1f - t) * (1f - t);
        float bitePull = MathHelper.sin(t * (float) Math.PI * 0.9f);
        float intenseShake = MathHelper.sin(t * (float) Math.PI * 14f) * (1f - easeOut * 0.7f);
        float secondaryShake = MathHelper.sin(t * (float) Math.PI * 22f + 0.8f) * (1f - easeOut) * 0.6f;
        float lateTwitch = MathHelper.sin((t - 0.65f) * (float) Math.PI * 3.5f) * Math.max(0f, t - 0.65f) * 0.9f;

        float pullStrength = bitePull * 1.35f + lateTwitch * 0.4f;

        GL11.glTranslatef(
            -0.22f * pullStrength + intenseShake * 0.085f + secondaryShake * 0.04f,
            0.18f * pullStrength + intenseShake * 0.06f,
            0.11f * pullStrength
        );

        GL11.glRotatef(-38f * pullStrength + intenseShake * 9f + lateTwitch * 12f, 1f, 0f, 0f);
        GL11.glRotatef(22f * pullStrength + secondaryShake * 8f + intenseShake * 5f, 0f, 0f, 1f);
        GL11.glRotatef(-15f * pullStrength + intenseShake * 6f, 0f, 1f, 0f);
        GL11.glRotatef(intenseShake * 14f + secondaryShake * 18f, 0.2f, 0.8f, 0.4f);
    }
}