package net.minecraft.src.vaderetro.material;

import net.minecraft.src.MapColor;
import net.minecraft.src.MaterialLiquid;

public class MaterialOil extends MaterialLiquid {
    public MaterialOil(MapColor color) {
        super(color);
        this.setIsGroundCover();
        this.setNoPushMobility();
    }

    public boolean getIsLiquid() {
        return true;
    }

    public boolean isSolid() {
        return false;
    }

    public boolean getIsSolid() {
        return false;
    }

    public boolean isThick() {
        return true;
    }
}
