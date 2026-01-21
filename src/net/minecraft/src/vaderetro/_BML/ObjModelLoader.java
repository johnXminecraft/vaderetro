package net.minecraft.src.vaderetro._BML;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import java.io.*;
import java.util.*;

public class ObjModelLoader {
    private static Map<String, ObjModel> loadedModels = new HashMap<>();
    
    public static class ObjModel {
        public List<float[]> vertices = new ArrayList<>();
        public List<float[]> normals = new ArrayList<>();
        public List<float[]> texCoords = new ArrayList<>();
        public List<ObjFace> faces = new ArrayList<>();
        public String materialName = "";
        
        public void render(float scale) {
            Tessellator tessellator = Tessellator.instance;
            
            for (ObjFace face : faces) {
                tessellator.startDrawing(GL11.GL_TRIANGLES);
                
                for (int i = 0; i < face.vertexIndices.length; i++) {
                    int vertexIndex = face.vertexIndices[i];
                    int normalIndex = face.normalIndices[i];
                    int texIndex = face.texCoordIndices[i];
                    
                    float[] vertex = null;
                    if (vertexIndex < vertices.size()) {
                        vertex = vertices.get(vertexIndex);
                    }
                    
                    if (normalIndex < normals.size()) {
                        float[] normal = normals.get(normalIndex);
                        tessellator.setNormal(normal[0], normal[1], normal[2]);
                    }
                    
                    if (texIndex < texCoords.size() && vertex != null) {
                        float[] texCoord = texCoords.get(texIndex);
                        tessellator.addVertexWithUV(
                            vertex[0] * scale,
                            vertex[1] * scale,
                            vertex[2] * scale,
                            texCoord[0],
                            texCoord[1]
                        );
                    } else if (vertex != null) {
                        tessellator.addVertex(
                            vertex[0] * scale,
                            vertex[1] * scale,
                            vertex[2] * scale
                        );
                    }
                }
                
                tessellator.draw();
            }
        }
    }
    
    public static class ObjFace {
        public int[] vertexIndices;
        public int[] normalIndices;
        public int[] texCoordIndices;
        
        public ObjFace(int[] vertexIndices, int[] normalIndices, int[] texCoordIndices) {
            this.vertexIndices = vertexIndices;
            this.normalIndices = normalIndices;
            this.texCoordIndices = texCoordIndices;
        }
    }
    
    public static ObjModel loadModel(String modelPath) {
        if (loadedModels.containsKey(modelPath)) {
            return loadedModels.get(modelPath);
        }
        
        try {
            InputStream inputStream = ObjModelLoader.class.getResourceAsStream(modelPath);
            if (inputStream == null) {
                File file = new File(modelPath);
                if (file.exists()) {
                    inputStream = new FileInputStream(file);
                } else {
                    System.err.println("BML: OBJ model not found: " + modelPath);
                    return null;
                }
            }
            
            ObjModel model = parseObjFile(inputStream);
            loadedModels.put(modelPath, model);
            System.out.println("BML: Loaded OBJ model: " + modelPath);
            return model;
            
        } catch (IOException e) {
            System.err.println("BML: Error loading OBJ model: " + modelPath);
            e.printStackTrace();
            return null;
        }
    }
    
    private static ObjModel parseObjFile(InputStream inputStream) throws IOException {
        ObjModel model = new ObjModel();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            
            String[] parts = line.split("\\s+");
            if (parts.length == 0) continue;
            
            switch (parts[0]) {
                case "v": // Vertex
                    if (parts.length >= 4) {
                        float[] vertex = {
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])
                        };
                        model.vertices.add(vertex);
                    }
                    break;
                    
                case "vn": // Normal
                    if (parts.length >= 4) {
                        float[] normal = {
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])
                        };
                        model.normals.add(normal);
                    }
                    break;
                    
                case "vt": // Texture coordinate
                    if (parts.length >= 3) {
                        float[] texCoord = {
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2])
                        };
                        model.texCoords.add(texCoord);
                    }
                    break;
                    
                case "f": // Face
                    if (parts.length >= 4) {
                        int[] vertexIndices = new int[parts.length - 1];
                        int[] normalIndices = new int[parts.length - 1];
                        int[] texCoordIndices = new int[parts.length - 1];
                        
                        for (int i = 1; i < parts.length; i++) {
                            String[] indices = parts[i].split("/");
                            
                            // Vertex index (required)
                            vertexIndices[i-1] = Integer.parseInt(indices[0]) - 1; // OBJ uses 1-based indexing
                            
                            if (indices.length > 1 && !indices[1].isEmpty()) {
                                texCoordIndices[i-1] = Integer.parseInt(indices[1]) - 1;
                            } else {
                                texCoordIndices[i-1] = -1;
                            }
                            
                            // Normal index (optional)
                            if (indices.length > 2 && !indices[2].isEmpty()) {
                                normalIndices[i-1] = Integer.parseInt(indices[2]) - 1;
                            } else {
                                normalIndices[i-1] = -1;
                            }
                        }
                        
                        // Convert faces to triangles
                        for (int i = 1; i < vertexIndices.length - 1; i++) {
                            int[] triVertexIndices = {vertexIndices[0], vertexIndices[i], vertexIndices[i+1]};
                            int[] triNormalIndices = {normalIndices[0], normalIndices[i], normalIndices[i+1]};
                            int[] triTexCoordIndices = {texCoordIndices[0], texCoordIndices[i], texCoordIndices[i+1]};
                            
                            model.faces.add(new ObjFace(triVertexIndices, triNormalIndices, triTexCoordIndices));
                        }
                    }
                    break;
                    
                case "usemtl": // Material
                    if (parts.length >= 2) {
                        model.materialName = parts[1];
                    }
                    break;
            }
        }
        
        reader.close();
        return model;
    }
    
    public static void clearCache() {
        loadedModels.clear();
        System.out.println("BML: OBJ model cache cleared");
    }
    
    public static int getLoadedModelCount() {
        return loadedModels.size();
    }
    
    public static String[] getLoadedModelPaths() {
        return loadedModels.keySet().toArray(new String[0]);
    }
}
