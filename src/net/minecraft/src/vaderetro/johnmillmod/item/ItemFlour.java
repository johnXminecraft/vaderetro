package net.minecraft.src.vaderetro.johnmillmod.item;

import net.minecraft.src.*;

public class ItemFlour extends Item {
	public ItemFlour(int id) {
		super(id);
		this.setItemName("flour");
		this.setMaxStackSize(64);
	}
	
	public String getItemNameIS(ItemStack itemstack) {
		return "flour";
	}
}
