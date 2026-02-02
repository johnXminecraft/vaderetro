package net.minecraft.src.vaderetro.entity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.render.NukeEffectsManager;

public class EntityObjModel extends Entity {
    private String modelPath;
    private String texturePath;
    private float scale;
    private float rotationSpeed;
    private boolean growAnimationEnabled;
    private int growAnimationAgeTicks;
    private int growAnimationMaxTicks;
    private int growAnimationGrowTicks;
    private float growAnimationStartScale;
    private float growAnimationEndScale;
    private boolean startSoundPlayed;
    private boolean explosionPlayed;
    private int soundWaveStage;
    private boolean postWaveSmokeActive;
    private int postWaveTicks;
    private int craterRadius;
    private int craterDepth;
    private boolean explosionBegan;
    private int explosionTicks;
    private boolean particleShutoff;
    private boolean uraniumOrePlaced;

    public EntityObjModel(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.modelPath = "/models/test.obj";
        this.texturePath = "/textures/test.png";
        this.scale = 1.0f;
        this.rotationSpeed = 1.0f;
        this.growAnimationEnabled = false;
        this.growAnimationAgeTicks = 0;
        this.growAnimationMaxTicks = 0;
        this.growAnimationGrowTicks = 0;
        this.growAnimationStartScale = 1.0f;
        this.growAnimationEndScale = 1.0f;
        this.startSoundPlayed = false;
        this.explosionPlayed = false;
        this.soundWaveStage = 0;
        this.postWaveSmokeActive = false;
        this.postWaveTicks = 0;
        this.craterRadius = 0;
        this.craterDepth = 0;
        this.explosionBegan = false;
        this.explosionTicks = 0;
        this.particleShutoff = false;
        this.uraniumOrePlaced = false;
    }

    public EntityObjModel(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y, z);
    }
    
    public EntityObjModel(World world, double x, double y, double z, String modelPath, String texturePath) {
        this(world, x, y, z);
        this.modelPath = modelPath;
        this.texturePath = texturePath;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.rotationYaw += rotationSpeed;
        if (this.rotationYaw >= 360.0F) {
            this.rotationYaw -= 360.0F;
        }

        if (this.growAnimationEnabled && this.growAnimationMaxTicks > 0) {
            this.growAnimationAgeTicks++;
            int growTicks = this.growAnimationGrowTicks > 0 ? this.growAnimationGrowTicks : this.growAnimationMaxTicks;
            float t = (float)this.growAnimationAgeTicks / (float)growTicks;
            if (t < 0.0f) t = 0.0f;
            if (t > 1.0f) t = 1.0f;
            this.scale = this.growAnimationStartScale + t * (this.growAnimationEndScale - this.growAnimationStartScale);

            if (!this.startSoundPlayed && this.growAnimationAgeTicks == 1) {
                this.worldObj.playSoundAtEntity(this, "random.explode", 4.0F, 0.9F);
                this.startSoundPlayed = true;
                NukeEffectsManager.startNuke(this.posX, this.posY, this.posZ, 20 * 60 * 60);
                this.explosionBegan = true;
                this.explosionTicks = 0;
            }

            if (this.explosionBegan) {
                this.explosionTicks++;
                if (!this.particleShutoff && this.explosionTicks > 20 * 120) {
                    this.particleShutoff = true;
                }
            }

            if (!this.particleShutoff) {
            
            double ringY = this.posY + 0.1D;
            double maxRingRadius = 64.0D;
            double ringRadius = 0.5D + t * maxRingRadius;
            int segments = 128;
            double[] ringMultipliers = new double[] { 0.7D, 0.85D, 1.0D, 1.15D, 1.3D };
            for (int r = 0; r < ringMultipliers.length; r++) {
                double rMul = ringMultipliers[r];
                double rr = ringRadius * rMul;
                for (int i = 0; i < segments; i++) {
                    double angle = (Math.PI * 2.0D) * (double)i / (double)segments;
                    double baseX = this.posX + Math.cos(angle) * rr;
                    double baseZ = this.posZ + Math.sin(angle) * rr;
                    double dirX = Math.cos(angle);
                    double dirZ = Math.sin(angle);
                    for (int k = 0; k < 8; k++) {
                        double jitterX = (this.rand.nextDouble() - 0.5D) * 1.2D;
                        double jitterZ = (this.rand.nextDouble() - 0.5D) * 1.2D;
                        double rx = baseX + jitterX * 0.75D;
                        double rz = baseZ + jitterZ * 0.75D;
                        double vx = dirX * 0.04D;
                        double vz = dirZ * 0.04D;
                        if ((i + k + r) % 4 == 0) {
                            this.worldObj.spawnParticle("explode", rx, ringY, rz, 0.0D, 0.02D, 0.0D);
                        } else {
                            this.worldObj.spawnParticle("largesmoke", rx, ringY, rz, vx, 0.0D, vz);
                        }
                    }
                }
            }
            int plumeCount = 32;
            for (int j = 0; j < plumeCount; j++) {
                double dx = (this.rand.nextDouble() - 0.5D) * 1.6D;
                double dz = (this.rand.nextDouble() - 0.5D) * 1.6D;
                double vy = 0.05D + t * 0.1D;
                if ((j & 1) == 0) {
                    this.worldObj.spawnParticle("largesmoke", this.posX + dx, this.posY + 0.2D, this.posZ + dz, 0.0D, vy, 0.0D);
                } else {
                    this.worldObj.spawnParticle("smoke", this.posX + dx, this.posY + 0.2D, this.posZ + dz, 0.0D, vy, 0.0D);
                }
            }
            spawnDenseCloud(50.0D);
            double airBase = 8.0D;
            double airRise = t * 16.0D;
            double airRingY = this.posY + airBase + airRise;
            double airRadius = ringRadius * 0.8D;
            int airSegments = 96;
            double[] airMultipliers = new double[] { 0.9D, 1.0D, 1.1D };
            for (int a = 0; a < airMultipliers.length; a++) {
                double arm = airMultipliers[a];
                double arr = airRadius * arm;
                for (int i = 0; i < airSegments; i++) {
                    double angle = (Math.PI * 2.0D) * (double)i / (double)airSegments;
                    double baseX = this.posX + Math.cos(angle) * arr;
                    double baseZ = this.posZ + Math.sin(angle) * arr;
                    double dirX = Math.cos(angle);
                    double dirZ = Math.sin(angle);
                    for (int k = 0; k < 6; k++) {
                        double jitterX = (this.rand.nextDouble() - 0.5D) * 1.0D;
                        double jitterZ = (this.rand.nextDouble() - 0.5D) * 1.0D;
                        double rx = baseX + jitterX * 0.6D;
                        double rz = baseZ + jitterZ * 0.6D;
                        double vy = 0.02D + t * 0.03D;
                        double vx = dirX * 0.02D;
                        double vz = dirZ * 0.02D;
                        if ((i + k + a) % 5 == 0) {
                            this.worldObj.spawnParticle("explode", rx, airRingY, rz, 0.0D, vy, 0.0D);
                        } else {
                            this.worldObj.spawnParticle("largesmoke", rx, airRingY, rz, vx, vy, vz);
                        }
                    }
                }
            }
            carveCraterStep();
            }
            if (!this.explosionPlayed && this.growAnimationAgeTicks >= growTicks) {
                this.worldObj.playSoundAtEntity(this, "bomb.nuclearExplosion", 10.0F, 1.0F);
                this.explosionPlayed = true;
                this.soundWaveStage = 0;
                NukeEffectsManager.startNuke(this.posX, this.posY, this.posZ, -1);
                NukeEffectsManager.persistToWorldInfo(this.worldObj);
                replaceBlocksInRadius(200);
                removeItemsInRadius(200);
                int bx = MathHelper.floor_double(this.posX);
                int by = MathHelper.floor_double(this.posY - 1.0D);
                int bz = MathHelper.floor_double(this.posZ);
                if (by >= 0 && by < 256) {
                    this.worldObj.setBlockWithNotify(bx, by, bz, 0);
                }
            }
            int interval = 20;
            if (this.growAnimationAgeTicks < this.growAnimationMaxTicks && (this.growAnimationAgeTicks % interval == 0)) {
                if (this.growAnimationAgeTicks != growTicks) {
                    float vol = 3.5F + (1.0F - t) * 3.0F;
                    float pit = 0.85F - t * 0.2F;
                    this.worldObj.playSoundAtEntity(this, "bomb.nuclearExplosion", vol, pit);
                }
            }
            if (this.growAnimationAgeTicks >= this.growAnimationMaxTicks) {
                this.postWaveSmokeActive = true;
            }
        }
        if (this.postWaveSmokeActive) {
            this.postWaveTicks++;
            this.scale = 0.0001f;
            if (!this.particleShutoff) {
                int coreBursts = 48;
                for (int i = 0; i < coreBursts; i++) {
                    double rx = this.posX + (this.rand.nextDouble() - 0.5D) * 12.0D;
                    double rz = this.posZ + (this.rand.nextDouble() - 0.5D) * 12.0D;
                    double ry = this.posY + 2.0D + this.rand.nextDouble() * 12.0D;
                    double vy = 0.02D + this.rand.nextDouble() * 0.04D;
                    if ((i & 1) == 0) {
                        this.worldObj.spawnParticle("largesmoke", rx, ry, rz, 0.0D, vy, 0.0D);
                    } else {
                        this.worldObj.spawnParticle("explode", rx, ry, rz, 0.0D, vy * 0.5D, 0.0D);
                    }
                }
            }
            if (!this.particleShutoff) {
                double capY = this.posY + 14.0D;
                double capRadius = 18.0D;
                int capSeg = 128;
                for (int j = 0; j < capSeg; j++) {
                    double angle = (Math.PI * 2.0D) * (double)j / (double)capSeg;
                    double bx = this.posX + Math.cos(angle) * capRadius + (this.rand.nextDouble() - 0.5D) * 1.0D;
                    double bz = this.posZ + Math.sin(angle) * capRadius + (this.rand.nextDouble() - 0.5D) * 1.0D;
                    double vy = 0.01D + this.rand.nextDouble() * 0.02D;
                    this.worldObj.spawnParticle("largesmoke", bx, capY, bz, 0.0D, vy, 0.0D);
                }
            }
            carveCraterStep();
            if (!this.particleShutoff) {
                spawnDenseCloud(50.0D);
                spawnAshPrecipitation(60 * 3);
            }
        }
    }
    
    private void replaceBlocksInRadius(int radius) {
        int centerX = (int)Math.floor(this.posX);
        int centerY = (int)Math.floor(this.posY);
        int centerZ = (int)Math.floor(this.posZ);

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist > radius) continue;

                int x = centerX + dx;
                int z = centerZ + dz;

                for (int y = 0; y < 128; y++) {
                    if (!this.worldObj.blockExists(x, y, z)) continue;
                    int blockId = this.worldObj.getBlockId(x, y, z);

                    if (blockId == Block.grass.blockID || blockId == Block.dirt.blockID) {
                        this.worldObj.setBlockWithNotify(x, y, z, Block.radioactiveDirt.blockID);
                    } else if (blockId == Block.wood.blockID) {
                        this.worldObj.setBlockWithNotify(x, y, z, Block.radioactiveWood.blockID);
                    } else if (blockId == Block.leaves.blockID) {
                        this.worldObj.setBlockWithNotify(x, y, z, 0);
                    }
                }
            }
        }
    }

    private void removeItemsInRadius(int radius) {
        double cx = this.posX;
        double cz = this.posZ;
        double rSq = (double)(radius * radius);
        java.util.List list = this.worldObj.getEntitiesWithinAABB(EntityItem.class,
            AxisAlignedBB.getBoundingBoxFromPool(cx - radius, 0.0D, cz - radius, cx + radius, 256.0D, cz + radius));
        for (int i = 0; i < list.size(); i++) {
            Entity e = (Entity)list.get(i);
            double dx = e.posX - cx;
            double dz = e.posZ - cz;
            if (dx * dx + dz * dz <= rSq) {
                e.setEntityDead();
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setString("modelPath", modelPath);
        nbt.setString("texturePath", texturePath);
        nbt.setFloat("scale", scale);
        nbt.setFloat("rotationSpeed", rotationSpeed);
    }
    
    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        this.modelPath = nbt.getString("modelPath");
        this.texturePath = nbt.getString("texturePath");
        this.scale = nbt.getFloat("scale");
        this.rotationSpeed = nbt.getFloat("rotationSpeed");
    }
    
    public String getModelPath() {
        return modelPath;
    }
    
    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }
    
    public String getTexturePath() {
        return texturePath;
    }
    
    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }
    
    public float getScale() {
        return scale;
    }
    
    public void setScale(float scale) {
        this.scale = scale;
    }
    
    public float getRotationSpeed() {
        return rotationSpeed;
    }
    
    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void enableGrowAnimation(float startScale, float endScale, int durationTicks) {
        this.growAnimationEnabled = true;
        this.growAnimationStartScale = startScale;
        this.growAnimationEndScale = endScale;
        this.growAnimationMaxTicks = durationTicks <= 0 ? 1 : durationTicks;
        this.growAnimationGrowTicks = this.growAnimationMaxTicks;
        this.growAnimationAgeTicks = 0;
        this.scale = startScale;
        this.startSoundPlayed = false;
        this.explosionPlayed = false;
        this.soundWaveStage = 0;
        this.postWaveSmokeActive = false;
        this.postWaveTicks = 0;
        this.craterRadius = 0;
        this.craterDepth = 0;
        this.explosionBegan = false;
        this.explosionTicks = 0;
        this.particleShutoff = false;
    }

    public boolean isGrowAnimationEnabled() {
        return growAnimationEnabled;
    }


    public void enableGrowAndHoldAnimation(float startScale, float endScale, int growDurationTicks, int holdDurationTicks) {
        int g = growDurationTicks <= 0 ? 1 : growDurationTicks;
        int h = Math.max(0, holdDurationTicks);
        this.growAnimationEnabled = true;
        this.growAnimationStartScale = startScale;
        this.growAnimationEndScale = endScale;
        this.growAnimationGrowTicks = g;
        this.growAnimationMaxTicks = g + h;
        this.growAnimationAgeTicks = 0;
        this.scale = startScale;
        this.startSoundPlayed = false;
        this.explosionPlayed = false;
        this.soundWaveStage = 0;
        this.postWaveSmokeActive = false;
        this.postWaveTicks = 0;
        this.craterRadius = 0;
        this.craterDepth = 0;
        this.explosionBegan = false;
        this.explosionTicks = 0;
        this.particleShutoff = false;
    }

    private void carveCraterStep() {
        if ((this.postWaveTicks % 3) != 0 && this.growAnimationAgeTicks > 0) return;
        this.craterRadius = Math.min(100, this.craterRadius + 2);
        this.craterDepth = Math.min(8, this.craterDepth + ((this.craterRadius % 6 == 0) ? 1 : 0));
        int cx = MathHelper.floor_double(this.posX);
        int cz = MathHelper.floor_double(this.posZ);
        int baseY = MathHelper.floor_double(this.posY - 1.0D);

        for (int dx = -craterRadius; dx <= craterRadius; dx++) {
            for (int dz = -craterRadius; dz <= craterRadius; dz++) {
                double dist = Math.sqrt(dx*dx + dz*dz);
                if (dist <= craterRadius) {
                    double norm = dist / Math.max(1.0, craterRadius);
                    int localDepth = (int)Math.max(1, Math.round((1.0 - norm*norm) * craterDepth));
                    for (int dy = 0; dy < localDepth; dy++) {
                        int x = cx + dx;
                        int y = baseY - dy;
                        int z = cz + dz;
                        if (y >= 0 && y < 256) {
                            this.worldObj.setBlockWithNotify(x, y, z, 0);
                        }
                    }
                }
            }
        }

        if (!this.uraniumOrePlaced && this.craterRadius >= 100 && this.craterDepth >= 8) {
            int uraniumBlockId = Block.oreUranium.blockID;
            final double centerRadius = 14.0D;
            final float centerChance = 0.04F;
            final float craterChance = 0.004F;
            for (int dx = -craterRadius; dx <= craterRadius; dx++) {
                for (int dz = -craterRadius; dz <= craterRadius; dz++) {
                    double dist = Math.sqrt(dx * dx + dz * dz);
                    if (dist > craterRadius) continue;
                    double norm = dist / Math.max(1.0, craterRadius);
                    int localDepth = (int) Math.max(1, Math.round((1.0 - norm * norm) * craterDepth));
                    int floorY = baseY - localDepth;
                    if (floorY < 0 || floorY >= 256) continue;
                    int bx = cx + dx;
                    int bz = cz + dz;
                    if (!this.worldObj.blockExists(bx, floorY, bz)) continue;
                    float roll = this.rand.nextFloat();
                    boolean inCenter = dist <= centerRadius;
                    if (inCenter && roll < centerChance) {
                        this.worldObj.setBlockWithNotify(bx, floorY, bz, uraniumBlockId);
                    } else if (!inCenter && roll < craterChance) {
                        this.worldObj.setBlockWithNotify(bx, floorY, bz, uraniumBlockId);
                    }
                }
            }
            this.uraniumOrePlaced = true;
        }

        int clearHeightMax = 30;
        for (int dx = -craterRadius; dx <= craterRadius; dx++) {
            for (int dz = -craterRadius; dz <= craterRadius; dz++) {
                double dist = Math.sqrt(dx*dx + dz*dz);
                if (dist <= craterRadius) {
                    double norm = dist / Math.max(1.0, craterRadius);
                    int localHeight = (int)Math.max(0, Math.round((1.0 - norm) * clearHeightMax));
                    for (int dy = 1; dy <= localHeight; dy++) {
                        int x = cx + dx;
                        int y = baseY + dy;
                        int z = cz + dz;
                        if (y >= 0 && y < 256) {
                            this.worldObj.setBlockWithNotify(x, y, z, 0);
                        }
                    }
                }
            }
        }
    }

    private void spawnPerimeterSmoke(double radius) {
        int seg = 128;
        double y = this.posY + 0.5D;
        for (int i = 0; i < seg; i++) {
            double angle = (Math.PI * 2.0D) * (double)i / (double)seg;
            double rx = this.posX + Math.cos(angle) * radius + (this.rand.nextDouble() - 0.5D) * 0.8D;
            double rz = this.posZ + Math.sin(angle) * radius + (this.rand.nextDouble() - 0.5D) * 0.8D;
            double vy = 0.02D + this.rand.nextDouble() * 0.03D;
            this.worldObj.spawnParticle("largesmoke", rx, y, rz, 0.0D, vy, 0.0D);
            if ((i & 7) == 0) {
                this.worldObj.spawnParticle("explode", rx, y + 0.2D, rz, 0.0D, vy * 0.5D, 0.0D);
            }
        }
    }

    private void spawnDenseCloud(double radius) {
        int shells = 3;
        for (int s = 1; s <= shells; s++) {
            double r = radius * (double)s / (double)shells;
            int seg = 192;
            double baseY = this.posY + 0.2D + s * 0.8D;
            for (int i = 0; i < seg; i++) {
                double angle = (Math.PI * 2.0D) * (double)i / (double)seg;
                double rx = this.posX + Math.cos(angle) * r + (this.rand.nextDouble() - 0.5D) * 1.2D;
                double rz = this.posZ + Math.sin(angle) * r + (this.rand.nextDouble() - 0.5D) * 1.2D;
                double vy = 0.02D + this.rand.nextDouble() * 0.04D;
                if ((i & 1) == 0) {
                    this.worldObj.spawnParticle("largesmoke", rx, baseY, rz, 0.0D, vy, 0.0D);
                } else {
                    this.worldObj.spawnParticle("smoke", rx, baseY, rz, 0.0D, vy * 0.8D, 0.0D);
                }
            }
        }
    }

    private void spawnAshPrecipitation(int strongSeconds) {
        int strongTicks = strongSeconds * 20;
        int age = this.postWaveTicks;
        int density = age < strongTicks ? 250 : 120; 
        int radius = 60;
        for (int i = 0; i < density; i++) {
            double rx = this.posX + (this.rand.nextDouble() - 0.5D) * radius * 2.0D;
            double rz = this.posZ + (this.rand.nextDouble() - 0.5D) * radius * 2.0D;
            int topY = this.worldObj.findTopSolidBlock((int)Math.floor(rx), (int)Math.floor(rz));
            double ry = (double)topY + 6.0D + this.rand.nextDouble() * 4.0D; 
            double vy = -0.02D - this.rand.nextDouble() * 0.015D;

            if ((i & 1) == 0) {
                this.worldObj.spawnParticle("largesmoke", rx, ry, rz, 0.0D, vy, 0.0D);
            } else {
                this.worldObj.spawnParticle("smoke", rx, ry, rz, 0.0D, vy * 0.8D, 0.0D);
            }
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.5F;
    }
}