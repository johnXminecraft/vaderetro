package net.minecraft.src.vaderetro.johnmillmod.entity.tileentity;

import net.minecraft.src.*;

public class TileEntityAxleRod extends TileEntity {
    public float rotation;
    public float prevRotation;

	public void updateEntity() {
		this.prevRotation = this.rotation;
		if (this.worldObj == null) return;
        boolean powered = isPowered();
		if (powered) {
			this.rotation += 10.0f; 
			if (this.rotation >= 360.0f) this.rotation -= 360.0f;
		} else {
			if (this.rotation != 0.0f) {
				this.rotation *= 0.9f;
				if (Math.abs(this.rotation) < 0.01f) this.rotation = 0.0f;
			}
		}
	}

	private int getOpposite(int side) {
		switch (side) {
			case 0: return 1;
			case 1: return 0;
			case 2: return 3;
			case 3: return 2;
			case 4: return 5;
			case 5: return 4;
			default: return side;
		}
	}

	private boolean isMillAxlePoweredAt(int ax, int ay, int az) {
		int millMeta = this.worldObj.getBlockMetadata(ax, ay, az) & 7;
		int inputSide = millMeta;
		int ox = ax + (inputSide == 5 ? 1 : inputSide == 4 ? -1 : 0);
		int oy = ay + (inputSide == 1 ? 1 : inputSide == 0 ? -1 : 0);
		int oz = az + (inputSide == 3 ? 1 : inputSide == 2 ? -1 : 0);
		int nid = this.worldObj.getBlockId(ox, oy, oz);
		return nid == Block.johnMill.blockID;
	}

	private boolean isPowered() {
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) & 3;
		int[][] dirs;
		if (meta == 1) dirs = new int[][] { {0,-1,0,0}, {0,1,0,1} };
		else if (meta == 2) dirs = new int[][] { {0,0,-1,2}, {0,0,1,3} };
		else dirs = new int[][] { {-1,0,0,4}, {1,0,0,5} };
		for (int d = 0; d < 2; d++) {
			int dx = dirs[d][0], dy = dirs[d][1], dz = dirs[d][2];
			int sideToward = dirs[d][3];
			int cx = this.xCoord, cy = this.yCoord, cz = this.zCoord;
			while (true) {
				cx += dx; cy += dy; cz += dz;
				int id = this.worldObj.getBlockId(cx, cy, cz);
				if (id == Block.axleRod.blockID) {
					continue;
				} else if (id == Block.millAxle.blockID) {
					int inputSide = this.worldObj.getBlockMetadata(cx, cy, cz) & 7;
					int outputSide = getOpposite(inputSide);
					if (sideToward == outputSide && isMillAxlePoweredAt(cx, cy, cz)) {
						return true;
					}
					break;
				} else {
					break;
				}
			}
		}
		return false;
	}
}
