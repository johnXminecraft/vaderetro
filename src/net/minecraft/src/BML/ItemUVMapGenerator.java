package net.minecraft.src.BML;

import net.minecraft.src.*;

public class ItemUVMapGenerator extends Item {
    public ItemUVMapGenerator(int id) {
        super(id);
        this.setIconCoord(6, 9);
        this.setItemName("uvMapGenerator");
        this.setMaxStackSize(1);
    }
    
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
        if (world.multiplayerWorld) {
            return true;
        }
        
        generateUVMapForTestModel();
        
        entityplayer.addChatMessage("BML: UV map generated for test model!");
        
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (world.multiplayerWorld) {
            return itemstack;
        }
        
        UVMapGenerator.generateUVMapForAllModels();
        
        String[] loadedModels = ObjModelLoader.getLoadedModelPaths();
        for (String modelPath : loadedModels) {
            UVMapGenerator.generateCleanUVMapForModel(modelPath);
        }
        
        entityplayer.addChatMessage("BML: UV maps and clean UV maps generated for all loaded models!");
        
        return itemstack;
    }
    
    private void generateUVMapForTestModel() {
        String modelPath = "/models/test.obj";
        UVMapGenerator.generateUVMapForModel(modelPath);
        UVMapGenerator.generateCleanUVMapForModel(modelPath);
    }
}
