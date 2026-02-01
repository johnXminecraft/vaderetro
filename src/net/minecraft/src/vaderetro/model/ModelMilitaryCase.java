package net.minecraft.src.vaderetro.model;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelMilitaryCase extends ModelBase
{
    public ModelRenderer boxBottom;
    public ModelRenderer boxLid;

    public ModelMilitaryCase()
    {
        this.boxBottom = new ModelRenderer(0, 0);
        this.boxBottom.addBox(-15.0F, -10.0F, -7.0F, 30, 10, 14);
        this.boxBottom.setRotationPoint(0.0F, 24.0F, 0.0F);

        this.boxLid = new ModelRenderer(0, 16);
        this.boxLid.addBox(-15.0F, -4.0F, -14.0F, 30, 4, 14);
        this.boxLid.setRotationPoint(0.0F, 14.0F, 7.0F);
    }

    public void renderAll(float scale)
    {
        this.boxBottom.render(scale);
        this.boxLid.render(scale);
    }

    public void setLidAngle(float lidAngle)
    {
        this.boxLid.rotateAngleX = lidAngle;
    }
}