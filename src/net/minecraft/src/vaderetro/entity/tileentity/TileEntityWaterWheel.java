package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;

public class TileEntityWaterWheel extends TileEntity {
    public static final float ROTATION_PER_TICK = 0.25F;

    public float rotation;
    public float prevRotation;
    public float rotationSpeed;
    public boolean providingPower;

    public void updateEntity() {
        if (worldObj != null && worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) != Block.waterWheel.blockID) {
            worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
            return;
        }
        this.prevRotation = this.rotation;
        this.rotationSpeed = computeRotation();
        this.rotation += this.rotationSpeed;

        while (this.rotation >= 360.0F) this.rotation -= 360.0F;
        while (this.rotation < 0.0F) this.rotation += 360.0F;
    }

    private float computeRotation() {
        if (worldObj == null) return 0.0F;
        int i = xCoord;
        int j = yCoord;
        int k = zCoord;
        int meta = getBlockMetadata() & 7;

        int waterId1 = Block.waterMoving.blockID;
        int waterId2 = Block.waterStill.blockID;

        Vec3D flow = getWaterFlowVector(i, j, k, waterId1, waterId2);
        float strength = 0.0F;
        if (flow != null) {
            double len = flow.lengthVector();
            if (len > 0.01D) strength = 1.0F;
        }
        if (strength == 0.0F) {
            if (worldObj.getBlockId(i + 1, j, k) == waterId1 || worldObj.getBlockId(i + 1, j, k) == waterId2) {
                flow = Vec3D.createVector(1.0D, 0.0D, 0.0D);
                strength = 1.0F;
            }
            if (worldObj.getBlockId(i - 1, j, k) == waterId1 || worldObj.getBlockId(i - 1, j, k) == waterId2) {
                flow = Vec3D.createVector(-1.0D, 0.0D, 0.0D);
                strength = 1.0F;
            }
            if (worldObj.getBlockId(i, j, k + 1) == waterId1 || worldObj.getBlockId(i, j, k + 1) == waterId2) {
                flow = Vec3D.createVector(0.0D, 0.0D, 1.0D);
                strength = 1.0F;
            }
            if (worldObj.getBlockId(i, j, k - 1) == waterId1 || worldObj.getBlockId(i, j, k - 1) == waterId2) {
                flow = Vec3D.createVector(0.0D, 0.0D, -1.0D);
                strength = 1.0F;
            }
        }

        float fRotationAmount = 0.0F;
        if (flow != null && strength > 0.0F) {
            int sign = flowToRotationSign(meta, flow);
            fRotationAmount = sign * ROTATION_PER_TICK;
        }

        this.providingPower = Math.abs(fRotationAmount) > 0.001F;
        if (this.providingPower && this.worldObj instanceof WorldClient && this.worldObj.rand.nextFloat() < 0.25F) {
            double px = (double) this.xCoord + 0.5D + (this.worldObj.rand.nextDouble() - 0.5D) * 1.2D;
            double py = (double) this.yCoord + 0.3D + this.worldObj.rand.nextDouble() * 0.4D;
            double pz = (double) this.zCoord + 0.5D + (this.worldObj.rand.nextDouble() - 0.5D) * 1.2D;
            this.worldObj.spawnParticle("splash", px, py, pz, 0.0D, 0.1D, 0.0D);
            if (this.worldObj.rand.nextFloat() < 0.5F) {
                this.worldObj.spawnParticle("bubble", px, py, pz, 0.0D, 0.05D, 0.0D);
            }
        }
        return fRotationAmount;
    }


    private int flowToRotationSign(int meta, Vec3D flow) {
        double fx = flow.xCoord;
        double fz = flow.zCoord;
        switch (meta) {
            case 1: 
                return fz > 0.1D ? -1 : (fz < -0.1D ? 1 : 0);
            case 2:  
                return fz > 0.1D ? 1 : (fz < -0.1D ? -1 : 0);
            case 3:  
                return fx > 0.1D ? 1 : (fx < -0.1D ? -1 : 0);
            case 4:  
                return fx > 0.1D ? -1 : (fx < -0.1D ? 1 : 0);
            case 5: 
                return fx > 0.1D ? 1 : (fx < -0.1D ? -1 : 0);
            case 6: 
                return fx < -0.1D ? 1 : (fx > 0.1D ? -1 : 0);
            default:
                return 0;
        }
    }

    private Vec3D getWaterFlowVector(int i, int j, int k, int waterId1, int waterId2) {
        Vec3D sum = Vec3D.createVector(0.0D, 0.0D, 0.0D);
        int count = 0;
        int[] ys = new int[] { j - 1, j - 2, j };
        for (int y : ys) {
            int id = worldObj.getBlockId(i, y, k);
            if (id == waterId1 || id == waterId2) {
                Vec3D f = getFlowVector(worldObj, i, y, k);
                if (f != null && f.lengthVector() > 0.01D) {
                    sum = sum.addVector(f.xCoord, f.yCoord, f.zCoord);
                    count++;
                }
            }
        }
        for (int d = 0; d < 4; d++) {
            int ni = i + (d == 0 ? -1 : d == 1 ? 1 : 0);
            int nk = k + (d == 2 ? -1 : d == 3 ? 1 : 0);
            if (ni != i || nk != k) {
                int id = worldObj.getBlockId(ni, j, nk);
                if (id == waterId1 || id == waterId2) {
                    Vec3D f = getFlowVector(worldObj, ni, j, nk);
                    if (f != null && f.lengthVector() > 0.01D) {
                        sum = sum.addVector(f.xCoord, f.yCoord, f.zCoord);
                        count++;
                    }
                }
            }
        }
        if (count == 0 || sum.lengthVector() < 0.01D) return null;
        return sum.normalize();
    }

    private Vec3D getFlowVector(IBlockAccess iblockaccess, int par2, int par3, int par4) {
        Vec3D vec3 = Vec3D.createVector(0.0D, 0.0D, 0.0D);
        int i = getEffectiveFlowDecay(iblockaccess, par2, par3, par4);

        for (int j = 0; j < 4; j++) {
            int k = par2, l = par3, i1 = par4;
            if (j == 0) k--;
            if (j == 1) i1--;
            if (j == 2) k++;
            if (j == 3) i1++;

            int j1 = getEffectiveFlowDecay(iblockaccess, k, l, i1);
            if (j1 < 0) {
                Material mat = iblockaccess.getBlockMaterial(k, l, i1);
                if (mat != null && mat.isSolid()) continue;
                j1 = getEffectiveFlowDecay(iblockaccess, k, l - 1, i1);
                if (j1 >= 0) {
                    int k1 = j1 - (i - 8);
                    vec3 = vec3.addVector((k - par2) * k1, (l - par3) * k1, (i1 - par4) * k1);
                }
                continue;
            }
            if (j1 >= 0) {
                int l1 = j1 - i;
                vec3 = vec3.addVector((k - par2) * l1, (l - par3) * l1, (i1 - par4) * l1);
            }
        }

        if (iblockaccess.getBlockMetadata(par2, par3, par4) >= 8) {
            boolean flag = isBlockSolid(iblockaccess, par2, par3, par4 - 1, 2)
                || isBlockSolid(iblockaccess, par2, par3, par4 + 1, 3)
                || isBlockSolid(iblockaccess, par2 - 1, par3, par4, 4)
                || isBlockSolid(iblockaccess, par2 + 1, par3, par4, 5)
                || isBlockSolid(iblockaccess, par2, par3 + 1, par4 - 1, 2)
                || isBlockSolid(iblockaccess, par2, par3 + 1, par4 + 1, 3)
                || isBlockSolid(iblockaccess, par2 - 1, par3 + 1, par4, 4)
                || isBlockSolid(iblockaccess, par2 + 1, par3 + 1, par4, 5);
            if (flag) vec3 = vec3.normalize().addVector(0.0D, -6D, 0.0D);
        }
        return vec3.normalize();
    }

    private boolean isBlockSolid(IBlockAccess iblockaccess, int x, int y, int z, int side) {
        Material material = iblockaccess.getBlockMaterial(x, y, z);
        if (material == Block.waterMoving.blockMaterial) return false;
        if (side == 1) return true;
        if (material == Material.ice) return false;
        return material.isSolid();
    }

    private int getEffectiveFlowDecay(IBlockAccess iblockaccess, int x, int y, int z) {
        if (iblockaccess.getBlockMaterial(x, y, z) != Block.waterMoving.blockMaterial) return -1;
        int l = iblockaccess.getBlockMetadata(x, y, z);
        if (l >= 8) l = 0;
        return l;
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.rotation = tag.getFloat("Rot");
        this.prevRotation = tag.getFloat("PrevRot");
        this.providingPower = tag.getBoolean("bProvidingPower");
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setFloat("Rot", this.rotation);
        tag.setFloat("PrevRot", this.prevRotation);
        tag.setBoolean("bProvidingPower", this.providingPower);
    }
}
