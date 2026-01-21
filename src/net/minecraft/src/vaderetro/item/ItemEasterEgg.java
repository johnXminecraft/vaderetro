package net.minecraft.src.vaderetro.item;

import net.minecraft.src.*;

public class ItemEasterEgg extends Item {

    public static final String[] colors = new String[]{
            "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink",
            "lime", "yellow", "lightBlue", "magenta", "orange", "white"
    };

    public ItemEasterEgg(int id) {
        super(id);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
    }

    public int getIconFromDamage(int var1) {
        return this.iconIndex + var1 % 8 * 16 + var1 / 8;
    }

    public String getItemNameIS(ItemStack var1) {
        return super.getItemName() + "." + colors[var1.getItemDamage()];
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        return itemStack.getItemDamage() == 0 ? blackEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 1 ? redEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 2 ? greenEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 3 ? brownEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 4 ? blueEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 5 ? purpleEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 6 ? cyanEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 7 ? silverEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 8 ? grayEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 9 ? pinkEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 10 ? limeEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 11 ? yellowEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 12 ? lightBlueEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 13 ? magentaEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 14 ? orangeEffect(itemStack, world, player) :
                (itemStack.getItemDamage() == 15 ? whiteEffect(itemStack, world, player) :
                itemStack
                )))))))))))))));
    }

    private ItemStack blackEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // must protect from undead mobs for 1 minute
        player.heal(20);
        return itemStack;
    }

    private ItemStack redEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // heals full hp
        player.heal(20);
        return itemStack;
    }

    private ItemStack greenEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // must protect from wild mobs for 1 minute
        player.heal(20);
        return itemStack;
    }

    private ItemStack brownEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // sets time to sunset
        world.setWorldTime(12000);
        return itemStack;
    }

    private ItemStack blueEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // makes player invincible for 1 minute
        player.heal(20);
        return itemStack;
    }

    private ItemStack purpleEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // removes any water within 10 blocks radius
        player.heal(20);
        return itemStack;
    }

    private ItemStack cyanEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // makes player able to walk on water for 1 minute
        player.heal(20);
        return itemStack;
    }

    private ItemStack silverEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // doubles player's speed for 1 minute
        player.heal(20);
        return itemStack;
    }

    private ItemStack grayEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // makes an explosion
        world.createExplosion(null, player.posX, player.posY, player.posZ, 6.0F);
        return itemStack;
    }

    private ItemStack pinkEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // multiplies fish and bread if are in inventory
        player.heal(20);
        return itemStack;
    }

    private ItemStack limeEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // protects player from explosions for 1 minute
        player.heal(20);
        return itemStack;
    }

    private ItemStack yellowEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // sets time to sunrise
        world.setWorldTime(23000);
        return itemStack;
    }

    private ItemStack lightBlueEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // makes player able to see in the dark for 1 minute
        player.heal(20);
        return itemStack;
    }

    private ItemStack magentaEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // makes player's damage higher for 1 minute
        player.heal(20);
        return itemStack;
    }

    private ItemStack orangeEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // spawns 3 sheep nearby player
        for (int i = 0; i < 3; i++) {
            EntitySheep sheep = new EntitySheep(world);
            sheep.setLocationAndAngles(
                    player.posX,
                    player.posY,
                    player.posZ,
                    world.rand.nextFloat() * 360.0F,
                    0.0F
            );
            world.entityJoinedWorld(sheep);
        }
        return itemStack;
    }

    private ItemStack whiteEffect(ItemStack itemStack, World world, EntityPlayer player) {
        --itemStack.stackSize;
        // teleports player to respawn point
        player.setPosition(
                player.getPlayerSpawnCoordinate().x,
                player.getPlayerSpawnCoordinate().y + 2,
                player.getPlayerSpawnCoordinate().z
        );
        return itemStack;
    }
}
