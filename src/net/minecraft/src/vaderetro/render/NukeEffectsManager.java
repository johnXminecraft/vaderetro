package net.minecraft.src.vaderetro.render;

public final class NukeEffectsManager {
    private static boolean active = false;
    private static double epicenterX;
    private static double epicenterY;
    private static double epicenterZ;
    private static int ticksLeft;
    private static int maxTicks;
    private static int flashTicks = 0;
    private static int flashMaxTicks = 0;

    private NukeEffectsManager() {}

    public static void startNuke(double x, double y, double z, int durationTicks) {
        active = true;
        epicenterX = x;
        epicenterY = y;
        epicenterZ = z;
        maxTicks = Math.max(1, durationTicks);
        ticksLeft = maxTicks;
        try {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();
            if (mc != null && mc.theWorld != null) {
                persistToWorldInfo(mc.theWorld);
            }
        } catch (Throwable t) {}
    }

    public static void endNuke() {
        active = false;
        ticksLeft = 0;
        maxTicks = 0;
    }

    public static boolean isActive() {
        return active && (ticksLeft > 0 || ticksLeft == -1);
    }

    public static void tickDown() {
        if (ticksLeft > 0) {
            --ticksLeft;
            if (ticksLeft <= 0) {
                active = false;
            }
        }
    }

    public static double getEpicenterX() { return epicenterX; }
    public static double getEpicenterY() { return epicenterY; }
    public static double getEpicenterZ() { return epicenterZ; }
    public static int getTicksLeft() { return ticksLeft; }
    public static int getMaxTicks() { return maxTicks; }

    public static float getIntensityAt(double x, double y, double z) {
        if (!isActive()) return 0.0f;
        double dx = x - epicenterX;
        double dy = y - epicenterY;
        double dz = z - epicenterZ;
        double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
        double falloff = 1.0 - Math.min(1.0, dist / 200.0);
        if (falloff <= 0.0) return 0.0f;
        float timeFactor = (ticksLeft == -1) ? 1.0f : (float)ticksLeft / (float)maxTicks;
        return (float)(falloff) * (0.5f + 0.5f * timeFactor);
    }

    public static void persistToWorldInfo(net.minecraft.src.World world) {
        if (world == null) return;
        net.minecraft.src.WorldInfo wi = world.getWorldInfo();
        try {
            wi.bml_setNukeContaminated(true, epicenterX, epicenterY, epicenterZ, 200.0);
        } catch (Throwable t) {
            
        }
    }

    public static void startFlash(int durationTicks) {
        flashMaxTicks = Math.max(1, durationTicks);
        flashTicks = flashMaxTicks;
    }

    public static void tickDownFlash() {
        if (flashTicks > 0) {
            --flashTicks;
        }
    }

    public static float getFlashAlpha() {
        if (flashTicks <= 0) return 0.0f;
        float progress = (float)flashTicks / (float)flashMaxTicks;
        if (progress > 0.5f) {
            return 1.0f;
        } else {
            return progress * 2.0f;
        }
    }

    public static boolean isFlashActive() {
        return flashTicks > 0;
    }

    public static void restoreFromWorldInfo(net.minecraft.src.World world) {
        if (world == null) return;
        net.minecraft.src.WorldInfo wi = world.getWorldInfo();
        try {
            if (wi.bml_isNukeContaminated()) {
                active = true;
                epicenterX = wi.bml_getNukeX();
                epicenterY = wi.bml_getNukeY();
                epicenterZ = wi.bml_getNukeZ();
                ticksLeft = -1;
                maxTicks = 1;
            }
        } catch (Throwable t) {
            
        }
    }
}