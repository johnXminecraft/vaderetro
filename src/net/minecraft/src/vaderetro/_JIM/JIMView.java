package net.minecraft.src.vaderetro._JIM;

import java.util.*;
import java.lang.reflect.Field;
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
    private int itemsPerPage = 54; // default, will be calculated dynamically
    private boolean cheatMode = false;
    private String searchText = "";
    private boolean isSearchBoxFocused = false;

    private boolean wasPreviouslyClicked = false;
    private boolean wasRightClickDown = false;
    private boolean wasPreviousItemClicked = false;
    private int lastMouseX, lastMouseY;

    private int panelLeft, panelTop, panelWidth, panelHeight;
    private int searchBoxX, searchBoxY, searchBoxWidth, searchBoxHeight;
    private int cheatModeX, cheatModeY;
    
    private int dayButtonX, dayButtonY;
    private int nightButtonX, nightButtonY;
    private int healButtonX, healButtonY;
    private int rainButtonX, rainButtonY;
    private int flyButtonX, flyButtonY;

    private boolean isRecipeView = false;
    private List<Object> foundRecipes = new ArrayList<Object>();
    private int currentRecipePage = 0;
    private ItemStack targetItem = null;

    private static final int FONT_HEIGHT = 9;
    private boolean needsFiltering = true;

    private long lastRenderTime = 0;
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
                                allItems.add(new ItemStack(i, 1, damage));
                            }
                        } else {
                            for (int damage = 0; damage < 16; damage++) {
                                try {
                                    ItemStack stack = new ItemStack(i, 1, damage);
                                    String name = stack.getItemName();
                                    if (name != null && !name.contains(".name")) {
                                        allItems.add(stack);
                                    }
                                } catch (Exception e) {}
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
                if (o1.itemID != o2.itemID) return o1.itemID - o2.itemID;
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
                } catch (Exception e) {}

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

    private void findRecipes(ItemStack target) {
        this.targetItem = target;
        this.foundRecipes.clear();
        this.currentRecipePage = 0;

        List recipes = CraftingManager.getInstance().getRecipeList();
        for (Object obj : recipes) {
            if (obj instanceof IRecipe) {
                IRecipe recipe = (IRecipe) obj;
                ItemStack output = recipe.getRecipeOutput();
                if (output != null && output.itemID == target.itemID && 
                   (output.getItemDamage() == target.getItemDamage() || target.getItemDamage() == -1)) {
                    foundRecipes.add(recipe);
                }
            }
        }

        Map smelting = FurnaceRecipes.smelting().getSmeltingList();
        for (Object key : smelting.keySet()) {
            ItemStack result = (ItemStack) smelting.get(key);
            if (result != null && result.itemID == target.itemID) {
                foundRecipes.add(new Integer((Integer)key)); 
            }
        }
    }

    private void drawRecipeView(Minecraft minecraft, int mouseX, int mouseY, boolean mouseDown) {
        int backBtnX = panelLeft + 10;
        int backBtnY = panelTop + 25;
        drawSimpleButton(minecraft, "< Back", backBtnX, backBtnY, 40);
        
        if (mouseDown && !wasPreviouslyClicked) {
            if (mouseX >= backBtnX && mouseX <= backBtnX + 40 && mouseY >= backBtnY && mouseY <= backBtnY + 12) {
                isRecipeView = false;
                return;
            }
        }

        if (foundRecipes.isEmpty()) {
            minecraft.fontRenderer.drawStringWithShadow("No recipes found", panelLeft + 50, panelTop + 50, 0xFF5555);
            return;
        }

        Object currentRecipe = foundRecipes.get(currentRecipePage);
        String title = "Recipe " + (currentRecipePage + 1) + "/" + foundRecipes.size();
        
        if (currentRecipe instanceof Integer) title += " (Furnace)";
        else title += " (Crafting)";

        minecraft.fontRenderer.drawStringWithShadow(title, panelLeft + 60, panelTop + 25, 0xFFFFFF);

        if (foundRecipes.size() > 1) {
             if (currentRecipePage > 0) {
                 drawSimpleButton(minecraft, "<", panelLeft + 130, backBtnY, 15);
                 if (mouseDown && !wasPreviouslyClicked && 
                     mouseX >= panelLeft + 130 && mouseX <= panelLeft + 145 && 
                     mouseY >= backBtnY && mouseY <= backBtnY + 12) {
                     currentRecipePage--;
                 }
             }
             if (currentRecipePage < foundRecipes.size() - 1) {
                 drawSimpleButton(minecraft, ">", panelLeft + 150, backBtnY, 15);
                 if (mouseDown && !wasPreviouslyClicked && 
                     mouseX >= panelLeft + 150 && mouseX <= panelLeft + 165 && 
                     mouseY >= backBtnY && mouseY <= backBtnY + 12) {
                     currentRecipePage++;
                 }
             }
        }

        int centerX = panelLeft + panelWidth / 2;
        int centerY = panelTop + 80;

        if (currentRecipe instanceof Integer) {
            int inputID = (Integer) currentRecipe;
            ItemStack inputStack = new ItemStack(inputID, 1, 0);
            
            drawRect(centerX - 20, centerY - 20, centerX, centerY, 0x40000000);
            drawItemSimple(minecraft, inputStack, centerX - 19, centerY - 19);
            
            minecraft.fontRenderer.drawStringWithShadow("->", centerX + 5, centerY - 15, 0xFFFFFF);
            minecraft.fontRenderer.drawStringWithShadow("Fire", centerX - 15, centerY + 5, 0xFF5500);

            drawRect(centerX + 20, centerY - 20, centerX + 40, centerY, 0x40000000);
            drawItemSimple(minecraft, targetItem, centerX + 21, centerY - 19);
        } 
        else if (currentRecipe instanceof IRecipe) {
            drawRect(centerX + 40, centerY - 10, centerX + 60, centerY + 10, 0x40000000);
            drawItemSimple(minecraft, ((IRecipe)currentRecipe).getRecipeOutput(), centerX + 41, centerY - 9);
            minecraft.fontRenderer.drawStringWithShadow("=", centerX + 28, centerY - 4, 0xFFFFFF);

            int startX = centerX - 40;
            int startY = centerY - 28;
            
            if (currentRecipe instanceof ShapedRecipes) {
                ShapedRecipes shaped = (ShapedRecipes) currentRecipe;
                try {
                    int width = getPrivateInt(shaped, "recipeWidth", "b"); 
                    int height = getPrivateInt(shaped, "recipeHeight", "c");
                    ItemStack[] items = (ItemStack[]) getPrivateValue(shaped, "recipeItems", "d"); 
                    
                    if (items != null) {
                        for (int y = 0; y < height; y++) {
                            for (int x = 0; x < width; x++) {
                                int index = x + y * width;
                                if (index < items.length && items[index] != null) {
                                    int slotX = startX + x * 18;
                                    int slotY = startY + y * 18;
                                    drawRect(slotX, slotY, slotX + 18, slotY + 18, 0x40000000);
                                    drawItemSimple(minecraft, items[index], slotX + 1, slotY + 1);
                                }
                            }
                        }
                    }
                } catch (Exception e) { e.printStackTrace(); }
            } 
            else if (currentRecipe instanceof ShapelessRecipes) {
                ShapelessRecipes shapeless = (ShapelessRecipes) currentRecipe;
                try {
                    List items = (List) getPrivateValue(shapeless, "recipeItems", "b");
                    
                    if (items != null) {
                        for (int i = 0; i < items.size(); i++) {
                            int x = i % 3;
                            int y = i / 3;
                            Object obj = items.get(i);
                            ItemStack stack = null;
                            if (obj instanceof ItemStack) stack = (ItemStack)obj;
                            else if (obj instanceof Block) stack = new ItemStack((Block)obj);
                            else if (obj instanceof Item) stack = new ItemStack((Item)obj);
                            
                            if (stack != null) {
                                int slotX = startX + x * 18;
                                int slotY = startY + y * 18;
                                drawRect(slotX, slotY, slotX + 18, slotY + 18, 0x40000000);
                                drawItemSimple(minecraft, stack, slotX + 1, slotY + 1);
                            }
                        }
                    }
                } catch (Exception e) { e.printStackTrace(); }
                minecraft.fontRenderer.drawStringWithShadow("Shapeless", startX, startY - 10, 0xAAAAAA);
            }
        }
    }

    private int getPrivateInt(Object obj, String name1, String name2) {
        try {
            Field f = null;
            try { f = obj.getClass().getDeclaredField(name1); } catch(Exception e) {}
            if (f == null) f = obj.getClass().getDeclaredField(name2);
            f.setAccessible(true);
            return f.getInt(obj);
        } catch(Exception e) { return 0; }
    }

    private Object getPrivateValue(Object obj, String name1, String name2) {
        try {
            Field f = null;
            try { f = obj.getClass().getDeclaredField(name1); } catch(Exception e) {}
            if (f == null) {
                try { f = obj.getClass().getDeclaredField(name2); } catch(Exception e) {}
            }
            if (f == null) {
                return null;
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch(Exception e) { return null; }
    }

    public void draw(Minecraft minecraft) {
        try {
            if (isRenderingJIM || !renderingEnabled) return;
            isRenderingJIM = true;
            lastRenderTime = System.currentTimeMillis();

            if (minecraft == null || minecraft.fontRenderer == null) {
                isRenderingJIM = false; return;
            }

            ScaledResolution res = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();

            panelWidth = 176;
            panelHeight = height - 20;
            panelLeft = (width - panelWidth) / 2 + panelWidth + 5;
            panelTop = 10;

            searchBoxX = panelLeft + 8;
            searchBoxY = panelTop + 6;
            searchBoxWidth = panelWidth - 16;
            searchBoxHeight = 12;

            int usableHeight = panelHeight - 65;
            int rows = usableHeight / 18;
            if (rows < 1) rows = 1;
            itemsPerPage = rows * 9;

            if (needsFiltering) filterItems();

            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
            int boundTexture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glPushMatrix(); GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, width, height, 0.0D, -2000.0D, 2000.0D);

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glPushMatrix(); GL11.glLoadIdentity();

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_FOG); 
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            drawRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xA0000000);
            drawBorder(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xFFFFFFFF);
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            int mouseX = Mouse.getX() * width / minecraft.displayWidth;
            int mouseY = height - Mouse.getY() * height / minecraft.displayHeight - 1;
            boolean mouseDown = Mouse.isButtonDown(0);
            boolean rightClick = Mouse.isButtonDown(1); 

            if (isRecipeView) {
                drawRecipeView(minecraft, mouseX, mouseY, mouseDown);
            } else {
                drawMainInterface(minecraft, mouseX, mouseY, mouseDown, rightClick);
            }

            if (isSearchBoxFocused && !isRecipeView) processKeyInput();
            
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            wasPreviouslyClicked = mouseDown;
            wasRightClickDown = rightClick;

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, boundTexture);
            GL11.glMatrixMode(GL11.GL_MODELVIEW); GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_PROJECTION); GL11.glPopMatrix();
            GL11.glPopAttrib();

            isRenderingJIM = false;

        } catch (Exception e) {
            System.out.println("JIM: Render error: " + e.getMessage());
            e.printStackTrace();
            isRenderingJIM = false;
        }
    }

    private void drawMainInterface(Minecraft minecraft, int mouseX, int mouseY, boolean mouseDown, boolean rightClick) {
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

        minecraft.fontRenderer.drawStringWithShadow(pageInfo, centerPageInfoX, pageY, 0xFFFFFF);

        String prevPage = "<";
        String nextPage = ">";
        int prevX = centerPageInfoX - 15;
        int nextX = centerPageInfoX + pageInfoWidth + 5;
        boolean canPrev = currentPage > 0;
        boolean canNext = (currentPage + 1) < totalPages;

        minecraft.fontRenderer.drawStringWithShadow(prevPage, prevX, pageY, canPrev ? 0xFFFFFF : 0x808080);
        minecraft.fontRenderer.drawStringWithShadow(nextPage, nextX, pageY, canNext ? 0xFFFFFF : 0x808080);

        if (mouseDown && !wasPreviouslyClicked) {
            if (mouseX >= prevX - 2 && mouseX <= prevX + 10 && mouseY >= pageY - 2 && mouseY <= pageY + 10 && canPrev) {
                currentPage--;
            }
            if (mouseX >= nextX - 2 && mouseX <= nextX + 10 && mouseY >= pageY - 2 && mouseY <= pageY + 10 && canNext) {
                currentPage++;
            }
            if (mouseX >= searchBoxX && mouseX <= searchBoxX + searchBoxWidth && mouseY >= searchBoxY && mouseY <= searchBoxY + searchBoxHeight) {
                isSearchBoxFocused = true;
            } else {
                isSearchBoxFocused = false;
            }
        }

        int wheel = Mouse.getDWheel();
        if (wheel > 0 && currentPage > 0) currentPage--;
        else if (wheel < 0 && (currentPage + 1) < totalPages) currentPage++;

        int gridStartY = searchBoxY + searchBoxHeight + 20;
        int startIndex = currentPage * itemsPerPage;

        if (filteredItems.isEmpty() || filteredItems.size() > allItems.size()) {
            filteredItems.clear(); filteredItems.addAll(allItems); searchText = ""; needsFiltering = false;
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

                boolean mouseOver = isMouseOverSlot(x, y, mouseX, mouseY);
                
                if (cheatMode && mouseDown && mouseOver) {
                    if (!wasPreviousItemClicked) {
                        int quantity = itemstack.getMaxStackSize() > 1 ? 64 : 1;
                        controller.giveItem(itemstack.itemID, quantity, itemstack.getItemDamage());
                        minecraft.thePlayer.swingItem();
                        wasPreviousItemClicked = true;
                    }
                }

                if (rightClick && !wasRightClickDown && mouseOver) {
                    findRecipes(itemstack);
                    isRecipeView = true;
                    if (minecraft.sndManager != null) {
                        minecraft.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    }
                }

            } catch (Exception e) {}
        }
        if (!mouseDown) wasPreviousItemClicked = false;

        drawControls(minecraft, mouseX, mouseY, mouseDown);
    }

    private void drawControls(Minecraft minecraft, int mouseX, int mouseY, boolean mouseDown) {
        int controlsY = panelTop + panelHeight - 25;
        cheatModeX = panelLeft + 8; cheatModeY = controlsY;
        
        drawRect(cheatModeX, cheatModeY, cheatModeX + 10, cheatModeY + 10, 0xFFFFFFFF);
        if (cheatMode) drawRect(cheatModeX + 2, cheatModeY + 2, cheatModeX + 8, cheatModeY + 8, 0xFF00FF00);
        minecraft.fontRenderer.drawStringWithShadow("Cheat Mode", cheatModeX + 15, cheatModeY + 2, 0xFFFFFF);
        
        if (mouseDown && !wasPreviouslyClicked) {
             if (mouseX >= cheatModeX - 2 && mouseX <= cheatModeX + 12 && mouseY >= cheatModeY - 2 && mouseY <= cheatModeY + 12) {
                 cheatMode = !cheatMode;
             }
        }

        int buttonY = controlsY + 25;
        int buttonWidth = 25;
        int spacing = 5;
        
        dayButtonX = panelLeft + 10; dayButtonY = buttonY;
        drawSimpleButton(minecraft, "Day", dayButtonX, dayButtonY, buttonWidth);
        
        nightButtonX = dayButtonX + buttonWidth + spacing; nightButtonY = buttonY;
        drawSimpleButton(minecraft, "Night", nightButtonX, nightButtonY, buttonWidth);

        healButtonX = nightButtonX + buttonWidth + spacing; healButtonY = buttonY;
        drawSimpleButton(minecraft, "Heal", healButtonX, healButtonY, buttonWidth);
        
        rainButtonX = healButtonX + buttonWidth + spacing; rainButtonY = buttonY;
        drawSimpleButton(minecraft, "Rain", rainButtonX, rainButtonY, buttonWidth);

        flyButtonX = rainButtonX + buttonWidth + spacing; flyButtonY = buttonY;
        String flyLabel = "Fly: " + (controller.getConfig() != null && controller.getConfig().isFlyEnabled() ? "ON" : "OFF");
        drawSimpleButton(minecraft, flyLabel, flyButtonX, flyButtonY, buttonWidth);

        if (mouseDown && !wasPreviouslyClicked && cheatMode) {
             if (isMouseOver(mouseX, mouseY, dayButtonX, dayButtonY, buttonWidth, 12)) minecraft.theWorld.setWorldTime(6000);
             if (isMouseOver(mouseX, mouseY, nightButtonX, nightButtonY, buttonWidth, 12)) minecraft.theWorld.setWorldTime(18000);
             if (isMouseOver(mouseX, mouseY, healButtonX, healButtonY, buttonWidth, 12)) minecraft.thePlayer.health = 20;
             if (isMouseOver(mouseX, mouseY, rainButtonX, rainButtonY, buttonWidth, 12)) {
                 try {
                     WorldInfo info = null;
                     try {
                         info = minecraft.theWorld.getWorldInfo(); 
                     } catch (Throwable t) {
                         Field f = World.class.getDeclaredField("worldInfo");
                         f.setAccessible(true);
                         info = (WorldInfo)f.get(minecraft.theWorld);
                     }
                     if (info != null) info.setRaining(!info.getRaining());
                 } catch(Exception e) { System.out.println("JIM: Rain toggle failed"); }
             }
             if (isMouseOver(mouseX, mouseY, flyButtonX, flyButtonY, buttonWidth, 12)) controller.toggleFly();
        }
    }
    
    private boolean isMouseOver(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    private void processKeyInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                int keyCode = Keyboard.getEventKey();
                if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RETURN) {
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
        if (left < right) { int i = left; left = right; right = i; }
        if (top < bottom) { int j = top; top = bottom; bottom = j; }
        float f = (float)(color >> 24 & 255) / 255.0F;
        float f1 = (float)(color >> 16 & 255) / 255.0F;
        float f2 = (float)(color >> 8 & 255) / 255.0F;
        float f3 = (float)(color & 255) / 255.0F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(f1, f2, f3, f);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertex(left, bottom, 0.0D);
        tessellator.addVertex(right, bottom, 0.0D);
        tessellator.addVertex(right, top, 0.0D);
        tessellator.addVertex(left, top, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
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
            if (ts <= 0) ts = 256.0F; 
            
            float eps = 0.5F / ts;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Tessellator t = Tessellator.instance;
            t.startDrawingQuads();
            t.addVertexWithUV(x + 0, y + 16, 0, (iconX + 0.0F) / ts + eps, (iconY + 16.0F) / ts - eps);
            t.addVertexWithUV(x + 16, y + 16, 0, (iconX + 16.0F) / ts - eps, (iconY + 16.0F) / ts - eps);
            t.addVertexWithUV(x + 16, y + 0, 0, (iconX + 16.0F) / ts - eps, (iconY + 0.0F) / ts + eps);
            t.addVertexWithUV(x + 0, y + 0, 0, (iconX + 0.0F) / ts + eps, (iconY + 0.0F) / ts + eps);
            t.draw();

            if (itemstack.stackSize > 1) {
                String s = String.valueOf(itemstack.stackSize);
                minecraft.fontRenderer.drawStringWithShadow(s, x + 19 - 2 - minecraft.fontRenderer.getStringWidth(s), y + 6 + 3, 0xFFFFFF);
            }
        } catch (Exception e) {}
    }
}