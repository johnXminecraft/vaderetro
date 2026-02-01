package net.minecraft.src.vaderetro._JPM;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Frustrum;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.World;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class IsometricScreenshotRenderer {
    private IProgressUpdate progressUpdate;
    private Minecraft mc;
    private World worldObj;
    private RenderGlobal renderGlobal;
    private int width;
    private int length;
    private int height = 256;
    private float maxCloudHeight = 108.0F;
    private ByteBuffer byteBuffer;
    private FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);

    public IsometricScreenshotRenderer(Minecraft m) {
        this.progressUpdate = m.loadingScreen;
        this.mc = m;
        this.worldObj = this.mc.theWorld;
        this.renderGlobal = this.mc.renderGlobal;
        this.width = (64 << (3 - this.mc.gameSettings.renderDistance)) + 16;
        if(this.width > 416) this.width = 416;
        this.length = this.width;
    }

    public void doRender() {
        final int scale = this.mc.gameSettings.isomScale;
        this.progressUpdate.func_594_b("Rendering Preview...");
        this.progressUpdate.displayLoadingString("Please wait");
        this.progressUpdate.setLoadingProgress(0);
        
        RenderGlobal.isTakingIsometricScreenshot = true;
        
        double posX = this.mc.renderViewEntity.lastTickPosX;
        double posZ = this.mc.renderViewEntity.lastTickPosZ;
        posX -= (MathHelper.floor_double(posX) >> 4) * 16 + 8;
        posZ -= (MathHelper.floor_double(posZ) >> 4) * 16 + 8;
        if(posX < 0) { posX += 16; if(posX > 8) posX -= 8; }
        if(posZ < 0) { posZ += 16; if(posZ > 8) posZ -= 8; }

        File tempFile = new File(this.mc.getMinecraftDir(), "temp_iso_data.raw");
        BufferedImage previewImage = null;

        try {
            int fullWidth = (this.width * scale) + (this.length * scale);
            int fullHeight = (this.height * scale) + fullWidth / 2;
            int dWidth = this.mc.displayWidth;
            int dHeight = this.mc.displayHeight;

            if(this.byteBuffer == null) {
                this.byteBuffer = BufferUtils.createByteBuffer(dWidth * dHeight * 3);
            }
            
            byte[] rowStripBuffer = new byte[fullWidth * dHeight * 3];
            FileOutputStream stream = new FileOutputStream(tempFile);
            
            int totalTiles = (fullWidth / dWidth + 1) * (fullHeight / dHeight + 1);
            int processedTiles = 0;

            int previewW = Math.min(800, fullWidth / 10); 
            if (previewW < 300) previewW = 300;
            int previewH = (int)((double)previewW / fullWidth * fullHeight);
            BufferedImage fullPreview = new BufferedImage(previewW, previewH, BufferedImage.TYPE_INT_RGB);
            int[] previewPixels = new int[previewW * previewH];

            for(int i9 = 0; i9 < fullHeight; i9 += dHeight) {
                int currentStripHeight = Math.min(dHeight, fullHeight - i9);
                
                for(int i8 = 0; i8 < fullWidth; i8 += dWidth) {
                    this.progressUpdate.setLoadingProgress(++processedTiles * 100 / totalTiles);
                    int currentTileWidth = Math.min(dWidth, fullWidth - i8);
                    
                    int i12 = i9 - fullHeight / 2;
                    int i10 = i8 - fullWidth / 2;
                    GL11.glViewport(0, 0, dWidth, dHeight);
                    this.mc.entityRenderer.updateFogColor();
                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                    GL11.glEnable(GL11.GL_CULL_FACE);
                    this.mc.entityRenderer.setupCameraTransform();
                    GL11.glMatrixMode(GL11.GL_PROJECTION);
                    GL11.glLoadIdentity();
                    GL11.glOrtho(0.0D, (double)dWidth, 0.0D, (double)dHeight, 10.0D, 10000.0D);
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glLoadIdentity();
                    GL11.glTranslatef((float)-i10, (float)-i12, -5000.0F);
                    GL11.glScalef((float)scale, (float)-scale, (float)-scale);
                    this.floatBuffer.clear();
                    this.floatBuffer.put(1.0F).put(-0.5F).put(0.0F).put(0.0F);
                    this.floatBuffer.put(0.0F).put(1.0F).put(-1.0F).put(0.0F);
                    this.floatBuffer.put(1.0F).put(0.5F).put(0.0F).put(0.0F);
                    this.floatBuffer.put(0.0F).put(0.0F).put(0.0F).put(1.0F);
                    this.floatBuffer.flip();
                    GL11.glMultMatrix(this.floatBuffer);
                    GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslated(posX, 0, posZ);
                    GL11.glTranslated(-this.mc.renderViewEntity.lastTickPosX, (double)-this.height / 2.0D, -this.mc.renderViewEntity.lastTickPosZ);
                    Frustrum frustrum = new net.minecraft.src.vaderetro._JPM.FrustrumIsom();
                    this.renderGlobal.clipRenderersByFrustrum(frustrum, 0.0F);
                    GL11.glTranslated(this.mc.renderViewEntity.lastTickPosX, this.mc.renderViewEntity.lastTickPosY, this.mc.renderViewEntity.lastTickPosZ);
                    this.renderGlobal.updateRenderers(this.mc.renderViewEntity, false);
                    this.mc.entityRenderer.setupFog();
                    GL11.glEnable(GL11.GL_FOG);
                    GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
                    float f3 = (float)this.height * 8.0F;
                    GL11.glFogf(GL11.GL_FOG_START, 5000.0F - f3);
                    GL11.glFogf(GL11.GL_FOG_END, 5000.0F + f3 * 8.0F);
                    RenderHelper.enableStandardItemLighting();
                    this.renderGlobal.renderEntities(this.mc.renderViewEntity.getPosition(0.0F), frustrum, 0.0F);
                    this.mc.entityRenderer.renderRainSnow();
                    RenderHelper.disableStandardItemLighting();
                    this.renderGlobal.renderSky(0.0F);
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
                    if(this.mc.gameSettings.ambientOcclusion) GL11.glShadeModel(GL11.GL_SMOOTH);
                    this.renderGlobal.sortAndRender(this.mc.renderViewEntity, 0, 0.0F);
                    GL11.glShadeModel(GL11.GL_FLAT);
                    if(this.worldObj.worldProvider.getCloudHeight() < this.maxCloudHeight) {
                        GL11.glPushMatrix();
                        this.renderGlobal.renderClouds(0.0F);
                        GL11.glPopMatrix();
                    }
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColorMask(false, false, false, false);
                    if(this.mc.gameSettings.ambientOcclusion) GL11.glShadeModel(GL11.GL_SMOOTH);
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
                    int i11 = this.renderGlobal.sortAndRender(this.mc.renderViewEntity, 1, 0.0F);
                    GL11.glShadeModel(GL11.GL_FLAT);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColorMask(true, true, true, true);
                    if(i11 > 0) this.renderGlobal.renderAllRenderLists(1, 0.0F);
                    GL11.glTranslated(-posX, 0.0D, -posZ);
                    GL11.glDepthMask(true);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_FOG);
                    
                    this.byteBuffer.clear();
                    GL11.glReadPixels(0, 0, currentTileWidth, currentStripHeight, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, this.byteBuffer);

                    for (int row = 0; row < currentStripHeight; row++) {
                        for (int col = 0; col < currentTileWidth; col++) {
                            int srcIndex = (row * currentTileWidth + col) * 3;
                            int dstIndex = ((row * fullWidth) + (i8 + col)) * 3;
                            
                            byte r = this.byteBuffer.get(srcIndex);
                            byte g = this.byteBuffer.get(srcIndex + 1);
                            byte b = this.byteBuffer.get(srcIndex + 2);
                            
                            rowStripBuffer[dstIndex] = r;
                            rowStripBuffer[dstIndex + 1] = g;
                            rowStripBuffer[dstIndex + 2] = b;

                            int totalX = i8 + col;
                            int totalY = i9 + row;
                            
                            int pX = (totalX * previewW) / fullWidth;
                            int pY = (totalY * previewH) / fullHeight;
                            
                   
                            if (pX >= 0 && pX < previewW && pY >= 0 && pY < previewH) {
                                int rgb = ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
                                previewPixels[pY * previewW + pX] = rgb;
                            }
                        }
                    }
                }

                stream.write(rowStripBuffer, 0, fullWidth * currentStripHeight * 3);
            }

            stream.close();
            fullPreview.setRGB(0, 0, previewW, previewH, previewPixels, 0, previewW);
            previewImage = fullPreview;

        } catch (Exception e) {
            e.printStackTrace();
            this.mc.ingameGUI.addChatMessage("Render Error: " + e.getMessage());
        } finally {
            RenderGlobal.isTakingIsometricScreenshot = false;
        }

        if (previewImage != null) {
             this.mc.displayGuiScreen(new GuiIsometricPreview(this.mc, tempFile, previewImage, 
                     (this.width * scale) + (this.length * scale), 
                     (this.height * scale) + ((this.width * scale) + (this.length * scale)) / 2));
        }
    }
}