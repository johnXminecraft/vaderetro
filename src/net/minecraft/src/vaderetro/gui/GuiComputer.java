package net.minecraft.src.vaderetro.gui;

import net.minecraft.src.*;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityCardReader;
import net.minecraft.src.vaderetro.entity.tileentity.TileEntityComputer;
import org.lwjgl.input.Keyboard;

public class GuiComputer extends GuiScreen {

    private TileEntityComputer tileEntity;
    private GuiTextField textInput;

    private static final int NUKE_COUNTDOWN_SECONDS = 10;

    private static final int SCREEN_WIDTH = 380;
    private static final int SCREEN_HEIGHT = 240;

    public GuiComputer(InventoryPlayer inventoryPlayer, TileEntityComputer tileEntity) {
        this.tileEntity = tileEntity;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        int left = (this.width - SCREEN_WIDTH) / 2;
        int top = (this.height - SCREEN_HEIGHT) / 2;
        
        this.textInput = new GuiTextField(this, this.fontRenderer, left + 22, top + SCREEN_HEIGHT - 22, SCREEN_WIDTH - 40, 12, "");
        this.textInput.setMaxStringLength(40);
        this.textInput.isFocused = true;

        if (tileEntity.outputText.length() <= 0) {
            bootSystem();
        }
    }

    private void bootSystem() {
        if (hasKey()) {
            tileEntity.appendOutputLine("STRATEGIC DEFENSE NETWORK v4.2.0");
            tileEntity.appendOutputLine("MILITARY ACCESS GRANTED.");
        } else {
            tileEntity.appendOutputLine("VADE-RETRO OS v1.0.4 (C) 1981");
            tileEntity.appendOutputLine("SYSTEM READY.");
        }
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        this.textInput.updateCursorCounter();
    }

    protected void keyTyped(char c, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        } else if (keyCode == 28) {
            submitCommand();
        } else {
            this.textInput.textboxKeyTyped(c, keyCode);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        this.textInput.mouseClicked(mouseX, mouseY, button);
    }

    private void submitCommand() {
        String cmd = this.textInput.getText().trim();
        this.textInput.setText("");
        if (cmd.length() == 0) return;

        tileEntity.appendOutputLine("> " + cmd);
        String upperCmd = cmd.toUpperCase();

        if (upperCmd.equals("/CLEAR")) {
            tileEntity.outputText = "";
            return;
        }

        if (upperCmd.equals("/REBOOT")) {
            tileEntity.outputText = "";
            bootSystem();
            return;
        }

        if (hasKey()) {
            handleMilitaryCommand(upperCmd);
        } else {
            handleStandardCommand(upperCmd);
        }
    }

    private void handleStandardCommand(String cmd) {
        if (cmd.equals("/HELP")) {
            tileEntity.appendOutputLine("AVAILABLE: /HELP, /INFO, /REBOOT, /CLEAR");
        } else if (cmd.equals("/INFO")) {
            runInfoCommand();
        } else if (cmd.equals("/ARM") || cmd.equals("/LAUNCH") || cmd.equals("/STATUS")) {
            tileEntity.appendOutputLine("ERROR: ACCESS LEVEL 5 REQUIRED");
        } else {
            tileEntity.appendOutputLine("UNKNOWN COMMAND");
        }
    }

    private void handleMilitaryCommand(String cmd) {
        if (cmd.equals("/HELP")) {
            tileEntity.appendOutputLine("MILITARY OPS: /STATUS, /ARM, /LAUNCH, /INFO, /CLEAR");
        } else if (cmd.equals("/STATUS")) {
            tileEntity.appendOutputLine("WARHEAD: " + (tileEntity.armed ? "READY" : "LOCKED"));
            if (tileEntity.nukeTimer > 0) {
                tileEntity.appendOutputLine("T-MINUS: " + (tileEntity.nukeTimer / 20) + "s");
            }
        } else if (cmd.equals("/ARM")) {
            tileEntity.armed = true;
            tileEntity.appendOutputLine("SYSTEM ARMED. STANDBY FOR LAUNCH.");
        } else if (cmd.equals("/LAUNCH")) {
            if (!tileEntity.armed) {
                tileEntity.appendOutputLine("ERROR: SEQUENCE NOT ARMED");
            } else {
                int durationTicks = 20 * NUKE_COUNTDOWN_SECONDS;
                tileEntity.startBombsInRange(durationTicks);
                tileEntity.nukeTimer = durationTicks;
                tileEntity.appendOutputLine("!!! LAUNCH INITIATED !!!");
                tileEntity.appendOutputLine("DETONATION IN " + NUKE_COUNTDOWN_SECONDS + " SECONDS.");
            }
        } else if (cmd.equals("/INFO")) {
            runInfoCommand();
        } else {
            tileEntity.appendOutputLine("INVALID MILITARY COMMAND");
        }
    }

    private void runInfoCommand() {
        World world = tileEntity.worldObj;
        BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(tileEntity.xCoord, tileEntity.zCoord);
        tileEntity.appendOutputLine("LOC: " + tileEntity.xCoord + "," + tileEntity.zCoord);
        tileEntity.appendOutputLine("BIOME: " + (biome != null ? biome.biomeName.toUpperCase() : "NULL"));
        tileEntity.appendOutputLine("TEMP: " + (15 + world.rand.nextInt(5)) + " C");
    }

    private boolean hasKey() {
        int[] dx = {1, -1, 0, 0};
        int[] dz = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            int nx = tileEntity.xCoord + dx[i];
            int nz = tileEntity.zCoord + dz[i];
            if (tileEntity.worldObj.getBlockId(nx, tileEntity.yCoord, nz) == Block.cardReader.blockID) {
                TileEntity te = tileEntity.worldObj.getBlockTileEntity(nx, tileEntity.yCoord, nz);
                if (te instanceof TileEntityCardReader && ((TileEntityCardReader) te).hasCard()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int left = (this.width - SCREEN_WIDTH) / 2;
        int top = (this.height - SCREEN_HEIGHT) / 2;

        boolean military = hasKey();
        int frameColor = military ? 0xFF552222 : 0xFF333333;
        int textColor = military ? 0xFFFF5555 : 0xFF55FF55;

        this.drawRect(left, top, left + SCREEN_WIDTH, top + SCREEN_HEIGHT, frameColor);
        this.drawRect(left + 2, top + 2, left + SCREEN_WIDTH - 2, top + SCREEN_HEIGHT - 2, 0xFF050A05);

        for (int i = 0; i < SCREEN_HEIGHT; i += 2) {
            this.drawRect(left, top + i, left + SCREEN_WIDTH, top + i + 1, 0x08000000);
        }

        String[] lines = tileEntity.outputText.split("\n");
        int maxLines = 17;
        int start = Math.max(0, lines.length - maxLines);
        
        for (int i = start; i < lines.length; i++) {
            this.fontRenderer.drawString(lines[i], left + 10, top + 10 + (i - start) * 10, textColor);
        }

        if (tileEntity.nukeTimer > 0) {
            String timerTxt = "T-MINUS: " + (tileEntity.nukeTimer / 20) + "s";
            this.fontRenderer.drawString(timerTxt, left + SCREEN_WIDTH - 100, top + 10, 0xFFFF0000);
        }

        this.fontRenderer.drawString(">", left + 10, top + SCREEN_HEIGHT - 20, textColor);
        this.textInput.drawTextBox();
    }
}