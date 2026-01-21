package net.minecraft.src.JPM;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Frustrum;

public class FrustrumIsom extends Frustrum {
	public boolean isBoxInFrustum(double d1, double d3, double d5, double d7, double d9, double d11) {
		return true;
	}

	public boolean isBoundingBoxInFrustum(AxisAlignedBB axisAlignedBB1) {
		return true;
	}
}
