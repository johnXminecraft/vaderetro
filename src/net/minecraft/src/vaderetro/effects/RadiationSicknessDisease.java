package net.minecraft.src.vaderetro.effects;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;

public class RadiationSicknessDisease extends Disease {

    public static final float CRITICAL_THRESHOLD = 0.70f;
    private static final int DEATH_COUNTDOWN_TICKS = 20 * 60;
    private static final float RADIATION_GAIN_PER_TICK_URANIUM = 0.0008f;
    private static final float RADIATION_GAIN_PER_TICK_WASTELAND_MAX = 0.018f;
    private static final float RADIATION_DECAY_PER_TICK = 0.00015f;
    private static final int DAMAGE_INTERVAL_TICKS = 40;
    private static final float STAGE_MILD = 0.25f;
    private static final float STAGE_MODERATE = 0.50f;
    private static final float STAGE_SEVERE = 0.75f;

    private float radiationLevel = 0.0f;
    private int deathCountdownTicks = -1;
    private int damageCooldown = 0;

    public RadiationSicknessDisease() {
        this.maxDurationTicks = DEATH_COUNTDOWN_TICKS;
    }

    @Override
    public String getDiseaseId() {
        return "radiation_sickness";
    }

    @Override
    public String getDiseaseName() {
        return "Radiation Sickness";
    }

    @Override
    public String onInfected(EntityPlayer player) {
        if (player.worldObj != null) {
            player.worldObj.playSoundAtEntity(player, "random.fizz", 0.4f, 0.7f);
        }
        RadiationSicknessSkinEffect.applyToPlayer(player);
        return "You feel a burning sensation...";
    }

    private boolean hasUraniumInInventory(EntityPlayer player) {
        if (player.inventory == null || player.inventory.mainInventory == null) return false;
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            ItemStack stack = player.inventory.mainInventory[i];
            if (stack != null) {
                if (stack.itemID == Item.uraniumDust.shiftedIndex) return true;
                if (stack.itemID == Block.oreUranium.blockID) return true;
            }
        }
        return false;
    }

    private float getWastelandRadiationGain(EntityPlayer player) {
        if (player.worldObj == null) return 0f;
        if (player.worldObj.getWorldChunkManager() == null) return 0f;
        BiomeGenBase biome = player.worldObj.getWorldChunkManager().getBiomeGenAt((int) player.posX, (int) player.posZ);
        if (biome != BiomeGenBase.nuclearWasteland) return 0f;

        WorldInfo wi = player.worldObj.getWorldInfo();
        double cx, cy, cz, radius;
        if (wi != null && wi.bml_isNukeContaminated()) {
            cx = wi.bml_getNukeX();
            cy = wi.bml_getNukeY();
            cz = wi.bml_getNukeZ();
            radius = wi.bml_getNukeRadius();
            if (radius <= 0) radius = 200.0;
        } else {
            return RADIATION_GAIN_PER_TICK_WASTELAND_MAX * 0.4f;
        }

        double dx = player.posX - cx;
        double dy = player.posY - cy;
        double dz = player.posZ - cz;
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double t = 1.0 - Math.min(1.0, dist / radius);
        return (float) (RADIATION_GAIN_PER_TICK_WASTELAND_MAX * (0.3 + 0.7 * t));
    }

    @Override
    public void onTick(EntityPlayer player) {
        if (player == null || player.worldObj == null) return;
        if (player.isWearingFullHazmatSuit()) {
            radiationLevel = Math.max(0f, radiationLevel - RADIATION_DECAY_PER_TICK);
            if (radiationLevel <= 0f) {
                cure();
                return;
            }
            if (damageCooldown > 0) damageCooldown--;
            return;
        }

        boolean hasUranium = hasUraniumInInventory(player);
        float wastelandGain = getWastelandRadiationGain(player);

        if (hasUranium || wastelandGain > 0f) {
            float gain = hasUranium ? RADIATION_GAIN_PER_TICK_URANIUM : 0f;
            gain += wastelandGain;
            radiationLevel = Math.min(1.0f, radiationLevel + gain);
            if (radiationLevel >= CRITICAL_THRESHOLD) {
                if (deathCountdownTicks < 0) deathCountdownTicks = DEATH_COUNTDOWN_TICKS;
                deathCountdownTicks--;
                ticksInfected = maxDurationTicks - deathCountdownTicks;
            } else {
                deathCountdownTicks = -1;
            }
        } else {
            deathCountdownTicks = -1;
            radiationLevel = Math.max(0f, radiationLevel - RADIATION_DECAY_PER_TICK);
            if (radiationLevel <= 0f) {
                cure();
                return;
            }
        }

        if (radiationLevel >= 0.2f && damageCooldown <= 0) {
            player.attackEntityFrom(null, 1);
            damageCooldown = DAMAGE_INTERVAL_TICKS;
        }
        if (damageCooldown > 0) damageCooldown--;

        if (radiationLevel >= STAGE_MODERATE && ticksInfected == (int) (maxDurationTicks * 0.3f)) {
            DiseaseManager.getInstance().showMessage("Your skin feels wrong...", 0x88AA88);
        } else if (radiationLevel >= STAGE_SEVERE && ticksInfected == (int) (maxDurationTicks * 0.6f)) {
            DiseaseManager.getInstance().showMessage("Acute radiation syndrome...", 0x66AA66);
        }
    }

    @Override
    public void cure() {
        super.cure();
        RadiationSicknessSkinEffect.clearFromLocalPlayer();
    }

    @Override
    public void onFinalStage(EntityPlayer player, World world) {
        RadiationSicknessSkinEffect.clearFromLocalPlayer();
    }

    @Override
    public float getRedOverlayIntensity() {
        float progress = getRadiationProgress();
        if (progress < STAGE_MILD) return progress * 0.15f / STAGE_MILD;
        if (progress < STAGE_MODERATE) return 0.15f + (progress - STAGE_MILD) / (STAGE_MODERATE - STAGE_MILD) * 0.1f;
        if (progress < STAGE_SEVERE) return 0.25f + (progress - STAGE_MODERATE) / (STAGE_SEVERE - STAGE_MODERATE) * 0.15f;
        return 0.4f + (progress - STAGE_SEVERE) / (1f - STAGE_SEVERE) * 0.2f;
    }

    @Override
    public float getBlurIntensity() {
        float progress = getRadiationProgress();
        if (progress < STAGE_MODERATE) return 0f;
        float t = (progress - STAGE_MODERATE) / (1f - STAGE_MODERATE);
        return 0.25f + t * 0.7f;
    }

    @Override
    public float getCameraShakeIntensity() {
        float progress = getRadiationProgress();
        if (progress < STAGE_MODERATE) return 0f;
        if (progress < STAGE_SEVERE) return (progress - STAGE_MODERATE) / (STAGE_SEVERE - STAGE_MODERATE) * 0.12f;
        return 0.12f + (progress - STAGE_SEVERE) / (1f - STAGE_SEVERE) * 0.2f;
    }

    public float getRadiationProgress() {
        return Math.max(0f, Math.min(1f, radiationLevel));
    }

    public float getOverlayRed() { return 0.15f; }
    public float getOverlayGreen() { return 0.55f; }
    public float getOverlayBlue() { return 0.45f; }

    @Override
    public boolean shouldCauseDeath() {
        return deathCountdownTicks == 0;
    }

    @Override
    public void restoreState(int ticksInfected, int maxDurationTicks) {
        super.restoreState(ticksInfected, maxDurationTicks);
        this.radiationLevel = CRITICAL_THRESHOLD + 0.1f;
        this.deathCountdownTicks = Math.max(0, this.maxDurationTicks - ticksInfected);
    }

    public void restoreRadiationLevel(float level) {
        this.radiationLevel = Math.max(0f, Math.min(1f, level));
        if (radiationLevel >= CRITICAL_THRESHOLD) {
            deathCountdownTicks = Math.max(0, deathCountdownTicks);
        }
    }
}
