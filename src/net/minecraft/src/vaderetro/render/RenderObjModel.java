package net.minecraft.src.vaderetro.render;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import net.minecraft.src.vaderetro._BML.RenderObj;

public class RenderObjModel extends Render {
    private RenderObj renderObj;
    
    public RenderObjModel() {
        this.shadowSize = 0.5F;
    }
    
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        if (!(entity instanceof net.minecraft.src.vaderetro.entity.EntityObjModel)) {
            return;
        }
        
        net.minecraft.src.vaderetro.entity.EntityObjModel objEntity = (net.minecraft.src.vaderetro.entity.EntityObjModel) entity;
        
        if (renderObj == null || !renderObj.getModel().getModelPath().equals(objEntity.getModelPath())) {
            renderObj = new RenderObj(objEntity.getModelPath(), objEntity.getTexturePath());
        }
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-pitch, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(objEntity.getScale(), objEntity.getScale(), objEntity.getScale());
        
        if (objEntity.getTexturePath() != null && !objEntity.getTexturePath().isEmpty()) {
            try {
                this.loadTexture(objEntity.getTexturePath());
            } catch (Exception e) {
                System.err.println("BML: Failed to load texture: " + objEntity.getTexturePath() + " - " + e.getMessage());
            }
        }
        float scrollSpeedU = 0.02f;
        float scrollSpeedV = 0.01f;
        float u = (entity.ticksExisted * scrollSpeedU) % 1.0f;
        float v = (entity.ticksExisted * scrollSpeedV) % 1.0f;
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        GL11.glTranslatef(u, v, 0.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        renderObj.getModel().render(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);

        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        GL11.glPopMatrix();
    }
    
    public void renderObjModel(net.minecraft.src.vaderetro.entity.EntityObjModel entity, double x, double y, double z, float yaw, float pitch) {
        this.doRender(entity, x, y, z, yaw, pitch);
    }
}