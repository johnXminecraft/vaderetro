package net.minecraft.src.Airship;

import net.minecraft.src.*;

public class ModelBalloon extends ModelBase {
	public CustomModelRenderer boxes = new CustomModelRenderer(0, 0, 256, 256);

	public ModelBalloon() {
		this.boxes.addBox(0.0F, 0.0F, 0.0F, 64, 64, 64, -8.0F);
		this.boxes.setPosition(-30.0F, -15.0F, -30.0F);
		this.boxes.rotateAngleX = 1.570796F;
	}

	public void render(float f, float f1, float f2, float f3, float f4, float f5) {
		this.boxes.render(f5);
	}
}
