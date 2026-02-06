package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;

public class TileEntityAxleRod extends TileEntity {
    public float rotation;
	public float prevRotation;
	public float rotationSpeed;
	private boolean needsUpdate = true;

	public void forceUpdate() {
		this.needsUpdate = true;
	}

	public void updateEntity() {
		this.prevRotation = this.rotation;
		if (this.worldObj == null) return;

		if (this.needsUpdate || (this.worldObj.getWorldTime() + this.xCoord + this.yCoord + this.zCoord) % 20 == 0) {
			this.rotationSpeed = getRotationSpeedFromPowerSource();
			this.needsUpdate = false;
		}

		if (this.rotationSpeed != 0.0f) {
			this.rotation += this.rotationSpeed;
			if (this.rotation >= 360.0f) this.rotation -= 360.0f;
			if (this.rotation < 0.0f) this.rotation += 360.0f;
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
		return getMillAxleRotationSpeed(this.worldObj, ax, ay, az) != 0.0f;
	}

	private boolean isPowered() {
		return getRotationSpeedFromPowerSource() != 0.0f;
	}

	private float getRotationSpeedFromPowerSource() {
		return getRotationSpeedForRodAt(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 16);
	}

	private float getRotationSpeedForRodAt(World world, int rx, int ry, int rz, int depth) {
		if (depth <= 0) return 0.0f;
		float speed;
		speed = getRotationSpeedAlongAxis(world, rx, ry, rz, depth);
		if (speed != 0.0f) return speed;
		speed = getRotationSpeedFromAdjacentGearbox(world, rx, ry, rz, depth);
		if (speed != 0.0f) return speed;
		return 0.0f;
	}

	private float getRotationSpeedAlongAxis(World world, int rx, int ry, int rz, int depth) {
		int meta = world.getBlockMetadata(rx, ry, rz) & 3;
		int[][] dirs;
		if (meta == 1) dirs = new int[][] { {0,-1,0,0}, {0,1,0,1} };
		else if (meta == 2) dirs = new int[][] { {0,0,-1,2}, {0,0,1,3} };
		else dirs = new int[][] { {-1,0,0,4}, {1,0,0,5} };
		for (int d = 0; d < 2; d++) {
			int dx = dirs[d][0], dy = dirs[d][1], dz = dirs[d][2];
			int sideToward = dirs[d][3];
			int cx = rx, cy = ry, cz = rz;
			while (true) {
				cx += dx; cy += dy; cz += dz;
				int id = world.getBlockId(cx, cy, cz);
				if (id == Block.axleRod.blockID) {
					continue;
				} else if (id == Block.millAxle.blockID) {
					int inputSide = world.getBlockMetadata(cx, cy, cz) & 7;
					int outputSide = getOpposite(inputSide);
					int fromAxleTowardRod = getOpposite(sideToward);
					if (fromAxleTowardRod == outputSide) {
						float speed = getMillAxleRotationSpeed(world, cx, cy, cz);
						if (speed != 0.0f) return speed;
					}
					break;
				} else if (id == Block.gearbox.blockID) {
					int gmeta = world.getBlockMetadata(cx, cy, cz) & 3;
					int inputSide = gmeta == 0 ? 2 : gmeta == 1 ? 5 : gmeta == 2 ? 3 : 4;
					int sideOfGearboxFacingRod = offsetToSide(-dx, -dy, -dz);
					if (sideOfGearboxFacingRod != inputSide) {
						float speed = getGearboxRotationSpeed(world, cx, cy, cz, depth - 1);
						if (speed != 0.0f) return speed;
					}
					break;
				} else {
					break;
				}
			}
		}
		return 0.0f;
	}

	private float getRotationSpeedFromAdjacentGearbox(World world, int rx, int ry, int rz, int depth) {
		int[][] neighbors = new int[][] { {-1,0,0}, {1,0,0}, {0,-1,0}, {0,1,0}, {0,0,-1}, {0,0,1} };
		for (int i = 0; i < neighbors.length; i++) {
			int dx = neighbors[i][0], dy = neighbors[i][1], dz = neighbors[i][2];
			int gx = rx + dx, gy = ry + dy, gz = rz + dz;
			if (world.getBlockId(gx, gy, gz) != Block.gearbox.blockID) continue;
			int gmeta = world.getBlockMetadata(gx, gy, gz) & 3;
			int inputSide = gmeta == 0 ? 2 : gmeta == 1 ? 5 : gmeta == 2 ? 3 : 4;
			int sideOfGearboxFacingRod = offsetToSide(-dx, -dy, -dz);
			if (sideOfGearboxFacingRod != inputSide) {
				float speed = getGearboxRotationSpeed(world, gx, gy, gz, depth - 1);
				if (speed != 0.0f) return speed;
			}
		}
		return 0.0f;
	}

	private int offsetToSide(int dx, int dy, int dz) {
		if (dy == -1) return 0;
		if (dy == 1) return 1;
		if (dz == -1) return 2;
		if (dz == 1) return 3;
		if (dx == -1) return 4;
		if (dx == 1) return 5;
		return 0;
	}

	private float getGearboxRotationSpeed(World world, int gx, int gy, int gz, int depth) {
		int gmeta = world.getBlockMetadata(gx, gy, gz) & 3;
		int inputSide = gmeta == 0 ? 2 : gmeta == 1 ? 5 : gmeta == 2 ? 3 : 4;
		int nx = gx + (inputSide == 4 ? 1 : inputSide == 5 ? -1 : 0);
		int ny = gy + (inputSide == 1 ? -1 : inputSide == 0 ? 1 : 0);
		int nz = gz + (inputSide == 2 ? 1 : inputSide == 3 ? -1 : 0);
		if (world.getBlockId(nx, ny, nz) != Block.axleRod.blockID) return 0.0f;
		return getRotationSpeedForRodAt(world, nx, ny, nz, depth);
	}

	private float getMillAxleRotationSpeed(World world, int ax, int ay, int az) {
		int millMeta = world.getBlockMetadata(ax, ay, az) & 7;
		int inputSide = millMeta;
		int ox = ax + (inputSide == 5 ? 1 : inputSide == 4 ? -1 : 0);
		int oy = ay + (inputSide == 1 ? 1 : inputSide == 0 ? -1 : 0);
		int oz = az + (inputSide == 3 ? 1 : inputSide == 2 ? -1 : 0);
		int nid = world.getBlockId(ox, oy, oz);
		if (nid == Block.johnMill.blockID) return 1.0f;
		if (nid == Block.waterWheel.blockID) {
			if (world.getBlockId(ox, oy, oz) != Block.waterWheel.blockID) return 0.0f;
			TileEntity te = world.getBlockTileEntity(ox, oy, oz);
			if (te instanceof TileEntityWaterWheel && ((TileEntityWaterWheel) te).providingPower) {
				return ((TileEntityWaterWheel) te).rotationSpeed;
			}
		}
		return 0.0f;
	}
}
