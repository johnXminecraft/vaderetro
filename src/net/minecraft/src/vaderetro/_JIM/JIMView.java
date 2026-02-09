package net.minecraft.src.vaderetro._JIM;

import java.util.*;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.vaderetro.recipes.creators.cooking.CookingManager;
import net.minecraft.src.vaderetro.recipes.processors.*;
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
    private List<RecipeEntry> foundRecipes = new ArrayList<RecipeEntry>();
    private int currentRecipePage = 0;
    private ItemStack targetItem = null;

    private static final int FONT_HEIGHT = 9;
    private boolean needsFiltering = true;
    private static final int MAX_FOUND_RECIPES = 200;

    private long lastRenderTime = 0;
    private boolean renderingEnabled = true;
    private static boolean isRenderingJIM = false;
    private static boolean useDoubleBuffering = true;

    private String hoverText = null;
    private int hoverX, hoverY;

    private static class ItemStackDepth {
        ItemStack stack;
        int depth;

        ItemStackDepth(ItemStack stack, int depth) {
            this.stack = stack;
            this.depth = depth;
        }
    }

    private static class RecipeEntry {
        String kind;
        String machine;
        Object recipe;
        ItemStack input;
        ItemStack output;
        int depth;

        RecipeEntry(String kind, String machine, Object recipe, ItemStack input, ItemStack output, int depth) {
            this.kind = kind;
            this.machine = machine;
            this.recipe = recipe;
            this.input = input;
            this.output = output;
            this.depth = depth;
        }
    }

    public JIMView(JIMController controller) {
        this.controller = controller;
        populateItems();
        this.filteredItems.addAll(allItems);
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
                } catch (Exception e) {}
            }
        }
        for (int i = 0; i < Block.blocksList.length; i++) {
            if (Block.blocksList[i] != null && Item.itemsList[i] == null) {
                try {
                    ItemStack stack = new ItemStack(Block.blocksList[i], 1, 0);
                    allItems.add(stack);
                } catch (Exception e) {}
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
                String displayName = getDisplayName(item); 

                if (displayName.toLowerCase().contains(search) ||
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

        Set<String> recipeKeys = new HashSet<String>();
        List<RecipeEntry> direct = collectDirectRecipes(target, 0);
        for (int i = 0; i < direct.size() && foundRecipes.size() < MAX_FOUND_RECIPES; i++) {
            RecipeEntry entry = direct.get(i);
            String key = getRecipeKey(entry);
            if (!recipeKeys.contains(key)) {
                recipeKeys.add(key);
                foundRecipes.add(entry);
            }
        }
    }

    private void drawRecipeView(Minecraft minecraft, int mouseX, int mouseY, boolean mouseDown, boolean rightClick) {
        hoverText = null;
        int backBtnX = panelLeft + 10;
        int backBtnY = panelTop + 25;
        
        drawGradientButton(minecraft, "< Back", backBtnX, backBtnY, 40, 12, 0xFF555555, 0xFFAAAAAA);
        
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

        RecipeEntry currentRecipe = foundRecipes.get(currentRecipePage);
        String title = "Recipe " + (currentRecipePage + 1) + "/" + foundRecipes.size();
        String typeLabel = getRecipeTypeLabel(currentRecipe);

        minecraft.fontRenderer.drawStringWithShadow(title, panelLeft + 60, panelTop + 25, 0xFFFFFF);
        if (typeLabel != null && typeLabel.length() > 0) {
            minecraft.fontRenderer.drawStringWithShadow(typeLabel, panelLeft + 60, panelTop + 35, 0xAAAAAA);
        }

        int centerX = panelLeft + panelWidth / 2;
        int centerY = panelTop + 80;

        int navY = centerY + 60;
        
        if (foundRecipes.size() > 1) {
             if (currentRecipePage > 0) {
                 int prevX = centerX - 30;
                 drawGradientButton(minecraft, "<", prevX, navY, 20, 12, 0xFF555555, 0xFFAAAAAA);
                 if (mouseDown && !wasPreviouslyClicked && 
                     mouseX >= prevX && mouseX <= prevX + 20 && 
                     mouseY >= navY && mouseY <= navY + 12) {
                     currentRecipePage--;
                 }
             }
             if (currentRecipePage < foundRecipes.size() - 1) {
                 int nextX = centerX + 10;
                 drawGradientButton(minecraft, ">", nextX, navY, 20, 12, 0xFF555555, 0xFFAAAAAA);
                 if (mouseDown && !wasPreviouslyClicked && 
                     mouseX >= nextX && mouseX <= nextX + 20 && 
                     mouseY >= navY && mouseY <= navY + 12) {
                     currentRecipePage++;
                 }
             }
        }

        if ("smelting".equals(currentRecipe.kind) || "processing".equals(currentRecipe.kind)) {
            ItemStack inputStack = currentRecipe.input;
            ItemStack outputStack = currentRecipe.output;
            
            drawSlot(centerX - 20, centerY - 20, 20, 20);
            drawItemSimple(minecraft, inputStack, centerX - 19, centerY - 19);
            
            if (isMouseOverSlot(centerX - 20, centerY - 20, mouseX, mouseY, 20)) {
                hoverText = getDisplayName(inputStack);
                hoverX = mouseX + 10;
                hoverY = mouseY;
                if (rightClick && !wasRightClickDown) {
                    findRecipes(inputStack);
                    targetItem = inputStack;
                }
            }

            minecraft.fontRenderer.drawStringWithShadow("->", centerX + 5, centerY - 15, 0xFFFFFF);
            String machineLabel = currentRecipe.machine != null ? currentRecipe.machine : "Process";
            minecraft.fontRenderer.drawStringWithShadow(machineLabel, centerX - 20, centerY + 5, 0xFFAAAAAA);

            drawSlot(centerX + 20, centerY - 20, 20, 20);
            drawItemSimple(minecraft, outputStack, centerX + 21, centerY - 19);
            
            if (isMouseOverSlot(centerX + 20, centerY - 20, mouseX, mouseY, 20)) {
                hoverText = getDisplayName(outputStack);
                hoverX = mouseX + 10;
                hoverY = mouseY;
            }

            drawFuelList(minecraft, centerX, centerY + 28, currentRecipe.machine, mouseX, mouseY);
        } 
        else if ("mining".equals(currentRecipe.kind)) {
            ItemStack sourceStack = currentRecipe.input;
            ItemStack outputStack = currentRecipe.output;
            
            drawSlot(centerX - 20, centerY - 20, 20, 20);
            drawItemSimple(minecraft, sourceStack, centerX - 19, centerY - 19);
            
            if (isMouseOverSlot(centerX - 20, centerY - 20, mouseX, mouseY, 20)) {
                hoverText = getDisplayName(sourceStack);
                hoverX = mouseX + 10;
                hoverY = mouseY;
                if (rightClick && !wasRightClickDown) {
                    findRecipes(sourceStack);
                    targetItem = sourceStack;
                }
            }

            minecraft.fontRenderer.drawStringWithShadow("->", centerX + 5, centerY - 15, 0xFFFFFF);
            minecraft.fontRenderer.drawStringWithShadow("Mined", centerX - 15, centerY + 5, 0xFF8888);

            drawSlot(centerX + 20, centerY - 20, 20, 20);
            drawItemSimple(minecraft, outputStack, centerX + 21, centerY - 19);
            
            if (isMouseOverSlot(centerX + 20, centerY - 20, mouseX, mouseY, 20)) {
                hoverText = getDisplayName(outputStack);
                hoverX = mouseX + 10;
                hoverY = mouseY;
            }
        }
        else if ("crafting".equals(currentRecipe.kind) || "cooking".equals(currentRecipe.kind)) {
            IRecipe recipe = currentRecipe.recipe instanceof IRecipe ? (IRecipe) currentRecipe.recipe : null;
            if (recipe == null) return;
            drawSlot(centerX + 40, centerY - 10, 20, 20);
            drawItemSimple(minecraft, recipe.getRecipeOutput(), centerX + 41, centerY - 9);
            
            if (isMouseOverSlot(centerX + 40, centerY - 10, mouseX, mouseY, 20)) {
                hoverText = getDisplayName(recipe.getRecipeOutput());
                hoverX = mouseX + 10;
                hoverY = mouseY;
            }
            
            minecraft.fontRenderer.drawStringWithShadow("=", centerX + 28, centerY - 4, 0xFFFFFF);

            int startX = centerX - 40;
            int startY = centerY - 28;
            
            if (recipe instanceof ShapedRecipes) {
                ShapedRecipes shaped = (ShapedRecipes) recipe;
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
                                    drawSlot(slotX, slotY, 18, 18);
                                    drawItemSimple(minecraft, items[index], slotX + 1, slotY + 1);
                                    
                                    if (isMouseOverSlot(slotX, slotY, mouseX, mouseY, 18)) {
                                        hoverText = getDisplayName(items[index]);
                                        hoverX = mouseX + 10;
                                        hoverY = mouseY;
                                        if (rightClick && !wasRightClickDown) {
                                            findRecipes(items[index]);
                                            targetItem = items[index];
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {}
            } 
            else if (recipe instanceof ShapelessRecipes) {
                ShapelessRecipes shapeless = (ShapelessRecipes) recipe;
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
                                drawSlot(slotX, slotY, 18, 18);
                                drawItemSimple(minecraft, stack, slotX + 1, slotY + 1);
                                
                                if (isMouseOverSlot(slotX, slotY, mouseX, mouseY, 18)) {
                                    hoverText = getDisplayName(stack);
                                    hoverX = mouseX + 10;
                                    hoverY = mouseY;
                                    if (rightClick && !wasRightClickDown) {
                                        findRecipes(stack);
                                        targetItem = stack;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {}
                minecraft.fontRenderer.drawStringWithShadow("Shapeless", startX, startY - 10, 0xAAAAAA);
            }
        }

        if (hoverText != null) {
            int textWidth = minecraft.fontRenderer.getStringWidth(hoverText);
            drawGradientRect(hoverX, hoverY, hoverX + textWidth + 4, hoverY + 12, 0xFF000000, 0xFF333333);
            minecraft.fontRenderer.drawStringWithShadow(hoverText, hoverX + 2, hoverY + 2, 0xFFFFFF);
        }
    }

    private String getDisplayName(ItemStack stack) {
        if (stack == null) return "Unknown";
        String rawName = null;
        try {
            rawName = stack.getItem().getItemNameIS(stack);
        } catch (Exception e) {}
        
        if (rawName == null) return "Unknown";

        String translated = StatCollector.translateToLocal(rawName);
        if (!translated.equals(rawName)) return translated;

        String withName = rawName + ".name";
        String translatedWithName = StatCollector.translateToLocal(withName);
        if (!translatedWithName.equals(withName)) return translatedWithName;

        return rawName.replace("item.", "").replace("tile.", "");
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

    private List<RecipeEntry> collectDirectRecipes(ItemStack target, int depth) {
        List<RecipeEntry> results = new ArrayList<RecipeEntry>();

        List crafting = CraftingManager.getInstance().getRecipeList();
        for (Object obj : crafting) {
            if (obj instanceof IRecipe) {
                IRecipe recipe = (IRecipe) obj;
                ItemStack output = recipe.getRecipeOutput();
                if (matchesTarget(output, target)) {
                    results.add(new RecipeEntry("crafting", "Crafting", recipe, null, output, depth));
                }
            }
        }

        List cooking = CookingManager.getInstance().getRecipeList();
        for (Object obj : cooking) {
            if (obj instanceof IRecipe) {
                IRecipe recipe = (IRecipe) obj;
                ItemStack output = recipe.getRecipeOutput();
                if (matchesTarget(output, target)) {
                    results.add(new RecipeEntry("cooking", "Cooking Table", recipe, null, output, depth));
                }
            }
        }

        Map smelting = FurnaceRecipes.smelting().getSmeltingList();
        for (Object key : smelting.keySet()) {
            ItemStack result = (ItemStack) smelting.get(key);
            if (matchesTarget(result, target)) {
                ItemStack input = new ItemStack(((Integer) key).intValue(), 1, 0);
                results.add(new RecipeEntry("smelting", "Furnace", null, input, result, depth));
            }
        }

        addProcessorRecipes(results, target, depth, "Oven", getProcessorMap(OvenRecipes.processing()));
        addProcessorRecipes(results, target, depth, "Ceramic Furnace", getProcessorMap(CeramicFurnaceRecipes.processing()));
        addProcessorRecipes(results, target, depth, "Open Hearth", getProcessorMap(OpenHearthFurnaceRecipes.processing()));
        addProcessorRecipes(results, target, depth, "Wheat Grinder", getProcessorMap(WheatGrinderRecipes.processing()));
        addProcessorRecipes(results, target, depth, "Macerator", getProcessorMap(MaceratorRecipes.processing()));
        addProcessorRecipes(results, target, depth, "Dryer", getProcessorMap(DryerRecipes.processing()));
        addProcessorRecipes(results, target, depth, "Turntable", getProcessorMap(TurntableRecipes.processing()));
        addProcessorRecipes(results, target, depth, "Kerosene Lamp", getProcessorMap(KeroseneLampRecipes.processing()));

        for (int i = 0; i < Block.blocksList.length; i++) {
            Block b = Block.blocksList[i];
            if (b != null) {
                try {
                    for (int meta = 0; meta < 16; meta++) {
                        int droppedId = b.idDropped(meta, new Random());
                        if (droppedId == target.itemID) {
                            if (b.blockID == Block.oreLapis.blockID && target.itemID == Item.dyePowder.shiftedIndex && target.getItemDamage() != 4) {
                                continue;
                            }
                            if (b.blockID == target.itemID && meta == target.getItemDamage()) continue;
                            ItemStack sourceBlock = new ItemStack(b, 1, meta);
                            results.add(new RecipeEntry("mining", "Mining", null, sourceBlock, target, depth));
                            break;
                        }
                    }
                } catch (Exception e) {}
            }
        }

        return results;
    }

    private void addProcessorRecipes(List<RecipeEntry> results, ItemStack target, int depth, String machine, Map map) {
        if (map == null) return;
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            if (!(value instanceof ItemStack)) continue;
            ItemStack output = (ItemStack) value;
            if (matchesTarget(output, target)) {
                int inputId = key instanceof Integer ? ((Integer) key).intValue() : -1;
                if (inputId >= 0) {
                    ItemStack input = new ItemStack(inputId, 1, 0);
                    results.add(new RecipeEntry("processing", machine, null, input, output, depth));
                }
            }
        }
    }

    private Map getProcessorMap(Object processor) {
        if (processor == null) return null;
        try {
            Field f = processor.getClass().getDeclaredField("processingList");
            f.setAccessible(true);
            Object v = f.get(processor);
            if (v instanceof Map) return (Map) v;
        } catch (Exception e) {}
        try {
            if (processor instanceof IProcessorRecipes) {
                return ((IProcessorRecipes) processor).getProcessingList();
            }
        } catch (Exception e) {}
        return null;
    }

    private boolean matchesTarget(ItemStack output, ItemStack target) {
        if (output == null || target == null) return false;
        if (output.itemID != target.itemID) return false;
        int targetDamage = target.getItemDamage();
        return targetDamage == -1 || output.getItemDamage() == targetDamage;
    }

    private String getRecipeTypeLabel(RecipeEntry entry) {
        if (entry == null) return "";
        if ("crafting".equals(entry.kind)) return "Crafting";
        if ("cooking".equals(entry.kind)) return "Cooking Table";
        if ("smelting".equals(entry.kind)) return "Furnace";
        if ("mining".equals(entry.kind)) return "Mining";
        if ("processing".equals(entry.kind)) return entry.machine != null ? entry.machine : "Processing";
        return "";
    }

    private String getRecipeKey(RecipeEntry entry) {
        if (entry == null) return "";
        if (entry.recipe != null) {
            return entry.kind + "|" + entry.machine + "|" + System.identityHashCode(entry.recipe);
        }
        return entry.kind + "|" + entry.machine + "|" + getStackKey(entry.input) + "|" + getStackKey(entry.output);
    }

    private String getStackKey(ItemStack stack) {
        if (stack == null) return "null";
        return stack.itemID + ":" + stack.getItemDamage();
    }

    private List<ItemStack> getRecipeIngredients(RecipeEntry entry) {
        List<ItemStack> items = new ArrayList<ItemStack>();
        if (entry == null) return items;
        if ("smelting".equals(entry.kind) || "processing".equals(entry.kind)) {
            if (entry.input != null) items.add(entry.input);
            return items;
        }
        if ("crafting".equals(entry.kind) || "cooking".equals(entry.kind)) {
            if (!(entry.recipe instanceof IRecipe)) return items;
            IRecipe recipe = (IRecipe) entry.recipe;
            if (recipe instanceof ShapedRecipes) {
                try {
                    ItemStack[] stacks = (ItemStack[]) getPrivateValue(recipe, "recipeItems", "d");
                    if (stacks != null) {
                        for (int i = 0; i < stacks.length; i++) {
                            if (stacks[i] != null) items.add(stacks[i]);
                        }
                    }
                } catch (Exception e) {}
            } else if (recipe instanceof ShapelessRecipes) {
                try {
                    List list = (List) getPrivateValue(recipe, "recipeItems", "b");
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            Object obj = list.get(i);
                            if (obj instanceof ItemStack) items.add((ItemStack) obj);
                            else if (obj instanceof Item) items.add(new ItemStack((Item) obj));
                            else if (obj instanceof Block) items.add(new ItemStack((Block) obj));
                        }
                    }
                } catch (Exception e) {}
            }
        }
        return items;
    }

    private void drawFuelList(Minecraft minecraft, int centerX, int startY, String machine, int mouseX, int mouseY) {
        List<ItemStack> fuels = getFuelItemsForMachine(machine);
        if (fuels.isEmpty()) return;
        int perRow = 7;
        int total = fuels.size();
        int rows = (total + perRow - 1) / perRow;
        int rowWidth = Math.min(perRow, total) * 18;
        int startX = centerX - rowWidth / 2;
        minecraft.fontRenderer.drawStringWithShadow("Fuel", centerX - 12, startY - 10, 0xAAAAAA);
        for (int i = 0; i < total; i++) {
            int row = i / perRow;
            int col = i % perRow;
            int x = startX + col * 18;
            int y = startY + row * 18;
            drawSlot(x, y, 18, 18);
            ItemStack fuel = fuels.get(i);
            drawItemSimple(minecraft, fuel, x + 1, y + 1);
            if (isMouseOverSlot(x, y, mouseX, mouseY, 18)) {
                hoverText = getDisplayName(fuel);
                hoverX = mouseX + 10;
                hoverY = mouseY;
            }
        }
    }

    private List<ItemStack> getFuelItemsForMachine(String machine) {
        List<ItemStack> fuels = new ArrayList<ItemStack>();
        if (machine == null) return fuels;
        if ("Furnace".equals(machine)) {
            fuels.add(new ItemStack(Block.planks));
            fuels.add(new ItemStack(Block.wood));
            fuels.add(new ItemStack(Item.stick));
            fuels.add(new ItemStack(Item.coal));
            fuels.add(new ItemStack(Item.charcoal));
            fuels.add(new ItemStack(Block.sapling));
            fuels.add(new ItemStack(Block.blockCoal));
            fuels.add(new ItemStack(Item.bucketLava));
        } else if ("Oven".equals(machine) || "Ceramic Furnace".equals(machine)) {
            fuels.add(new ItemStack(Block.planks));
            fuels.add(new ItemStack(Block.wood));
            fuels.add(new ItemStack(Item.stick));
            fuels.add(new ItemStack(Item.coal));
            fuels.add(new ItemStack(Item.charcoal));
            fuels.add(new ItemStack(Block.sapling));
            fuels.add(new ItemStack(Block.blockCoal));
        } else if ("Open Hearth".equals(machine)) {
            fuels.add(new ItemStack(Item.bucketLava));
        }
        return fuels;
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
            drawGradientRect(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xA0000000, 0xA0333333);
            drawBorder(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xFFFFFFFF);
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            int mouseX = Mouse.getX() * width / minecraft.displayWidth;
            int mouseY = height - Mouse.getY() * height / minecraft.displayHeight - 1;
            boolean mouseDown = Mouse.isButtonDown(0);
            boolean rightClick = Mouse.isButtonDown(1); 

            if (isRecipeView) {
                drawRecipeView(minecraft, mouseX, mouseY, mouseDown, rightClick);
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
            isRenderingJIM = false;
        }
    }

    private void drawMainInterface(Minecraft minecraft, int mouseX, int mouseY, boolean mouseDown, boolean rightClick) {
        drawGradientRect(searchBoxX, searchBoxY, searchBoxX + searchBoxWidth, searchBoxY + searchBoxHeight, 0xFFFFFFFF, 0xFFDDDDDD);
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

        hoverText = null;

        for (int i = 0; i < itemsPerPage && i + startIndex < filteredItems.size(); i++) {
            int row = i / 9;
            int col = i % 9;
            int x = panelLeft + 8 + col * 18;
            int y = gridStartY + row * 18;

            drawSlot(x - 1, y - 1, 18, 18);
            try {
                ItemStack itemstack = filteredItems.get(i + startIndex);
                drawItemSimple(minecraft, itemstack, x, y);

                boolean mouseOver = isMouseOverSlot(x - 1, y - 1, mouseX, mouseY, 18);
                
                if (mouseOver) {
                    hoverText = getDisplayName(itemstack);
                    hoverX = mouseX + 10;
                    hoverY = mouseY;
                }

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

        if (hoverText != null) {
            int textWidth = minecraft.fontRenderer.getStringWidth(hoverText);
            drawGradientRect(hoverX, hoverY, hoverX + textWidth + 4, hoverY + 12, 0xFF000000, 0xFF333333);
            minecraft.fontRenderer.drawStringWithShadow(hoverText, hoverX + 2, hoverY + 2, 0xFFFFFF);
        }

        drawControls(minecraft, mouseX, mouseY, mouseDown);
    }

    private void drawControls(Minecraft minecraft, int mouseX, int mouseY, boolean mouseDown) {
        int controlsY = panelTop + panelHeight - 40;
        cheatModeX = panelLeft + 8; cheatModeY = controlsY;
        
        drawGradientRect(cheatModeX, cheatModeY, cheatModeX + 10, cheatModeY + 10, 0xFFFFFFFF, 0xFFDDDDDD);
        if (cheatMode) drawGradientRect(cheatModeX + 2, cheatModeY + 2, cheatModeX + 8, cheatModeY + 8, 0xFF00FF00, 0xFF00AA00);
        minecraft.fontRenderer.drawStringWithShadow("Cheat Mode", cheatModeX + 15, cheatModeY + 2, 0xFFFFFF);
        
        if (mouseDown && !wasPreviouslyClicked) {
             if (mouseX >= cheatModeX - 2 && mouseX <= cheatModeX + 12 && mouseY >= cheatModeY - 2 && mouseY <= cheatModeY + 12) {
                 cheatMode = !cheatMode;
             }
        }

        int buttonY = controlsY + 15;
        int buttonWidth = 25;
        int spacing = 5;
        
        dayButtonX = panelLeft + 10; dayButtonY = buttonY;
        drawGradientButton(minecraft, "Day", dayButtonX, dayButtonY, buttonWidth, 12, 0xFF555555, 0xFFAAAAAA);
        
        nightButtonX = dayButtonX + buttonWidth + spacing; nightButtonY = buttonY;
        drawGradientButton(minecraft, "Night", nightButtonX, nightButtonY, buttonWidth, 12, 0xFF555555, 0xFFAAAAAA);

        healButtonX = nightButtonX + buttonWidth + spacing; healButtonY = buttonY;
        drawGradientButton(minecraft, "Heal", healButtonX, healButtonY, buttonWidth, 12, 0xFF555555, 0xFFAAAAAA);
        
        rainButtonX = healButtonX + buttonWidth + spacing; rainButtonY = buttonY;
        drawGradientButton(minecraft, "Rain", rainButtonX, rainButtonY, buttonWidth, 12, 0xFF555555, 0xFFAAAAAA);

        flyButtonX = rainButtonX + buttonWidth + spacing; flyButtonY = buttonY;
        String flyLabel = "Fly " + (controller.getConfig() != null && controller.getConfig().isFlyEnabled() ? "ON" : "OFF");
        drawGradientButton(minecraft, flyLabel, flyButtonX, flyButtonY, buttonWidth, 12, 0xFF555555, 0xFFAAAAAA);

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
                 } catch(Exception e) {}
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

    private void drawGradientButton(Minecraft minecraft, String text, int x, int y, int width, int height, int color1, int color2) {
        String fitted = fitText(minecraft, text, width - 4);
        int textWidth = minecraft.fontRenderer.getStringWidth(fitted);
        int paddingX = (width - textWidth) / 2;
        drawGradientRect(x, y, x + width, y + height, color1, color2);
        drawBorder(x, y, x + width, y + height, 0xFF000000);
        minecraft.fontRenderer.drawStringWithShadow(fitted, x + paddingX, y + 2, 0xFFFFFF);
    }

    private String fitText(Minecraft minecraft, String text, int maxWidth) {
        if (text == null) return "";
        if (maxWidth <= 0) return "";
        if (minecraft.fontRenderer.getStringWidth(text) <= maxWidth) return text;
        String trimmed = text;
        while (trimmed.length() > 0 && minecraft.fontRenderer.getStringWidth(trimmed) > maxWidth) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        return trimmed;
    }

    private void drawBorder(int left, int top, int right, int bottom, int color) {
        drawRect(left, top, right, top + 1, color);
        drawRect(left, bottom - 1, right, bottom, color);
        drawRect(left, top + 1, left + 1, bottom - 1, color);
        drawRect(right - 1, top + 1, right, bottom - 1, color);
    }

    private void drawRect(int left, int top, int right, int bottom, int color) {
        if (left > right) { int i = left; left = right; right = i; }
        if (top > bottom) { int j = top; top = bottom; bottom = j; }
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

    private void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float startA = (float)(startColor >> 24 & 255) / 255.0F;
        float startR = (float)(startColor >> 16 & 255) / 255.0F;
        float startG = (float)(startColor >> 8 & 255) / 255.0F;
        float startB = (float)(startColor & 255) / 255.0F;
        float endA = (float)(endColor >> 24 & 255) / 255.0F;
        float endR = (float)(endColor >> 16 & 255) / 255.0F;
        float endG = (float)(endColor >> 8 & 255) / 255.0F;
        float endB = (float)(endColor & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(startR, startG, startB, startA);
        tessellator.addVertex(right, top, 0.0D);
        tessellator.addVertex(left, top, 0.0D);
        tessellator.setColorRGBA_F(endR, endG, endB, endA);
        tessellator.addVertex(left, bottom, 0.0D);
        tessellator.addVertex(right, bottom, 0.0D);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    private void drawSlot(int x, int y, int width, int height) {
        drawGradientRect(x, y, x + width, y + height, 0xFFCCCCCC, 0xFF888888);
        drawBorder(x, y, x + width, y + height, 0xFF000000);
    }

    private boolean isMouseOverSlot(int x, int y, int mouseX, int mouseY, int size) {
        return mouseX >= x && mouseX < x + size && mouseY >= y && mouseY < y + size;
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
