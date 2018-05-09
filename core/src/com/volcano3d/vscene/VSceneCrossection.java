package com.volcano3d.vscene;

import com.badlogic.gdx.Gdx;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcore.VConfig;
import com.volcano3d.vcore.VMain;
import com.volcano3d.vcore.VRenderable;
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
    	
    	undergroundComp = new VRenderable(volcano, "models/undergroundComp", shaderUnderground);
    	lava = new VRenderable(volcano, "models/lava", shaderLava);
    	
    }
    public void onLoad(){
    	
    	shaderUnderground.onLoad();
    	shaderLava.onLoad();
    	
    	undergroundComp.onLoad();
    	lava.onLoad();    	
    	
    }
    public void render(VCamera camera){
    	
    	lavaMove += 0.1f * Gdx.graphics.getDeltaTime();
    	lavaMove = lavaMove % 1;
        
        lava.setShininess("underg19", lavaMove);
        
    	undergroundComp.render(camera.get());  
    	lava.render(camera.get()); 
    	
    }
    
}
