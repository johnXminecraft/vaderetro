package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;


public class TileEntityCardReader extends TileEntity {

    public static final int KEYCARD_ITEM_ID = Item.militaryKeycard.shiftedIndex;

    private boolean hasCard;

    public boolean hasCard() {
        return hasCard;
    }

    public void setHasCard(boolean hasCard) {
        this.hasCard = hasCard;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.hasCard = nbt.getBoolean("hasCard");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("hasCard", this.hasCard);
    }
}
