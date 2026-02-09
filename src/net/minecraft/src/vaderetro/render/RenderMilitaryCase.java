
package net.minecraft.src.vaderetro.render;
import net.minecraft.src.Block;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraft.src.vaderetro.block.BlockMilitaryCase;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityMilitaryCase;
import net.minecraft.src.vaderetro.model.ModelMilitaryCase;
import org.lwjgl.opengl.GL11;
public class RenderMilitaryCase extends TileEntitySpecialRenderer {
    private ModelMilitaryCase model;
    public RenderMilitaryCase() {
        this.model = new ModelMilitaryCase();
    }
    public void renderTileEntityMilitaryCaseAt(TileEntityMilitaryCase te, double x, double y, double z, float partialTick) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glRotatef(te.randomRotation, 0.0F, 1.0F, 0.0F);
        String texturePath = "/terrain/military_case.png";
        if (te.worldObj != null) {
            Block blockType = te.getBlockType();
            if (blockType instanceof BlockMilitaryCase) {
                texturePath = ((BlockMilitaryCase) blockType).getTexturePath();
            }
        }
        this.bindTextureByName(texturePath);
        float lidAngle = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTick;
        this.model.setLidAngle(-lidAngle);
        this.model.renderAll(0.0625F);
        GL11.glPopMatrix();
    }
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTick) {
        this.renderTileEntityMilitaryCaseAt((TileEntityMilitaryCase) te, x, y, z, partialTick);
    }
}
