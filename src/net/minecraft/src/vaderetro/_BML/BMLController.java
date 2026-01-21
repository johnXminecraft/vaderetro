package net.minecraft.src.vaderetro._BML;

import net.minecraft.client.Minecraft;

public class BMLController {
    private BMLView view;
    private boolean visible = true;
    
    public BMLController() {
        this.view = new BMLView();
    }
    
    public void onTick(Minecraft minecraft) {
        if (view != null) {
            view.update();
        }
    }
    
    public void renderInterface(Minecraft minecraft, float partialTicks) {
        if (visible && view != null) {
            view.render(minecraft, partialTicks);
        }
    }
    
    public void toggleVisibility() {
        this.visible = !this.visible;
        System.out.println("BML: Interface visibility toggled to " + visible);
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public BMLView getView() {
        return view;
    }
    
    public void generateUVMapForModel(String modelPath) {
        UVMapGenerator.generateUVMapForModel(modelPath);
    }
    
    public void generateUVMapsForAllModels() {
        UVMapGenerator.generateUVMapForAllModels();
    }
}
