package net.minecraft.src.vaderetro.model;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.__config.mod_airship;

public class ModelAirship extends ModelBase {
	private float bladespin = 0.0F;
	private long lastframe = System.nanoTime();
	public CustomModelRenderer[] boxes = new CustomModelRenderer[27];

	public ModelAirship() {
		this.boxes[0] = new CustomModelRenderer(0, 8, 64, 64);
		this.boxes[1] = new CustomModelRenderer(0, 0, 64, 64);
		this.boxes[2] = new CustomModelRenderer(0, 0, 64, 64);
		this.boxes[3] = new CustomModelRenderer(0, 0, 64, 64);
		this.boxes[4] = new CustomModelRenderer(0, 0, 64, 64);
		this.boxes[5] = new CustomModelRenderer(56, 0, 64, 64);
		this.boxes[6] = new CustomModelRenderer(56, 0, 64, 64);
		this.boxes[7] = new CustomModelRenderer(56, 0, 64, 64);
		this.boxes[8] = new CustomModelRenderer(56, 0, 64, 64);
		this.boxes[9] = new CustomModelRenderer(36, 39, 64, 64);
		this.boxes[14] = new CustomModelRenderer(36, 39, 64, 64);
		this.boxes[10] = new CustomModelRenderer(0, 55, 64, 64);
		this.boxes[11] = new CustomModelRenderer(0, 55, 64, 64);
		this.boxes[12] = new CustomModelRenderer(0, 55, 64, 64);
		this.boxes[13] = new CustomModelRenderer(0, 55, 64, 64);
		this.boxes[15] = new CustomModelRenderer(0, 55, 64, 64);
		this.boxes[16] = new CustomModelRenderer(0, 55, 64, 64);
		this.boxes[17] = new CustomModelRenderer(0, 55, 64, 64);
		this.boxes[18] = new CustomModelRenderer(0, 55, 64, 64);
		this.boxes[19] = new CustomModelRenderer(0, 0, 64, 64);
		this.boxes[20] = new CustomModelRenderer(0, 43, 64, 64);
		this.boxes[21] = new CustomModelRenderer(28, 44, 64, 64);
		this.boxes[22] = new CustomModelRenderer(0, 32, 64, 64);
		this.boxes[23] = new CustomModelRenderer(56, 0, 64, 64);
		this.boxes[24] = new CustomModelRenderer(56, 0, 64, 64);
		this.boxes[25] = new CustomModelRenderer(56, 0, 64, 64);
		this.boxes[26] = new CustomModelRenderer(56, 0, 64, 64);
		byte byte0 = 24;
		byte byte1 = 6;
		byte byte2 = 20;
		byte byte3 = 4;
		this.boxes[0].addBox(-12.0F, -8.0F, -3.0F, 24, 16, 4, 0.0F);
		this.boxes[0].setPosition(0.0F, (float)(0 + byte3), 0.0F);
		this.boxes[0].rotateAngleX = 1.570796F;
		this.boxes[1].addBox((float)(-byte0 / 2 + 2), (float)(-byte1 - 1), -1.0F, byte0 - 4, byte1, 2, 0.0F);
		this.boxes[1].setPosition((float)(-byte0 / 2 + 1), (float)(0 + byte3), 0.0F);
		this.boxes[1].rotateAngleY = (float)Math.PI * 3.0F / 2.0F;
		this.boxes[2].addBox((float)(-byte0 / 2 + 2), (float)(-byte1 - 1), -1.0F, byte0 - 4, byte1, 2, 0.0F);
		this.boxes[2].setPosition((float)(byte0 / 2 - 1), (float)(0 + byte3), 0.0F);
		this.boxes[2].rotateAngleY = 1.570796F;
		this.boxes[3].addBox((float)(-byte0 / 2 + 2), (float)(-byte1 - 1), -1.0F, byte0 - 4, byte1, 2, 0.0F);
		this.boxes[3].setPosition(0.0F, (float)(0 + byte3), (float)(-byte2 / 2 + 1));
		this.boxes[3].rotateAngleY = 3.141593F;
		this.boxes[4].addBox((float)(-byte0 / 2 + 2), (float)(-byte1 - 1), -1.0F, byte0 - 4, byte1, 2, 0.0F);
		this.boxes[4].setPosition(0.0F, (float)(0 + byte3), (float)(byte2 / 2 - 1));
		this.boxes[5].addBox(-2.0F, -13.0F, -1.0F, 2, byte0, 2, 0.0F);
		this.boxes[5].setPosition(-12.0F, -12.5F, (float)(-byte2 / 2) - 3.5F);
		this.boxes[5].rotateAngleY = 0.392699F;
		this.boxes[5].rotateAngleX = 0.392699F;
		this.boxes[7].addBox(-2.0F, -13.0F, -1.0F, 2, byte0, 2, 0.0F);
		this.boxes[7].setPosition(13.5F, -12.5F, (float)(-byte2 / 2 - 2));
		this.boxes[7].rotateAngleY = -0.392699F;
		this.boxes[7].rotateAngleX = 0.392699F;
		this.boxes[6].addBox(-2.0F, -13.0F, -1.0F, 2, byte0, 2, 0.0F);
		this.boxes[6].setPosition(-12.0F, -12.5F, (float)(byte2 / 2) + 3.5F);
		this.boxes[6].rotateAngleY = -0.392699F;
		this.boxes[6].rotateAngleX = -0.392699F;
		this.boxes[8].addBox(-2.0F, -13.0F, -1.0F, 2, byte0, 2, 0.0F);
		this.boxes[8].setPosition(13.5F, -12.5F, (float)(byte2 / 2 + 2));
		this.boxes[8].rotateAngleY = 0.392699F;
		this.boxes[8].rotateAngleX = -0.392699F;
		this.boxes[23].addBox(-2.0F, -2.0F, -1.0F, 2, 13, 2, 0.0F);
		this.boxes[23].setPosition(0.0F, 3.0F, (float)(-byte0 / 2) - 7.0F);
		this.boxes[23].rotateAngleX = 1.570796F;
		this.boxes[24].addBox(-2.0F, -2.0F, -1.0F, 2, 13, 2, 0.0F);
		this.boxes[24].setPosition(6.0F, 3.0F, (float)(-byte0 / 2) - 7.0F);
		this.boxes[24].rotateAngleX = 1.570796F;
		this.boxes[25].addBox(-2.0F, -2.0F, -1.0F, 2, 13, 2, 0.0F);
		this.boxes[25].setPosition(0.0F, 3.0F, (float)(byte0 / 2) - 2.0F);
		this.boxes[25].rotateAngleX = 1.570796F;
		this.boxes[26].addBox(-2.0F, -2.0F, -1.0F, 2, 13, 2, 0.0F);
		this.boxes[26].setPosition(6.0F, 3.0F, (float)(byte0 / 2) - 2.0F);
		this.boxes[26].rotateAngleX = 1.570796F;
		this.boxes[9].addBox(-8.0F, -4.0F, 0.0F, 10, 8, 4, 1.0F);
		this.boxes[9].setPosition(0.0F, 3.0F, (float)(byte0 / 2) + 9.0F);
		this.boxes[9].rotateAngleY = (float)Math.PI;
		this.boxes[14].addBox(-8.0F, -4.0F, 0.0F, 10, 8, 4, 1.0F);
		this.boxes[14].setPosition(0.0F, 3.0F, (float)(-byte0 / 2) - 5.0F);
		this.boxes[14].rotateAngleY = (float)Math.PI;
		this.boxes[10].addBox(-6.0F, -0.5F, -0.5F, 12, 1, 2, 0.0F);
		this.boxes[10].setPosition(9.5F, 0.0F, (float)(byte0 / 2) + 7.0F);
		this.boxes[10].rotateAngleX = 1.570796F;
		this.boxes[10].rotateAngleZ = 1.570796F;
		this.boxes[11].addBox((float)(-byte3 - 2), -0.5F, -0.5F, byte0 / 2, 1, 2, 0.0F);
		this.boxes[11].setPosition(9.5F, 0.0F, (float)(byte0 / 2) + 7.0F);
		this.boxes[11].rotateAngleX = 1.570796F;
		this.boxes[11].rotateAngleZ = 1.570796F;
		this.boxes[12].addBox((float)(-byte3 - 2), -0.5F, -0.5F, byte0 / 2, 1, 2, 0.0F);
		this.boxes[12].setPosition(9.5F, 0.0F, (float)(byte0 / 2) + 7.0F);
		this.boxes[12].rotateAngleX = 1.570796F;
		this.boxes[12].rotateAngleZ = 1.570796F;
		this.boxes[13].addBox((float)(-byte3 - 2), -0.5F, -0.5F, byte0 / 2, 1, 2, 0.0F);
		this.boxes[13].setPosition(9.5F, 0.0F, (float)(byte0 / 2) + 7.0F);
		this.boxes[13].rotateAngleX = 1.570796F;
		this.boxes[13].rotateAngleZ = 1.570796F;
		this.boxes[15].addBox((float)(-byte3 - 2), -0.5F, -0.5F, byte0 / 2, 1, 2, 0.0F);
		this.boxes[15].setPosition(9.5F, 0.0F, (float)(-byte0 / 2) - 7.0F);
		this.boxes[15].rotateAngleX = 1.570796F;
		this.boxes[15].rotateAngleZ = 1.570796F;
		this.boxes[16].addBox((float)(-byte3 - 2), -0.5F, -0.5F, byte0 / 2, 1, 2, 0.0F);
		this.boxes[16].setPosition(9.5F, 0.0F, (float)(-byte0 / 2) - 7.0F);
		this.boxes[16].rotateAngleX = 1.570796F;
		this.boxes[16].rotateAngleZ = 1.570796F;
		this.boxes[17].addBox((float)(-byte3 - 2), -0.5F, -0.5F, byte0 / 2, 1, 2, 0.0F);
		this.boxes[17].setPosition(9.5F, 0.0F, (float)(-byte0 / 2) - 7.0F);
		this.boxes[17].rotateAngleX = 1.570796F;
		this.boxes[17].rotateAngleZ = 1.570796F;
		this.boxes[18].addBox((float)(-byte3 - 2), -0.5F, -0.5F, byte0 / 2, 1, 2, 0.0F);
		this.boxes[18].setPosition(9.5F, 0.0F, (float)(-byte0 / 2) - 7.0F);
		this.boxes[18].rotateAngleX = 1.570796F;
		this.boxes[18].rotateAngleZ = 1.570796F;
		this.boxes[19].addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		if(mod_airship.SHOW_BOILER) {
			this.boxes[20].addBox(-8.0F, -4.0F, 0.0F, 10, 8, 4, 0.0F);
			this.boxes[21].addBox(-2.0F, -13.0F, -1.0F, 2, 14, 2, 0.0F);
		} else {
			this.boxes[20].addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
			this.boxes[21].addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		}

		this.boxes[20].setPosition(0.0F, 3.0F, 0.0F);
		this.boxes[20].rotateAngleX = 1.570796F;
		this.boxes[21].setPosition(0.0F, 0.0F, 0.0F);
		this.boxes[22].addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
	}

	public void render(float f, float f1, float f2, float f3, float f4, float f5) {
		long now = System.nanoTime();
		int elapsed = (int)((now - this.lastframe) / 1000000L);
		this.bladespin -= (float)elapsed / 300.0F;
		this.lastframe = now;
		this.boxes[10].rotateAngleY = this.bladespin;
		this.boxes[11].rotateAngleY = this.bladespin + 0.78539824F;
		this.boxes[12].rotateAngleY = this.bladespin + 1.570796F;
		this.boxes[13].rotateAngleY = this.bladespin + 2.3561947F;
		this.boxes[15].rotateAngleY = this.bladespin;
		this.boxes[16].rotateAngleY = this.bladespin + 0.78539824F;
		this.boxes[17].rotateAngleY = this.bladespin + 1.570796F;
		this.boxes[18].rotateAngleY = this.bladespin + 2.3561947F;

		for(int i = 0; i < 27; ++i) {
			this.boxes[i].render(f5);
		}

	}
}
