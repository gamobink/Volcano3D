package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.volcano3d.utility.TextAsset;
import com.volcano3d.utility.TextAssetLoader;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcamera.VCameraPresetCollection;
import com.volcano3d.vdecal.VDecal;
import com.volcano3d.vdecal.VDecalGroup;
import com.volcano3d.vshaders.VDefaultShaderProvider;
import com.volcano3d.vshaders.VMinimalistShaderProvider;

/**
 * Created by T510 on 8/2/2017.
 */

public class VMain{

    public AssetManager assetsManager = new AssetManager();
    public Environment environment = new Environment();
    public VCamera camera = new VCamera(this);   
    public VInputProcessor inputProcessor = new VInputProcessor(this);
    public VStageMain stage2D = new VStageMain(this);
    public VDecalGroup decalsTags = new VDecalGroup(this);
    public boolean  objectsLoaded = false;    

    public VRenderable modelSkybox = null; 
    public VRenderable modelWater = null; 
    public VRenderable modelGround1 = null; 
    public VRenderable modelGround2 = null; 
    public VRenderable modelGroundCenter = null;     
    public VRenderable modelGroundFar = null;    
    public VRenderable modelGroundAround1 = null; 
    public VRenderable modelGroundAround2 = null;     
    public VRenderable modelGroundAround3 = null; 
    public VRenderable modelUnderground1 = null; 
    
    public VDefaultShaderProvider shaderSky = null;    
    public VMinimalistShaderProvider shaderSimple = null;
    public VDefaultShaderProvider shaderWater = null; 
    
    public VCubemap environmentCubemap = null;
    
    public VMain(){
    	
        assetsManager.setLoader(TextAsset.class,new TextAssetLoader(new InternalFileHandleResolver()));
        assetsManager.setErrorListener(new AssetErrorListener() {
            @Override
            public void error(@SuppressWarnings("rawtypes") AssetDescriptor assetDescriptor, Throwable throwable) {
                System.out.println("ASSET: "+assetDescriptor.toString()+" - "+throwable.getMessage());
            }
        });
        
        Gdx.input.setInputProcessor(new InputMultiplexer(stage2D.mainStage, inputProcessor.gestureDetector, inputProcessor));

        create();
    }
    public void create(){
    	
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(0.9f, 0.9f, 0.5f,  -1, -0.8f, 1));  
        environment.add(new DirectionalLight().set(0.4f, 0.4f, 0.6f,  0f, -0.8f, 1));  
        
        shaderSky = new VDefaultShaderProvider(this, "shaders/sky.vertex.glsl", "shaders/sky.fragment.glsl");        
        shaderSimple = new  VMinimalistShaderProvider(this, "shaders/min.vertex.glsl", "shaders/min.fragment.glsl");
        shaderWater = new VDefaultShaderProvider(this, "shaders/water.vertex.glsl", "shaders/water.fragment.glsl");
        
        modelSkybox = new VRenderable(this, "sky.g3dj", shaderSky);
        modelWater = new VRenderable(this, "water.g3dj", shaderWater);     
        
        modelGround1 = new VRenderable(this, "ground1.g3dj");
        modelGround2 = new VRenderable(this, "ground2.g3dj");
        modelGroundCenter = new VRenderable(this, "groundCenter.g3dj");
        modelGroundFar = new VRenderable(this, "groundFar.g3dj");
        modelGroundAround1 = new VRenderable(this, "groundAround1.g3dj");
        modelGroundAround2 = new VRenderable(this, "groundAround2.g3dj");
        modelGroundAround3 = new VRenderable(this, "groundAround3.g3dj");
        
        modelUnderground1 = new VRenderable(this, "underground.g3dj", shaderSky);
        
        decalsTags.addDecal(new VDecal("sign.png", new Vector3(-220, 150, -10), new Vector2(50,50)));
        decalsTags.addDecal(new VDecal("sign2.png", new Vector3(-32, 92, 8), new Vector2(50,50)));
        decalsTags.addDecal(new VDecal("sign3.png", new Vector3(146, 42, -216), new Vector2(50,50)));
        decalsTags.addDecal(new VDecal("sign4.png", new Vector3(-7, 45, -550), new Vector2(50,50)));
        
        environmentCubemap = new VCubemap(this);
    }    
    //Call on loading complete
    void init(){
    	shaderSky.init();
    	shaderSimple.init();
    	shaderWater.init();
    	
    	modelSkybox.init();
    	modelWater.init();
    	modelGround1.init();    	
    	modelGround2.init();    	
    	modelGroundCenter.init();
    	modelGroundFar.init();
    	modelGroundAround1.init();
    	modelGroundAround2.init();
    	modelGroundAround3.init();
    	modelUnderground1.init();
    	decalsTags.init(); 	
    }	
    
    public void render() {
        
    	if (!assetsManager.update()) {
            stage2D.renderLoader();
            return;
        }
        
        if(!objectsLoaded){
        	init();
        	objectsLoaded = true;
        }

        camera.update();
        
        environmentCubemap.renderCubemap();
        modelWater.setEnvironmentCubemap(environmentCubemap);
        
    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);
        
        //Frustum culling
        modelSkybox.render(camera.get(), environment);
        
        modelWater.render(camera.get(), environment);        
        modelGround1.render(camera.get(), environment);                
        modelGroundCenter.render(camera.get(), environment);
        modelGroundFar.render(camera.get(), environment);
        modelGroundAround1.render(camera.get(), environment);        
        modelGroundAround3.render(camera.get(), environment); 
        
        if(camera.getCurrentPreset() == VCameraPresetCollection.PresetsIdentifiers.STATIC_VIEW_1){
        	modelGround2.setFadeOff();
        	modelGroundAround2.setFadeOff();        	
        }else{
        	modelGround2.setFadeOn();
        	modelGroundAround2.setFadeOn();
        }
        //TODO: Render parts based on camera states
     //   if(camera.getCurrentPreset() != VCameraPresetCollection.PresetsIdentifiers.MAIN){
        	modelUnderground1.render(camera.get(), environment);
      //  }
  
        modelGround2.render(camera.get(), environment);  
        modelGroundAround2.render(camera.get(), environment);
    	
        if(camera.getState() == VCamera.States.MAIN){
        	decalsTags.setFadeOn();
        }else{
        	decalsTags.setFadeOff();
        }
    	decalsTags.render();
        
        stage2D.renderMainStage();
        
        //Load all assets before creating new objects
        if (assetsManager.getQueuedAssets() > 0) {
            assetsManager.finishLoading();
        }
        /**/
    }
    
    public void onPan(float x, float y, float deltaX, float deltaY){
    	camera.pan(new Vector2(deltaX, deltaY));
    }

    public void onTap(float x, float y, int count, int button){
    	if(camera.getCurrentPreset() == VCameraPresetCollection.PresetsIdentifiers.MAIN){
	    	Ray r = camera.get().getPickRay(x, y);
	    	int iint = decalsTags.Intersect(r);
			switch(iint){
			case 0:
				camera.setCameraState(VCamera.States.STATIC_1);
				break;
			case 2:
				camera.setCameraState(VCamera.States.STATIC_3);
				break;
			case 3:
				camera.setCameraState(VCamera.States.STATIC_4);
				break;			
			};
    	}
    }
    
    public void onKeyDown(int keycode){
    	camera.onKeyDown(keycode);
    	
    	/*
    	if(keycode == 8){	//'1'    		
    		renderUndergroundPart1 = false;
    		camera.setCameraMode(0);
    	}
    	if(keycode == 9){	//'2'    		
    		renderUndergroundPart1 = false;
    		camera.setCameraMode(1);
    	}    	
    	if(keycode == 10){	//'3'    		
    		renderUndergroundPart1 = true;
    		camera.setCameraMode(2);
    	} 
    	if(keycode == 11){	//'4'    		
    		renderUndergroundPart1 = false;
    		camera.setCameraMode(3);
    	} 
    	*/    	
    }
    
    public void dispose(){

    }

    public void renderCubemapScene(PerspectiveCamera cam){
        
    	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);
        
        modelSkybox.render(cam, environment);        
        modelWater.render(cam, environment);        
        modelGround1.render(cam, environment);                
        modelGroundCenter.render(cam, environment);
        modelGroundFar.render(cam, environment);
        modelGroundAround1.render(cam, environment);        
        modelGroundAround3.render(cam, environment);     	
        modelGround2.render(cam, environment);  
        modelGroundAround2.render(cam, environment);    	
    }
    
    
    
}
