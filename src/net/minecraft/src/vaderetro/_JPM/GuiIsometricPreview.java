package net.minecraft.src.vaderetro._JPM;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiIsometricPreview extends GuiScreen {
    private File tempRawFile;
    private BufferedImage previewImage;
    private int fullWidth, fullHeight;
    private int glTextureId = -1;
    private String status = "Preview Mode";
    private DecimalFormat decimalFormat = new DecimalFormat("0000");

    public GuiIsometricPreview(Minecraft mc, File tempFile, BufferedImage preview, int w, int h) {
        this.mc = mc;
        this.tempRawFile = tempFile;
        this.previewImage = preview;
        this.fullWidth = w;
        this.fullHeight = h;
    }

    public void initGui() {
        this.controlList.add(new GuiButton(1, this.width / 2 - 155, this.height - 30, 150, 20, "Save as PNG"));
        this.controlList.add(new GuiButton(2, this.width / 2 + 5, this.height - 30, 150, 20, "Cancel & Delete"));
        
        if (glTextureId == -1) {
            glTextureId = this.mc.renderEngine.allocateAndSetupTexture(previewImage);
        }
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 2) {
            if (tempRawFile.exists()) tempRawFile.delete();
            this.mc.displayGuiScreen(null);
        }
        if (button.id == 1) {
            savePng();
        }
    }

    private void savePng() {
        this.status = "Saving PNG... (Don't close)";
        this.drawScreen(0, 0, 0); 
        
        Thread saver = new Thread(new Runnable() {
            public void run() {
                try {
                    File outFile = getOutputFile();
                    convertRawToPng(tempRawFile, outFile, fullWidth, fullHeight);
                    
                    if (tempRawFile.exists()) tempRawFile.delete();
                    status = "Saved: " + outFile.getName();
                } catch (Exception e) {
                    e.printStackTrace();
                    status = "Error: " + e.getMessage();
                }
            }
        });
        saver.start();
        ((GuiButton)this.controlList.get(0)).enabled = false;
    }

    private void convertRawToPng(File rawIn, File pngOut, int width, int height) throws IOException {
        FileInputStream fis = new FileInputStream(rawIn);
        
        try {
            BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int[] data = ((java.awt.image.DataBufferInt) finalImage.getRaster().getDataBuffer()).getData();
            
            byte[] rowBuffer = new byte[width * 3];
            
            for (int y = 0; y < height; y++) {
                int readNeeded = width * 3;
                int offset = 0;
                while (offset < readNeeded) {
                    int r = fis.read(rowBuffer, offset, readNeeded - offset);
                    if (r == -1) break;
                    offset += r;
                }
                
                int targetRow = y; 
                int baseIndex = targetRow * width;

                for (int x = 0; x < width; x++) {
                    int r = rowBuffer[x*3] & 0xFF;
                    int g = rowBuffer[x*3+1] & 0xFF;
                    int b = rowBuffer[x*3+2] & 0xFF;
                    data[baseIndex + x] = (r << 16) | (g << 8) | b;
                }
            }
            
            ImageIO.write(finalImage, "png", pngOut);
            
        } catch (OutOfMemoryError e) {
            throw new IOException("Map too big for PNG RAM. Try smaller scale.");
        } finally {
            fis.close();
        }
    }

    private File getOutputFile() {
        File file = null;
        int scrNumber = 0;
        do {
            File home = new File(System.getProperty("user.home", "."));
            file = new File(home, "mc_map_" + this.decimalFormat.format(scrNumber++) + ".png");
        } while(file.exists());
        return file.getAbsoluteFile();
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.glTextureId);
        
        int drawW = Math.min(this.width - 40, fullWidth);
        int drawH = (int)((double)drawW / fullWidth * fullHeight);
        if (drawH > this.height - 50) {
            drawH = this.height - 50;
            drawW = (int)((double)drawH / fullHeight * fullWidth);
        }
        
        int x = (this.width - drawW) / 2;
        int y = (this.height - 50 - drawH) / 2 + 10;
        
        drawScaledCustomSizeModalRect(x, y, 0, 0, drawW, drawH, drawW, drawH);

        drawCenteredString(this.fontRenderer, this.status, this.width / 2, y - 12, 0xFFFFFF);
        drawCenteredString(this.fontRenderer, "Size: " + fullWidth + "x" + fullHeight, this.width / 2, y + drawH + 5, 0xAAAAAA);
        
        super.drawScreen(i, j, f);
    }
    
    public void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height) {
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV((double)(x + 0), (double)(y + height), 0.0D, 0.0D, 1.0D);
        t.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, 1.0D, 1.0D);
        t.addVertexWithUV((double)(x + width), (double)(y + 0), 0.0D, 1.0D, 0.0D);
        t.addVertexWithUV((double)(x + 0), (double)(y + 0), 0.0D, 0.0D, 0.0D);
        t.draw();
    }
    
    public void onGuiClosed() {
        if (glTextureId != -1) {
            GL11.glDeleteTextures(glTextureId);
        }
    }
}