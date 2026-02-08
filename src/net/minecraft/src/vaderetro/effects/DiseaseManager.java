package net.minecraft.src.vaderetro.effects;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import net.minecraft.src.vaderetro.effects.ZombieBiteSkinEffect;

public final class DiseaseManager {
    
    private static final DiseaseManager INSTANCE = new DiseaseManager();
    private static final Random random = new Random();
    
    private final Map<String, Class<? extends Disease>> diseaseRegistry = new HashMap<String, Class<? extends Disease>>();
    
    private Disease activeDisease = null;
    
    private String displayMessage = null;
    private int messageDisplayTicks = 0;
    private static final int MESSAGE_DURATION_TICKS = 100;
    private int messageColor = 0xFF0000; 
    
    private boolean shouldKillPlayer = false;
    private boolean zombieVirusDeathPending = false;
    
    public DiseaseManager() {
        registerDisease("zombie_virus", ZombieVirusDisease.class);
        registerDisease("radiation_sickness", RadiationSicknessDisease.class);
    }
    
    public static DiseaseManager getInstance() {
        return INSTANCE;
    }
    
    public void registerDisease(String diseaseId, Class<? extends Disease> diseaseClass) {
        diseaseRegistry.put(diseaseId, diseaseClass);
    }
    
    public boolean tryInfect(EntityPlayer player, String diseaseId, float chance) {
        if (activeDisease != null && activeDisease.isActive()) {
            return false;
        }
        
        if (random.nextFloat() > chance) {
            return false;
        }
        
        return infect(player, diseaseId);
    }
    
    public boolean infect(EntityPlayer player, String diseaseId) {
        Class<? extends Disease> diseaseClass = diseaseRegistry.get(diseaseId);
        if (diseaseClass == null) {
            return false;
        }
        
        try {
            activeDisease = diseaseClass.newInstance();
            activeDisease.startInfection(player);
            
            showMessage(activeDisease.getInfectionMessage(), 0xFF0000);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void update(EntityPlayer player) {
        if (messageDisplayTicks > 0) {
            messageDisplayTicks--;
        }
        
        if (activeDisease != null && activeDisease.isActive()) {
            activeDisease.update(player);
            if (activeDisease != null && activeDisease.isActive() && activeDisease.shouldCauseDeath()) {
                if (activeDisease instanceof ZombieVirusDisease) {
                    zombieVirusDeathPending = true;
                }
                shouldKillPlayer = true;
            }
        }
        if (activeDisease != null && activeDisease.isActive() && activeDisease instanceof RadiationSicknessDisease) {
            RadiationSicknessSkinEffect.applyToPlayer(player);
        }
    }
    
    public void showMessage(String message, int color) {
        this.displayMessage = message;
        this.messageColor = color;
        this.messageDisplayTicks = MESSAGE_DURATION_TICKS;
    }
    
    public String getDisplayMessage() {
        if (messageDisplayTicks > 0) {
            return displayMessage;
        }
        return null;
    }
    
    public float getMessageAlpha() {
        if (messageDisplayTicks <= 0) return 0.0f;
        
        if (messageDisplayTicks > MESSAGE_DURATION_TICKS - 20) {
            return (float)(MESSAGE_DURATION_TICKS - messageDisplayTicks) / 20.0f;
        } else if (messageDisplayTicks < 20) {
            return (float)messageDisplayTicks / 20.0f;
        }
        return 1.0f;
    }
    
    public int getMessageColor() {
        return messageColor;
    }
    
    public boolean hasActiveDisease() {
        return activeDisease != null && activeDisease.isActive();
    }
    
    public Disease getActiveDisease() {
        return activeDisease;
    }
    
    public float getRedOverlayIntensity() {
        if (activeDisease != null && activeDisease.isActive()) {
            return activeDisease.getRedOverlayIntensity();
        }
        return 0.0f;
    }
    
    public float getOverlayRed() {
        if (activeDisease != null && activeDisease.isActive()) {
            return activeDisease.getOverlayRed();
        }
        return 0.8f;
    }
    public float getOverlayGreen() {
        if (activeDisease != null && activeDisease.isActive()) {
            return activeDisease.getOverlayGreen();
        }
        return 0f;
    }
    public float getOverlayBlue() {
        if (activeDisease != null && activeDisease.isActive()) {
            return activeDisease.getOverlayBlue();
        }
        return 0f;
    }
    
    public float getBlurIntensity() {
        if (activeDisease != null && activeDisease.isActive()) {
            return activeDisease.getBlurIntensity();
        }
        return 0.0f;
    }
    
    public float getCameraShakeIntensity() {
        if (activeDisease != null && activeDisease.isActive()) {
            return activeDisease.getCameraShakeIntensity();
        }
        return 0.0f;
    }
    
    public boolean shouldKillPlayer() {
        return shouldKillPlayer;
    }
    
    public boolean isZombieVirusDeath() {
        return zombieVirusDeathPending;
    }
    
    public void clearKillFlag() {
        shouldKillPlayer = false;
    }
    
    public void clearZombieVirusDeathFlag() {
        zombieVirusDeathPending = false;
    }
    
    public void cureAll() {
        if (activeDisease != null) {
            activeDisease.cure();
            activeDisease = null;
        }
        ZombieBiteSkinEffect.clearFromLocalPlayer();
        RadiationSicknessSkinEffect.clearFromLocalPlayer();
        shouldKillPlayer = false;
        zombieVirusDeathPending = false;
    }

    public void cureZombieDisease() {
        if (activeDisease != null && Objects.equals(activeDisease.getDiseaseId(), "zombie_virus")) {
            activeDisease.cure();
            activeDisease.active = false;
        }
        ZombieBiteSkinEffect.clearFromLocalPlayer();
        shouldKillPlayer = false;
        zombieVirusDeathPending = false;
    }
    
    public void reset() {
        cureAll();
        displayMessage = null;
        messageDisplayTicks = 0;
    }

    private static final String NBT_DISEASE = "VadeRetroDisease";
    private static final String NBT_DISEASE_ID = "Id";
    private static final String NBT_TICKS = "Ticks";
    private static final String NBT_MAX_TICKS = "MaxTicks";
    private static final String NBT_RADIATION_LEVEL = "RadiationLevel";

    public void writeToNBT(NBTTagCompound nbt) {
        if (activeDisease == null || !activeDisease.isActive()) {
            return;
        }
        NBTTagCompound d = new NBTTagCompound();
        d.setString(NBT_DISEASE_ID, activeDisease.getDiseaseId());
        d.setInteger(NBT_TICKS, activeDisease.ticksInfected);
        d.setInteger(NBT_MAX_TICKS, activeDisease.maxDurationTicks);
        if (activeDisease instanceof RadiationSicknessDisease) {
            d.setFloat(NBT_RADIATION_LEVEL, ((RadiationSicknessDisease) activeDisease).getRadiationProgress());
        }
        nbt.setCompoundTag(NBT_DISEASE, d);
    }

    public void restoreFromNBT(NBTTagCompound nbt, EntityPlayer player) {
        this.reset();

        if (!nbt.hasKey(NBT_DISEASE)) {
            return;
        }

        NBTTagCompound d = nbt.getCompoundTag(NBT_DISEASE);
        String id = d.getString(NBT_DISEASE_ID);
        int ticks = d.getInteger(NBT_TICKS);
        int max = d.getInteger(NBT_MAX_TICKS);

        if (id == null || id.length() == 0 || max <= 0) {
            return;
        }

        Class<? extends Disease> cls = diseaseRegistry.get(id);
        if (cls != null) {
            try {
                activeDisease = cls.newInstance();
                activeDisease.restoreState(ticks, max);
                if (activeDisease instanceof RadiationSicknessDisease && d.hasKey(NBT_RADIATION_LEVEL)) {
                    ((RadiationSicknessDisease) activeDisease).restoreRadiationLevel(d.getFloat(NBT_RADIATION_LEVEL));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void onPlayerDeath(EntityPlayer player, World world) {
        if (activeDisease != null && activeDisease.isActive()) {
            activeDisease.onFinalStage(player, world);
        }
    }
}