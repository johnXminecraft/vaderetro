package net.minecraft.src.vaderetro._JIM;

import java.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;


public class JIMView {
    private JIMController controller;
    
    private List<ItemStack> allItems = new ArrayList<ItemStack>();
    private List<ItemStack> filteredItems = new ArrayList<ItemStack>();
    
    private int currentPage = 0;
    private int itemsPerPage = 54;
    private boolean cheatMode = false;
    private String searchText = "";
    private boolean isSearchBoxFocused = false;
    
    private boolean wasPreviouslyClicked = false;
    private boolean wasPreviousItemClicked = false;
    private int lastMouseX, lastMouseY;
    private long lastClickTime;
    
    private int panelLeft, panelTop, panelWidth, panelHeight;
    private int searchBoxX, searchBoxY, searchBoxWidth, searchBoxHeight;
    private int cheatModeX, cheatModeY;
    private int dayButtonX, dayButtonY;
    private int nightButtonX, nightButtonY;
    private int rainButtonX, rainButtonY;
    private int healButtonX, healButtonY;

    private static final int FONT_HEIGHT = 9;
    private boolean needsFiltering = true;
    
    private long lastRenderTime = 0;
    private static final long RENDER_THROTTLE_MS = 0;
    
    private boolean renderingEnabled = true;
    private static boolean isRenderingJIM = false;
    
    private static boolean useDoubleBuffering = true;

    public JIMView(JIMController controller) {
        this.controller = controller;
        populateItems();
        this.filteredItems.addAll(allItems);
        System.out.println("JIM: View initialized with " + allItems.size() + " items");
    }
    

    private void populateItems() {
        for (int i = 1; i < Item.itemsList.length; i++) {
            if (Item.itemsList[i] != null) {
                try {
                    if (Item.itemsList[i].getHasSubtypes()) {
                        if (i == Block.stairSingle.blockID) {
                            for (int damage = 0; damage < 4; damage++) {
                                ItemStack stack = new ItemStack(i, 1, damage);
                                allItems.add(stack);
                            }
                        } else {
                            for (int damage = 0; damage < 16; damage++) {
                                try {
                                    ItemStack stack = new ItemStack(i, 1, damage);
                                    String name = stack.getItemName();
                                    if (name != null && !name.contains(".name")) {
                                        allItems.add(stack);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    } else {
                        allItems.add(new ItemStack(i, 1, 0));
                    }
                } catch (Exception e) {
                    System.out.println("JIM: Skipping problematic item ID " + i);
                }
            }
        }
        
        Collections.sort(allItems, new Comparator<ItemStack>() {
            public int compare(ItemStack o1, ItemStack o2) {
                if (o1.itemID != o2.itemID)
                    return o1.itemID - o2.itemID;
                return o1.getItemDamage() - o2.getItemDamage();
            }
        });
    }

    private void filterItems() {
        filteredItems.clear();
        
        if (searchText.matches(".*[^a-zA-Z0-9\\s].*") && searchText.length() > 5) {
            searchText = "";
            filteredItems.addAll(allItems);
            return;
        }
        
        if (searchText.isEmpty()) {
            filteredItems.addAll(allItems);
        } else {
            String search = searchText.toLowerCase();
            for (ItemStack item : allItems) {
                if (filteredItems.size() >= allItems.size()) {
                    break;
                }
                
                String name = item.getItemName();
                if (name == null) name = "Unknown";
                
                String displayName = name;
                try {
                    if (item.getItem() != null) {
                        String translationKey = item.getItem().getItemNameIS(item);
                        if (translationKey != null) {
                            String translated = StatCollector.translateToLocal(translationKey);
                            if (translated != null && !translated.equals(translationKey)) {
                                displayName = translated;
                            }
                        }
                    }
                } catch (Exception e) {
                }
                
                if (name.toLowerCase().contains(search) || 
                    displayName.toLowerCase().contains(search) || 
                    String.valueOf(item.itemID).contains(search)) {
                    filteredItems.add(item);
                }
            }
        }
        
        currentPage = 0;
        needsFiltering = false;
    }
    

    public void draw(Minecraft minecraft) {
        try {
            if (isRenderingJIM || !renderingEnabled) {
                return;
            }
            isRenderingJIM = true;
            
            lastRenderTime = System.currentTimeMillis();
            
            if (minecraft == null || minecraft.fontRenderer == null) {
                isRenderingJIM = false;
                return;
            }
            
            ScaledResolution res = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();
            
            panelWidth = 176;
            panelHeight = height - 30;
            panelLeft = (width - panelWidth) / 2 + panelWidth + 5;
            panelTop = 10;
            
            searchBoxX = panelLeft + 8;
            searchBoxY = panelTop + 6;
            searchBoxWidth = panelWidth - 16;
            searchBoxHeight = 12;
            
            if (needsFiltering) {
                filterItems();
            }
            
            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
            
            int boundTexture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
            
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, width, height, 0.0D, -2000.0D, 2000.0D);
            
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_FOG);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            
            if (useDoubleBuffering) {
                drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 1, 0xA0FFFFFF);
                drawRect(panelLeft, panelTop, panelLeft + 1, panelTop + panelHeight, 0xA0FFFFFF);
                drawRect(panelLeft + panelWidth - 1, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xA0FFFFFF);
                drawRect(panelLeft, panelTop + panelHeight - 1, panelLeft + panelWidth, panelTop + panelHeight, 0xA0FFFFFF);
            } else {
                drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 1, 0xA0FFFFFF);
                drawRect(panelLeft, panelTop, panelLeft + 1, panelTop + panelHeight, 0xA0FFFFFF);
                drawRect(panelLeft + panelWidth - 1, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xA0FFFFFF);
                drawRect(panelLeft, panelTop + panelHeight - 1, panelLeft + panelWidth, panelTop + panelHeight, 0xA0FFFFFF);
            }
            
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            
            drawRect(searchBoxX, searchBoxY, searchBoxX + searchBoxWidth, searchBoxY + searchBoxHeight, 0xFFFFFFFF);
            drawBorder(searchBoxX, searchBoxY, searchBoxX + searchBoxWidth, searchBoxY + searchBoxHeight, 0xFF000000);
            
            String displayText = searchText.isEmpty() ? (isSearchBoxFocused ? "|" : "Search...") : searchText + (isSearchBoxFocused ? "|" : "");
            minecraft.fontRenderer.drawStringWithShadow(displayText, searchBoxX + 2, searchBoxY + 2, isSearchBoxFocused ? 0x000000 : 0x808080);
            
            int totalPages = Math.max(1, (filteredItems.size() - 1) / itemsPerPage + 1);
            String pageInfo = "Page " + (currentPage + 1) + "/" + totalPages;
            int pageInfoWidth = minecraft.fontRenderer.getStringWidth(pageInfo) + 4;
            
            int centerX = panelLeft + (panelWidth / 2);
            int centerPageInfoX = centerX - (pageInfoWidth / 2);
            int pageY = searchBoxY + searchBoxHeight + 5;
            
            drawRect(centerPageInfoX - 2, pageY - 2,
                     centerPageInfoX + pageInfoWidth + 2, pageY + 10, 0x80000000);
            minecraft.fontRenderer.drawStringWithShadow(pageInfo, centerPageInfoX, 
                pageY, 0xFFFFFF);
            
            String prevPage = "<";
            String nextPage = ">";
            int prevX = centerPageInfoX - 15;
            int nextX = centerPageInfoX + pageInfoWidth + 5;
            
            
            boolean canPrev = currentPage > 0;
            boolean canNext = (currentPage + 1) < totalPages;
            
            if (canPrev) {
                drawRect(prevX - 2, pageY - 2, prevX + minecraft.fontRenderer.getStringWidth(prevPage) + 2, pageY + 10, 0x80000000);
            }
            minecraft.fontRenderer.drawStringWithShadow(prevPage, prevX, pageY, canPrev ? 0xFFFFFF : 0x808080);
            
            if (canNext) {
                drawRect(nextX - 2, pageY - 2, nextX + minecraft.fontRenderer.getStringWidth(nextPage) + 2, pageY + 10, 0x80000000);
            }
            minecraft.fontRenderer.drawStringWithShadow(nextPage, nextX, pageY, canNext ? 0xFFFFFF : 0x808080);
            
            int mouseX = Mouse.getX() * width / minecraft.displayWidth;
            int mouseY = height - Mouse.getY() * height / minecraft.displayHeight - 1;
            
            boolean mouseDown = Mouse.isButtonDown(0);

            if (mouseDown) {
                if (cheatMode) {
                    if (mouseX >= dayButtonX && mouseX <= dayButtonX + 30 &&
                        mouseY >= dayButtonY && mouseY <= dayButtonY + 12) {
                        minecraft.theWorld.setWorldTime(6000);
                    }
                    
                    if (mouseX >= nightButtonX && mouseX <= nightButtonX + 30 &&
                        mouseY >= nightButtonY && mouseY <= nightButtonY + 12) {
                        minecraft.theWorld.setWorldTime(18000);
                    }
                    
                    if (mouseX >= healButtonX && mouseX <= healButtonX + 30 &&
                        mouseY >= healButtonY && mouseY <= healButtonY + 12) {
                        minecraft.thePlayer.health = 20;
                    }
                    
                    if (mouseX >= rainButtonX && mouseX <= rainButtonX + 30 &&
                        mouseY >= rainButtonY && mouseY <= rainButtonY + 12) {
                        try {
                            World world = minecraft.theWorld;
                            if (world != null) {
                                Class<?> worldClass = World.class;
                                java.lang.reflect.Field worldInfoField = worldClass.getDeclaredField("worldInfo");
                                worldInfoField.setAccessible(true);
                                WorldInfo worldInfo = (WorldInfo)worldInfoField.get(world);
                                worldInfo.setRaining(!worldInfo.getRaining());
                            }
                        } catch (Exception e) {
                            System.out.println("JIM: Failed to toggle rain: " + e.getMessage());
                        }
                    }
                }
            }
            
            if (mouseDown && !wasPreviouslyClicked) {
                if (mouseX >= prevX - 2 && mouseX <= prevX + minecraft.fontRenderer.getStringWidth(prevPage) + 2 &&
                    mouseY >= pageY - 2 && mouseY <= pageY + 10 && canPrev) {
                    currentPage--;
                }
                
                if (mouseX >= nextX - 2 && mouseX <= nextX + minecraft.fontRenderer.getStringWidth(nextPage) + 2 &&
                    mouseY >= pageY - 2 && mouseY <= pageY + 10 && canNext) {
                    currentPage++;
                }
                
                if (mouseX >= searchBoxX && mouseX <= searchBoxX + searchBoxWidth &&
                    mouseY >= searchBoxY && mouseY <= searchBoxY + searchBoxHeight) {
                    isSearchBoxFocused = true;
                } else {
                    isSearchBoxFocused = false;
                    
                    if (mouseX >= cheatModeX - 2 && mouseX <= cheatModeX + 12 &&
                        mouseY >= cheatModeY - 2 && mouseY <= cheatModeY + 12) {
                        cheatMode = !cheatMode;
                    }
                }
            }
            
            int wheel = Mouse.getDWheel();
            if (wheel > 0 && currentPage > 0) {
                currentPage--;
            } else if (wheel < 0 && (currentPage + 1) < totalPages) {
                currentPage++;
            }
            
            int gridStartY = searchBoxY + searchBoxHeight + 20;
            int startIndex = currentPage * itemsPerPage;
            
            if (filteredItems.isEmpty() || filteredItems.size() > allItems.size()) {
                filteredItems.clear();
                filteredItems.addAll(allItems);
                searchText = "";
                needsFiltering = false;
            }
            
            for (int i = 0; i < itemsPerPage && i + startIndex < filteredItems.size(); i++) {
                int row = i / 9;
                int col = i % 9;
                int x = panelLeft + 8 + col * 18;
                int y = gridStartY + row * 18;
                
                drawRect(x - 1, y - 1, x + 17, y + 17, 0x40000000);
                
                try {
                    ItemStack itemstack = filteredItems.get(i + startIndex);
                    drawItemSimple(minecraft, itemstack, x, y);
                    
                    if (cheatMode && mouseDown && isMouseOverSlot(x, y, mouseX, mouseY)) {
                        if (!wasPreviousItemClicked) {
                            int quantity = itemstack.getMaxStackSize() > 1 ? 64 : 1;
                            controller.giveItem(itemstack.itemID, quantity, itemstack.getItemDamage());
                            minecraft.thePlayer.swingItem();
                            wasPreviousItemClicked = true;
                        }
                    }
                } catch (Exception e) {
                }
            }
            
            if (!mouseDown) {
                wasPreviousItemClicked = false;
            }
            
            int controlsY = panelTop + panelHeight - 25;
            
            cheatModeX = panelLeft + 8;
            cheatModeY = controlsY;
            drawRect(cheatModeX - 2, cheatModeY - 2, cheatModeX + 12, cheatModeY + 12, 0x80000000);
            drawRect(cheatModeX, cheatModeY, cheatModeX + 10, cheatModeY + 10, 0xFFFFFFFF);
            if (cheatMode) {
                drawRect(cheatModeX + 2, cheatModeY + 2, cheatModeX + 8, cheatModeY + 8, 0xFF00FF00);
            }
            minecraft.fontRenderer.drawStringWithShadow("Cheat Mode", cheatModeX + 15, cheatModeY + 2, 0xFFFFFF);
            
            int buttonY = controlsY + 25;
            int buttonWidth = 25;
            int buttonSpacing = 5;
            
            dayButtonX = panelLeft + 10;
            dayButtonY = buttonY;
            drawSimpleButton(minecraft, "Day", dayButtonX, dayButtonY, buttonWidth);
            
            nightButtonX = dayButtonX + buttonWidth + buttonSpacing;
            nightButtonY = buttonY;
            drawSimpleButton(minecraft, "Night", nightButtonX, nightButtonY, buttonWidth);
            
            healButtonX = nightButtonX + buttonWidth + buttonSpacing;
            healButtonY = buttonY;
            drawSimpleButton(minecraft, "Heal", healButtonX, healButtonY, buttonWidth);
            
            rainButtonX = healButtonX + buttonWidth + buttonSpacing;
            rainButtonY = buttonY;
            drawSimpleButton(minecraft, "Rain", rainButtonX, rainButtonY, buttonWidth);
            
            if (isSearchBoxFocused) {
                processKeyInput();
            }
            
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            wasPreviouslyClicked = mouseDown;

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, boundTexture);
            
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPopMatrix();
            
            GL11.glPopAttrib();
            
            isRenderingJIM = false;
            
        } catch (Exception e) {
            System.out.println("JIM: Render error: " + e.getMessage());
            e.printStackTrace();
            
            try {
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glPopMatrix();
                GL11.glMatrixMode(GL11.GL_PROJECTION);
                GL11.glPopMatrix();
                GL11.glPopAttrib();
            } catch (Exception ex) {
                GL11.glLoadIdentity();
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_FOG);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
            
            isRenderingJIM = false;
        }
    }

    private void processKeyInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                int keyCode = Keyboard.getEventKey();
                if (keyCode == Keyboard.KEY_ESCAPE) {
                    isSearchBoxFocused = false;
                } else if (keyCode == Keyboard.KEY_RETURN) {
                    isSearchBoxFocused = false;
                } else if (keyCode == Keyboard.KEY_BACK && searchText.length() > 0) {
                    searchText = searchText.substring(0, searchText.length() - 1);
                    needsFiltering = true;
                } else {
                    char c = Keyboard.getEventCharacter();
                    if (c >= ' ' && c <= '~') {
                        searchText += c;
                        needsFiltering = true;
                    }
                }
            }
        }
    }
    

    private void drawSimpleButton(Minecraft minecraft, String text, int x, int y, int width) {
        int textWidth = minecraft.fontRenderer.getStringWidth(text);
        int paddingX = (width - textWidth) / 2;
        
        drawRect(x, y, x + width, y + 12, 0x80000000);
        drawBorder(x, y, x + width, y + 12, 0xFF000000);
        minecraft.fontRenderer.drawStringWithShadow(text, x + paddingX, y + 2, 0xFFFFFF);
    }

    private void drawBorder(int left, int top, int right, int bottom, int color) {
        drawRect(left, top, right, top + 1, color);
        drawRect(left, bottom - 1, right, bottom, color);
        drawRect(left, top + 1, left + 1, bottom - 1, color);
        drawRect(right - 1, top + 1, right, bottom - 1, color);
    }

    private void drawRect(int left, int top, int right, int bottom, int color) {
        int temp;
        if (left < right) {
            temp = left;
            left = right;
            right = temp;
        }
        
        if (top < bottom) {
            temp = top;
            top = bottom;
            bottom = temp;
        }
        
        float alpha = (float)(color >> 24 & 255) / 255.0F;
        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;
        
        boolean wasTextureEnabled = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
        
        if (wasTextureEnabled) {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(red, green, blue, alpha);
        
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertex(left, bottom, 0.0D);
        tessellator.addVertex(right, bottom, 0.0D);
        tessellator.addVertex(right, top, 0.0D);
        tessellator.addVertex(left, top, 0.0D);
        tessellator.draw();
        
        if (wasTextureEnabled) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    

    private boolean isMouseOverSlot(int x, int y, int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16;
    }

    private void drawItemSimple(Minecraft minecraft, ItemStack itemstack, int x, int y) {
        if (itemstack == null) return;
        
        try {
            int itemTexture = minecraft.renderEngine.getTexture("/gui/items.png");
            if (itemstack.itemID < 256) {
                itemTexture = minecraft.renderEngine.getTexture("/terrain.png");
            }
            
            minecraft.renderEngine.bindTexture(itemTexture);
            
            int iconIndex = itemstack.getItem().getIconIndex(itemstack);
            if (itemstack.itemID < 256 && Block.blocksList[itemstack.itemID] != null) {
                iconIndex = Block.blocksList[itemstack.itemID].getBlockTextureFromSideAndMetadata(0, itemstack.getItemDamage());
            }
            int iconX = iconIndex % 16 * 16;
            int iconY = iconIndex / 16 * 16;
            float ts = itemstack.itemID < 256 ? (float)TerrainTextureManager.getTerrainTextureSize() : (float)ItemTextureManager.getItemTextureSize();
            float eps = 0.5F / ts;
            
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(x + 0, y + 16, 0, (iconX + 0.0F) / ts + eps, (iconY + 16.0F) / ts - eps);
            tessellator.addVertexWithUV(x + 16, y + 16, 0, (iconX + 16.0F) / ts - eps, (iconY + 16.0F) / ts - eps);
            tessellator.addVertexWithUV(x + 16, y + 0, 0, (iconX + 16.0F) / ts - eps, (iconY + 0.0F) / ts + eps);
            tessellator.addVertexWithUV(x + 0, y + 0, 0, (iconX + 0.0F) / ts + eps, (iconY + 0.0F) / ts + eps);
            tessellator.draw();
            
            if (itemstack.stackSize > 1) {
                String stackSize = String.valueOf(itemstack.stackSize);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_BLEND);
                minecraft.fontRenderer.drawStringWithShadow(stackSize, 
                    x + 19 - 2 - minecraft.fontRenderer.getStringWidth(stackSize), 
                    y + 6 + 3, 
                    0xFFFFFF);
            }
        } catch (Exception e) {

        }
    }
} 