package net.minecraft.src.vaderetro.effects;

import net.minecraft.src.EntityPlayer;

public final class InfectionAnimationHelper {

	public static void applyArmTransform(EntityPlayer player, float partialTick) {
		if (player == null || !player.isInfectionAnimationActive()) return;

		String id = player.getInfectionAnimationId();
		IInfectionAnimation anim = InfectionAnimationRegistry.get(id);
		if (anim == null) return;

		float progress = player.getInfectionAnimationProgress(partialTick);
		if (progress <= 0f || progress >= 1f) return;

		anim.applyArmTransform(progress, partialTick);
	}
}
