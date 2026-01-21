package net.minecraft.src.JPM;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GameSettings;

public class mod_JPM {
    private static boolean initialized = false;
    
    public static void initialize() {
        if (!initialized) {
            System.out.println("John Photo Mod (JPM) initialized");
            initialized = true;
        }
    }
    
    public static void onTickInGame(Minecraft mc) {
    }
    
    public static void onWorldLoad(Minecraft mc) {
    }
    
    public static void onWorldUnload(Minecraft mc) {
    }
}
