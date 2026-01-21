package net.minecraft.src.vaderetro._BML;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class UVMapGenerator {
    private static final int UV_MAP_SIZE = 512;
    private static final Color FACE_COLOR = Color.WHITE;
    private static final Color EDGE_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE; 
    private static final boolean SHOW_FACE_NUMBERS = false; 
    
    public static class UVFace {
        public float[] uv1, uv2, uv3;
        public int faceIndex;
        public String materialName;
        
        public UVFace(float[] uv1, float[] uv2, float[] uv3, int faceIndex, String materialName) {
            this.uv1 = uv1;
            this.uv2 = uv2;
            this.uv3 = uv3;
            this.faceIndex = faceIndex;
            this.materialName = materialName;
        }
    }
    
    public static void generateUVMap(String modelPath, String outputPath) {
        System.out.println("BML: Generating UV map for model: " + modelPath);
        
        ObjModelLoader.ObjModel model = ObjModelLoader.loadModel(modelPath);
        if (model == null) {
            System.err.println("BML: Failed to load model for UV map generation: " + modelPath);
            return;
        }
        
        java.util.List<UVFace> uvFaces = extractUVFaces(model);
        if (uvFaces.isEmpty()) {
            System.err.println("BML: No UV coordinates found in model: " + modelPath);
            return;
        }
        
        BufferedImage uvMap = createUVMapImage(uvFaces);
        
        try {
            File outputFile = new File(outputPath);
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                System.out.println("BML: Created directory: " + parentDir.getAbsolutePath() + " (success: " + created + ")");
            }
            
            boolean saved = javax.imageio.ImageIO.write(uvMap, "PNG", outputFile);
            System.out.println("BML: UV map saved to: " + outputFile.getAbsolutePath() + " (success: " + saved + ")");
            
            if (outputFile.exists()) {
                System.out.println("BML: File confirmed to exist, size: " + outputFile.length() + " bytes");
            } else {
                System.err.println("BML: File was not created successfully!");
            }
        } catch (IOException e) {
            System.err.println("BML: Failed to save UV map: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static java.util.List<UVFace> extractUVFaces(ObjModelLoader.ObjModel model) {
        java.util.List<UVFace> uvFaces = new ArrayList<>();
        
        for (int i = 0; i < model.faces.size(); i++) {
            ObjModelLoader.ObjFace face = model.faces.get(i);
            
            if (face.texCoordIndices[0] >= 0 && face.texCoordIndices[1] >= 0 && face.texCoordIndices[2] >= 0) {
                float[] uv1 = model.texCoords.get(face.texCoordIndices[0]);
                float[] uv2 = model.texCoords.get(face.texCoordIndices[1]);
                float[] uv3 = model.texCoords.get(face.texCoordIndices[2]);
                
                uvFaces.add(new UVFace(uv1, uv2, uv3, i, model.materialName));
            }
        }
        
        return uvFaces;
    }
    
    private static BufferedImage createUVMapImage(java.util.List<UVFace> uvFaces) {
        BufferedImage image = new BufferedImage(UV_MAP_SIZE, UV_MAP_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, UV_MAP_SIZE, UV_MAP_SIZE);
        
        g2d.setColor(FACE_COLOR);
        g2d.setStroke(new BasicStroke(1.0f));
        
        for (UVFace uvFace : uvFaces) {
            drawUVFace(g2d, uvFace);
        }
        
        g2d.setColor(EDGE_COLOR);
        g2d.setStroke(new BasicStroke(2.0f));
        
        for (UVFace uvFace : uvFaces) {
            drawUVFaceEdges(g2d, uvFace);
        }
        
        if (SHOW_FACE_NUMBERS) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            
            for (UVFace uvFace : uvFaces) {
                drawFaceNumber(g2d, uvFace);
            }
        }
        
        g2d.dispose();
        return image;
    }
    
    private static void drawUVFace(Graphics2D g2d, UVFace uvFace) {
        int[] xPoints = {
            (int)(uvFace.uv1[0] * UV_MAP_SIZE),
            (int)(uvFace.uv2[0] * UV_MAP_SIZE),
            (int)(uvFace.uv3[0] * UV_MAP_SIZE)
        };
        int[] yPoints = {
            (int)((1.0f - uvFace.uv1[1]) * UV_MAP_SIZE),
            (int)((1.0f - uvFace.uv2[1]) * UV_MAP_SIZE),
            (int)((1.0f - uvFace.uv3[1]) * UV_MAP_SIZE)
        };
        
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
    
    private static void drawUVFaceEdges(Graphics2D g2d, UVFace uvFace) {
        int[] xPoints = {
            (int)(uvFace.uv1[0] * UV_MAP_SIZE),
            (int)(uvFace.uv2[0] * UV_MAP_SIZE),
            (int)(uvFace.uv3[0] * UV_MAP_SIZE)
        };
        int[] yPoints = {
            (int)((1.0f - uvFace.uv1[1]) * UV_MAP_SIZE),
            (int)((1.0f - uvFace.uv2[1]) * UV_MAP_SIZE),
            (int)((1.0f - uvFace.uv3[1]) * UV_MAP_SIZE)
        };
        
        g2d.drawPolygon(xPoints, yPoints, 3);
    }
    
    private static void drawFaceNumber(Graphics2D g2d, UVFace uvFace) {
        float centerX = (uvFace.uv1[0] + uvFace.uv2[0] + uvFace.uv3[0]) / 3.0f;
        float centerY = (uvFace.uv1[1] + uvFace.uv2[1] + uvFace.uv3[1]) / 3.0f;
        
        int x = (int)(centerX * UV_MAP_SIZE);
        int y = (int)((1.0f - centerY) * UV_MAP_SIZE);
        
        String text = String.valueOf(uvFace.faceIndex);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        
        g2d.drawString(text, x - textWidth/2, y + textHeight/4);
    }
    
    public static void generateUVMapForAllModels() {
        System.out.println("BML: Generating UV maps for all loaded models...");
        
        String[] loadedModels = ObjModelLoader.getLoadedModelPaths();
        for (String modelPath : loadedModels) {
            generateUVMapForModel(modelPath);
        }
    }
    
    public static void generateUVMapForModel(String modelPath) {
        String fileName = new File(modelPath).getName();
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        
        String currentDir = System.getProperty("user.dir");
        String outputPath = currentDir + File.separator + "resources" + File.separator + "uvmaps" + File.separator + baseName + "_uvmap.png";
        
        System.out.println("BML: Current working directory: " + currentDir);
        System.out.println("BML: Output path: " + outputPath);
        
        generateUVMap(modelPath, outputPath);
    }
    
    public static void generateCleanUVMapForModel(String modelPath) {
        String fileName = new File(modelPath).getName();
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        
        String currentDir = System.getProperty("user.dir");
        String outputPath = currentDir + File.separator + "resources" + File.separator + "uvmaps" + File.separator + baseName + "_clean_uvmap.png";
        
        System.out.println("BML: Generating clean UV map for: " + modelPath);
        System.out.println("BML: Output path: " + outputPath);
        
        generateUVMap(modelPath, outputPath);
    }
}
