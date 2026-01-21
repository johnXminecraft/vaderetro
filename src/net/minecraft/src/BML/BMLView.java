package net.minecraft.src.BML;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class BMLView {
    private int tickCounter = 0;
    
    public BMLView() {
        System.out.println("BML: View initialized");
    }
    
    public void update() {
        tickCounter++;
        

    }
    
    public void render(Minecraft minecraft, float partialTicks) {
        if (minecraft == null || minecraft.theWorld == null) {
            return;
        }
        

        if (minecraft.gameSettings.showDebugInfo) {
            renderDebugInfo(minecraft);
        }
    }
    
    private void renderDebugInfo(Minecraft minecraft) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
     
        
        GL11.glPopMatrix();
    }
    
    public void onWorldLoad() {
        System.out.println("BML: World loaded, clearing OBJ model cache");
        ObjModelLoader.clearCache();
    }
    
    public void onWorldUnload() {
        System.out.println("BML: World unloaded, cleaning up resources");
    }
}
