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
import com.volcano3d.vcamera.VCameraPresetCollection;
import com.volcano3d.vparticles.VParticleEffect;
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
    public VDefaultShaderProvider shaderGround = null;  
    public VDefaultShaderProvider shaderWaterFoam = null;  
    
    protected Map<String, VRenderable> renderables = new HashMap<String, VRenderable>();
    
    public Array<VTextureRender> waterTexturesArray = new Array<VTextureRender>();
    
    public VParticleEffect	particleFireSmoke = null;
    //TODO Particle effects
    public VParticleEffect	particleSmokeCloud = null;
    public VParticleEffect	particleSmokeCloudIsland = null;
    public VParticleEffect	particleSecondaryFire = null;
    public VParticleEffect	particleSmoke1 = null;  
    public VParticleEffect	particleSmoke2 = null;  
    public VParticleEffect	particleSmoke3 = null;  
    
    private float waterMove = 0;
    private float foamMove = 0;
    
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
        shaderGround = new VDefaultShaderProvider(volcano, "shaders/ground.vertex.glsl", "shaders/ground.fragment.glsl"); 
        shaderWaterFoam = new VDefaultShaderProvider(volcano, "shaders/foam.vertex.glsl", "shaders/foam.fragment.glsl"); 
        
        add("skybox", shaderSky);
        add("underwater", shaderUnderwater);
        add("underwaterCenter", shaderUnderwater);
        
        add("island", shaderSimple);
        //add("underground");
        add("waterWall", shaderWaterWall);
        add("undergroundComp", shaderUnderground);
        
        VRenderable r = add("ground", shaderGround);
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
                
        r = add("foam1", shaderWaterFoam);
        r.enableTween();
        r.alphaFader.set("Plane04", 1.0f, 1.0f);
        //System.out.println(r);
        
        
        waterTexturesArray.add(new VTextureRender(volcano));		//Reflection
        waterTexturesArray.add(new VTextureRender(volcano));		//Reflected skybox stretched
        waterTexturesArray.add(new VTextureRender(volcano));		//Refraction       
        
        particleFireSmoke = new VParticleEffect(volcano, "volcanoFire2.pfx");
        particleFireSmoke.setPosition(-220, 115, -10);
        
        particleSmokeCloud = new VParticleEffect(volcano, "smokeCloud.pfx");
        particleSmokeCloud.setPosition(-220, 115, -10);        
        
        particleSmokeCloudIsland = new VParticleEffect(volcano, "smokeIsland.pfx");        
        particleSmokeCloudIsland.setPosition(1400, 55, 20);
        
        particleSecondaryFire = new VParticleEffect(volcano, "fireSecondary.pfx");
        particleSecondaryFire.setPosition(-270, 65, -25);
                
        particleSmoke1 = new VParticleEffect(volcano, "smoke1.pfx");
        particleSmoke1.setPosition(-180, 40, -40);
        
        particleSmoke2 = new VParticleEffect(volcano, "smoke2.pfx");
        particleSmoke2.setPosition(-270, 40, -50);  
        
        particleSmoke3 = new VParticleEffect(volcano, "smoke3.pfx");
        particleSmoke3.setPosition(-200, 20, -90);          
    }     
    public void onLoad(){
    	
    	shaderSky.onLoad();
    	shaderSimple.onLoad();
    	shaderWater.onLoad();
    	shaderUnderwater.onLoad();
    	shaderUnderground.onLoad();
    	shaderWaterWall.onLoad();
    	shaderGround.onLoad();
    	shaderWaterFoam.onLoad();
    	
		for(Map.Entry<String, VRenderable> m:renderables.entrySet()){  
			m.getValue().onLoad();			   
		}
		
        VRenderable r = get("water");
        
        r.setReflectionTexture("water1", waterTexturesArray.get(0).get());
        r.setAmbientTexture("water1", waterTexturesArray.get(1).get());
        r.setSpecularTexture("water1", waterTexturesArray.get(2).get());
        
        r.setReflectionTexture("water2", waterTexturesArray.get(0).get());
        r.setAmbientTexture("water2", waterTexturesArray.get(1).get());
        r.setSpecularTexture("water2", waterTexturesArray.get(2).get());
        
        r.setReflectionTexture("waterCenter", waterTexturesArray.get(0).get());
        r.setAmbientTexture("waterCenter", waterTexturesArray.get(1).get());
        r.setSpecularTexture("waterCenter", waterTexturesArray.get(2).get());	
        
        r = get("waterWall");
        r.setSpecularTexture(null, waterTexturesArray.get(2).get());
        
        particleFireSmoke.onLoad();
        particleSmokeCloudIsland.onLoad();
        particleSmokeCloud.onLoad();
        particleSecondaryFire.onLoad();
        particleSmoke1.onLoad();
        particleSmoke2.onLoad();
        particleSmoke3.onLoad();
    }    
    public void render(VCamera camera, Environment environment){
        
    	waterMove += 0.1f * Gdx.graphics.getDeltaTime();
        waterMove = waterMove % 1;
        
        foamMove += 0.2f * Gdx.graphics.getDeltaTime();
        foamMove = foamMove % 1;
        
        VRenderable r = get("water");
        r.setShininess("water1", waterMove);
        r.setShininess("water2", waterMove);
        r.setShininess("waterCenter", waterMove);
        
        r = get("foam1");
        r.setShininess("Plane04", foamMove);

        get("skybox").render(camera.get(), environment);
        
        if(volcano.camera.getPreset() != VCameraPresetCollection.PresetsIdentifiers.MAIN
        	|| volcano.camera.getTargetPreset() != VCameraPresetCollection.PresetsIdentifiers.MAIN)
        	renderCrossectionParts(camera, environment);   
        
        get("waterWall").render(camera.get(), environment);

        get("water").render(camera.get(), environment);        
        get("ground").render(camera.get(), environment);
        get("island").render(camera.get(), environment);     
        get("foam1").render(camera.get(), environment);
        
        /**/
        particleSmokeCloudIsland.render(camera.get());        
        particleSmoke1.render(camera.get()); 
        particleSmoke2.render(camera.get()); 
        particleSmoke3.render(camera.get()); 
        particleSmokeCloud.render(camera.get());        
        particleFireSmoke.render(camera.get());  
        particleSecondaryFire.render(camera.get());
        /**/
        
        //TODO Render underground parts based on camera states
      //  if(camera.getCurrentPreset() != VCameraPresetCollection.PresetsIdentifiers.MAIN){
        //	modelUnderground.render(camera.get(), environment);
      //  }
    	
    }
    public void renderCrossectionParts(VCamera camera, Environment environment){   	
    	get("undergroundComp").render(camera.get(), environment);   
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
	    	
	    	Gdx.gl.glClearColor(0,0,0, 0.0f);
	    	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
	        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);   
	        
	        if(i==0){	//reflection
	        	get("skybox").scale(1, 1, 1);
	            get("skybox").render(c, environment);
	            get("ground").render(c, environment);
	            get("island").render(c, environment);
	            
	            particleFireSmoke.render(c);  
	            particleSmokeCloudIsland.render(c);
	            	            
	        }else if(i==1){	//reflection stretched skybox
	        	get("skybox").scale(1, 50, 1);
	            get("skybox").render(c, environment);
	            
	        }else if(i == 2){	//under water part - refraction	  
		        if(c.position.y < 0){    
		        	get("skybox").scale(1, 1, 1);
		            get("skybox").render(c, environment);
		            get("ground").render(c, environment);		        	
		        	get("underwaterCenter").render(c, environment);	        	
		        }else{
		        	get("underwater").render(c, environment);
		        }
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
