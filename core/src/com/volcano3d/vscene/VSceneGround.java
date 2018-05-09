package com.volcano3d.vscene;

import com.badlogic.gdx.Gdx;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcore.VMain;
import com.volcano3d.vcore.VRenderable;
import com.volcano3d.vshaders.VDefaultShaderProvider;
import com.volcano3d.vshaders.VMinimalistShaderProvider;

public class VSceneGround {
	
	protected VMain volcano;
	
    public VDefaultShaderProvider shaderSky = null;    
    public VMinimalistShaderProvider shaderSimple = null;
    public VDefaultShaderProvider shaderWater = null;
    public VDefaultShaderProvider shaderUnderwater = null; 
    public VDefaultShaderProvider shaderRiverFoam = null; 
    public VDefaultShaderProvider shaderWaterWall = null; 
    public VDefaultShaderProvider shaderGround = null;  
    public VDefaultShaderProvider shaderWaterFoam = null;  	
    public VDefaultShaderProvider shaderLava = null; 
    
    public VRenderable 	skyboxModel = null;
    public VRenderable 	underwaterModel = null;
    public VRenderable 	underwaterCenterModel = null;
    public VRenderable 	islandModel = null;
    public VRenderable 	waterWallModel = null;
    public VRenderable 	groundModel = null;
    public VRenderable 	waterModel = null;   
    public VRenderable 	riverFoamModel = null;
    public VRenderable 	waterFoamModel = null;  
    public VRenderable 	lavaOuterModel = null;  
    
    private float waterMove = 0;
    private float foamMove = 0;    
    private float lavaMove = 0;
    
    public VSceneGround(VMain o){
    	volcano = o;
    }
    
    public void create(){
    	
        shaderSky = new VDefaultShaderProvider(volcano, "shaders/sky.vertex.glsl", "shaders/sky.fragment.glsl");        
        shaderSky.setDepthFunc(0);        
        shaderSimple = new  VMinimalistShaderProvider(volcano, "shaders/min.vertex.glsl", "shaders/min.fragment.glsl");
        shaderWater = new VDefaultShaderProvider(volcano, "shaders/water.vertex.glsl", "shaders/water.fragment.glsl");
        shaderUnderwater = new VDefaultShaderProvider(volcano, "shaders/min.vertex.glsl", "shaders/underwater.fragment.glsl");
        shaderWaterWall = new VDefaultShaderProvider(volcano, "shaders/waterwall.vertex.glsl", "shaders/waterwall.fragment.glsl");        
        shaderGround = new VDefaultShaderProvider(volcano, "shaders/ground.vertex.glsl", "shaders/ground.fragment.glsl"); 
        shaderWaterFoam = new VDefaultShaderProvider(volcano, "shaders/foam.vertex.glsl", "shaders/foam.fragment.glsl"); 
        shaderRiverFoam = new VDefaultShaderProvider(volcano, "shaders/foam.vertex.glsl", "shaders/riverfoam.fragment.glsl");     	
    	shaderLava = new VDefaultShaderProvider(volcano, "shaders/lava.vertex.glsl", "shaders/lava.fragment.glsl");
    	
        skyboxModel = new VRenderable(volcano, "models/skybox", shaderSky);
        underwaterModel = new VRenderable(volcano, "models/underwater", shaderUnderwater);
        underwaterCenterModel = new VRenderable(volcano, "models/underwaterCenter", shaderUnderwater);
        islandModel = new VRenderable(volcano, "models/island", shaderSimple);
        waterWallModel = new VRenderable(volcano, "models/waterWall", shaderWaterWall);
        
        groundModel = new VRenderable(volcano, "models/ground", shaderGround);
        groundModel.enableTween();
        groundModel.alphaFader.set("groundPart1", 1.0f, 1.0f);
        groundModel.alphaFader.set("groundPart2", 1.0f, 1.0f);
        groundModel.alphaFader.set("groundPart3", 1.0f, 1.0f);
        groundModel.alphaFader.set("groundFar1", 1.0f, 1.0f);
        groundModel.alphaFader.set("groundFar2", 1.0f, 1.0f);

        waterModel = new VRenderable(volcano, "models/water", shaderWater);
        waterModel.enableTween();
        waterModel.alphaFader.set("waterCenter", 1.0f, 1.0f);
        waterModel.alphaFader.set("water", 1.0f, 1.0f);        
        
        riverFoamModel = new VRenderable(volcano, "models/riverFoam", shaderRiverFoam);
        riverFoamModel.enableTween();
        riverFoamModel.alphaFader.set("riverFoam", 1.0f, 1.0f);        
        
        waterFoamModel = new VRenderable(volcano, "models/foam1", shaderWaterFoam);
        waterFoamModel.enableTween();
        waterFoamModel.alphaFader.set("foam", 1.0f, 1.0f);
        waterFoamModel.alphaFader.set("foamShort", 1.0f, 1.0f);        
        
        lavaOuterModel = new VRenderable(volcano, "models/outerLava", shaderLava);
        lavaOuterModel.enableTween();
        lavaOuterModel.alphaFader.set("lavaFar", 1.0f, 1.0f);
    }
    public void onLoad(){
    	
    	shaderSky.onLoad();
    	shaderSimple.onLoad();
    	shaderWater.onLoad();
    	shaderUnderwater.onLoad();
    	shaderWaterWall.onLoad();
    	shaderGround.onLoad();
    	shaderWaterFoam.onLoad();
    	shaderRiverFoam.onLoad();
    	shaderLava.onLoad();
    	
    	skyboxModel.onLoad();
    	underwaterModel.onLoad();
    	underwaterCenterModel.onLoad();
    	islandModel.onLoad();
    	waterWallModel.onLoad();
    	groundModel.onLoad();
    	waterModel.onLoad();
    	riverFoamModel.onLoad();
    	waterFoamModel.onLoad();    	
    	lavaOuterModel.onLoad();
    	
    	
    }
    
    public void renderSky(VCamera camera){
    	skyboxModel.render(camera.get());
    }
    
    public void render(VCamera camera){
    	
    	waterMove += 0.1f * Gdx.graphics.getDeltaTime();
        waterMove = waterMove % 1;
        
        foamMove += 0.2f * Gdx.graphics.getDeltaTime();
        foamMove = foamMove % 1;
        
    	lavaMove -= 0.5f * Gdx.graphics.getDeltaTime();
    	if(lavaMove < 0)lavaMove = 1.0f;
        
    	lavaOuterModel.setShininess("lavaMain", lavaMove);        
        waterModel.setShininess("water1", waterMove);
        waterModel.setShininess("water2", waterMove);
        waterModel.setShininess("waterCenter", waterMove);        
        waterFoamModel.setShininess("foam", foamMove);    	
        
    	waterModel.render(camera.get());        
    	groundModel.render(camera.get());
    	islandModel.render(camera.get());      	  
    	lavaOuterModel.render(camera.get());
    	waterFoamModel.render(camera.get());
        riverFoamModel.render(camera.get());    	
    }
	
}
