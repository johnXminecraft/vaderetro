package net.minecraft.src.vaderetro.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderEngine;

public final class RadiationSicknessSkinEffect {

    private RadiationSicknessSkinEffect() {
    }

    public static void applyToPlayer(EntityPlayer player) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.renderEngine == null || player == null) {
            return;
        }
        if (mc.thePlayer != player) {
            return;
        }
        String skinUrl = mc.thePlayer.skinUrl;
        String fallback = mc.thePlayer.getEntityTexture();
        RenderEngine engine = mc.renderEngine;
        engine.applyRadiationToPlayerSkin(skinUrl, fallback);
    }

    public static void clearFromLocalPlayer() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.renderEngine == null || mc.thePlayer == null) {
            return;
        }
        String skinUrl = mc.thePlayer.skinUrl;
        String fallback = mc.thePlayer.getEntityTexture();
        RenderEngine engine = mc.renderEngine;
        engine.clearRadiationFromPlayerSkin(skinUrl, fallback);
    }
}
