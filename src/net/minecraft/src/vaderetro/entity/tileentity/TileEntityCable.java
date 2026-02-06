package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.energy.IEnergySink;
import net.minecraft.src.vaderetro.energy.IEnergyEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TileEntityCable extends TileEntity implements IEnergySink, IEnergyEmitter {

    public boolean[] connections = new boolean[6];
    private static final int[] OPPOSITE_SIDE = {1, 0, 3, 2, 5, 4};
    private int tickCounter = 0;

    @Override
    public void updateEntity() {
        super.updateEntity();
        tickCounter++;
        if (tickCounter % 10 == 0) {
            updateConnections();
        }
    }

    public void updateConnections() {
        boolean changed = false;
        for (int i = 0; i < 6; i++) {
            boolean shouldConnect = canConnectTo(i);
            if (connections[i] != shouldConnect) {
                connections[i] = shouldConnect;
                changed = true;
            }
        }
        if (changed) {
            worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
        }
    }

    private boolean canConnectTo(int side) {
        int x = xCoord, y = yCoord, z = zCoord;
        switch (side) {
            case 0: y--; break;
            case 1: y++; break;
            case 2: z--; break;
            case 3: z++; break;
            case 4: x--; break;
            case 5: x++; break;
        }
        TileEntity te = worldObj.getBlockTileEntity(x, y, z);
        if (te == null) return false;
        
        if (te instanceof TileEntityCable) return true;
        
        if (te instanceof IEnergySink) {
             if (((IEnergySink) te).acceptsEnergy(OPPOSITE_SIDE[side])) return true;
        }
        if (te instanceof IEnergyEmitter) {
             if (((IEnergyEmitter) te).emitsEnergy(OPPOSITE_SIDE[side])) return true;
        }
        return false;
    }

    @Override
    public int injectEnergy(int amount, int side, Set<TileEntity> visited) {
        if (visited.contains(this)) return 0;
        visited.add(this);

        List<IEnergySink> validOutputs = new ArrayList<IEnergySink>();
        List<Integer> outputSides = new ArrayList<Integer>();

        for (int i = 0; i < 6; i++) {
            if (connections[i]) {
                int x = xCoord, y = yCoord, z = zCoord;
                switch (i) {
                    case 0: y--; break;
                    case 1: y++; break;
                    case 2: z--; break;
                    case 3: z++; break;
                    case 4: x--; break;
                    case 5: x++; break;
                }
                TileEntity te = worldObj.getBlockTileEntity(x, y, z);
                
                if (te == null || visited.contains(te)) continue;
                
                if (te instanceof IEnergySink) {
                    if (((IEnergySink) te).acceptsEnergy(OPPOSITE_SIDE[i])) {
                        validOutputs.add((IEnergySink) te);
                        outputSides.add(i);
                    }
                }
            }
        }

        if (validOutputs.isEmpty()) return 0;

        int energyPerOutput = amount / validOutputs.size();
        int totalConsumed = 0;
        int remainder = amount % validOutputs.size();

        for (int i = 0; i < validOutputs.size(); i++) {
            IEnergySink sink = validOutputs.get(i);
            int sideOut = outputSides.get(i);
            
            int toSend = energyPerOutput + (remainder > 0 ? 1 : 0);
            if (remainder > 0) remainder--;
            
            int consumed = sink.injectEnergy(toSend, OPPOSITE_SIDE[sideOut], visited);
            totalConsumed += consumed;
        }

        return totalConsumed;
    }

    @Override
    public boolean acceptsEnergy(int side) {
        return connections[side];
    }

    @Override
    public boolean emitsEnergy(int side) {
        return connections[side];
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        byte c = nbttagcompound.getByte("Connections");
        for (int i = 0; i < 6; i++) {
            connections[i] = ((c >> i) & 1) == 1;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        byte c = 0;
        for (int i = 0; i < 6; i++) {
            if (connections[i]) c |= (1 << i);
        }
        nbttagcompound.setByte("Connections", c);
    }
}
