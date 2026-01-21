package net.minecraft.src.vaderetro.__config;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.vaderetro._BML.BMLController;

public class mod_BML {
    private static mod_BML instance;
    private BMLController controller;
    private boolean initialized = false;
    
    public mod_BML() {
        instance = this;
        this.controller = new BMLController();
    }
    
    public static mod_BML getInstance() {
        if (instance == null) {
            instance = new mod_BML();
        }
        return instance;
    }

    public static void initialize() {
        getInstance();
    }
    
    public static void onTickInGame(Minecraft minecraft) {
        mod_BML self = getInstance();
        if (!self.initialized) {
            self.initializeMod();
            self.initialized = true;
        }
        self.controller.onTick(minecraft);
    }
    
    public boolean onTickInGUI(float partialTicks, Minecraft minecraft, GuiScreen screen) {
        controller.renderInterface(minecraft, partialTicks);
        return true;
    }
    
    public boolean handleKeyPress(int keyCode) {
        return false;
    }

    private void initializeMod() {
        System.out.println("BML: Bob Model Loader initialized");
        System.out.println("BML: OBJ model loading system ready");

    }
    
    public BMLController getController() {
        return controller;
    }
}
