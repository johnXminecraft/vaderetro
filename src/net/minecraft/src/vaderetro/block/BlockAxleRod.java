package net.minecraft.src.vaderetro.block;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityAxleRod;

public class BlockAxleRod extends BlockContainer {
	private static final float THICKNESS = 0.25F;

	public BlockAxleRod(int id, int textureIndex) {
		super(id, textureIndex, Material.wood);
		this.setHardness(2.0F);
		this.blockResistance = 5.0F;
		this.stepSound = soundWoodFootstep;
		this.setBlockName("axleRod");
	}

	protected TileEntity getBlockEntity() {
		return new TileEntityAxleRod();
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return -1;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z) & 3;
		float half = THICKNESS * 0.5F;
		if (meta == 1) { // Y-axis
			this.setBlockBounds(0.5F - half, 0.0F, 0.5F - half, 0.5F + half, 1.0F, 0.5F + half);
		} else if (meta == 2) { // Z-axis
			this.setBlockBounds(0.5F - half, 0.5F - half, 0.0F, 0.5F + half, 0.5F + half, 1.0F);
		} else { // X-axis (default)
			this.setBlockBounds(0.0F, 0.5F - half, 0.5F - half, 1.0F, 0.5F + half, 0.5F + half);
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (!(te instanceof TileEntityAxleRod)) {
			world.setBlockTileEntity(x, y, z, new TileEntityAxleRod());
		}
        
	}


	public void onBlockPlaced(World world, int x, int y, int z, int side) {
		int meta = axisFromSide(side);
		meta = chooseAxisFromNeighbors(world, x, y, z, meta);
		world.setBlockMetadataWithNotify(x, y, z, meta);
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving placer) {
		int meta = world.getBlockMetadata(x, y, z) & 3;
		if (meta == 0) {
			boolean vertical = Math.abs(placer.rotationPitch) > 60.0F;
			if (vertical) {
				meta = 1;
			} else {
				int yawIdx = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
				if (yawIdx == 0 || yawIdx == 2) meta = 2; // facing north/south -> Z-axis
				else meta = 0;
			}
			meta = chooseAxisFromNeighbors(world, x, y, z, meta);
			world.setBlockMetadataWithNotify(x, y, z, meta);
		}
	}

	private int chooseAxisFromNeighbors(World world, int x, int y, int z, int fallbackMeta) {
		int[][] neighbors = new int[][] { { -1,0,0 }, { 1,0,0 }, { 0,-1,0 }, { 0,1,0 }, { 0,0,-1 }, { 0,0,1 } };
		for (int i = 0; i < neighbors.length; i++) {
			int nx = x + neighbors[i][0];
			int ny = y + neighbors[i][1];
			int nz = z + neighbors[i][2];
			int id = world.getBlockId(nx, ny, nz);
			if (id == Block.millAxle.blockID) {
				int millMeta = world.getBlockMetadata(nx, ny, nz) & 7;
				int side = sideFromOffset(neighbors[i][0], neighbors[i][1], neighbors[i][2]);
				int input = millMeta;
				int output = getOpposite(input);
				if (side == output) {
					return axisFromSide(side);
				}
			}
			if (id == Block.axleRod.blockID) {
				int axis = world.getBlockMetadata(nx, ny, nz) & 3;
				return axis;
			}
		}
		return fallbackMeta & 3;
	}

	private int sideFromOffset(int dx, int dy, int dz) {
		if (dx == -1) return 4; // west
		if (dx == 1) return 5;  // east
		if (dz == -1) return 2; // north
		if (dz == 1) return 3;  // south
		if (dy == -1) return 0; // down
		if (dy == 1) return 1;  // up
		return 1;
	}

	private int axisFromSide(int side) {
		if (side == 0 || side == 1) return 1; // Y-axis
		if (side == 2 || side == 3) return 2; // Z-axis
		return 0;
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

	public int getBlockTextureFromSide(int side) {
		return 4;
	}

	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		int nx = x + (side == 4 ? 1 : side == 5 ? -1 : 0);
		int ny = y + (side == 1 ? -1 : side == 0 ? 1 : 0);
		int nz = z + (side == 2 ? 1 : side == 3 ? -1 : 0);
		int nid = world.getBlockId(nx, ny, nz);
		if (nid == Block.axleRod.blockID) {
			int neighborAxis = world.getBlockMetadata(nx, ny, nz) & 3;
			return neighborAxis == axisFromSide(side);
		}
		if (nid == Block.millAxle.blockID) {
			int millMeta = world.getBlockMetadata(nx, ny, nz) & 7;
			int inputSide = millMeta;
			int outputSide = getOpposite(inputSide);
			int neighborFace = getOpposite(side);
			return neighborFace == outputSide || neighborFace == inputSide;
		}
		if (nid == Block.gearbox.blockID) { 
			if (side == 0) return true;
			
			int gearboxMeta = world.getBlockMetadata(nx, ny, nz) & 3;
			int inputSide = gearboxMeta == 0 ? 2 : gearboxMeta == 1 ? 5 : gearboxMeta == 2 ? 3 : 4;
			int neighborFace = getOpposite(side);
			return neighborFace == inputSide;
		}
		return false;
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		int[][] neighbors = new int[][] { { -1,0,0,4 }, { 1,0,0,5 }, { 0,-1,0,0 }, { 0,1,0,1 }, { 0,0,-1,2 }, { 0,0,1,3 } };
		for (int i = 0; i < neighbors.length; i++) {
			if (canPlaceBlockOnSide(world, x, y, z, neighbors[i][3])) return true;
		}
		return false;
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

	public void randomDisplayTick(World world, int x, int y, int z, java.util.Random rand) {
		if (isPowered(world, x, y, z)) {
			double px = (double)x + 0.5D;
			double py = (double)y + 0.5D;
			double pz = (double)z + 0.5D;
			for (int i = 0; i < 2; i++) {
				world.spawnParticle("smoke", px + (rand.nextDouble()-0.5D)*0.2D, py + (rand.nextDouble()-0.5D)*0.2D, pz + (rand.nextDouble()-0.5D)*0.2D, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
