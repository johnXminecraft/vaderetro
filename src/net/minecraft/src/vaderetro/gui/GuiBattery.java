package net.minecraft.src.vaderetro.gui;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.vaderetro.container.ContainerBattery;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityBattery;
import org.lwjgl.opengl.GL11;

public class GuiBattery extends GuiContainer {
    private TileEntityBattery tileEntity;

    public GuiBattery(InventoryPlayer inventoryPlayer, TileEntityBattery tileEntity) {
        super(new ContainerBattery(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Battery", 60, 6, 4210752);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
        this.fontRenderer.drawString("VRE: " + this.tileEntity.vreStored, 100, 20, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        int textureId = this.mc.renderEngine.getTexture("/VRE/battery.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(textureId);
        
        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;
        
        this.drawTexturedModalRect(guiX, guiY, 0, 0, this.xSize, this.ySize);
        
        int vreScaled = this.tileEntity.getVreScaled(24);
        
        int barX = guiX + 93;
        int barY = guiY + 38;
        int barHeight = 9;
        
        int redColor = 0xFFFF0000;
        this.drawRect(barX, barY, barX + vreScaled, barY + barHeight, redColor);
    }
}
