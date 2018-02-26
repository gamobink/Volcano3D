package com.volcano3d.vcore;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vshaders.VDefaultShaderProvider;
import com.volcano3d.vshaders.VMinimalistShaderProvider;

public class VScene {
	
	protected VMain volcano;
	
    public VDefaultShaderProvider shaderSky = null;    
    public VMinimalistShaderProvider shaderSimple = null;
    public VDefaultShaderProvider shaderWater = null; 
    public VDefaultShaderProvider shaderUnderwater = null; 
    public VDefaultShaderProvider shaderUnderground = null; 
    public VDefaultShaderProvider shaderWaterWall = null; 
	
    protected Map<String, VRenderable> renderables = new HashMap<String, VRenderable>();
    
    public Array<VTextureRender> waterTexturesArray = new Array<VTextureRender>();
    
    private float waterMove = 0;
    
    public VScene(VMain o){
    	volcano = o;
    }
    public void create(){
    	
        shaderSky = new VDefaultShaderProvider(volcano, "shaders/sky.vertex.glsl", "shaders/sky.fragment.glsl");        
        shaderSky.setDepthFunc(0);
        
        shaderSimple = new  VMinimalistShaderProvider(volcano, "shaders/min.vertex.glsl", "shaders/min.fragment.glsl");
        shaderWater = new VDefaultShaderProvider(volcano, "shaders/water.vertex.glsl", "shaders/water.fragment.glsl");
        shaderUnderwater = new VDefaultShaderProvider(volcano, "shaders/min.vertex.glsl", "shaders/underwater.fragment.glsl");
        shaderUnderground = new VDefaultShaderProvider(volcano, "shaders/under.vertex.glsl", "shaders/under.fragment.glsl");
        shaderWaterWall = new VDefaultShaderProvider(volcano, "shaders/waterwall.vertex.glsl", "shaders/waterwall.fragment.glsl");
        
        add("skybox", shaderSky);
        add("underwater", shaderUnderwater);
        add("island", shaderSimple);
        add("underground");
        add("waterWall", shaderWaterWall);
        add("undergroundComp", shaderUnderground);
        
        VRenderable r = add("ground");
        r.enableTween();
        r.alphaFader.set("groundPart1", 1.0f, 1.0f);
        r.alphaFader.set("groundPart2", 1.0f, 1.0f);
        r.alphaFader.set("groundPart3", 1.0f, 1.0f);
        r.alphaFader.set("groundFar1", 1.0f, 1.0f);
        r.alphaFader.set("groundFar2", 1.0f, 1.0f);

        r = add("water", shaderWater);
        r.enableTween();
        r.alphaFader.set("waterCenter", 1.0f, 1.0f);
        r.alphaFader.set("water", 1.0f, 1.0f);
                
        waterTexturesArray.add(new VTextureRender(volcano));		//Reflection
        waterTexturesArray.add(new VTextureRender(volcano));		//Reflected skybox stretched
        waterTexturesArray.add(new VTextureRender(volcano));		//Refraction        
    }     
    public void init(){
    	
    	shaderSky.init();
    	shaderSimple.init();
    	shaderWater.init();
    	shaderUnderwater.init();
    	shaderUnderground.init();
    	shaderWaterWall.init();
    	
		for(Map.Entry<String, VRenderable> m:renderables.entrySet()){  
			m.getValue().init();			   
		}
		
        VRenderable r = get("water");
        
        r.setReflectionTexture("water", waterTexturesArray.get(0).get());
        r.setAmbientTexture("water", waterTexturesArray.get(1).get());
        r.setSpecularTexture("water", waterTexturesArray.get(2).get());
        
        r.setReflectionTexture("waterCenter", waterTexturesArray.get(0).get());
        r.setAmbientTexture("waterCenter", waterTexturesArray.get(1).get());
        r.setSpecularTexture("waterCenter", waterTexturesArray.get(2).get());	
        
        r = get("waterWall");
        r.setSpecularTexture(null, waterTexturesArray.get(2).get());
    }    
    public void render(VCamera camera, Environment environment){
        
    	waterMove += 0.1f * Gdx.graphics.getDeltaTime();
        waterMove = waterMove % 1;
        
        VRenderable r = get("water");
        r.setShininess("water", waterMove);
        r.setShininess("waterCenter", waterMove);  

        get("skybox").render(camera.get(), environment);
        get("water").render(camera.get(), environment);        
        get("ground").render(camera.get(), environment);
        get("island").render(camera.get(), environment);
        
        get("waterWall").render(camera.get(), environment);
        
        get("undergroundComp").render(camera.get(), environment);
        
        
        
        get("water").alphaFader.set("water", 0, 1f);    	
    	get("ground").alphaFader.set("groundPart1", 0, 1f);
    	get("ground").alphaFader.set("groundPart2", 0, 1f);
    	get("ground").alphaFader.set("groundPart3", 0, 1f);    	
    	get("ground").alphaFader.set("groundFar1", 0, 1f);
    	get("ground").alphaFader.set("groundFar2", 0, 1f); 
    	
        //TODO Render underground parts based on camera states
      //  if(camera.getCurrentPreset() != VCameraPresetCollection.PresetsIdentifiers.MAIN){
        //	modelUnderground.render(camera.get(), environment);
      //  }
    	
    }
    public void renderCrossectionParts(VCamera camera, Environment environment){
    	
    	//sectionWater
    	//section
    	
    }
    public void renderToWaterTextures(VCamera camera, Environment environment){
    	
    	PerspectiveCamera co = camera.get();
    	
    	PerspectiveCamera c = new PerspectiveCamera(35, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    	c.position.set(co.position);
    	c.direction.set(co.direction);
    	c.up.set(co.up);
    	c.fieldOfView = co.fieldOfView;
    	c.near = 10f;
    	c.far = 3000;
    	
    	for(int i=0; i<3; i++){
    		if(i==0 || i==1){
		    	c.position.y = -co.position.y;
		    	c.direction.y = -co.direction.y;
    		}else{
		    	c.position.y = co.position.y;
		    	c.direction.y = co.direction.y;
    		}
    		if(i==1){
    			c.far = 10000;
    		}else{
    			c.far = 3000;
    		}
	    	c.update();
	    	
	    	waterTexturesArray.get(i).beginRender();
	    	
	    	Gdx.gl.glClearColor(0,0,0,0.0f);
	    	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
	        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);   
	        
	        if(i==0){	//reflection
	        	get("skybox").scale(1, 1, 1);
	            get("skybox").render(c, environment);
	            get("ground").render(c, environment);
	            get("island").render(c, environment);
	            
	        }else if(i==1){	//reflection stretched skybox
	        	get("skybox").scale(1, 50, 1);
	            get("skybox").render(c, environment);
	            
	        }else if(i == 2){	//under water part - refraction	   
	        	//Render only for under-water cross section part, when camera is under horizon
	        	//if(c.position.y < 50){
		        	get("skybox").scale(1, 1, 1);
		            get("skybox").render(c, environment);
		            get("ground").render(c, environment);
	        	//}
	        	get("underwater").render(c, environment);
	        }
	        waterTexturesArray.get(i).endRender();
    	}
    	get("skybox").scale(1, 1.2f, 1);
    }
    public VRenderable add(String filename){
    	return add(filename, null);
    }
    public VRenderable add(String name, ShaderProvider shader){
    	VRenderable r = new VRenderable(volcano, name+".g3dj", shader);
    	renderables.put(name, r);
    	return r;
    }
    static VRenderable rStat = new VRenderable(null);
    public VRenderable get(String name){
    	VRenderable r = renderables.get(name);
    	if(r != null)return r;
    	return rStat;
    }
}
