package net.minecraft.src;

public class ItemArmor extends Item {
	private static final int[] damageReduceAmountArray = new int[]{3, 8, 6, 3};
	private static final int[] maxDamageArray = new int[]{44, 64, 60, 52};
	public final int armorLevel;
	public final int armorType;
	public final int damageReduceAmount;
	public final int renderIndex;

	public ItemArmor(int id, int armorLevel, int renderIndex, int armorType) {
		super(id);
		this.armorLevel = armorLevel;
		this.renderIndex = renderIndex;
		this.armorType = armorType;
		if(this.renderIndex == 8) {
			this.damageReduceAmount = 100;
			this.setMaxDamage(maxDamageArray[armorType]);
		} else {
			this.damageReduceAmount = damageReduceAmountArray[armorType];
			this.setMaxDamage(maxDamageArray[armorType] * (armorLevel + 4));
		}
		this.maxStackSize = 1;
	}

	public ItemArmor(int id, int armorLevel, int renderIndex, int armorType, int customDamageReduceAmount) {
		super(id);
		this.armorLevel = armorLevel;
		this.renderIndex = renderIndex;
		this.armorType = armorType;
		this.damageReduceAmount = customDamageReduceAmount;
		this.setMaxDamage(maxDamageArray[armorType] * (armorLevel + 4));
		this.maxStackSize = 1;
	}
}
