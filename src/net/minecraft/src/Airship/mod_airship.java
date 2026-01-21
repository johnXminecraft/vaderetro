package net.minecraft.src.Airship;

import net.minecraft.src.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class mod_airship {
	private static mod_airship instance;
	private boolean initialized = false;
	private static Minecraft minecraftInstance;
	
	public static int spriteindex;
	public static Item airShip;
	public static Item engine;
	public static Item balloon;
	public static int KEY_UP = 57;
	public static int KEY_DOWN = 42;
	public static int KEY_CHEST = 54;
	public static int KEY_FIRE = 29;
	public static boolean SHOW_BOILER = true;
	public static GuiIngame chat;
	private static World world;

	public mod_airship() {
		instance = this;
		initializeItems();
		loadConfig();
	}

	public static mod_airship getInstance() {
		if (instance == null) {
			instance = new mod_airship();
		}
		return instance;
	}

	public static void initialize() {
		getInstance();
	}

	public static void onTickInGame(Minecraft minecraft) {
		mod_airship self = getInstance();
		minecraftInstance = minecraft;
		self.handleTick(minecraft);
	}

	public static Minecraft getMinecraftInstance() {
		return minecraftInstance;
	}

	private void initializeItems() {
		airShip = Item.airShip;
		engine = Item.engine;
		balloon = Item.balloon;
	}

    private void loadConfig() {
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(Minecraft.getMinecraftDir() + "/airship.properties"));
			KEY_UP = Keyboard.getKeyIndex(properties.getProperty("ascend"));
			KEY_DOWN = Keyboard.getKeyIndex(properties.getProperty("descend"));
			KEY_CHEST = Keyboard.getKeyIndex(properties.getProperty("chest"));
			KEY_FIRE = Keyboard.getKeyIndex(properties.getProperty("fire"));
			String e = properties.getProperty("boiler");
			if(e.contains("show")) {
				SHOW_BOILER = true;
			} else {
				SHOW_BOILER = false;
			}
		} catch (IOException var4) {
			properties.setProperty("ascend", Keyboard.getKeyName(Keyboard.KEY_SPACE));
			properties.setProperty("descend", Keyboard.getKeyName(Keyboard.KEY_LSHIFT));
			properties.setProperty("chest", Keyboard.getKeyName(Keyboard.KEY_RSHIFT));
			properties.setProperty("fire", Keyboard.getKeyName(Keyboard.KEY_LCONTROL));
			properties.setProperty("boiler", "show");
		}

        saveConfig();
	}

    private void saveConfig() {
        try {
            String path = Minecraft.getMinecraftDir() + "/airship.properties";
            StringBuilder b = new StringBuilder();
            b.append("chest=").append(Keyboard.getKeyName(KEY_CHEST)).append('\n');
            b.append("ascend=").append(Keyboard.getKeyName(KEY_UP)).append('\n');
            b.append("fire=").append(Keyboard.getKeyName(KEY_FIRE)).append('\n');
            b.append("descend=").append(Keyboard.getKeyName(KEY_DOWN)).append('\n');
            b.append("boiler=").append(SHOW_BOILER ? "show" : "hide").append('\n');
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(b.toString().getBytes());
            fos.flush();
            fos.close();
        } catch (IOException ignored) {
        }
    }

	private void handleTick(Minecraft game) {
		if(game.currentScreen instanceof GuiInventory && game.thePlayer.ridingEntity instanceof EntityAirship && game.thePlayer.ridingEntity != null) {
			game.displayGuiScreen(new GuiAirship(game.thePlayer.inventory, (EntityAirship)game.thePlayer.ridingEntity));
		}
	}

	public String Version() {
		return "Airships V2.5 for 1.7.3";
	}
}
