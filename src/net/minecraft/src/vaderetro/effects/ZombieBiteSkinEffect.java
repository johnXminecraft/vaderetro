package net.minecraft.src.vaderetro.disease;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderEngine;

public final class ZombieBiteSkinEffect {

    private ZombieBiteSkinEffect() {
    }


    public static void applyToPlayerHand(EntityPlayer player) {
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
        engine.applyZombieBiteToPlayerSkin(skinUrl, fallback);
    }


    public static void clearFromPlayerHand(EntityPlayer player) {
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
        engine.clearZombieBiteFromPlayerSkin(skinUrl, fallback);
    }


    public static void clearFromLocalPlayer() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.renderEngine == null || mc.thePlayer == null) {
            return;
        }

        String skinUrl = mc.thePlayer.skinUrl;
        String fallback = mc.thePlayer.getEntityTexture();
        RenderEngine engine = mc.renderEngine;
        engine.clearZombieBiteFromPlayerSkin(skinUrl, fallback);
    }
}

