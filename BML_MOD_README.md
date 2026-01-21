# BML - Bob Model Loader

A separate mod for loading and rendering OBJ models in Minecraft, 13.4 dev

## Overview

Bob Model Loader is a modular addon that provides comprehensive OBJ model loading and rendering capabilities for Minecraft. 

## Features

- **Complete OBJ Model Support**: Loads Wavefront OBJ files with vertices, normals, texture coordinates, and faces
- **Automatic Triangulation**: Converts complex polygons to triangles automatically
- **Model Caching**: Efficiently caches loaded models for better performance
- **Entity Integration**: Spawn OBJ models as entities in the world
- **Modular Design**: Clean separation from main game code
- **Debug Support**: Built-in debugging and logging capabilities

## File Structure

```
src/net/minecraft/src/BML/
├── mod_BML.java              # Main BML mod class
├── BMLController.java        # BML controller for logic
├── BMLView.java             # BML view for UI/rendering
├── ObjModelLoader.java      # Core OBJ file parser
├── ModelObj.java            # OBJ model class extending ModelBase
├── RenderObj.java           # Basic OBJ renderer
├── RenderObjModel.java      # Entity-specific OBJ renderer
├── EntityObjModel.java      # Test entity using OBJ models
└── ItemObjSpawner.java      # Item to spawn OBJ entities

resources/
├── models/
│   └── (your model here)         
└── textures/
    └── (your textures here)
```

1. Place your `.obj` files in the `resources/models/` directory
2. Place corresponding texture files in the `resources/textures/` directory
3. Modify the `EntityObjModel` class to use your model path:
   ```java
   this.modelPath = "/models/yourmodel.obj";
   this.texturePath = "/textures/yourtexture.png";
   ```

### 3. Create Custom OBJ Entities

You can create custom entities that use OBJ models by:

1. Extending `EntityObjModel` or creating your own entity class
2. Using `ModelObj` for the model
3. Using `RenderObj` or `RenderObjModel` for rendering
4. Registering the entity in `EntityList.java`
5. Registering the renderer in `RenderManager.java`

## API Usage

### Loading Models
```java
// Load an OBJ model
ModelObj model = new ModelObj("/models/mymodel.obj", 1.0f);

// Check if model loaded successfully
if (model.isLoaded()) {
    // Render the model
    model.render(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
}
```

### Creating Renderers
```java
// Create a renderer for an OBJ model
RenderObj renderer = new RenderObj("/models/mymodel.obj", "/textures/mytexture.png");

// Render at a specific position
renderer.doRenderAtPosition(x, y, z, yaw, pitch, scale);
```

### Entity Management
```java
// Create an OBJ model entity
EntityObjModel objEntity = new EntityObjModel(world, x, y, z, 
    "/models/mymodel.obj", 
    "/textures/mytexture.png"
);

// Add to world
world.entityJoinedWorld(objEntity);
```

- Models are automatically cached after first load
- Use appropriate scaling to avoid performance issues
- Consider LOD (Level of Detail) for complex models
- The system integrates with Minecraft's existing rendering pipeline