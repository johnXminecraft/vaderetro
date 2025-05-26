package net.minecraft.src.vaderetro.gui;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.vaderetro.container.ContainerKeroseneLamp;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityKeroseneLamp;
import org.lwjgl.opengl.GL11;

public class GuiKeroseneLamp extends GuiContainer {

    private TileEntityKeroseneLamp tileEntity;

    public GuiKeroseneLamp(InventoryPlayer inventoryPlayer, TileEntityKeroseneLamp tileEntity) {
        super(new ContainerKeroseneLamp(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Kerosene Lamp", 8, 6, 1250067);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 1250067);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        int var2 = this.mc.renderEngine.getTexture("/gui/kerosene_lamp.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var2);
        int var3 = (this.width - this.xSize) / 2;
        int var4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.ySize);
        int var5;
        if(this.tileEntity.isBurning()) {
            var5 = this.tileEntity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var3 + 56, var4 + 36 + 12 - var5, 176, 12 - var5, 14, var5 + 2);
        }
        var5 = this.tileEntity.getCookProgressScaled(24);
        this.drawTexturedModalRect(var3 + 79, var4 + 34, 176, 14, var5 + 1, 16);
    }
}
