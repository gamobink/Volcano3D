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
    public VRenderable modelGround = null; 
    public VRenderable modelIsland = null; 
    
    public VDefaultShaderProvider shaderSky = null;    
    public VMinimalistShaderProvider shaderSimple = null;
    public VDefaultShaderProvider shaderWater = null; 
        
    public VTextureRender reflectionTexture = null;
    public VTextureRender refractionTexture = null;
    
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
        shaderSky.setDepthFunc(0);
        
        shaderSimple = new  VMinimalistShaderProvider(this, "shaders/min.vertex.glsl", "shaders/min.fragment.glsl");
        shaderWater = new VDefaultShaderProvider(this, "shaders/water.vertex.glsl", "shaders/water.fragment.glsl");
        
        modelSkybox = new VRenderable(this, "skybox.g3dj", shaderSky);
        modelWater = new VRenderable(this, "water.g3dj", shaderWater);    //shaderWater 
        
        modelGround = new VRenderable(this, "ground.g3dj");
        modelIsland = new VRenderable(this, "island.g3dj");        
        
        decalsTags.addDecal(new VDecal("sign.png", new Vector3(-220, 150, -10), new Vector2(50,50)));
        decalsTags.addDecal(new VDecal("sign2.png", new Vector3(-32, 92, 8), new Vector2(50,50)));
        decalsTags.addDecal(new VDecal("sign3.png", new Vector3(146, 42, -216), new Vector2(50,50)));
        decalsTags.addDecal(new VDecal("sign4.png", new Vector3(-7, 45, -550), new Vector2(50,50)));

        reflectionTexture = new VTextureRender(this);
        refractionTexture = new VTextureRender(this);        
    
    }    
    //Call on loading complete
    void init(){
    	shaderSky.init();
    	shaderSimple.init();
    	shaderWater.init();
    	
    	modelSkybox.init();
    	modelWater.init();
    	
    	modelGround.init();
    	modelIsland.init();
    	    	
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
        
        renderWaterScene(camera.get(), true);
        modelWater.setAmbientTexture(reflectionTexture.get());
        
    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);
        
        //Frustum culling
        modelSkybox.render(camera.get(), environment);        
        modelWater.render(camera.get(), environment); 
        modelGround.render(camera.get(), environment);
        modelIsland.render(camera.get(), environment);
        
        if(camera.getCurrentPreset() == VCameraPresetCollection.PresetsIdentifiers.STATIC_VIEW_1){
//        	modelGround2.setFadeOff();
//        	modelGroundAround2.setFadeOff();        	
        }else{
//        	modelGround2.setFadeOn();
//        	modelGroundAround2.setFadeOn();
        }
        //TODO: Render parts based on camera states
     //   if(camera.getCurrentPreset() != VCameraPresetCollection.PresetsIdentifiers.MAIN){
  //      	modelUnderground1.render(camera.get(), environment);
      //  }
  
//        modelGround2.render(camera.get(), environment);  
//        modelGroundAround2.render(camera.get(), environment);
    	
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

    public void renderWaterScene(PerspectiveCamera cam, boolean reflectOrRefract){
    	
    	PerspectiveCamera c = new PerspectiveCamera(35, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    	c.position.set(cam.position);
    	c.direction.set(cam.direction);
    	c.up.set(cam.up);
    	c.fieldOfView = cam.fieldOfView;
    	c.near = 0.1f;
    	c.far = 3000;
    	
    	c.position.y = -c.position.y;
    	c.direction.y = -c.direction.y;
    	c.update();
    	
    	reflectionTexture.beginRender();
    	
    	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0,0,1,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);   
        
        modelSkybox.render(c, environment);        
        modelGround.render(c, environment);
        modelIsland.render(c, environment);

        reflectionTexture.endRender();
    }
    public void renderWaterRefractionScene(PerspectiveCamera cam){

    }
    
}
