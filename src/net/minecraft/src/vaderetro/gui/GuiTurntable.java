package net.minecraft.src.vaderetro.gui;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.vaderetro.container.ContainerTurntable;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityTurntable;
import org.lwjgl.opengl.GL11;

public class GuiTurntable extends GuiContainer {

    static final int iPulleyGuiHeight = 174;
    static final int iPulleyMachineIconWidth = 14;
    static final int iPulleyMachineIconHeight = 14;
    private TileEntityTurntable tileEntity;

    public GuiTurntable(InventoryPlayer inventoryplayer, TileEntityTurntable tileEntity) {
        super(new ContainerTurntable(inventoryplayer, tileEntity));

        ySize = iPulleyGuiHeight;

        this.tileEntity = tileEntity;
    }

    protected void drawGuiContainerBackgroundLayer(float f) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int texture = mc.renderEngine.getTexture("/mill/fcguipulley.png");
        mc.renderEngine.bindTexture(texture);

        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;

        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);

        if (tileEntity.isPowered()) {
            boolean waterPowered = tileEntity.isPoweredByWaterWheel();
            int iconHeight = waterPowered ? iPulleyMachineIconHeight / 2 : iPulleyMachineIconHeight;
            drawTexturedModalRect(xPos + 80,
                    yPos + 18,
                    176,
                    0,
                    iPulleyMachineIconWidth,
                    iconHeight);
        }
    }
}
