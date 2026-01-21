package net.minecraft.src.vaderetro._BML;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class RenderObj extends Render {
    private ModelObj model;
    private String texturePath;
    
    public RenderObj(String modelPath) {
        this(modelPath, null);
    }
    
    public RenderObj(String modelPath, String texturePath) {
        this.model = new ModelObj(modelPath);
        this.texturePath = texturePath;
    }
    
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        if (!model.isLoaded()) {
            return;
        }
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-pitch, 1.0F, 0.0F, 0.0F);
        
        if (texturePath != null && !texturePath.isEmpty()) {
            try {
                this.loadTexture(texturePath);
            } catch (Exception e) {
                System.err.println("BML: Failed to load texture: " + texturePath + " - " + e.getMessage());
            }
        }
        
        model.render(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        
        GL11.glPopMatrix();
    }
    
    public void doRenderAtPosition(double x, double y, double z, float yaw, float pitch, float scale) {
        if (!model.isLoaded()) {
            return;
        }
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-pitch, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(scale, scale, scale);
        
        if (texturePath != null && !texturePath.isEmpty()) {
            try {
                this.loadTexture(texturePath);
            } catch (Exception e) {
                System.err.println("BML: Failed to load texture: " + texturePath + " - " + e.getMessage());
            }
        }
        
        model.render(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        
        GL11.glPopMatrix();
    }
    
    public void renderInInventory(float scale) {
        if (!model.isLoaded()) {
            return;
        }
        
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        
        if (texturePath != null && !texturePath.isEmpty()) {
            try {
                this.loadTexture(texturePath);
            } catch (Exception e) {
                System.err.println("BML: Failed to load texture: " + texturePath + " - " + e.getMessage());
            }
        }
        
        model.render(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        
        GL11.glPopMatrix();
    }
    
    public ModelObj getModel() {
        return model;
    }
    
    public void setTexture(String texturePath) {
        this.texturePath = texturePath;
    }
    
    public String getTexture() {
        return texturePath;
    }
}
