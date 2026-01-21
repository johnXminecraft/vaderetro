package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenShotHelper {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	private static ByteBuffer buffer;
	private static byte[] pixelData;
	private static int[] imageData;
	private int hugeLineHeight;
	private DataOutputStream hugeStream;
	private byte[] hugeData;
	private int[] hugeImageData;
	private int hugeWidth;
	private int hugeHeight;
	private File hugeFile;
	private BufferedImage hugeImage;
	private boolean useTarga;

	public static String saveScreenshot(File var0, int var1, int var2) {
		try {
			File var3 = new File(var0, "screenshots");
			var3.mkdir();
			if(buffer == null || buffer.capacity() < var1 * var2) {
				buffer = BufferUtils.createByteBuffer(var1 * var2 * 3);
			}

			if(imageData == null || imageData.length < var1 * var2 * 3) {
				pixelData = new byte[var1 * var2 * 3];
				imageData = new int[var1 * var2];
			}

			GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			buffer.clear();
			GL11.glReadPixels(0, 0, var1, var2, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)buffer);
			buffer.clear();
			String var4 = "" + dateFormat.format(new Date());
			int var6 = 1;

			while(true) {
				File var5 = new File(var3, var4 + (var6 == 1 ? "" : "_" + var6) + ".png");
				if(!var5.exists()) {
					buffer.get(pixelData);

					for(int var7 = 0; var7 < var1; ++var7) {
						for(int var8 = 0; var8 < var2; ++var8) {
							int var9 = var7 + (var2 - var8 - 1) * var1;
							int var10 = pixelData[var9 * 3 + 0] & 255;
							int var11 = pixelData[var9 * 3 + 1] & 255;
							int var12 = pixelData[var9 * 3 + 2] & 255;
							int var13 = -16777216 | var10 << 16 | var11 << 8 | var12;
							imageData[var7 + var8 * var1] = var13;
						}
					}

					BufferedImage var15 = new BufferedImage(var1, var2, 1);
					var15.setRGB(0, 0, var1, var2, imageData, 0, var1);
					ImageIO.write(var15, "png", var5);
					return "Saved screenshot as " + var5.getName();
				}

				++var6;
			}
		} catch (OutOfMemoryError e) {
			return "Failed to save: " + "Out of memory";
		} catch (Exception var14) {
			var14.printStackTrace();
			return "Failed to save: " + var14;
		}
	}

	public ScreenShotHelper(File file1, int i2, int i3, int i4, boolean z5) throws IOException {
		this(file1, (String)null, i2, i3, i4, z5);
	}

	public ScreenShotHelper(File file1, String string10, int i2, int i3, int i4, boolean z5) throws IOException {
		if(!z5) {
			this.hugeImage = new BufferedImage(i2, i3, 1);
		}

		this.useTarga = z5;
		this.hugeWidth = i2;
		this.hugeHeight = i3;
		this.hugeLineHeight = i4;
		File file6 = new File(file1, "screenshots");
		file6.mkdir();
		String string7 = "huge_" + dateFormat.format(new Date());
		if(string10 == null) {
			for(int i8 = 1; (this.hugeFile = new File(file6, string7 + (i8 == 1 ? "" : "_" + i8) + (z5 ? ".tga" : ".png"))).exists(); ++i8) {
			}
		} else {
			this.hugeFile = new File(file6, string10);
		}

		this.hugeData = new byte[i2 * i4 * 3];
		this.hugeStream = new DataOutputStream(new FileOutputStream(this.hugeFile));
		if(z5) {
			byte[] b9 = new byte[18];
			b9[2] = 2;
			b9[12] = (byte)(i2 % 256);
			b9[13] = (byte)(i2 / 256);
			b9[14] = (byte)(i3 % 256);
			b9[15] = (byte)(i3 / 256);
			b9[16] = 24;
			this.hugeStream.write(b9);
		} else {
			this.hugeImageData = new int[i2 * i3];
		}

	}

	public void saveHugePart(ByteBuffer byteBuffer1, int i2, int i3, int i4, int i5) throws IOException {
		int i6 = i4;
		int i7 = i5;
		if(i4 > this.hugeWidth - i2) {
			i6 = this.hugeWidth - i2;
		}

		if(i5 > this.hugeHeight - i3) {
			i7 = this.hugeHeight - i3;
		}

		this.hugeLineHeight = i7;
		for(int i8 = 0; i8 < i7; ++i8) {
			byteBuffer1.position((i5 - i7) * i4 * 3 + i8 * i4 * 3);
			int i9 = (i2 + i8 * this.hugeWidth) * 3;
			byteBuffer1.get(this.hugeData, i9, i6 * 3);
		}

	}

	public void saveHugeLine(int i1) throws IOException {
		if(this.useTarga) {
			this.hugeStream.write(this.hugeData, 0, this.hugeWidth * 3 * this.hugeLineHeight);
		} else {
			for(int i2 = 0; i2 < this.hugeWidth; ++i2) {
				for(int i3 = 0; i3 < this.hugeLineHeight; ++i3) {
					int i4 = i2 + (this.hugeLineHeight - i3 - 1) * this.hugeWidth;
					int i5 = this.hugeData[i4 * 3 + 0] & 255;
					int i6 = this.hugeData[i4 * 3 + 1] & 255;
					int i7 = this.hugeData[i4 * 3 + 2] & 255;
					int i8 = 0xFF000000 | i5 << 16 | i6 << 8 | i7;
					this.hugeImageData[i2 + (i1 + i3) * this.hugeWidth] = i8;
				}
			}
		}

	}

	public String saveHugeScreenshot() throws IOException {
		if(!this.useTarga) {
			this.hugeImage.setRGB(0, 0, this.hugeWidth, this.hugeHeight, this.hugeImageData, 0, this.hugeWidth);
			ImageIO.write(this.hugeImage, "png", this.hugeStream);
		}

		this.hugeStream.close();
		return "Saved screenshot as " + this.hugeFile.getName();
	}
}
