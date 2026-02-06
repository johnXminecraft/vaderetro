package net.minecraft.src.vaderetro.energy;

import net.minecraft.src.TileEntity;
import java.util.Set;

public interface IEnergySink {

    int injectEnergy(int amount, int side, Set<TileEntity> visited);

    boolean acceptsEnergy(int side);
}
