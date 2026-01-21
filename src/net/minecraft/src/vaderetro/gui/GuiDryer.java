package net.minecraft.src.vaderetro.gui;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.vaderetro.container.ContainerDryer;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityDryer;
import org.lwjgl.opengl.GL11;

public class GuiDryer extends GuiContainer {

    private final TileEntityDryer tileEntity;

    public GuiDryer(InventoryPlayer inventoryPlayer, TileEntityDryer tileEntity) {
        super(new ContainerDryer(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        int var2 = this.mc.renderEngine.getTexture("/gui/dryer.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var2);
        int var3 = (this.width - this.xSize) / 2;
        int var4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.ySize);
        int var5;
        var5 = this.tileEntity.getCookProgressScaled(24);
        this.drawTexturedModalRect(var3 + 79, var4 + 34, 176, 14, var5 + 1, 16);
    }
}