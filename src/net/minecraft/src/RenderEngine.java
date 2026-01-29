package net.minecraft.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public class RenderEngine {
	public static boolean useMipmaps = false;
	private HashMap textureMap = new HashMap();
	private HashMap field_28151_c = new HashMap();
	private HashMap textureNameToImageMap = new HashMap();
	private IntBuffer singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
	private ByteBuffer imageData = GLAllocation.createDirectByteBuffer(1048576);
	private List textureList = new ArrayList();
	private Map urlToImageDataMap = new HashMap();
	private GameSettings options;
	private boolean clampTexture = false;
	private boolean blurTexture = false;
	private TexturePackList texturePack;
	private BufferedImage missingTextureImage = new BufferedImage(64, 64, 2);
	
	private Map<String, BufferedImage> originalSkinImages = new HashMap<String, BufferedImage>();

	public RenderEngine(TexturePackList var1, GameSettings var2) {
		this.texturePack = var1;
		this.options = var2;
		Graphics var3 = this.missingTextureImage.getGraphics();
		var3.setColor(Color.WHITE);
		var3.fillRect(0, 0, 64, 64);
		var3.setColor(Color.BLACK);
		var3.drawString("missingtex", 1, 10);
		var3.dispose();
	}

	private void ensureImageDataCapacity(int requiredBytes) {
		if (this.imageData == null || this.imageData.capacity() < requiredBytes) {
			this.imageData = GLAllocation.createDirectByteBuffer(requiredBytes);
		}
	}

	public int[] func_28149_a(String var1) {
		TexturePackBase var2 = this.texturePack.selectedTexturePack;
		int[] var3 = (int[])this.field_28151_c.get(var1);
		if(var3 != null) {
			return var3;
		} else {
			try {
				Object var6 = null;
				if(var1.startsWith("##")) {
					var3 = this.func_28148_b(this.unwrapImageByColumns(this.readTextureImage(var2.getResourceAsStream(var1.substring(2)))));
				} else if(var1.startsWith("%clamp%")) {
					this.clampTexture = true;
					var3 = this.func_28148_b(this.readTextureImage(var2.getResourceAsStream(var1.substring(7))));
					this.clampTexture = false;
				} else if(var1.startsWith("%blur%")) {
					this.blurTexture = true;
					var3 = this.func_28148_b(this.readTextureImage(var2.getResourceAsStream(var1.substring(6))));
					this.blurTexture = false;
				} else {
					InputStream var7 = var2.getResourceAsStream(var1);
					if(var7 == null) {
						var3 = this.func_28148_b(this.missingTextureImage);
					} else {
						var3 = this.func_28148_b(this.readTextureImage(var7));
					}
				}

				this.field_28151_c.put(var1, var3);
				return var3;
			} catch (IOException var5) {
				int[] var4 = this.func_28148_b(this.missingTextureImage);
				this.field_28151_c.put(var1, var4);
				return var4;
			}
		}
	}

	private int[] func_28148_b(BufferedImage var1) {
		int var2 = var1.getWidth();
		int var3 = var1.getHeight();
		int[] var4 = new int[var2 * var3];
		var1.getRGB(0, 0, var2, var3, var4, 0, var2);
		return var4;
	}

	private int[] func_28147_a(BufferedImage var1, int[] var2) {
		int var3 = var1.getWidth();
		int var4 = var1.getHeight();
		var1.getRGB(0, 0, var3, var4, var2, 0, var3);
		return var2;
	}

	public int getTexture(String var1) {
		TexturePackBase var2 = this.texturePack.selectedTexturePack;
		Integer var3 = (Integer)this.textureMap.get(var1);
		if(var3 != null) {
			if("/terrain.png".equals(var1) && TerrainTextureManager.getTerrainTextureSize() == 256) {
				try {
					InputStream var7 = var2.getResourceAsStream(var1);
					if(var7 != null) {
						BufferedImage var8 = this.readTextureImage(var7);
						if(var8 != null && (var8.getWidth() != 256 || var8.getHeight() != 256)) {
							TerrainTextureManager.setTerrainTextureSizeFromImage(var8.getWidth(), var8.getHeight());
						}
					}
				} catch (Throwable var9) {
				}
			}
			if("/gui/items.png".equals(var1) && ItemTextureManager.getItemTextureSize() == 256) {
				try {
					InputStream var7 = var2.getResourceAsStream(var1);
					if(var7 != null) {
						BufferedImage var8 = this.readTextureImage(var7);
						if(var8 != null && (var8.getWidth() != 256 || var8.getHeight() != 256)) {
							ItemTextureManager.setItemTextureSizeFromImage(var8.getWidth(), var8.getHeight());
						}
					}
				} catch (Throwable var9) {
				}
			}
			return var3.intValue();
		} else {
			try {
				this.singleIntBuffer.clear();
				GLAllocation.generateTextureNames(this.singleIntBuffer);
				int var6 = this.singleIntBuffer.get(0);
				if(var1.startsWith("##")) {
					this.setupTexture(this.unwrapImageByColumns(this.readTextureImage(var2.getResourceAsStream(var1.substring(2)))), var6);
				} else if(var1.startsWith("%clamp%")) {
					this.clampTexture = true;
					this.setupTexture(this.readTextureImage(var2.getResourceAsStream(var1.substring(7))), var6);
					this.clampTexture = false;
				} else if(var1.startsWith("%blur%")) {
					this.blurTexture = true;
					this.setupTexture(this.readTextureImage(var2.getResourceAsStream(var1.substring(6))), var6);
					this.blurTexture = false;
				} else {
					InputStream var7 = var2.getResourceAsStream(var1);
					if(var7 == null) {
						this.setupTexture(this.missingTextureImage, var6);
					} else {
						BufferedImage var8 = this.readTextureImage(var7);
						if("/terrain.png".equals(var1) && var8 != null) {
							TerrainTextureManager.setTerrainTextureSizeFromImage(var8.getWidth(), var8.getHeight());
						}
						if("/gui/items.png".equals(var1) && var8 != null) {
							ItemTextureManager.setItemTextureSizeFromImage(var8.getWidth(), var8.getHeight());
						}
						this.setupTexture(var8, var6);
					}
				}

				this.textureMap.put(var1, Integer.valueOf(var6));
				return var6;
			} catch (IOException var5) {
				GLAllocation.generateTextureNames(this.singleIntBuffer);
				int var4 = this.singleIntBuffer.get(0);
				this.setupTexture(this.missingTextureImage, var4);
				this.textureMap.put(var1, Integer.valueOf(var4));
				return var4;
			}
		}
	}

	private BufferedImage unwrapImageByColumns(BufferedImage var1) {
		int var2 = var1.getWidth() / 16;
		BufferedImage var3 = new BufferedImage(16, var1.getHeight() * var2, 2);
		Graphics var4 = var3.getGraphics();

		for(int var5 = 0; var5 < var2; ++var5) {
			var4.drawImage(var1, -var5 * 16, var5 * var1.getHeight(), (ImageObserver)null);
		}

		var4.dispose();
		return var3;
	}

	public int allocateAndSetupTexture(BufferedImage var1) {
		this.singleIntBuffer.clear();
		GLAllocation.generateTextureNames(this.singleIntBuffer);
		int var2 = this.singleIntBuffer.get(0);
		this.setupTexture(var1, var2);
		this.textureNameToImageMap.put(Integer.valueOf(var2), var1);
		return var2;
	}

	public void setupTexture(BufferedImage var1, int var2) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, var2);
		if(useMipmaps) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		}

		if(this.blurTexture) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		}

		if(this.clampTexture) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		}

		int var3 = var1.getWidth();
		int var4 = var1.getHeight();
		int[] var5 = new int[var3 * var4];
		byte[] var6 = new byte[var3 * var4 * 4];
		var1.getRGB(0, 0, var3, var4, var5, 0, var3);

		int var7;
		int var8;
		int var9;
		int var10;
		int var11;
		int var12;
		int var13;
		int var14;
		for(var7 = 0; var7 < var5.length; ++var7) {
			var8 = var5[var7] >> 24 & 255;
			var9 = var5[var7] >> 16 & 255;
			var10 = var5[var7] >> 8 & 255;
			var11 = var5[var7] & 255;
			if(this.options != null && this.options.anaglyph) {
				var12 = (var9 * 30 + var10 * 59 + var11 * 11) / 100;
				var13 = (var9 * 30 + var10 * 70) / 100;
				var14 = (var9 * 30 + var11 * 70) / 100;
				var9 = var12;
				var10 = var13;
				var11 = var14;
			}

			var6[var7 * 4 + 0] = (byte)var9;
			var6[var7 * 4 + 1] = (byte)var10;
			var6[var7 * 4 + 2] = (byte)var11;
			var6[var7 * 4 + 3] = (byte)var8;
		}

		this.ensureImageDataCapacity(var6.length);
		this.imageData.clear();
		this.imageData.put(var6);
		this.imageData.position(0).limit(var6.length);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, var3, var4, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)this.imageData);
		if(useMipmaps) {
			for(var7 = 1; var7 <= 4; ++var7) {
				var8 = var3 >> var7 - 1;
				var9 = var3 >> var7;
				var10 = var4 >> var7;

				for(var11 = 0; var11 < var9; ++var11) {
					for(var12 = 0; var12 < var10; ++var12) {
						var13 = this.imageData.getInt((var11 * 2 + 0 + (var12 * 2 + 0) * var8) * 4);
						var14 = this.imageData.getInt((var11 * 2 + 1 + (var12 * 2 + 0) * var8) * 4);
						int var15 = this.imageData.getInt((var11 * 2 + 1 + (var12 * 2 + 1) * var8) * 4);
						int var16 = this.imageData.getInt((var11 * 2 + 0 + (var12 * 2 + 1) * var8) * 4);
						int var17 = this.weightedAverageColor(this.weightedAverageColor(var13, var14), this.weightedAverageColor(var15, var16));
						this.imageData.putInt((var11 + var12 * var9) * 4, var17);
					}
				}

				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, var7, GL11.GL_RGBA, var9, var10, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)this.imageData);
			}
		}

	}

	private static BufferedImage deepCopyImage(BufferedImage src) {
		if (src == null) {
			return null;
		}
		BufferedImage copy = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		copy.getGraphics().drawImage(src, 0, 0, null);
		return copy;
	}

	private void paintZombieBiteOnSkin(BufferedImage img) {
		if (img == null) return;
		int w = img.getWidth(), h = img.getHeight();
		if (w <= 0 || h <= 0) return;
		
		final float sx = w / 64.0f;
		final float sy = (h >= 64) ? 1.0f : (h / 32.0f);

		int biteRadius = Math.max(4, Math.round(3.5f * sx));
		int spatterRadius = biteRadius + Math.max(3, Math.round(3.0f * sx));

		int innerColor = 0xFFFF0000;
		int rimColor = 0xFF8B0000;

		int[] centersX = { 
			Math.round(46.0f * sx), 
			Math.round(50.0f * sx), 
			Math.round(42.0f * sx) 
		};
		int[] centersY = { 
			Math.round(26.0f * sy),
			Math.round(25.0f * sy),
			Math.round(24.0f * sy)
		};

		for (int c = 0; c < centersX.length; c++) {
			int centerX = centersX[c];
			int centerY = centersY[c];
			for (int dy = -spatterRadius; dy <= spatterRadius; dy++) {
				for (int dx = -spatterRadius; dx <= spatterRadius; dx++) {
					int x = centerX + dx, y = centerY + dy;
					if (x < 0 || y < 0 || x >= w || y >= h) continue;

					float dist = (float) Math.sqrt(dx * dx + dy * dy);

					if (dist <= biteRadius) {
						int argb = (dist < biteRadius * 0.6f) ? innerColor : rimColor;
						img.setRGB(x, y, argb);
					} else if (dist <= spatterRadius) {
						float t = (dist - biteRadius) / (spatterRadius - biteRadius);
						float chance = 0.55f * (1.0f - t);
						int hash = (x * 73428767) ^ (y * 91228529) ^ (c * 1337);
						hash ^= (hash >>> 13);
						float rnd = ((hash & 0xFF) / 255.0f);
						if (rnd < chance) {
							int variation = (hash >> 8) & 0x3F;
							int r = 0x50 + variation;
							if (r > 0xC0) r = 0xC0;
							int spatterColor = (0xFF << 24) | (r << 16) | 0x00101010;
							img.setRGB(x, y, spatterColor);
						}
					}
				}
			}
		}
	}

	public void applyZombieBiteToPlayerSkin(String skinUrl, String fallbackTexturePath) {
		boolean customSkinProcessed = false;
		
		if (skinUrl != null && skinUrl.length() > 0) {
			ThreadDownloadImageData data = (ThreadDownloadImageData)this.urlToImageDataMap.get(skinUrl);
			
			if (data != null && data.image != null) {
				String key = skinUrl;
				BufferedImage base = (BufferedImage)this.originalSkinImages.get(key);
				if (base == null) {
					base = deepCopyImage(data.image);
					if (base == null) return;
					this.originalSkinImages.put(key, base);
				}

				BufferedImage working = deepCopyImage(base);
				if (working == null) return;

				paintZombieBiteOnSkin(working);

				data.image = working;
				if (data.textureName < 0) {
					data.textureName = this.allocateAndSetupTexture(working);
				} else {
					this.setupTexture(working, data.textureName);
				}
				data.textureSetupComplete = true;
				customSkinProcessed = true;
				return;
			}
		}
		
		if (!customSkinProcessed && fallbackTexturePath != null && fallbackTexturePath.length() > 0) {
			try {
				String key = fallbackTexturePath;
				BufferedImage base = (BufferedImage)this.originalSkinImages.get(key);
				
				if (base == null) {
					TexturePackBase pack = this.texturePack.selectedTexturePack;
					InputStream in = pack.getResourceAsStream(fallbackTexturePath);
					if (in == null) return;
					BufferedImage src = this.readTextureImage(in);
					if (src == null) return;

					base = deepCopyImage(src);
					if (base == null) return;
					this.originalSkinImages.put(key, base);
				}

				BufferedImage working = deepCopyImage(base);
				if (working == null) return;

				paintZombieBiteOnSkin(working);

				int texId = this.getTexture(fallbackTexturePath);
				this.setupTexture(working, texId);
			} catch (Exception e) {
			}
		}
	}

	public void clearZombieBiteFromPlayerSkin(String skinUrl, String fallbackTexturePath) {
		if (skinUrl != null && skinUrl.length() > 0) {
			BufferedImage base = (BufferedImage)this.originalSkinImages.get(skinUrl);
			ThreadDownloadImageData data = (ThreadDownloadImageData)this.urlToImageDataMap.get(skinUrl);
			if (data != null && base != null) {
				BufferedImage restored = deepCopyImage(base);
				if (restored != null) {
					data.image = restored;
					if (data.textureName < 0) {
						data.textureName = this.allocateAndSetupTexture(restored);
					} else {
						this.setupTexture(restored, data.textureName);
					}
					data.textureSetupComplete = true;
				}
			}
			this.originalSkinImages.remove(skinUrl);
		}
		
		if (fallbackTexturePath != null && fallbackTexturePath.length() > 0) {
			BufferedImage base = (BufferedImage)this.originalSkinImages.get(fallbackTexturePath);
			if (base != null) {
				try {
					int texId = this.getTexture(fallbackTexturePath);
					this.setupTexture(base, texId);
				} catch (Exception ignored) {
				}
			}
			this.originalSkinImages.remove(fallbackTexturePath);
		}
	}

	public void func_28150_a(int[] var1, int var2, int var3, int var4) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, var4);
		if(useMipmaps) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		}

		if(this.blurTexture) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		}

		if(this.clampTexture) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		}

		byte[] var5 = new byte[var2 * var3 * 4];

		for(int var6 = 0; var6 < var1.length; ++var6) {
			int var7 = var1[var6] >> 24 & 255;
			int var8 = var1[var6] >> 16 & 255;
			int var9 = var1[var6] >> 8 & 255;
			int var10 = var1[var6] & 255;
			if(this.options != null && this.options.anaglyph) {
				int var11 = (var8 * 30 + var9 * 59 + var10 * 11) / 100;
				int var12 = (var8 * 30 + var9 * 70) / 100;
				int var13 = (var8 * 30 + var10 * 70) / 100;
				var8 = var11;
				var9 = var12;
				var10 = var13;
			}

			var5[var6 * 4 + 0] = (byte)var8;
			var5[var6 * 4 + 1] = (byte)var9;
			var5[var6 * 4 + 2] = (byte)var10;
			var5[var6 * 4 + 3] = (byte)var7;
		}

		this.ensureImageDataCapacity(var5.length);
		this.imageData.clear();
		this.imageData.put(var5);
		this.imageData.position(0).limit(var5.length);
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, var2, var3, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)this.imageData);
	}

	public void deleteTexture(int var1) {
		this.textureNameToImageMap.remove(Integer.valueOf(var1));
		this.singleIntBuffer.clear();
		this.singleIntBuffer.put(var1);
		this.singleIntBuffer.flip();
		GL11.glDeleteTextures(this.singleIntBuffer);
	}

	public int getTextureForDownloadableImage(String var1, String var2) {
		ThreadDownloadImageData var3 = (ThreadDownloadImageData)this.urlToImageDataMap.get(var1);
		
		try {
			net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();
			if (mc != null && mc.thePlayer != null) {
				if (var1 != null && var1.equals(mc.thePlayer.skinUrl)) {
					net.minecraft.src.vaderetro.effects.DiseaseManager dm = net.minecraft.src.vaderetro.effects.DiseaseManager.getInstance();
					if (dm != null && dm.hasActiveDisease()) {
						net.minecraft.src.vaderetro.effects.Disease d = dm.getActiveDisease();
						if (d != null && "zombie_virus".equals(d.getDiseaseId())) {
							this.applyZombieBiteToPlayerSkin(var1, var2);
						}
					}
				}
			}
		} catch (Exception e) {
		}
		
		if(var3 != null && var3.image != null && !var3.textureSetupComplete) {
			if(var3.textureName < 0) {
				var3.textureName = this.allocateAndSetupTexture(var3.image);
			} else {
				this.setupTexture(var3.image, var3.textureName);
			}

			var3.textureSetupComplete = true;
		}

		return var3 != null && var3.textureName >= 0 ? var3.textureName : (var2 == null ? -1 : this.getTexture(var2));
	}

	public ThreadDownloadImageData obtainImageData(String var1, ImageBuffer var2) {
		ThreadDownloadImageData var3 = (ThreadDownloadImageData)this.urlToImageDataMap.get(var1);
		if(var3 == null) {
			this.urlToImageDataMap.put(var1, new ThreadDownloadImageData(var1, var2));
		} else {
			++var3.referenceCount;
		}

		return var3;
	}

	public void releaseImageData(String var1) {
		ThreadDownloadImageData var2 = (ThreadDownloadImageData)this.urlToImageDataMap.get(var1);
		if(var2 != null) {
			--var2.referenceCount;
			if(var2.referenceCount == 0) {
				if(var2.textureName >= 0) {
					this.deleteTexture(var2.textureName);
				}

				this.urlToImageDataMap.remove(var1);
			}
		}

	}

	public void registerTextureFX(TextureFX var1) {
		this.textureList.add(var1);
		var1.onTick();
	}

	public void updateDynamicTextures() {
		int var1;
		TextureFX var2;
		int var3;
		int var4;
		int var5;
		int var6;
		int var7;
		int var8;
		int var9;
		int var10;
		int var11;
		int var12;
		for(var1 = 0; var1 < this.textureList.size(); ++var1) {
			var2 = (TextureFX)this.textureList.get(var1);
			var2.anaglyphEnabled = this.options.anaglyph;
			var2.onTick();
			this.ensureImageDataCapacity(var2.imageData.length);
			this.imageData.clear();
			this.imageData.put(var2.imageData);
			this.imageData.position(0).limit(var2.imageData.length);
			var2.bindImage(this);

			for(var3 = 0; var3 < var2.tileSize; ++var3) {
				for(var4 = 0; var4 < var2.tileSize; ++var4) {
					GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, var2.iconIndex % 16 * 16 + var3 * 16, var2.iconIndex / 16 * 16 + var4 * 16, 16, 16, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)this.imageData);
					if(useMipmaps) {
						for(var5 = 1; var5 <= 4; ++var5) {
							var6 = 16 >> var5 - 1;
							var7 = 16 >> var5;

							for(var8 = 0; var8 < var7; ++var8) {
								for(var9 = 0; var9 < var7; ++var9) {
									var10 = this.imageData.getInt((var8 * 2 + 0 + (var9 * 2 + 0) * var6) * 4);
									var11 = this.imageData.getInt((var8 * 2 + 1 + (var9 * 2 + 0) * var6) * 4);
									var12 = this.imageData.getInt((var8 * 2 + 1 + (var9 * 2 + 1) * var6) * 4);
									int var13 = this.imageData.getInt((var8 * 2 + 0 + (var9 * 2 + 1) * var6) * 4);
									int var14 = this.averageColor(this.averageColor(var10, var11), this.averageColor(var12, var13));
									this.imageData.putInt((var8 + var9 * var7) * 4, var14);
								}
							}

							GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, var5, var2.iconIndex % 16 * var7, var2.iconIndex / 16 * var7, var7, var7, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
						}
					}
				}
			}
		}

		for(var1 = 0; var1 < this.textureList.size(); ++var1) {
			var2 = (TextureFX)this.textureList.get(var1);
			if(var2.textureId > 0) {
				this.ensureImageDataCapacity(var2.imageData.length);
				this.imageData.clear();
				this.imageData.put(var2.imageData);
				this.imageData.position(0).limit(var2.imageData.length);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, var2.textureId);
				GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 16, 16, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)this.imageData);
				if(useMipmaps) {
					for(var3 = 1; var3 <= 4; ++var3) {
						var4 = 16 >> var3 - 1;
						var5 = 16 >> var3;

						for(var6 = 0; var6 < var5; ++var6) {
							for(var7 = 0; var7 < var5; ++var7) {
								var8 = this.imageData.getInt((var6 * 2 + 0 + (var7 * 2 + 0) * var4) * 4);
								var9 = this.imageData.getInt((var6 * 2 + 1 + (var7 * 2 + 0) * var4) * 4);
								var10 = this.imageData.getInt((var6 * 2 + 1 + (var7 * 2 + 1) * var4) * 4);
								var11 = this.imageData.getInt((var6 * 2 + 0 + (var7 * 2 + 1) * var4) * 4);
								var12 = this.averageColor(this.averageColor(var8, var9), this.averageColor(var10, var11));
								this.imageData.putInt((var6 + var7 * var5) * 4, var12);
							}
						}

						GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, var3, 0, 0, var5, var5, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)this.imageData);
					}
				}
			}
		}

	}

	private int averageColor(int var1, int var2) {
		int var3 = (var1 & -16777216) >> 24 & 255;
		int var4 = (var2 & -16777216) >> 24 & 255;
		return (var3 + var4 >> 1 << 24) + ((var1 & 16711422) + (var2 & 16711422) >> 1);
	}

	private int weightedAverageColor(int var1, int var2) {
		int var3 = (var1 & -16777216) >> 24 & 255;
		int var4 = (var2 & -16777216) >> 24 & 255;
		short var5 = 255;
		if(var3 + var4 == 0) {
			var3 = 1;
			var4 = 1;
			var5 = 0;
		}

		int var6 = (var1 >> 16 & 255) * var3;
		int var7 = (var1 >> 8 & 255) * var3;
		int var8 = (var1 & 255) * var3;
		int var9 = (var2 >> 16 & 255) * var4;
		int var10 = (var2 >> 8 & 255) * var4;
		int var11 = (var2 & 255) * var4;
		int var12 = (var6 + var9) / (var3 + var4);
		int var13 = (var7 + var10) / (var3 + var4);
		int var14 = (var8 + var11) / (var3 + var4);
		return var5 << 24 | var12 << 16 | var13 << 8 | var14;
	}

	public void refreshTextures() {
		TexturePackBase var1 = this.texturePack.selectedTexturePack;
		Iterator var2 = this.textureNameToImageMap.keySet().iterator();

		BufferedImage var4;
		while(var2.hasNext()) {
			int var3 = ((Integer)var2.next()).intValue();
			var4 = (BufferedImage)this.textureNameToImageMap.get(Integer.valueOf(var3));
			this.setupTexture(var4, var3);
		}

		ThreadDownloadImageData var8;
		for(var2 = this.urlToImageDataMap.values().iterator(); var2.hasNext(); var8.textureSetupComplete = false) {
			var8 = (ThreadDownloadImageData)var2.next();
		}

		var2 = this.textureMap.keySet().iterator();

		String var9;
		while(var2.hasNext()) {
			var9 = (String)var2.next();

			try {
				if(var9.startsWith("##")) {
					var4 = this.unwrapImageByColumns(this.readTextureImage(var1.getResourceAsStream(var9.substring(2))));
				} else if(var9.startsWith("%clamp%")) {
					this.clampTexture = true;
					var4 = this.readTextureImage(var1.getResourceAsStream(var9.substring(7)));
				} else if(var9.startsWith("%blur%")) {
					this.blurTexture = true;
					var4 = this.readTextureImage(var1.getResourceAsStream(var9.substring(6)));
				} else {
					var4 = this.readTextureImage(var1.getResourceAsStream(var9));
				}

				int var5 = ((Integer)this.textureMap.get(var9)).intValue();
				this.setupTexture(var4, var5);
				this.blurTexture = false;
				this.clampTexture = false;
			} catch (IOException var7) {
			}
		}

		var2 = this.field_28151_c.keySet().iterator();

		while(var2.hasNext()) {
			var9 = (String)var2.next();

			try {
				if(var9.startsWith("##")) {
					var4 = this.unwrapImageByColumns(this.readTextureImage(var1.getResourceAsStream(var9.substring(2))));
				} else if(var9.startsWith("%clamp%")) {
					this.clampTexture = true;
					var4 = this.readTextureImage(var1.getResourceAsStream(var9.substring(7)));
				} else if(var9.startsWith("%blur%")) {
					this.blurTexture = true;
					var4 = this.readTextureImage(var1.getResourceAsStream(var9.substring(6)));
				} else {
					var4 = this.readTextureImage(var1.getResourceAsStream(var9));
				}

				this.func_28147_a(var4, (int[])this.field_28151_c.get(var9));
				this.blurTexture = false;
				this.clampTexture = false;
			} catch (IOException var6) {
			}
		}

	}

	private BufferedImage readTextureImage(InputStream var1) throws IOException {
		BufferedImage var2 = ImageIO.read(var1);
		var1.close();
		return var2;
	}

	public void bindTexture(int var1) {
		if(var1 >= 0) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var1);
		}
	}
}