package net.minecraft.src.vaderetro.gui;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.container.ContainerMacerator;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityMacerator;
import org.lwjgl.opengl.GL11;

public class GuiMacerator extends GuiContainer {
	static final int iPulleyGuiHeight = 174;
	static final int iPulleyMachineIconWidth = 14;
	static final int iPulleyMachineIconHeight = 14;

    private TileEntityMacerator associatedTileEntityMacerator;

	public GuiMacerator(InventoryPlayer inventoryplayer, TileEntityMacerator tileEntityMacerator) {
        super(new ContainerMacerator(inventoryplayer, tileEntityMacerator));
        
        ySize = iPulleyGuiHeight;
        
        associatedTileEntityMacerator = tileEntityMacerator;
    }

    protected void drawGuiContainerBackgroundLayer(float f) {
    	
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        int texture = mc.renderEngine.getTexture("/mill/fcguipulley.png");
        mc.renderEngine.bindTexture(texture);
        
        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;
        
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
        
        
        if (associatedTileEntityMacerator.isPowered()) {
            boolean waterPowered = associatedTileEntityMacerator.isPoweredByWaterWheel();
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
