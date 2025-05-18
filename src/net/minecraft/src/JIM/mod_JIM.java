package net.minecraft.src.JIM;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class mod_JIM {
    private static mod_JIM instance;
    private JIMController controller;
    
    public mod_JIM() {
        instance = this;
        this.controller = new JIMController();
        System.out.println("JIM: Mod loaded directly");
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
        getInstance().controller.onTick(minecraft);
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
} 