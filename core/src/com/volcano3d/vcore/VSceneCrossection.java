package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vshaders.VDefaultShaderProvider;

public class VSceneCrossection {

	protected VMain volcano;
	
	public VDefaultShaderProvider shaderUnderground = null; 
	public VDefaultShaderProvider shaderLava = null; 
	
	public VRenderable 	lava;
	public VRenderable 	undergroundComp;
	
	private float lavaMove = 0;
	
    public VSceneCrossection(VMain o){
    	volcano = o;
    }	
	
    public void create(){
    	
    	shaderUnderground = new VDefaultShaderProvider(volcano, "shaders/under.vertex.glsl", "shaders/under.fragment.glsl");
    	shaderLava = new VDefaultShaderProvider(volcano, "shaders/lava.vertex.glsl", "shaders/lava.fragment.glsl");
    	
    	undergroundComp = new VRenderable(volcano, "undergroundComp.g3dj", shaderUnderground);
    	lava = new VRenderable(volcano, "lava.g3dj", shaderLava);
    	
    }
    public void onLoad(){
    	
    	shaderUnderground.onLoad();
    	shaderLava.onLoad();
    	
    	undergroundComp.onLoad();
    	lava.onLoad();    	
    	
    }
    public void render(VCamera camera, Environment environment){
    	
    	lavaMove += 0.1f * Gdx.graphics.getDeltaTime();
    	lavaMove = lavaMove % 1;
        
        lava.setShininess("underg19", lavaMove);
        
    	undergroundComp.render(camera.get(), environment);  
    	lava.render(camera.get(), environment); 
    	
    }
    
}
