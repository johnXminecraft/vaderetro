package net.minecraft.src.Airship;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class GuiAirship extends GuiContainer {
	private EntityAirship airship;

	public GuiAirship(InventoryPlayer inventoryplayer, EntityAirship air) {
		super(new ContainerAirship(inventoryplayer, air));
		this.airship = air;
	}

	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString("Airship Inventory", 8, 4, 4210752);
		this.fontRenderer.drawString("Arrows:", 89, 55, 4210752);
		this.fontRenderer.drawString("Fuel:", 105, 20, 4210752);
		this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float f) {
		int i = this.mc.renderEngine.getTexture("/gui/airshipgui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(i);
		int j = (this.width - this.xSize) / 2;
		int k = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);


		int fuelLevel = this.airship.getFuelScaled(32);

        if (fuelLevel > 0) {
   
            this.drawTexturedModalRect(j + 156, k + 15 + (32 - fuelLevel), 176, 32 - fuelLevel, 12, fuelLevel);
        }
	}
}