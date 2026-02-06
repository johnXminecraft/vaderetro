package net.minecraft.src.vaderetro.__config;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.vaderetro._JIM.JIMController;

public class mod_JIM {
    private static mod_JIM instance;
    private JIMController controller;
    private boolean soundInitDone = false;

    public mod_JIM() {
        instance = this;
        this.controller = new JIMController();
    }

    public static mod_JIM getInstance() {
        if (instance == null) {
            instance = new mod_JIM();
        }
        return instance;
    }

    public static void initialize() {
        getInstance();
    }

    public static void onTickInGame(Minecraft minecraft) {
        mod_JIM self = getInstance();
        self.initSoundsOnce(minecraft);
        self.controller.onTick(minecraft);
    }

    public boolean onTickInGUI(float partialTicks, Minecraft minecraft, GuiScreen screen) {
        controller.renderInterface(minecraft, partialTicks);
        return true;
    }

    public boolean handleKeyPress(int keyCode) {
        if (keyCode == org.lwjgl.input.Keyboard.KEY_R) {
            controller.toggleVisibility();
            return true;
        }
        return false;
    }

    private void addSoundSafe(Minecraft minecraft, String name, java.io.File file) {
        if (minecraft == null || minecraft.sndManager == null || name == null || file == null) {
            return;
        }
        try {
            minecraft.sndManager.addSound(name, file);
        } catch (Throwable t) {
            System.err.println("JIM: Failed to add sound " + name + ": " + t.getMessage());
            t.printStackTrace();
        }
    }

    private void initSoundsOnce(Minecraft minecraft) {
        if (this.soundInitDone || minecraft == null || minecraft.sndManager == null) {
            return;
        }

        java.io.File cwd = new java.io.File(".");

        String[] reloadCandidates = new String[] {
                "game/resources/newsound/weaponmod/reload.ogg",
                "minecraft/game/resources/newsound/weaponmod/reload.ogg",
                "./minecraft/minecraft/game/resources/newsound/weaponmod/reload.ogg",
                "../game/resources/newsound/weaponmod/reload.ogg",
                "resources/newsound/weaponmod/reload.ogg"
        };

        java.io.File reloadFile = null;
        for (String rel : reloadCandidates) {
            java.io.File f = new java.io.File(rel);
            if (f.exists()) { reloadFile = f; break; }
        }

        if (reloadFile != null) {
            addSoundSafe(minecraft, "random/reload.ogg", reloadFile);
            addSoundSafe(minecraft, "weaponmod/reload.ogg", reloadFile);
        }

        // Register AR15 sounds
        String[] ar15ShootCandidates = new String[] {
                "game/resources/newsound/weaponmod/ar15shoot.ogg",
                "minecraft/game/resources/newsound/weaponmod/ar15shoot.ogg",
                "./minecraft/minecraft/game/resources/newsound/weaponmod/ar15shoot.ogg",
                "../game/resources/newsound/weaponmod/ar15shoot.ogg",
                "resources/newsound/weaponmod/ar15shoot.ogg"
        };

        java.io.File ar15ShootFile = null;
        for (String rel : ar15ShootCandidates) {
            java.io.File f = new java.io.File(rel);
            if (f.exists()) { ar15ShootFile = f; break; }
        }

        if (ar15ShootFile != null) {
            addSoundSafe(minecraft, "weaponmod/ar15shoot.ogg", ar15ShootFile);
        }

        String[] ar15ReloadCandidates = new String[] {
                "game/resources/newsound/weaponmod/ar15reload.ogg",
                "minecraft/game/resources/newsound/weaponmod/ar15reload.ogg",
                "./minecraft/minecraft/game/resources/newsound/weaponmod/ar15reload.ogg",
                "../game/resources/newsound/weaponmod/ar15reload.ogg",
                "resources/newsound/weaponmod/ar15reload.ogg"
        };

        java.io.File ar15ReloadFile = null;
        for (String rel : ar15ReloadCandidates) {
            java.io.File f = new java.io.File(rel);
            if (f.exists()) { ar15ReloadFile = f; break; }
        }

        if (ar15ReloadFile != null) {
            addSoundSafe(minecraft, "weaponmod/ar15reload.ogg", ar15ReloadFile);
        }

        String[] ar15HndCandidates = new String[] {
                "game/resources/newsound/weaponmod/ar15hnd.ogg",
                "minecraft/game/resources/newsound/weaponmod/ar15hnd.ogg",
                "./minecraft/minecraft/game/resources/newsound/weaponmod/ar15hnd.ogg",
                "../game/resources/newsound/weaponmod/ar15hnd.ogg",
                "resources/newsound/weaponmod/ar15hnd.ogg"
        };

        java.io.File ar15HndFile = null;
        for (String rel : ar15HndCandidates) {
            java.io.File f = new java.io.File(rel);
            if (f.exists()) { ar15HndFile = f; break; }
        }

        if (ar15HndFile != null) {
            addSoundSafe(minecraft, "weaponmod/ar15hnd.ogg", ar15HndFile);
        }

        // Register bullet hit sound
        String[] bullethitCandidates = new String[] {
                "game/resources/newsound/weaponmod/bullethit.ogg",
                "minecraft/game/resources/newsound/weaponmod/bullethit.ogg",
                "./minecraft/minecraft/game/resources/newsound/weaponmod/bullethit.ogg",
                "../game/resources/newsound/weaponmod/bullethit.ogg",
                "resources/newsound/weaponmod/bullethit.ogg"
        };

        java.io.File bullethitFile = null;
        for (String rel : bullethitCandidates) {
            java.io.File f = new java.io.File(rel);
            if (f.exists()) { bullethitFile = f; break; }
        }

        if (bullethitFile != null) {
            addSoundSafe(minecraft, "weaponmod/bullethit.ogg", bullethitFile);
        }

        // Register nuclear explosion sound
        String[] nukeCandidates = new String[] {
                "game/resources/newsound/bomb/nuclearExplosion.ogg",
                "minecraft/game/resources/newsound/bomb/nuclearExplosion.ogg",
                "./minecraft/minecraft/game/resources/newsound/bomb/nuclearExplosion.ogg",
                "../game/resources/newsound/bomb/nuclearExplosion.ogg",
                "resources/newsound/bomb/nuclearExplosion.ogg"
        };

        java.io.File nukeFile = null;
        for (String rel : nukeCandidates) {
            java.io.File f = new java.io.File(rel);
            if (f.exists()) { nukeFile = f; break; }
        }

        if (nukeFile != null) {
            addSoundSafe(minecraft, "bomb/nuclearExplosion.ogg", nukeFile);
        }

        String[] geigerCandidates = new String[] {
                "game/resources/newsound/bomb/geiger.ogg",
                "minecraft/game/resources/newsound/bomb/geiger.ogg",
                "./minecraft/minecraft/game/resources/newsound/bomb/geiger.ogg",
                "../game/resources/newsound/bomb/geiger.ogg",
                "resources/newsound/bomb/geiger.ogg"
        };

        java.io.File geigerFile = null;
        for (String rel : geigerCandidates) {
            java.io.File f = new java.io.File(rel);
            if (f.exists()) { geigerFile = f; break; }
        }

        if (geigerFile != null) {
            addSoundSafe(minecraft, "bomb/geiger.ogg", geigerFile);
        }

        String[] windCandidates = new String[] {
                "game/resources/newsound/bomb/wind.ogg",
                "minecraft/game/resources/newsound/bomb/wind.ogg",
                "./minecraft/minecraft/game/resources/newsound/bomb/wind.ogg",
                "../game/resources/newsound/bomb/wind.ogg",
                "resources/newsound/bomb/wind.ogg"
        };

        java.io.File windFile = null;
        for (String rel : windCandidates) {
            java.io.File f = new java.io.File(rel);
            if (f.exists()) { windFile = f; break; }
        }

        if (windFile != null) {
            addSoundSafe(minecraft, "bomb/wind.ogg", windFile);
        }

        this.soundInitDone = true;
    }
} 