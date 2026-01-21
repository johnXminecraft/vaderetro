package net.minecraft.src.vaderetro.enumeration;

public enum EnumIcon {

    Block111("Block111", 16, 16, 0, 0),
    Block112("Block112", 16, 16, 16, 0),
    Block113("Block113", 16, 16, 32, 0),
    Block114("Block114", 16, 16, 48, 0),
    Block115("Block115", 16, 16, 64, 0),
    Block116("Block116", 16, 16, 80, 0),
    Block117("Block117", 16, 16, 96, 0),
    Block211("Block211", 32, 16, 0, 32),
    Block212("Block212", 32, 16, 32, 32),
    Block213("Block213", 32, 16, 64, 32),
    Block214("Block214", 32, 16, 96, 32),
    Block215("Block215", 32, 16, 128, 32),
    Block121("Block121", 16, 32, 0, 64),
    Block122("Block122", 16, 32, 16, 64),
    Block221("Block221", 32, 32, 0, 128),
    Block222("Block222", 32, 32, 32, 128),
    Block223("Block223", 32, 32, 64, 128),
    Block224("Block224", 32, 32, 96, 128),
    Block225("Block225", 32, 32, 128, 128),
    Block421("Block421", 64, 32, 0, 96),
    Block441("Block441", 64, 64, 0, 192),
    Block442("Block442", 64, 64, 64, 192),
    Block443("Block443", 64, 64, 128, 192),
    Block431("Block431", 64, 48, 192, 64),
    Block432("Block431", 64, 48, 192, 112);

    public static final int maxIconTitleLength = "SkullAndRoses".length();
    public final String title;
    public final int sizeX;
    public final int sizeY;
    public final int offsetX;
    public final int offsetY;

    private EnumIcon(String var3, int var4, int var5, int var6, int var7) {
        this.title = var3;
        this.sizeX = var4;
        this.sizeY = var5;
        this.offsetX = var6;
        this.offsetY = var7;
    }
}
