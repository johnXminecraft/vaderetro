package net.minecraft.src;

import net.minecraft.src.vaderetro._BML.ItemObjSpawner;
import net.minecraft.src.vaderetro._BML.ItemUVMapGenerator;
import net.minecraft.src.vaderetro.item.ItemAirship;
import net.minecraft.src.vaderetro.item.*;
import net.minecraft.src.vaderetro.item.ItemIcon;

import java.util.Random;

public class Item {
	protected static Random itemRand = new Random();
	public static Item[] itemsList = new Item[32000];
	public static Item shovelSteel = (new ItemSpade(0, EnumToolMaterial.IRON)).setIconCoord(2, 5).setItemName("shovelIron");
	public static Item pickaxeSteel = (new ItemPickaxe(1, EnumToolMaterial.IRON)).setIconCoord(2, 6).setItemName("pickaxeIron");
	public static Item axeSteel = (new ItemAxe(2, EnumToolMaterial.IRON)).setIconCoord(2, 7).setItemName("hatchetIron");
	public static Item flintAndSteel = (new ItemFlintAndSteel(3)).setIconCoord(5, 0).setItemName("flintAndSteel");
	public static Item appleRed = (new ItemFood(4, 4, false)).setIconCoord(10, 0).setItemName("apple");
	public static Item bow = (new ItemBow(5)).setIconCoord(5, 1).setItemName("bow");
	public static Item arrow = (new Item(6)).setIconCoord(5, 2).setItemName("arrow");
	public static Item coal = (new Item(7)).setIconCoord(7, 0).setItemName("coal");
	public static Item diamond = (new Item(8)).setIconCoord(7, 3).setItemName("emerald");
	public static Item ingotIron = (new Item(9)).setIconCoord(7, 1).setItemName("ingotIron");
	public static Item ingotGold = (new Item(10)).setIconCoord(7, 2).setItemName("ingotGold");
	public static Item swordSteel = (new ItemSword(11, EnumToolMaterial.IRON)).setIconCoord(2, 4).setItemName("swordIron");
	public static Item swordWood = (new ItemSword(12, EnumToolMaterial.WOOD)).setIconCoord(0, 4).setItemName("swordWood");
	public static Item shovelWood = (new ItemSpade(13, EnumToolMaterial.WOOD)).setIconCoord(0, 5).setItemName("shovelWood");
	public static Item pickaxeWood = (new ItemPickaxe(14, EnumToolMaterial.WOOD)).setIconCoord(0, 6).setItemName("pickaxeWood");
	public static Item axeWood = (new ItemAxe(15, EnumToolMaterial.WOOD)).setIconCoord(0, 7).setItemName("hatchetWood");
	public static Item swordStone = (new ItemSword(16, EnumToolMaterial.STONE)).setIconCoord(1, 4).setItemName("swordStone");
	public static Item shovelStone = (new ItemSpade(17, EnumToolMaterial.STONE)).setIconCoord(1, 5).setItemName("shovelStone");
	public static Item pickaxeStone = (new ItemPickaxe(18, EnumToolMaterial.STONE)).setIconCoord(1, 6).setItemName("pickaxeStone");
	public static Item axeStone = (new ItemAxe(19, EnumToolMaterial.STONE)).setIconCoord(1, 7).setItemName("hatchetStone");
	public static Item swordDiamond = (new ItemSword(20, EnumToolMaterial.EMERALD)).setIconCoord(3, 4).setItemName("swordDiamond");
	public static Item shovelDiamond = (new ItemSpade(21, EnumToolMaterial.EMERALD)).setIconCoord(3, 5).setItemName("shovelDiamond");
	public static Item pickaxeDiamond = (new ItemPickaxe(22, EnumToolMaterial.EMERALD)).setIconCoord(3, 6).setItemName("pickaxeDiamond");
	public static Item axeDiamond = (new ItemAxe(23, EnumToolMaterial.EMERALD)).setIconCoord(3, 7).setItemName("hatchetDiamond");
	public static Item stick = (new Item(24)).setIconCoord(5, 3).setFull3D().setItemName("stick");
	public static Item bowlEmpty = (new Item(25)).setIconCoord(7, 4).setItemName("bowl");
	public static Item bowlSoup = (new ItemSoup(26, 10)).setIconCoord(8, 4).setItemName("mushroomStew");
	public static Item swordGold = (new ItemSword(27, EnumToolMaterial.GOLD)).setIconCoord(4, 4).setItemName("swordGold");
	public static Item shovelGold = (new ItemSpade(28, EnumToolMaterial.GOLD)).setIconCoord(4, 5).setItemName("shovelGold");
	public static Item pickaxeGold = (new ItemPickaxe(29, EnumToolMaterial.GOLD)).setIconCoord(4, 6).setItemName("pickaxeGold");
	public static Item axeGold = (new ItemAxe(30, EnumToolMaterial.GOLD)).setIconCoord(4, 7).setItemName("hatchetGold");
	public static Item silk = (new Item(31)).setIconCoord(8, 0).setItemName("string");
	public static Item feather = (new Item(32)).setIconCoord(8, 1).setItemName("feather");
	public static Item gunpowder = (new Item(33)).setIconCoord(8, 2).setItemName("sulphur");
	public static Item hoeWood = (new ItemHoe(34, EnumToolMaterial.WOOD)).setIconCoord(0, 8).setItemName("hoeWood");
	public static Item hoeStone = (new ItemHoe(35, EnumToolMaterial.STONE)).setIconCoord(1, 8).setItemName("hoeStone");
	public static Item hoeSteel = (new ItemHoe(36, EnumToolMaterial.IRON)).setIconCoord(2, 8).setItemName("hoeIron");
	public static Item hoeDiamond = (new ItemHoe(37, EnumToolMaterial.EMERALD)).setIconCoord(3, 8).setItemName("hoeDiamond");
	public static Item hoeGold = (new ItemHoe(38, EnumToolMaterial.GOLD)).setIconCoord(4, 8).setItemName("hoeGold");
	public static Item seeds = (new ItemSeeds(39, Block.crops.blockID)).setIconCoord(9, 0).setItemName("seeds");
	public static Item wheat = (new Item(40)).setIconCoord(9, 1).setItemName("wheat");
	public static Item bread = (new ItemFood(41, 5, false)).setIconCoord(9, 2).setItemName("bread");
	public static Item helmetLeather = (new ItemArmor(42, 0, 0, 0)).setIconCoord(0, 0).setItemName("helmetCloth");
	public static Item plateLeather = (new ItemArmor(43, 0, 0, 1)).setIconCoord(0, 1).setItemName("chestplateCloth");
	public static Item legsLeather = (new ItemArmor(44, 0, 0, 2)).setIconCoord(0, 2).setItemName("leggingsCloth");
	public static Item bootsLeather = (new ItemArmor(45, 0, 0, 3)).setIconCoord(0, 3).setItemName("bootsCloth");
	public static Item helmetChain = (new ItemArmor(46, 1, 1, 0)).setIconCoord(1, 0).setItemName("helmetChain");
	public static Item plateChain = (new ItemArmor(47, 1, 1, 1)).setIconCoord(1, 1).setItemName("chestplateChain");
	public static Item legsChain = (new ItemArmor(48, 1, 1, 2)).setIconCoord(1, 2).setItemName("leggingsChain");
	public static Item bootsChain = (new ItemArmor(49, 1, 1, 3)).setIconCoord(1, 3).setItemName("bootsChain");
	public static Item helmetSteel = (new ItemArmor(50, 2, 2, 0)).setIconCoord(2, 0).setItemName("helmetIron");
	public static Item plateSteel = (new ItemArmor(51, 2, 2, 1)).setIconCoord(2, 1).setItemName("chestplateIron");
	public static Item legsSteel = (new ItemArmor(52, 2, 2, 2)).setIconCoord(2, 2).setItemName("leggingsIron");
	public static Item bootsSteel = (new ItemArmor(53, 2, 2, 3)).setIconCoord(2, 3).setItemName("bootsIron");
	public static Item helmetDiamond = (new ItemArmor(54, 3, 3, 0)).setIconCoord(3, 0).setItemName("helmetDiamond");
	public static Item plateDiamond = (new ItemArmor(55, 3, 3, 1)).setIconCoord(3, 1).setItemName("chestplateDiamond");
	public static Item legsDiamond = (new ItemArmor(56, 3, 3, 2)).setIconCoord(3, 2).setItemName("leggingsDiamond");
	public static Item bootsDiamond = (new ItemArmor(57, 3, 3, 3)).setIconCoord(3, 3).setItemName("bootsDiamond");
	public static Item helmetGold = (new ItemArmor(58, 1, 4, 0)).setIconCoord(4, 0).setItemName("helmetGold");
	public static Item plateGold = (new ItemArmor(59, 1, 4, 1)).setIconCoord(4, 1).setItemName("chestplateGold");
	public static Item legsGold = (new ItemArmor(60, 1, 4, 2)).setIconCoord(4, 2).setItemName("leggingsGold");
	public static Item bootsGold = (new ItemArmor(61, 1, 4, 3)).setIconCoord(4, 3).setItemName("bootsGold");
	public static Item flint = (new Item(62)).setIconCoord(6, 0).setItemName("flint");
	public static Item porkRaw = (new ItemFood(63, 3, true)).setIconCoord(7, 5).setItemName("porkchopRaw");
	public static Item porkCooked = (new ItemFood(64, 8, true)).setIconCoord(8, 5).setItemName("porkchopCooked");
	public static Item painting = (new ItemPainting(65)).setIconCoord(10, 1).setItemName("painting");
	public static Item appleGold = (new ItemFood(66, 42, false)).setIconCoord(11, 0).setItemName("appleGold");
	public static Item sign = (new ItemSign(67)).setIconCoord(10, 2).setItemName("sign");
	public static Item doorWood = (new ItemDoor(68, Material.wood)).setIconCoord(11, 2).setItemName("doorWood");
	public static Item bucketEmpty = (new ItemBucket(69, 0)).setIconCoord(10, 4).setItemName("bucket");
	public static Item bucketWater = (new ItemBucket(70, Block.waterMoving.blockID)).setIconCoord(11, 4).setItemName("bucketWater").setContainerItem(bucketEmpty);
	public static Item bucketLava = (new ItemBucket(71, Block.lavaMoving.blockID)).setIconCoord(12, 4).setItemName("bucketLava").setContainerItem(bucketEmpty);
	public static Item minecartEmpty = (new ItemMinecart(72, 0)).setIconCoord(7, 8).setItemName("minecart");
	public static Item saddle = (new ItemSaddle(73)).setIconCoord(8, 6).setItemName("saddle");
	public static Item doorSteel = (new ItemDoor(74, Material.iron)).setIconCoord(12, 2).setItemName("doorIron");
	public static Item redstone = (new ItemRedstone(75)).setIconCoord(8, 3).setItemName("redstone");
	public static Item snowball = (new ItemSnowball(76)).setIconCoord(14, 0).setItemName("snowball");
	public static Item boat = (new ItemBoat(77)).setIconCoord(8, 8).setItemName("boat");
	public static Item leather = (new Item(78)).setIconCoord(7, 6).setItemName("leather");
	public static Item bucketMilk = (new ItemBucket(79, -1)).setIconCoord(13, 4).setItemName("milk").setContainerItem(bucketEmpty);
	public static Item brick = (new Item(80)).setIconCoord(6, 1).setItemName("brick");
	public static Item clay = (new Item(81)).setIconCoord(9, 3).setItemName("clay");
	public static Item reed = (new ItemReed(82, Block.reed)).setIconCoord(11, 1).setItemName("reeds");
	public static Item paper = (new Item(83)).setIconCoord(10, 3).setItemName("paper");
	public static Item book = (new Item(84)).setIconCoord(11, 3).setItemName("book");
	public static Item slimeBall = (new Item(85)).setIconCoord(14, 1).setItemName("slimeball");
	public static Item minecartCrate = (new ItemMinecart(86, 1)).setIconCoord(7, 9).setItemName("minecartChest");
	public static Item minecartPowered = (new ItemMinecart(87, 2)).setIconCoord(7, 10).setItemName("minecartFurnace");
	public static Item egg = (new ItemEgg(88)).setIconCoord(12, 0).setItemName("egg");
	public static Item compass = (new Item(89)).setIconCoord(6, 3).setItemName("compass");
	public static Item fishingRod = (new ItemFishingRod(90)).setIconCoord(5, 4).setItemName("fishingRod");
	public static Item pocketSundial = (new Item(91)).setIconCoord(6, 4).setItemName("clock");
	public static Item lightStoneDust = (new Item(92)).setIconCoord(9, 4).setItemName("yellowDust");
	public static Item fishRaw = (new ItemFood(93, 2, false)).setIconCoord(9, 5).setItemName("fishRaw");
	public static Item fishCooked = (new ItemFood(94, 5, false)).setIconCoord(10, 5).setItemName("fishCooked");
	public static Item dyePowder = (new ItemDye(95)).setIconCoord(14, 4).setItemName("dyePowder");
	public static Item bone = (new Item(96)).setIconCoord(12, 1).setItemName("bone").setFull3D();
	public static Item sugar = (new Item(97)).setIconCoord(13, 0).setItemName("sugar").setFull3D();
	public static Item cake = (new ItemReed(98, Block.cake)).setMaxStackSize(1).setIconCoord(13, 1).setItemName("cake");
	public static Item bed = (new ItemBed(99)).setMaxStackSize(1).setIconCoord(13, 2).setItemName("bed");
	public static Item redstoneRepeater = (new ItemReed(100, Block.redstoneRepeaterIdle)).setIconCoord(6, 5).setItemName("diode");
	public static Item cookie = (new ItemCookie(101, 1, false, 8)).setIconCoord(12, 5).setItemName("cookie");
	public static ItemMap mapItem = (ItemMap)(new ItemMap(102)).setIconCoord(12, 3).setItemName("map");
	public static ItemShears shears = (ItemShears)(new ItemShears(103)).setIconCoord(13, 5).setItemName("shears");
	public static Item record13 = (new ItemRecord(2000, "13")).setIconCoord(0, 15).setItemName("record");
	public static Item recordCat = (new ItemRecord(2001, "cat")).setIconCoord(1, 15).setItemName("record");

	public static Item ingotStainedSteel = (new Item(104)).setIconCoord(0, 9).setItemName("ingotStainedSteel");
	public static Item helmetStainedSteel = (new ItemArmor(105, 4, 5, 0)).setIconCoord(1, 9).setItemName("helmetStainedSteel");
	public static Item plateStainedSteel = (new ItemArmor(106, 4, 5, 1)).setIconCoord(1, 10).setItemName("chestplateStainedSteel");
	public static Item legsStainedSteel = (new ItemArmor(107, 4, 5, 2)).setIconCoord(1, 11).setItemName("leggingsStainedSteel");
	public static Item bootsStainedSteel = (new ItemArmor(108, 4, 5, 3)).setIconCoord(1, 12).setItemName("bootsStainedSteel");
	public static Item chain = (new Item(109)).setIconCoord(0, 11).setItemName("chain");
	public static Item swordStainedSteel = (new ItemSword(110, EnumToolMaterial.STAINED_STEEL)).setIconCoord(0, 10).setItemName("swordStainedSteel");
	public static Item shovelStainedSteel = (new ItemSpade(111, EnumToolMaterial.STAINED_STEEL)).setIconCoord(2, 10).setItemName("shovelStainedSteel");
	public static Item pickaxeStainedSteel = (new ItemPickaxe(112, EnumToolMaterial.STAINED_STEEL)).setIconCoord(2, 11).setItemName("pickaxeStainedSteel");
	public static Item axeStainedSteel = (new ItemAxe(113, EnumToolMaterial.STAINED_STEEL)).setIconCoord(2, 12).setItemName("hatchetStainedSteel");
	public static Item hoeStainedSteel = (new ItemHoe(114, EnumToolMaterial.STAINED_STEEL)).setIconCoord(2, 9).setItemName("hoeStainedSteel");
	public static Item ingotCopper = (new Item(115)).setIconCoord(4, 12).setItemName("ingotCopper");
	public static Item wire = (new Item(116)).setIconCoord(3, 11).setItemName("wire");
	public static Item bucketOil = (new ItemBucket(117, Block.oilMoving.blockID)).setIconCoord(0, 12).setItemName("bucketOil").setContainerItem(bucketEmpty);
	public static Item bucketKerosene = (new ItemBucket(118, -1)).setIconCoord(2, 13).setItemName("bucketKerosene").setContainerItem(bucketEmpty);
	public static Item ingotPlastic = (new Item(119)).setIconCoord(0, 13).setItemName("ingotPlastic");
	public static Item detailPlastic = (new Item(120)).setIconCoord(1, 13).setItemName("detailPlastic");
	public static Item anodeCathode = (new Item(121)).setIconCoord(3, 12).setItemName("anodeCathode");
	public static Item vacuumTube = (new Item(122)).setIconCoord(3, 10).setItemName("vacuumTube");
	public static Item circuitryPlateEmpty = (new Item(123)).setIconCoord(3, 9).setItemName("circuitryPlateEmpty");
	public static Item circuitryPlate = (new Item(124)).setIconCoord(4, 9).setItemName("circuitryPlate");
	public static Item resistor = (new Item(125)).setIconCoord(4, 10).setItemName("resistor");
	public static Item capacitor = (new Item(126)).setIconCoord(4, 11).setItemName("capacitor");
	public static Item cathodeRayTube = (new Item(127)).setIconCoord(3, 13).setItemName("cathodeRayTube");
	public static Item eggFried = (new ItemFood(128, 6, false)).setIconCoord(4, 13).setItemName("eggFried");
	public static Item ingotTin = (new Item(129)).setIconCoord(5, 11).setItemName("ingotTin");
	public static Item ingotSulfur = (new Item(130)).setIconCoord(5, 6).setItemName("ingotSulfur");
	public static Item musket = (new ItemMusket(131)).setIconCoord(5, 10).setItemName("musket");
	public static Item musketButt = (new Item(132)).setIconCoord(5, 9).setItemName("musketButt");
	public static Item musketBarrel = (new Item(133)).setIconCoord(5, 8).setItemName("musketBarrel");
	public static Item musketBullet = (new Item(134)).setIconCoord(5, 7).setItemName("musketBullet");
	public static Item ar15Bullet = (new Item(135)).setIconCoord(6, 8).setItemName("ar15Bullet");
	public static Item ar15 = (new ItemAR15(136)).setIconCoord(6, 10).setItemName("ar15");
	public static Item icon = (new ItemIcon(137)).setIconCoord(5, 12).setItemName("icon");
	public static Item canvas = (new Item(138)).setIconCoord(5, 13).setItemName("canvas");
	public static Item stainedSteelCompound = (new Item(139)).setIconCoord(6, 6).setItemName("stainedSteelCompound");
	public static Item charcoal = (new Item(140)).setIconCoord(6, 7).setItemName("charcoal");
	public static Item easterEgg = (new ItemEasterEgg(141)).setIconCoord(9, 6).setItemName("easterEgg");
	public static Item onion = (new ItemSeed(142, Block.onion.blockID)).setIconCoord(14, 2).setItemName("onion");
	public static Item tobaccoSeeds = (new ItemTobaccoSeeds(143, Block.tobaccoPlant.blockID)).setIconCoord(6, 2).setItemName("tobaccoSeeds");
	public static Item tobaccoLeaf = (new Item(144)).setIconCoord(13, 3).setItemName("tobaccoLeaf");
	public static Item tobacco = (new Item(145)).setIconCoord(14, 3).setItemName("tobacco");
	public static Item cigarette = (new ItemCigarette(146)).setIconCoord(11, 5).setItemName("cigarette");
	public static Item salt = (new Item(147)).setIconCoord(6, 9).setItemName("salt");
	public static Item rottenFlesh = (new Item(151)).setIconCoord(7, 7).setItemName("rottenFlesh");
	public static Item jerky = (new ItemJerky(152)).setIconCoord(8, 7).setItemName("jerky");
	public static Item cannabisSeeds = (new ItemCannabisSeeds(153, Block.cannabisPlant.blockID)).setIconCoord(7, 11).setItemName("cannabisSeeds");
	public static Item cannabisLeaf = (new Item(154)).setIconCoord(8, 11).setItemName("cannabisLeaf");
	public static Item rope = (new Item(155)).setIconCoord(7, 12).setItemName("rope");
	public static Item cloth = (new Item(156)).setIconCoord(8, 12).setItemName("cloth");
	public static Item uraniumDust = (new Item(157)).setIconCoord(8, 10).setItemName("uraniumDust");
	public static Item bowlOfGold = (new Item(158)).setIconCoord(8, 9).setItemName("bowlOfGold");
	public static Item driedLeather = (new Item(159)).setIconCoord(7, 13).setItemName("driedLeather");
	public static Item belt = (new Item(160)).setIconCoord(8, 13).setItemName("belt");
	public static Item objSpawner = (new ItemObjSpawner(161)).setIconCoord(2, 15).setItemName("objSpawner");
	public static Item uvMapGenerator = (new ItemUVMapGenerator(162)).setIconCoord(3, 15).setItemName("uvMapGenerator");
	public static Item nuclearBombPlacer = (new ItemNuclearBombPlacer(163)).setIconCoord(3, 14).setItemName("nuclearBomb");
	public static Item flour = (new Item(164)).setIconCoord(6, 13).setItemName("flour");
	public static Item engine = (new Item(165)).setIconCoord(0, 14).setItemName("engine");
	public static Item balloon = (new Item(166)).setIconCoord(1, 14).setItemName("balloon");
	public static Item airShip = (new ItemAirship(167)).setIconCoord(2, 14).setItemName("airship");
	public static Item zombieSpawnEgg = (new ItemZombieSpawnEgg(168)).setIconCoord(4, 14).setItemName("zombieSpawnEgg");
	public static Item helmetSchema = (new ItemArmor(169, 1, 6, 0)).setIconCoord(11, 6).setItemName("helmetSchema");
	public static Item plateSchema = (new ItemArmor(170, 1, 6, 1)).setIconCoord(11, 7).setItemName("plateSchema");
	public static Item legsSchema = (new ItemArmor(171, 1, 6, 2)).setIconCoord(11, 8).setItemName("legsSchema");
	public static Item bootsSchema = (new ItemArmor(172, 1, 6, 3)).setIconCoord(11, 9).setItemName("bootsSchema");
	public static Item unobtainium = (new Item(173)).setIconCoord(5, 14).setItemName("unobtainium");
	public static Item dough = (new Item(174)).setIconCoord(6, 14).setItemName("dough");
	public static Item rawClayPlate = (new Item(175)).setIconCoord(7, 14).setItemName("rawClayPlate");
	public static Item ceramicPlate = (new Item(176)).setIconCoord(8, 14).setItemName("ceramicPlate");
	public static Item uncookedMeal = (new ItemFood(177, 1, false)).setIconCoord(9, 14).setItemName("uncookedMeal");
	public static Item cookedMeal = (new ItemFood(178, 10, false)).setIconCoord(10, 14).setItemName("cookedMeal");
	public static Item potato = (new ItemSeed(179, Block.potato.blockID)).setIconCoord(6, 11).setItemName("potato");
	public static Item carrot = (new ItemSeed(180, Block.carrot.blockID)).setIconCoord(6, 12).setItemName("carrot");

	public final int shiftedIndex;
	protected int maxStackSize = 64;
	private int maxDamage = 0;
	protected int iconIndex;
	protected boolean bFull3D = false;
	protected boolean hasSubtypes = false;
	private Item containerItem = null;
	private String itemName;

	protected Item(int var1) {
		this.shiftedIndex = 256 + var1;
		if(itemsList[256 + var1] != null) {
			System.out.println("CONFLICT @ " + var1);
		}

		itemsList[256 + var1] = this;
	}

	public Item setIconIndex(int var1) {
		this.iconIndex = var1;
		return this;
	}

	public Item setMaxStackSize(int var1) {
		this.maxStackSize = var1;
		return this;
	}

	public Item setIconCoord(int var1, int var2) {
		this.iconIndex = var1 + var2 * 16;
		return this;
	}

	public int getIconFromDamage(int var1) {
		return this.iconIndex;
	}

	public final int getIconIndex(ItemStack var1) {
		return this.getIconFromDamage(var1.getItemDamage());
	}

	public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
		return false;
	}

	public float getStrVsBlock(ItemStack var1, Block var2) {
		return 1.0F;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		return var1;
	}

	public int getItemStackLimit() {
		return this.maxStackSize;
	}

	public int getPlacedBlockMetadata(int var1) {
		return 0;
	}

	public boolean getHasSubtypes() {
		return this.hasSubtypes;
	}

	protected Item setHasSubtypes(boolean var1) {
		this.hasSubtypes = var1;
		return this;
	}

	public int getMaxDamage() {
		return this.maxDamage;
	}

	protected Item setMaxDamage(int var1) {
		this.maxDamage = var1;
		return this;
	}

	public boolean isDamagable() {
		return this.maxDamage > 0 && !this.hasSubtypes;
	}

	public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3) {
		return false;
	}

	public boolean onBlockDestroyed(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6) {
		return false;
	}

	public int getDamageVsEntity(Entity var1) {
		return 1;
	}

	public boolean canHarvestBlock(Block var1) {
		return false;
	}

	public void saddleEntity(ItemStack var1, EntityLiving var2) {
	}

	public Item setFull3D() {
		this.bFull3D = true;
		return this;
	}

	public boolean isFull3D() {
		return this.bFull3D;
	}

	public boolean shouldRotateAroundWhenRendering() {
		return false;
	}

	public Item setItemName(String var1) {
		this.itemName = "item." + var1;
		return this;
	}

	public String getItemName() {
		return this.itemName;
	}

	public String getItemNameIS(ItemStack var1) {
		return this.itemName;
	}

	public Item setContainerItem(Item var1) {
		if(this.maxStackSize > 1) {
			throw new IllegalArgumentException("Max stack size must be 1 for items with crafting results");
		} else {
			this.containerItem = var1;
			return this;
		}
	}

	public Item getContainerItem() {
		return this.containerItem;
	}

	public boolean hasContainerItem() {
		return this.containerItem != null;
	}

	public String getStatName() {
		return StatCollector.translateToLocal(this.getItemName() + ".name");
	}

	public int getColorFromDamage(int var1) {
		return 16777215;
	}

	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
	}

	public void onCreated(ItemStack var1, World var2, EntityPlayer var3) {
	}

	static {
		StatList.func_25151_b();
	}
}
