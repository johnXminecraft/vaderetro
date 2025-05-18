package net.minecraft.src.vaderetro.gui;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.vaderetro.container.ContainerCrtTvSet;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityCrtTvSet;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class GuiCrtTvSet extends GuiContainer {

    private TileEntityCrtTvSet tileEntity;
    private static final Random random = new Random();
    private String splash;
    private String fileName;
    private final int maxPicture = 20;

    public GuiCrtTvSet(InventoryPlayer inventoryPlayer, TileEntityCrtTvSet tileEntity) {
        super(new ContainerCrtTvSet(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;

        this.splash = "PLEASE STANDBY";
        try {
            ArrayList var1 = new ArrayList();
            BufferedReader var2 = new BufferedReader(new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/crt/crt_splashes.txt"), Charset.forName("UTF-8")));

            while(true) {
                this.splash = var2.readLine();
                if(this.splash == null) {
                    this.splash = (String)var1.get(random.nextInt(var1.size()));
                    break;
                }

                this.splash = this.splash.trim();
                if(this.splash.length() > 0) {
                    var1.add(this.splash);
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        fileName = "/crt/crt_active_";
        int fileNumber = random.nextInt(maxPicture - 1) + 1;
        fileName += fileNumber + ".png";
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        if(tileEntity.isPowered()) {
            this.fontRenderer.drawString(splash, 15, 101, 16777215);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        xSize = 192;
        ySize = 128;

        int var2;
        if (tileEntity.isPowered()) {
            if(random.nextInt(100) == 1) {
                var2 = this.mc.renderEngine.getTexture("/crt/crt_active_static.png");
            } else {
                var2 = this.mc.renderEngine.getTexture(fileName);
            }
        } else {
            var2 = this.mc.renderEngine.getTexture("/crt/crt_idle.png");
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var2);
        int var3 = (this.width - this.xSize) / 2;
        int var4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.ySize);
    }
}
