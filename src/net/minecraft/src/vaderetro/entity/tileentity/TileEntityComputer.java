package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;

public class TileEntityComputer extends TileEntity {

    public String outputText = "";
    public int nukeTimer = -1;
    public boolean armed = false;

    @Override
    public void updateEntity() {
        if (nukeTimer > 0) nukeTimer--;
        if (worldObj != null && !worldObj.multiplayerWorld) {
            worldObj.markBlocksDirty(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
        }
    }

    public void startBombsInRange(int durationTicks) {
        if (worldObj == null) return;
        int r = 10;
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (dx * dx + dy * dy + dz * dz > r * r) continue;
                    int nx = xCoord + dx;
                    int ny = yCoord + dy;
                    int nz = zCoord + dz;
                    if (worldObj.getBlockId(nx, ny, nz) == Block.nuclearBomb.blockID) {
                        TileEntity te = worldObj.getBlockTileEntity(nx, ny, nz);
                        if (te instanceof TileEntityNuclearBomb) {
                            ((TileEntityNuclearBomb) te).countdownTicks = durationTicks;
                        }
                    }
                }
            }
        }
    }

    public String getOutputText() {
        return outputText == null ? "" : outputText;
    }

    public void appendOutputLine(String line) {
        if (outputText == null) outputText = "";
        if (outputText.length() > 0) outputText += "\n";
        outputText += line;
        if (outputText.length() > 2048) {
            outputText = outputText.substring(outputText.length() - 2048);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.outputText = nbt.getString("outputText");
        this.nukeTimer = nbt.getInteger("nukeTimer");
        this.armed = nbt.getBoolean("armed");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("outputText", outputText);
        nbt.setInteger("nukeTimer", nukeTimer);
        nbt.setBoolean("armed", armed);
    }
}