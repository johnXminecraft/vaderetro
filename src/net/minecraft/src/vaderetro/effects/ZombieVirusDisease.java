package net.minecraft.src.vaderetro.disease;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.vaderetro.entity.mob.undead.zombie.EntityZombie;
import net.minecraft.src.vaderetro.disease.ZombieBiteSkinEffect;

public class ZombieVirusDisease extends Disease {
    
    public static final float INFECTION_CHANCE = 0.07f;
    
    private static final int DURATION_TICKS = 1200;
    
    private static final float STAGE_MILD = 0.25f;     
    private static final float STAGE_MODERATE = 0.50f; 
    private static final float STAGE_SEVERE = 0.75f;   
    private static final float STAGE_CRITICAL = 1.0f;   
    
    private int heartbeatCooldown = 0;
    private int biteMarkRetryTicks = 0;
    private static final int BITE_MARK_RETRY_INTERVAL = 20; 
    
    public ZombieVirusDisease() {
        this.maxDurationTicks = DURATION_TICKS;
    }
    
    @Override
    public String getDiseaseId() {
        return "zombie_virus";
    }
    
    @Override
    public String getDiseaseName() {
        return "Zombie Virus";
    }
    
    @Override
    public String onInfected(EntityPlayer player) {
        if (player.worldObj != null) {
            player.worldObj.playSoundAtEntity(player, "mob.zombiehurt", 0.5f, 0.5f);
        }
		
		ZombieBiteSkinEffect.applyToPlayerHand(player);
        return "You feel panic...";
    }
    
    @Override
    public void onTick(EntityPlayer player) {
        if (player == null || player.worldObj == null) return;
        
        biteMarkRetryTicks++;
        if (biteMarkRetryTicks >= BITE_MARK_RETRY_INTERVAL) {
            biteMarkRetryTicks = 0;
            ZombieBiteSkinEffect.applyToPlayerHand(player);
        }
        
        float progress = getProgress();
        
        if (heartbeatCooldown > 0) {
            heartbeatCooldown--;
        } else {
  
            int baseInterval = 40;
            int minInterval = 10;
            int interval = (int)(baseInterval - (baseInterval - minInterval) * progress);
            
            if (progress > STAGE_MILD) {
                float volume = 0.3f + 0.4f * progress;
                float pitch = 0.8f + 0.4f * progress;
                player.worldObj.playSoundAtEntity(player, "random.breath", volume, pitch);
                heartbeatCooldown = interval;
            }
        }
        
        if (progress > STAGE_MODERATE && player.worldObj.rand.nextInt(200) == 0) {
            player.worldObj.playSoundAtEntity(player, "mob.zombie", 0.3f, 1.2f);
        }
        
        if (progress > STAGE_SEVERE) {
    
        }
        
        if (ticksInfected == (int)(DURATION_TICKS * 0.5f)) {
            DiseaseManager.getInstance().showMessage("Your vision blurs...", 0xFF0000);
        } else if (ticksInfected == (int)(DURATION_TICKS * 0.75f)) {
            DiseaseManager.getInstance().showMessage("You feel your strength fading...", 0xFF0000);
        } else if (ticksInfected == (int)(DURATION_TICKS * 0.90f)) {
            DiseaseManager.getInstance().showMessage("Everything is going dark...", 0x880000);
        }
    }
    
    @Override
    public void onFinalStage(EntityPlayer player, World world) {
        if (player == null || world == null) return;
        
        if (!world.multiplayerWorld) {
            EntityZombie zombie = new EntityZombie(world);
            zombie.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw, 0.0f);
            world.entityJoinedWorld(zombie);
            
            world.playSoundAtEntity(zombie, "mob.zombie", 1.0f, 1.0f);
        }
    }
    
    @Override
    public float getRedOverlayIntensity() {
        float progress = getProgress();
        
   
        if (progress < STAGE_MILD) {
            return progress * 0.1f / STAGE_MILD;
        } else if (progress < STAGE_MODERATE) {
            float stageProgress = (progress - STAGE_MILD) / (STAGE_MODERATE - STAGE_MILD);
            return 0.1f + stageProgress * 0.15f;
        } else if (progress < STAGE_SEVERE) {
            float stageProgress = (progress - STAGE_MODERATE) / (STAGE_SEVERE - STAGE_MODERATE);
            return 0.25f + stageProgress * 0.20f;
        } else {
            float stageProgress = (progress - STAGE_SEVERE) / (STAGE_CRITICAL - STAGE_SEVERE);
            return 0.45f + stageProgress * 0.25f;
        }
    }
    
    @Override
    public float getBlurIntensity() {
        float progress = getProgress();
        

        if (progress < STAGE_MODERATE) {
            return 0.0f;
        }
        
        float t = (progress - STAGE_MODERATE) / (STAGE_CRITICAL - STAGE_MODERATE); // 0..1
        if (t < 0.0f) t = 0.0f;
        if (t > 1.0f) t = 1.0f;
        return 0.3f + t * 0.7f;
    }
    
    @Override
    public float getCameraShakeIntensity() {
        float progress = getProgress();
        
        if (progress < STAGE_MODERATE) {
            return 0.0f;
        } else if (progress < STAGE_SEVERE) {
            float stageProgress = (progress - STAGE_MODERATE) / (STAGE_SEVERE - STAGE_MODERATE);
            return stageProgress * 0.15f;
        } else {
            float stageProgress = (progress - STAGE_SEVERE) / (STAGE_CRITICAL - STAGE_SEVERE);
            return 0.15f + stageProgress * 0.25f;
        }
    }
}
