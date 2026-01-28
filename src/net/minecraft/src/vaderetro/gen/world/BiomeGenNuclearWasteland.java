package net.minecraft.src;

public class BiomeGenNuclearWasteland extends BiomeGenBase {
	public BiomeGenNuclearWasteland() {
		super();
		this.setBiomeName("Nuclear Wasteland");
		this.setColor(0x99FF99); 
		this.topBlock = (byte)Block.dirt.blockID;
		this.fillerBlock = (byte)Block.dirt.blockID;
		this.field_6502_q = 0x99FF99;
		this.setEnableSnow(); 
	}
	
	public boolean canSpawnLightningBolt() {
		return false;
}
}