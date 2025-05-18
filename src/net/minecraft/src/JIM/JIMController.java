package net.minecraft.src.JIM;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class JIMController {
    private JIMView view;
    private JIMConfig config;

    private boolean isVisible = true;
    private boolean messageSent = false;
    private long lastToggleTime = 0;
    private static final long TOGGLE_COOLDOWN = 500;

    private static Minecraft cachedMinecraft;

    public JIMController() {
        this.config = new JIMConfig();
        this.view = new JIMView(this);
        System.out.println("JIM: Controller created");
    }

    public void onTick(Minecraft minecraft) {
        cachedMinecraft = minecraft;


        if (!messageSent && minecraft != null && minecraft.ingameGUI != null) {
            messageSent = true;
            minecraft.ingameGUI.addChatMessage("§aJIM loaded. Press R to toggle interface");
            System.out.println("JIM: Welcome message sent");
        }
    }


    public void renderInterface(Minecraft minecraft, float partialTicks) {
        try {
            if (minecraft == null || view == null) {
                return;
            }

            if (isVisible()) {

                if (minecraft.thePlayer != null &&
                        minecraft.fontRenderer != null &&
                        minecraft.currentScreen instanceof GuiInventory) {

                    view.draw(minecraft);
                }
            }
        } catch (Exception e) {
            System.out.println("JIM: Error in controller renderInterface: " + e.getMessage());
        }
    }

    public void toggleVisibility() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastToggleTime < TOGGLE_COOLDOWN) {
            return;
        }
        lastToggleTime = currentTime;


        isVisible = !isVisible;


        if (cachedMinecraft != null && cachedMinecraft.ingameGUI != null) {
            cachedMinecraft.ingameGUI.addChatMessage("JIM visibility: " + (isVisible ? "§aON" : "§cOFF"));
        }
    }


    public boolean isVisible() {

        return true;
    }


    public void giveItem(int itemId, int quantity, int damage) {
        if (cachedMinecraft != null) {
            EntityPlayerSP player = cachedMinecraft.thePlayer;

            if (player != null) {
                ItemStack stack = new ItemStack(itemId, quantity, damage);
                player.inventory.addItemStackToInventory(stack);
            }
        }
    }


    public JIMConfig getConfig() {
        return config;
    }


    public static Minecraft getMinecraftInstance() {
        return cachedMinecraft;
    }
}
