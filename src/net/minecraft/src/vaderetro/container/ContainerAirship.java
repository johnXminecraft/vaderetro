package net.minecraft.src.vaderetro.container;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.EntityAirship;

public class ContainerAirship extends Container {
	private EntityAirship airship;

	public ContainerAirship(InventoryPlayer inventoryplayer, EntityAirship air) {
		this.addSlot(new Slot(air, 13, 134, 16));
		this.addSlot(new Slot(air, 12, 134, 52));

		int l;
		int k1;
		for(l = 0; l < 3; ++l) {
			for(k1 = 0; k1 < 4; ++k1) {
				this.addSlot(new Slot(air, k1 + l * 4, 8 + k1 * 18, 16 + l * 18));
			}
		}

		for(l = 0; l < 3; ++l) {
			for(k1 = 0; k1 < 9; ++k1) {
				this.addSlot(new Slot(inventoryplayer, k1 + (l + 1) * 9, 8 + k1 * 18, 84 + l * 18));
			}
		}

		for(l = 0; l < 9; ++l) {
			this.addSlot(new Slot(inventoryplayer, l, 8 + l * 18, 142));
		}

	}

	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	public ItemStack getStackInSlot(int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot)this.slots.get(i);
		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(i == 0) {
				this.func_28125_a(itemstack1, 9, 45, true);
			} else if(i >= 9 && i < 36) {
				this.func_28125_a(itemstack1, 36, 45, false);
			} else if(i >= 36 && i < 45) {
				this.func_28125_a(itemstack1, 9, 36, false);
			} else {
				this.func_28125_a(itemstack1, 9, 45, false);
			}

			if(itemstack1.stackSize == 0) {
				slot.putStack((ItemStack)null);
			} else {
				slot.onSlotChanged();
			}

			if(itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(itemstack1);
		}

		return itemstack;
	}
}
