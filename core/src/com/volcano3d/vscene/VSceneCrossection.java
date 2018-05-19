package com.volcano3d.vscene;

import com.badlogic.gdx.Gdx;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcore.VMain;
import com.volcano3d.vcore.VRenderable;
import com.volcano3d.vshaders.VDefaultShaderProvider;

public class VSceneCrossection {

	protected VMain volcano;
	
	public VDefaultShaderProvider shaderUnderground = null; 
	public VDefaultShaderProvider shaderLava = null; 
	public VDefaultShaderProvider shaderSchema = null; 
	
	public VRenderable 	lava;
	public VRenderable 	undergroundComp;
	public VRenderable 	schemaL1Model;
	public VRenderable 	schemaL2Model;
	
	private float lavaMove = 0;
	
    public VSceneCrossection(VMain o){
    	volcano = o;
    }	
	
    public void create(){
    	
    	shaderUnderground = new VDefaultShaderProvider(volcano, "shaders/under.vertex.glsl", "shaders/under.fragment.glsl");
    	shaderLava = new VDefaultShaderProvider(volcano, "shaders/lava.vertex.glsl", "shaders/lava.fragment.glsl");
    	shaderSchema = new VDefaultShaderProvider(volcano, "shaders/schema.vertex.glsl", "shaders/schema.fragment.glsl");
    	
    	undergroundComp = new VRenderable(volcano, "models/undergroundComp", shaderUnderground);
    	lava = new VRenderable(volcano, "models/lava", shaderLava);    	
    	schemaL1Model = new VRenderable(volcano, "models/schemaLayer1", shaderSchema);
    	schemaL2Model = new VRenderable(volcano, "models/schemaLayer2", shaderSchema);    	  	
    }
    public void onLoad(){
    	
    	shaderUnderground.onLoad();
    	shaderLava.onLoad();
    	shaderSchema.onLoad();
    	
    	undergroundComp.onLoad();
    	lava.onLoad();   
    	schemaL1Model.onLoad();
    	schemaL2Model.onLoad();  
    	
    	schemaL1Model.enableBlending();
    	schemaL2Model.enableBlending(); 
    	
    	schemaL1Model.enableTween();
    	schemaL1Model.alphaFader.set("sediment", 1.0f, 1.0f);    	
    }
    public void render(VCamera camera){
    	
    	lavaMove += 0.1f * Gdx.graphics.getDeltaTime();
    	lavaMove = lavaMove % 1;
        
        lava.setShininess("underg19", lavaMove);
        
    	undergroundComp.render(camera.get());  
    	lava.render(camera.get()); 

    }
    
    public void renderSchematicOverlay(VCamera camera){
    	schemaL1Model.render(camera.get());
    //	schemaL2Model.render(camera.get());
    }
    
}
