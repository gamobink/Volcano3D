package com.volcano3d.vscene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcamera.VCameraPresetCollection;
import com.volcano3d.vcore.VMain;
import com.volcano3d.vcore.VTextureRender;

public class VScene {
	
	protected VMain volcano;

    public Array<VTextureRender> waterTexturesArray = new Array<VTextureRender>();

    public VSceneCrossection		crossectionScene = null;
    public VSceneGround				groundScene = null;
    public VSceneParticleEffects 	particleEffectsScene = null;
    
    public VScene(VMain o){
    	volcano = o;
    	crossectionScene = new VSceneCrossection(o); 
    	groundScene = new VSceneGround(o); 
    	particleEffectsScene = new VSceneParticleEffects(o);
    }
    public void create(){
    	
        waterTexturesArray.add(new VTextureRender(volcano));		//Reflection
        waterTexturesArray.add(new VTextureRender(volcano));		//Reflected skybox stretched
        waterTexturesArray.add(new VTextureRender(volcano));		//Refraction       

        particleEffectsScene.create();
        groundScene.create();
        crossectionScene.create();
    }     
    public void onLoad(){
    	
    	particleEffectsScene.onLoad();
        groundScene.onLoad();
        crossectionScene.onLoad();
        
        groundScene.waterModel.setReflectionTexture("water1", waterTexturesArray.get(0).get());
        groundScene.waterModel.setAmbientTexture("water1", waterTexturesArray.get(1).get());
        groundScene.waterModel.setSpecularTexture("water1", waterTexturesArray.get(2).get());
        groundScene.waterModel.setReflectionTexture("water2", waterTexturesArray.get(0).get());
        groundScene.waterModel.setAmbientTexture("water2", waterTexturesArray.get(1).get());
        groundScene.waterModel.setSpecularTexture("water2", waterTexturesArray.get(2).get());
        groundScene.waterModel.setReflectionTexture("waterCenter", waterTexturesArray.get(0).get());
        groundScene.waterModel.setAmbientTexture("waterCenter", waterTexturesArray.get(1).get());
        groundScene.waterModel.setSpecularTexture("waterCenter", waterTexturesArray.get(2).get());	        
        groundScene.waterWallModel.setSpecularTexture(null, waterTexturesArray.get(2).get());
           
    }    
    public void render(VCamera camera){
        
    	groundScene.renderSky(camera);

    	if(camera.getCurrentPreset() != VCameraPresetCollection.PresetsIdentifiers.MAIN
    		|| camera.getTargetPreset() == VCameraPresetCollection.PresetsIdentifiers.MAIN){
    		
        	crossectionScene.render(camera);      
        	groundScene.waterWallModel.render(camera.get());        	
        }
    	
    	groundScene.render(camera);
    	particleEffectsScene.render(camera);
    	
    }

    public void renderToWaterTextures(VCamera camera){
    	
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
	        	groundScene.skyboxModel.scale(1, 1, 1);
	        	groundScene.skyboxModel.render(c);
	        	groundScene.groundModel.render(c);
	        	groundScene.islandModel.render(c);
	        		        	
	        	particleEffectsScene.particleFireSmoke.render(c);  
	        	particleEffectsScene.particleSmokeCloudIsland.render(c);
	            	            
	        }else if(i==1){	//reflection stretched skybox
	        	groundScene.skyboxModel.scale(1, 50, 1);
	        	groundScene.skyboxModel.render(c);	        	
	        }else if(i == 2){	//under water part - refraction	  
		        if(c.position.y < 0){    
		        	groundScene.skyboxModel.scale(1, 1, 1);
		        	groundScene.skyboxModel.render(c);
		        	groundScene.groundModel.render(c);
		        	groundScene.underwaterCenterModel.render(c);
		        }else{
		        	groundScene.underwaterModel.render(c);
		        }
	        }
	        waterTexturesArray.get(i).endRender();
    	}
    	groundScene.skyboxModel.scale(1, 1.2f, 1);
    }

}