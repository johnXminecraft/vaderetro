package net.minecraft.src.vaderetro.gui;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.vaderetro.container.ContainerCrtTvSet;
import net.minecraft.src.vaderetro.tileentity.TileEntityCrtTvSet;
import org.lwjgl.opengl.GL11;

public class GuiCrtTvSet extends GuiContainer {

    private TileEntityCrtTvSet tileEntity;

    public GuiCrtTvSet(InventoryPlayer inventoryPlayer, TileEntityCrtTvSet tileEntity) {
        super(new ContainerCrtTvSet(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        xSize = 192;
        ySize = 128;

        int var2;
        if (tileEntity.isPowered()) {
            var2 = this.mc.renderEngine.getTexture("/gui/crt_active.png");
        } else {
            var2 = this.mc.renderEngine.getTexture("/gui/crt_idle.png");
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var2);
        int var3 = (this.width - this.xSize) / 2;
        int var4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.ySize);
    }
}
