package net.minecraft.src.BML;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class ModelObj extends ModelBase {
    private ObjModelLoader.ObjModel objModel;
    private String modelPath;
    private float scale;
    
    public ModelObj(String modelPath) {
        this(modelPath, 1.0f);
    }
    
    public ModelObj(String modelPath, float scale) {
        this.modelPath = modelPath;
        this.scale = scale;
        this.objModel = ObjModelLoader.loadModel(modelPath);
        
        if (objModel == null) {
            System.err.println("BML: Failed to load OBJ model: " + modelPath);
        }
    }
    
    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        if (objModel == null) {
            System.err.println("BML: OBJ model not loaded: " + modelPath);
            return;
        }
        
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        
        objModel.render(1.0f);
        
        GL11.glPopMatrix();
    }
    
    public void renderWithTexture(String texturePath) {
        if (objModel == null) {
            System.err.println("BML: OBJ model not loaded: " + modelPath);
            return;
        }
        
        if (texturePath != null && !texturePath.isEmpty()) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);

        }
        
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        
        objModel.render(1.0f);
        
        GL11.glPopMatrix();
    }
    
    public boolean isLoaded() {
        return objModel != null;
    }
    
    public String getModelPath() {
        return modelPath;
    }
    
    public void setScale(float scale) {
        this.scale = scale;
    }
    
    public float getScale() {
        return scale;
    }
    
    public ObjModelLoader.ObjModel getObjModel() {
        return objModel;
    }
}
