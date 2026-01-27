package net.minecraft.src.vaderetro._JIM;

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

        try {
            if (minecraft != null && minecraft.thePlayer != null && config != null && config.isFlyEnabled()) {
                EntityPlayerSP player = minecraft.thePlayer;

                if (player.movementInput != null) {
                    double verticalSpeed = 0.8D;
                    if (player.movementInput.jump) {
                        player.motionY = verticalSpeed;
                    } else if (player.movementInput.sneak) {
                        player.motionY = -verticalSpeed;
                    } else {
                        if (!player.onGround) {
                            player.motionY = 0.0D;
                        }
                    }

                    float forward = player.movementInput.moveForward;
                    float strafe = player.movementInput.moveStrafe;
                    if (forward != 0.0F || strafe != 0.0F) {
                        double targetSpeed = 1.2D;
                        double yawRad = Math.toRadians((double)player.rotationYaw);
                        double sin = Math.sin(yawRad);
                        double cos = Math.cos(yawRad);

                        double x = (double)strafe * cos - (double)forward * sin;
                        double z = (double)forward * cos + (double)strafe * sin;
                        double len = Math.sqrt(x * x + z * z);
                        if (len > 0.0D) {
                            x /= len;
                            z /= len;
                            player.motionX = x * targetSpeed;
                            player.motionZ = z * targetSpeed;
                        }
                    } else {
                        player.motionX *= 0.8D;
                        player.motionZ *= 0.8D;
                    }
                }

                try {
                    java.lang.reflect.Field fd = Entity.class.getDeclaredField("fallDistance");
                    fd.setAccessible(true);
                    fd.setFloat(player, 0.0F);
                } catch (Throwable t) {
                }
            }
        } catch (Exception ignored) {
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


    public void toggleFly() {
        boolean enabled = !config.isFlyEnabled();
        config.setFlyEnabled(enabled);
        if (cachedMinecraft != null && cachedMinecraft.ingameGUI != null) {
            cachedMinecraft.ingameGUI.addChatMessage("JIM Fly: " + (enabled ? "§aON" : "§cOFF"));
        }
    }
}
