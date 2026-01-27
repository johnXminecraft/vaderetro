package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ItemTextureManager {
	private static final int ITEM_TILE_SIZE = 16;
	private static final int VANILLA_TILES_PER_ROW = 16;
	
	private static int itemTextureSize = 256;
	private static int itemTilesPerRow = VANILLA_TILES_PER_ROW;
	private static boolean itemSizeLoaded = false;
	
	private static synchronized void ensureItemSizeLoaded() {
		if (itemSizeLoaded) return;
		try {
			java.io.InputStream in = ItemTextureManager.class.getResourceAsStream("/gui/items.png");
			if (in != null) {
				BufferedImage img = ImageIO.read(in);
				in.close();
				if (img != null) {
					itemTextureSize = img.getWidth();
					itemTilesPerRow = VANILLA_TILES_PER_ROW;
				}
			}
		} catch (Throwable t) {
		}
		itemSizeLoaded = true;
	}

	public static void setItemTextureSizeFromImage(int width, int height) {
		System.out.println("[ItemTextureManager] setItemTextureSizeFromImage: " + width + "x" + height);
		itemTextureSize = width;
		itemTilesPerRow = VANILLA_TILES_PER_ROW;
		itemSizeLoaded = true;
	}

	public static int getItemTextureSize() {
		ensureItemSizeLoaded();
		return itemTextureSize;
	}
	
	public static void debugPrint(String context) {
		System.out.println("[ItemTextureManager] " + context + ": size=" + itemTextureSize + ", tilesPerRow=" + itemTilesPerRow + ", loaded=" + itemSizeLoaded);
	}
	
	public static int getItemTilesPerRow() {
		ensureItemSizeLoaded();
		return itemTilesPerRow;
	}
}
