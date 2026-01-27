package net.minecraft.src.vaderetro._JIM;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import net.minecraft.client.Minecraft;


public class JIMConfig {
    private Properties properties;
    private File configFile;

    public static final String CONFIG_FILENAME = "JIM.cfg";


    public JIMConfig() {
        properties = new Properties();
        configFile = new File(Minecraft.getMinecraftDir(), CONFIG_FILENAME);
        load();
    }


    public void load() {
        try {
            if (configFile.exists()) {
                FileInputStream fis = new FileInputStream(configFile);
                properties.load(fis);
                fis.close();
            } else {

                setProperty("favoriteItems", "");
                setProperty("lastGameMode", "0");
                setProperty("flyEnabled", "false");
                save();
            }
        } catch (Exception e) {
            System.out.println("JIM: Failed to load config: " + e.getMessage());
        }
    }


    public void save() {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("favoriteItems=").append(properties.getProperty("favoriteItems", "")).append('\n');
            builder.append("lastGameMode=").append(properties.getProperty("lastGameMode", "0")).append('\n');
            builder.append("flyEnabled=").append(properties.getProperty("flyEnabled", "false")).append('\n');

            FileOutputStream fos = new FileOutputStream(configFile);
            fos.write(builder.toString().getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            System.out.println("JIM: Failed to save config: " + e.getMessage());
        }
    }


    public String getProperty(String key) {
        return properties.getProperty(key, "");
    }


    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        save();
    }


    public int getGameMode() {
        try {
            return Integer.parseInt(getProperty("lastGameMode"));
        } catch (Exception e) {
            return 0;
        }
    }


    public void setGameMode(int mode) {
        setProperty("lastGameMode", String.valueOf(mode));
    }


    public boolean isFlyEnabled() {
        try {
            return Boolean.parseBoolean(getProperty("flyEnabled"));
        } catch (Exception e) {
            return false;
        }
    }


    public void setFlyEnabled(boolean enabled) {
        setProperty("flyEnabled", String.valueOf(enabled));
    }
} 