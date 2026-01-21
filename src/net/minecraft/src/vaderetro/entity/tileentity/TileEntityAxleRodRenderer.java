package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import net.minecraft.src.Tessellator;

public class TileEntityAxleRodRenderer extends TileEntitySpecialRenderer {
	public void renderTileEntityAxleRodAt(TileEntityAxleRod te, double x, double y, double z, float partial) {
        int meta = te.worldObj.getBlockMetadata(te.xCoord, te.yCoord, te.zCoord) & 3;
        boolean powered = isPowered(te.worldObj, te.xCoord, te.yCoord, te.zCoord);
        float ticks = (float)(te.worldObj.getWorldTime() & 0x7FFFFFFF) + partial;
        float speedDegPerTick = powered ? 1.0f : 0.0f; 
        float angle = (-ticks * speedDegPerTick) % 360.0f;
        if (angle < 0.0f) angle += 360.0f;
        
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);

		if (meta == 1) {
			GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		} else if (meta == 0) {
			GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		}

        GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);

		this.bindTextureByName("/terrain.png");
		renderRodQuadPrism(0.0625f);
		GL11.glPopMatrix();
	}

	private void renderRodQuadPrism(float scale) {
		float half = 2.0f * scale;
		float z0 = -8.0f * scale;
		float z1 = 8.0f * scale;

		int idx = Block.planks.blockIndexInTexture;
		float u0 = (idx % 16) / 16.0f;
		float v0 = (idx / 16) / 16.0f;
		float u1 = u0 + 1.0f / 16.0f;
		float v1 = v0 + 1.0f / 16.0f;

		Tessellator t = Tessellator.instance;

		t.startDrawingQuads();
		t.addVertexWithUV(-half, -half, z1, u0, v1);
		t.addVertexWithUV( half, -half, z1, u1, v1);
		t.addVertexWithUV( half,  half, z1, u1, v0);
		t.addVertexWithUV(-half,  half, z1, u0, v0);
		t.draw();

		t.startDrawingQuads();
		t.addVertexWithUV( half, -half, z0, u0, v1);
		t.addVertexWithUV(-half, -half, z0, u1, v1);
		t.addVertexWithUV(-half,  half, z0, u1, v0);
		t.addVertexWithUV( half,  half, z0, u0, v0);
		t.draw();

		t.startDrawingQuads();
		t.addVertexWithUV(-half, -half, z0, u0, v1);
		t.addVertexWithUV(-half, -half, z1, u1, v1);
		t.addVertexWithUV(-half,  half, z1, u1, v0);
		t.addVertexWithUV(-half,  half, z0, u0, v0);
		t.draw();

		t.startDrawingQuads();
		t.addVertexWithUV( half, -half, z1, u0, v1);
		t.addVertexWithUV( half, -half, z0, u1, v1);
		t.addVertexWithUV( half,  half, z0, u1, v0);
		t.addVertexWithUV( half,  half, z1, u0, v0);
		t.draw();

		t.startDrawingQuads();
		t.addVertexWithUV(-half, -half, z0, u0, v1);
		t.addVertexWithUV( half, -half, z0, u1, v1);
		t.addVertexWithUV( half, -half, z1, u1, v0);
		t.addVertexWithUV(-half, -half, z1, u0, v0);
		t.draw();

		t.startDrawingQuads();
		t.addVertexWithUV(-half,  half, z1, u0, v1);
		t.addVertexWithUV( half,  half, z1, u1, v1);
		t.addVertexWithUV( half,  half, z0, u1, v0);
		t.addVertexWithUV(-half,  half, z0, u0, v0);
		t.draw();
	}

	private boolean isPowered(World world, int x, int y, int z) {
		int axis = world.getBlockMetadata(x, y, z) & 3;
		int[][] dirs;
		if (axis == 1) dirs = new int[][] { {0,-1,0,0}, {0,1,0,1} }; 
		else if (axis == 2) dirs = new int[][] { {0,0,-1,2}, {0,0,1,3} };
		else dirs = new int[][] { {-1,0,0,4}, {1,0,0,5} };
		for (int d = 0; d < 2; d++) {
			int dx = dirs[d][0], dy = dirs[d][1], dz = dirs[d][2];
			int sideToward = dirs[d][3];
			int cx = x, cy = y, cz = z;
			while (true) {
				cx += dx; cy += dy; cz += dz;
                int id = world.getBlockId(cx, cy, cz);
				if (id == Block.axleRod.blockID) {
					continue;
				} else if (id == Block.millAxle.blockID) {
					int inputSide = world.getBlockMetadata(cx, cy, cz) & 7;
					int outputSide = getOpposite(inputSide);
					int fromAxleTowardRod = getOpposite(sideToward);
                    if (fromAxleTowardRod == outputSide && isMillAxlePoweredAt(world, cx, cy, cz)) {
						return true;
					}
					break;
				} else if (id == Block.gearbox.blockID) {
					if (isGearboxPoweredAt(world, cx, cy, cz)) {
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

	private boolean isMillAxlePoweredAt(World world, int ax, int ay, int az) {
		int millMeta = world.getBlockMetadata(ax, ay, az) & 7;
		int inputSide = millMeta;
		int ox = ax + (inputSide == 5 ? 1 : inputSide == 4 ? -1 : 0);
		int oy = ay + (inputSide == 1 ? 1 : inputSide == 0 ? -1 : 0);
		int oz = az + (inputSide == 3 ? 1 : inputSide == 2 ? -1 : 0);
		int nid = world.getBlockId(ox, oy, oz);
		return nid == Block.johnMill.blockID;
	}

	private boolean isGearboxPoweredAt(World world, int gx, int gy, int gz) {
		int meta = world.getBlockMetadata(gx, gy, gz) & 3;
		int inputSide = meta == 0 ? 2 : meta == 1 ? 5 : meta == 2 ? 3 : 4;
		
		int nx = gx + (inputSide == 4 ? 1 : inputSide == 5 ? -1 : 0);
		int ny = gy + (inputSide == 1 ? -1 : inputSide == 0 ? 1 : 0);
		int nz = gz + (inputSide == 2 ? 1 : inputSide == 3 ? -1 : 0);
		
		int nid = world.getBlockId(nx, ny, nz);
		if (nid == Block.axleRod.blockID) {
			return isAxleRodPowered(world, nx, ny, nz);
		}
		return false;
	}

	private boolean isAxleRodPowered(World world, int x, int y, int z) {
		int axis = world.getBlockMetadata(x, y, z) & 3;
		int[][] dirs;
		if (axis == 1) dirs = new int[][] { {0,-1,0,0}, {0,1,0,1} };
		else if (axis == 2) dirs = new int[][] { {0,0,-1,2}, {0,0,1,3} };
		else dirs = new int[][] { {-1,0,0,4}, {1,0,0,5} };
		
		for (int d = 0; d < 2; d++) {
			int dx = dirs[d][0], dy = dirs[d][1], dz = dirs[d][2];
			int sideToward = dirs[d][3];
			int cx = x, cy = y, cz = z;
			while (true) {
				cx += dx; cy += dy; cz += dz;
				int id = world.getBlockId(cx, cy, cz);
				if (id == Block.axleRod.blockID) {
					continue;
				} else if (id == Block.millAxle.blockID) {
					int inputSide = world.getBlockMetadata(cx, cy, cz) & 7;
					int outputSide = getOpposite(inputSide);
					int fromAxleTowardRod = getOpposite(sideToward);
					if (fromAxleTowardRod == outputSide && isMillAxlePoweredAt(world, cx, cy, cz)) {
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

	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partial) {
		this.renderTileEntityAxleRodAt((TileEntityAxleRod)te, x, y, z, partial);
	}
}
