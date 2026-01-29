package net.minecraft.src.vaderetro.effects;

import java.util.HashMap;
import java.util.Map;

public final class InfectionAnimationRegistry {

	private static final Map<String, IInfectionAnimation> REGISTRY = new HashMap<String, IInfectionAnimation>();

	static {
		register("zombie_bite", new ZombieBiteAnimation());
	}

	public static void register(String id, IInfectionAnimation animation) {
		REGISTRY.put(id, animation);
	}

	public static IInfectionAnimation get(String id) {
		return REGISTRY.get(id);
	}

	public static int getDurationTicks(String id) {
		IInfectionAnimation a = REGISTRY.get(id);
		return a == null ? 0 : a.getDurationTicks();
	}
}
