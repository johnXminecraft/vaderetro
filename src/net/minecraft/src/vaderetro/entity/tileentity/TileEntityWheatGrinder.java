package net.minecraft.src.vaderetro.entity.tileentity;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.block.BlockDryer;
import net.minecraft.src.vaderetro.recipes.processors.DryerRecipes;
import net.minecraft.src.vaderetro.recipes.processors.WheatGrinderRecipes;

public class TileEntityWheatGrinder extends TileEntity implements IInventory {
	private ItemStack[] grinderItemStacks = new ItemStack[2]; 
	public int grinderBurnTime = 0;
	public int currentItemBurnTime = 0;
	public int grinderCookTime = 0;
	
	@Override
	public int getSizeInventory() {
		return this.grinderItemStacks.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.grinderItemStacks[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.grinderItemStacks[slot] != null) {
			ItemStack itemStack;
			if (this.grinderItemStacks[slot].stackSize <= amount) {
				itemStack = this.grinderItemStacks[slot];
				this.grinderItemStacks[slot] = null;
			} else {
				itemStack = this.grinderItemStacks[slot].splitStack(amount);
				if (this.grinderItemStacks[slot].stackSize == 0) {
					this.grinderItemStacks[slot] = null;
				}
			}
			return itemStack;
		} else {
			return null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		this.grinderItemStacks[slot] = itemStack;
		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	@Override
	public String getInvName() {
		return "Wheat Grinder";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList tagList = nbt.getTagList("Items");
		this.grinderItemStacks = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tagCompound.getByte("Slot");
			if (slot >= 0 && slot < this.grinderItemStacks.length) {
				this.grinderItemStacks[slot] = new ItemStack(tagCompound);
			}
		}
		
		this.grinderBurnTime = nbt.getShort("BurnTime");
		this.grinderCookTime = nbt.getShort("CookTime");
		this.currentItemBurnTime = 50;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("BurnTime", (short) this.grinderBurnTime);
		nbt.setShort("CookTime", (short) this.grinderCookTime);
		NBTTagList tagList = new NBTTagList();
		
		for (int i = 0; i < this.grinderItemStacks.length; i++) {
			if (this.grinderItemStacks[i] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) i);
				this.grinderItemStacks[i].writeToNBT(tagCompound);
				tagList.setTag(tagCompound);
			}
		}
		
		nbt.setTag("Items", tagList);
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public int getCookProgressScaled(int scale) {
		return this.grinderCookTime * scale / 50;
	}
	
	public int getBurnTimeRemainingScaled(int scale) {
		if (this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = 50;
		}
		return this.grinderBurnTime * scale / this.currentItemBurnTime;
	}
	
	public boolean isBurning() {
		return this.grinderBurnTime > 0;
	}
	
	public boolean isPowered() {
		int id = this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord);
		if (id == Block.axleRod.blockID) {
			return isAxleRodPowered(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord);
		}
		return false;
	}
	
	private boolean isAxleRodPowered(World world, int x, int y, int z) {
		return isAxleRodPowered(world, x, y, z, new java.util.HashSet<String>());
	}
	
	private boolean isAxleRodPowered(World world, int x, int y, int z, java.util.Set<String> visited) {
		String key = x + "," + y + "," + z;
		if (visited.contains(key)) {
			return false;
		}
		visited.add(key);
		
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
					if (isGearboxPoweredAt(world, cx, cy, cz, visited)) {
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
		return isGearboxPoweredAt(world, gx, gy, gz, new java.util.HashSet<String>());
	}
	
	private boolean isGearboxPoweredAt(World world, int gx, int gy, int gz, java.util.Set<String> visited) {
		String key = gx + "," + gy + "," + gz;
		if (visited.contains(key)) {
			return false;
		}
		visited.add(key);
		
		int meta = world.getBlockMetadata(gx, gy, gz) & 3;
		int inputSide = meta == 0 ? 2 : meta == 1 ? 5 : meta == 2 ? 3 : 4;
		
		int nx = gx + (inputSide == 4 ? 1 : inputSide == 5 ? -1 : 0);
		int ny = gy + (inputSide == 1 ? -1 : inputSide == 0 ? 1 : 0);
		int nz = gz + (inputSide == 2 ? 1 : inputSide == 3 ? -1 : 0);
		
		int nid = world.getBlockId(nx, ny, nz);
		if (nid == Block.axleRod.blockID) {
			return isAxleRodPowered(world, nx, ny, nz, visited);
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

	// rework
	@Override
	public void updateEntity() {
		boolean wasBurning = this.isBurning();
		boolean powered = this.isPowered();
		
		if (powered && this.grinderItemStacks[0] != null && canProcess()) {
			this.grinderCookTime++;
			if (this.grinderCookTime >= 50) {
				if(canProcess()) {
					this.grinderCookTime = 0;
					this.processItem();
				}
			}
		} else {
			this.grinderCookTime = 0;
		}
		
		if (wasBurning != this.isBurning()) {
			this.onInventoryChanged();
		}
	}

	private boolean canProcess() {
		if(this.grinderItemStacks[0] == null) {
			return false;
		} else {
			ItemStack output = WheatGrinderRecipes.processing().getResult(this.grinderItemStacks[0].getItem().shiftedIndex);
			return output != null
					&& (this.grinderItemStacks[1] == null
					|| (this.grinderItemStacks[1].isItemEqual(output)
					&& (this.grinderItemStacks[1].stackSize < this.getInventoryStackLimit()
					&& this.grinderItemStacks[1].stackSize < this.grinderItemStacks[1].getMaxStackSize()
					|| this.grinderItemStacks[1].stackSize < output.getMaxStackSize())));
		}
	}

	public void processItem() {
		if(this.canProcess()) {
			ItemStack var1 = WheatGrinderRecipes.processing().getResult(this.grinderItemStacks[0].getItem().shiftedIndex);
			if(this.grinderItemStacks[1] == null) {
				this.grinderItemStacks[1] = var1.copy();
			} else if(this.grinderItemStacks[1].itemID == var1.itemID) {
				++this.grinderItemStacks[1].stackSize;
			}
			--this.grinderItemStacks[0].stackSize;
			if(this.grinderItemStacks[0].stackSize <= 0) {
				this.grinderItemStacks[0] = null;
			}
		}
	}

	public boolean canInteractWith(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
	}
}
