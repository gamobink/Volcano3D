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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.volcano3d.utility.TextAsset;
import com.volcano3d.utility.TextAssetLoader;
import com.volcano3d.utility.VCommon;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcamera.VCameraPresetCollection;
import com.volcano3d.vdecal.VDecal;
import com.volcano3d.vdecal.VDecalGroup;
import com.volcano3d.vshaders.VDefaultShaderProvider;
import com.volcano3d.vshaders.VMinimalistShaderProvider;
import com.volcano3d.vstage.VFollowPathEditor;
import com.volcano3d.vstage.VStageMain;

/**
 * Created by T510 on 8/2/2017.
 */
/*
 * TODO Korekcijas
 * 
 * 
 * -) Juuras skataa nogulsnes - biologjiskie procesi
 * -) Kalna skatÄ� Ä·Ä«miskÄ� dÄ“dÄ“Å¡ana - tikai informatÄ«vs process, bes vizualizÄ�cijas. Ar attÄ“liem un shÄ“mÄ�m
 * -) Pludmales skatÄ� lielÄ�ka lagÅ«na/sÄ“klis kur Å«dens Å¾Å«st un  nogulsnÄ“jas Ä£ipsis un sÄ�Ä¼i
 * -) VulkÄ�na dÅ«mos ikona uz gÄ�zveida procesiem (seera fotogrÄ�fijas)
 * -) Magmatiskie kristÄ�li - jÄ�parÄ�da arÄ« shÄ“ma ar atdziÅ¡anu
 * -) Magma sarkanÄ�kÄ� krÄ�sÄ�
 * -) Metamorfiskie, skats starp kalnu un vulkaanu, dziÄ¼umÄ�, spiediena ietekmÄ“ starp magmatiskajiem un nogulumieÅ¾iem
 * 		veidojas kristÄ�li. Shematiska bilde!? +FotogrÄ�fijas	
 * 
 * 
 * -) SkaÅ†a ??
 * 
 * */
public class VMain{

    public AssetManager assetsManager = new AssetManager();
    public Environment environment = new Environment();
    public VCamera camera = null;   
    public VInputProcessor inputProcessor = new VInputProcessor(this);
    public VStageMain stage2D = null;
    public VDecalGroup decalsTags = null;
    public boolean  objectsLoaded = false;    

    public VRenderable modelSkybox = null; 
    public VRenderable modelWater = null; 
    public VRenderable modelUnderwater = null;
    
    public VRenderable modelGround = null; 
    public VRenderable modelIsland = null; 
    public VRenderable modelUnderground = null; 
    
    
    public VDefaultShaderProvider shaderSky = null;    
    public VMinimalistShaderProvider shaderSimple = null;
    public VDefaultShaderProvider shaderWater = null; 
        
    public Array<VTextureRender> waterTexturesArray = new Array<VTextureRender>();
    
    protected boolean userActionActive = false;
    private Timer.Task userActionActiveCountdown = null;
    
    private float waterMove = 0;
    
    public VFollowPathEditor pathEdit = new VFollowPathEditor(this);
        
    public VMain(){
        assetsManager.setLoader(TextAsset.class,new TextAssetLoader(new InternalFileHandleResolver()));
        assetsManager.setErrorListener(new AssetErrorListener() {
            @Override
            public void error(@SuppressWarnings("rawtypes") AssetDescriptor assetDescriptor, Throwable throwable) {
                System.out.println("ASSET: "+assetDescriptor.toString()+" - "+throwable.getMessage());
            }
        });
        
        VStaticAssets.Init();
        
        create();

        Gdx.input.setInputProcessor(new InputMultiplexer(stage2D.mainStage, inputProcessor.gestureDetector, inputProcessor));
    }
    public void switchInputProc(boolean i){
    	if(i)Gdx.input.setInputProcessor(new InputMultiplexer(pathEdit.stage)); 
    	else Gdx.input.setInputProcessor(new InputMultiplexer(stage2D.mainStage, inputProcessor.gestureDetector, inputProcessor));
    }
    public void create(){
    	
        stage2D = new VStageMain(this);
        camera = new VCamera(this);
        decalsTags = new VDecalGroup(this);
        		
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(0.9f, 0.9f, 0.5f,  -1, -0.8f, 1));  
        environment.add(new DirectionalLight().set(0.4f, 0.4f, 0.6f,  0f, -0.8f, 1));  
        environment.set(new ColorAttribute(ColorAttribute.Fog, 0.6f, 0.6f, 0.8f, 1f));
        
        shaderSky = new VDefaultShaderProvider(this, "shaders/sky.vertex.glsl", "shaders/sky.fragment.glsl");        
        shaderSky.setDepthFunc(0);
        
        shaderSimple = new  VMinimalistShaderProvider(this, "shaders/min.vertex.glsl", "shaders/min.fragment.glsl");
        shaderWater = new VDefaultShaderProvider(this, "shaders/water.vertex.glsl", "shaders/water.fragment.glsl");
        
        modelSkybox = new VRenderable(this, "skybox.g3dj", shaderSky);
        modelWater = new VRenderable(this, "water.g3dj", shaderWater);    //shaderWater 
        
        modelUnderwater = new VRenderable(this, "underwater.g3dj",shaderSimple);	//TODO underwater model
        
        modelGround = new VRenderable(this, "ground_reef.g3dj");
        modelIsland = new VRenderable(this, "island.g3dj", shaderSimple);        
        
        modelUnderground = new VRenderable(this, "underground.g3dj");     

        decalsTags.addDecal(new VDecal("pinhead1.png", new Vector3(-220, 140, -10), new Vector2(36,50)));        
        decalsTags.addDecal(new VDecal("pinhead1.png", new Vector3(-32, 82, 8), new Vector2(36,50)));
        decalsTags.addDecal(new VDecal("pinhead1.png", new Vector3(146, 26, -216), new Vector2(36,50)));
        decalsTags.addDecal(new VDecal("pinhead1.png", new Vector3(-7, 35, -550), new Vector2(36,50)));
        
//        decalsTags.addDecal(new VDecal("sign.png", new Vector3(-220, 140, -10), new Vector2(50,50)));
//        decalsTags.addDecal(new VDecal("sign2.png", new Vector3(-32, 82, 8), new Vector2(50,50)));
//        decalsTags.addDecal(new VDecal("sign3.png", new Vector3(146, 32, -216), new Vector2(50,50)));
//        decalsTags.addDecal(new VDecal("sign4.png", new Vector3(-7, 35, -550), new Vector2(50,50)));

        waterTexturesArray.add(new VTextureRender(this));		//reflection
        waterTexturesArray.add(new VTextureRender(this));		//reflected skybox stretched
        waterTexturesArray.add(new VTextureRender(this));		//refraction
        
        modelGround.enableTween();
        //add faders to all ground parts to be faded 
        modelGround.alphaFader.set("groundPart1", 1.0f, 1.0f);
        modelGround.alphaFader.set("groundPart2", 1.0f, 1.0f);
        modelGround.alphaFader.set("groundPart3", 1.0f, 1.0f);
        modelGround.alphaFader.set("groundFar1", 1.0f, 1.0f);
        modelGround.alphaFader.set("groundFar2", 1.0f, 1.0f);
        
        //Fading for under water part
        modelWater.enableTween();
        modelWater.alphaFader.set("waterCenter", 1.0f, 1.0f);
        modelWater.alphaFader.set("water", 1.0f, 1.0f);
        
        pathEdit.setPath(stage2D.pathAction1, "pathAction1");
    }    
    //Call on loading complete
    void init(){
    	shaderSky.init();
    	shaderSimple.init();
    	shaderWater.init();
    	
    	modelSkybox.init();
    	modelWater.init();
    	modelUnderwater.init();
    	
    	modelGround.init();
    	modelIsland.init();
    	
    	modelUnderground.init();
    	
    	decalsTags.init();
    	
    	stage2D.introStage.showIntro();
//    	stage2D.introStage.hideIntro();
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
        
        updateModelFaders();

        waterMove += 0.1f * Gdx.graphics.getDeltaTime();
        waterMove = waterMove % 1;
        
        renderWaterScene(camera.get());
        
        modelWater.setReflectionTexture("water", waterTexturesArray.get(0).get());	//Reflection
        modelWater.setAmbientTexture("water", waterTexturesArray.get(1).get());	//Stretched reflection
        modelWater.setSpecularTexture("water", waterTexturesArray.get(2).get());	//Refraction
        
        modelWater.setReflectionTexture("waterCenter", waterTexturesArray.get(0).get());	//Reflection
        modelWater.setAmbientTexture("waterCenter", waterTexturesArray.get(1).get());	//Stretched reflection
        modelWater.setSpecularTexture("waterCenter", waterTexturesArray.get(2).get());	//Refraction

        modelWater.setShininess("water", waterMove);
        modelWater.setShininess("waterCenter", waterMove);
        
    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);
        
        modelSkybox.render(camera.get(), environment);
        
        //TODO Render underground parts based on camera states
      //  if(camera.getCurrentPreset() != VCameraPresetCollection.PresetsIdentifiers.MAIN){
        //	modelUnderground.render(camera.get(), environment);
      //  }
        	
        modelWater.render(camera.get(), environment);        	
        //modelUnderwater.render(camera.get(), environment);        	
            
        modelGround.render(camera.get(), environment);
        modelIsland.render(camera.get(), environment);
        
        //VCommon.drawGrid(camera.get());
        
    	decalsTags.render();       
        stage2D.renderMainStage();
        
        //pathEdit.render();
        
        //Load all assets before creating new objects
        if (assetsManager.getQueuedAssets() > 0) {
            assetsManager.finishLoading();
        }
    }
    
    public void onPan(float x, float y, float deltaX, float deltaY){
    	
    	setUserActionActive();
    	
    	camera.pan(new Vector2(deltaX, deltaY));
    }

    public void onTap(float x, float y, int count, int button){
    	
    	if(camera.getCurrentPreset() == VCameraPresetCollection.PresetsIdentifiers.MAIN && userActionActive){
	    	Ray r = camera.get().getPickRay(x, y);
	    	int iint = decalsTags.Intersect(r);
			switch(iint){
			case 0:
				camera.setCameraState(VCamera.States.STATIC_1);
				this.stage2D.transitionToStaticView(0);
				break;
			case 1:
				camera.setCameraState(VCamera.States.STATIC_2);
				this.stage2D.transitionToStaticView(0);
				break;
			case 2:
				camera.setCameraState(VCamera.States.STATIC_3);
				this.stage2D.transitionToStaticView(0);
				break;
			case 3:
				camera.setCameraState(VCamera.States.STATIC_4);
				this.stage2D.transitionToStaticView(0);
				break;				
			};
    	}
    	
    	setUserActionActive();
    }
    
    public void onKeyDown(int keycode){
    	camera.onKeyDown(keycode);
    	
//    	System.out.println(keycode);
    	/*
    	if(keycode == 51){	//'W'   
    		this.switchInputProc(true);
    	}
    	if(keycode == 8){	//'1'   
    		pathEdit.setPath(stage2D.pathAction1, "pathAction1");
    	}
    	if(keycode == 9){	//'2'    		
    		pathEdit.setPath(stage2D.pathAction2, "pathAction2");
    	}    	
    	if(keycode == 10){	//'3'    		
    		pathEdit.setPath(stage2D.pathAction3, "pathAction3");
    	} 
    	if(keycode == 11){	//'4'    		
    	//	renderUndergroundPart1 = false;
    	//	camera.setCameraMode(3);
    	} 
    	 	*/
    }
    
    public void dispose(){

    }

    public void renderWaterScene(PerspectiveCamera cam){
    	
    	PerspectiveCamera c = new PerspectiveCamera(35, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    	c.position.set(cam.position);
    	c.direction.set(cam.direction);
    	c.up.set(cam.up);
    	c.fieldOfView = cam.fieldOfView;
    	c.near = 10f;
    	c.far = 3000;
    	
    	for(int i=0; i<3; i++){
    		if(i==0 || i==1){
		    	c.position.y = -cam.position.y;
		    	c.direction.y = -cam.direction.y;
    		}else{
		    	c.position.y = cam.position.y;
		    	c.direction.y = cam.direction.y;
    		}
    		if(i==1){
    			c.far = 10000;
    		}else{
    			c.far = 3000;
    		}
	    	c.update();
	    	
	    	waterTexturesArray.get(i).beginRender();
	    	
	    	Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
	        Gdx.gl.glClearColor(0,0,0,1.0f);
	        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);   
	        
	        if(i==0){	//reflection
	        	modelSkybox.scale(1, 1, 1);
		        modelSkybox.render(c, environment);
		        modelGround.render(c, environment);
		        modelIsland.render(c, environment);
		        
	        }else if(i==1){	//reflection stretched skybox

	        	modelSkybox.scale(1, 50, 1);
	        	modelSkybox.render(c, environment);
	        	
	        }else if(i == 2){	//under water part - refraction	        	
	        	modelUnderwater.render(c, environment);
	        }
	        waterTexturesArray.get(i).endRender();
    	}
    	modelSkybox.scale(1, 1.2f, 1);
    }
    public void renderWaterRefractionScene(PerspectiveCamera cam){

    }
    public void setUserActionActive(){
    	
    	userActionActive = true;
    	stage2D.introStage.hideIntro();
    	
    	if(userActionActiveCountdown != null)userActionActiveCountdown.cancel();
    	
    	userActionActiveCountdown = Timer.schedule(new Timer.Task() {
	        @Override
	        public void run(){
	        	onUserActionLost();
	        }}, 50);
    }
    public void onUserActionLost(){
    	userActionActiveCountdown = null;
    	userActionActive = false;
    	stage2D.introStage.showIntro();
    	camera.setCameraState(VCamera.States.MAIN);
    	stage2D.transitionToMainView();
    }
    public void updateModelFaders(){
    	
    	

		float angleUnits = camera.anglePos.x;
    	if(angleUnits < 0){    		
    		angleUnits += 360.0f;    		
    	}
    	
    	//System.out.println(angleUnits);
    	
    	float waterFade = 1.0f;
    	float groundPart1Fade = 1.0f;
    	float groundPart2Fade = 1.0f;
    	float groundPart3Fade = 1.0f;
    	
    	if(camera.getState() != VCamera.States.MAIN){
	    	if(angleUnits < 3 || (angleUnits > 190 && angleUnits < 360))waterFade = 0.0f;
	    	if(angleUnits < 220 && angleUnits > 190)groundPart1Fade = 0.0f;
	    	if(angleUnits < 190 && angleUnits > 110)groundPart2Fade = 0.0f;
	    	if(angleUnits < 110 || (angleUnits > 330 && angleUnits < 360))groundPart3Fade = 0.0f;    	
    	}
    	modelWater.alphaFader.set("water", waterFade, 1f);    	
    	modelGround.alphaFader.set("groundPart1", groundPart1Fade, 1f);
    	modelGround.alphaFader.set("groundPart2", groundPart2Fade, 1f);
    	modelGround.alphaFader.set("groundPart3", groundPart3Fade, 1f);
    	
    	modelGround.alphaFader.set("groundFar1", groundPart1Fade * groundPart2Fade, 1f);
    	modelGround.alphaFader.set("groundFar2", groundPart3Fade, 1f);

//		STATIC_VIEW_1, 				//Volcano
//		STATIC_VIEW_2,				//Hill	
//		STATIC_VIEW_3,				//Sea	
//		STATIC_VIEW_4,				//Beach
//		STATIC_VIEW_5,				//Rocks
//		STATIC_VIEW_6				//Rain	    	
    	/*
    	float fadeView1 = (camera.getCurrentPreset() == VCameraPresetCollection.PresetsIdentifiers.STATIC_VIEW_1) ? 0.0f : 1.0f;
    	float fadeView2 = (camera.getCurrentPreset() == VCameraPresetCollection.PresetsIdentifiers.STATIC_VIEW_2) ? 0.0f : 1.0f;
    	float fadeView3 = (camera.getCurrentPreset() == VCameraPresetCollection.PresetsIdentifiers.STATIC_VIEW_3) ? 0.0f : 1.0f;
    	float fadeView4 = (camera.getCurrentPreset() == VCameraPresetCollection.PresetsIdentifiers.STATIC_VIEW_4) ? 0.0f : 1.0f;
    	
    	modelGround.alphaFader.set("groundPart2", fadeView1 * fadeView2, 0.3f);
    	modelGround.alphaFader.set("groundFar1", fadeView1 * fadeView2, 0.3f);
    	
    	modelGround.alphaFader.set("groundPart1", fadeView2, 0.3f);
    	
    	modelGround.alphaFader.set("groundPart3", fadeView4, 0.3f);
    	modelGround.alphaFader.set("groundFar2", fadeView4, 0.3f);
    	
    	modelWater.alphaFader.set("water", fadeView2 * fadeView3 * fadeView4, 0.4f);

    	*/
    	
    	//TODO Fade in and out different parts of ground geometry
    	
        float decalsFade = (camera.getState() == VCamera.States.MAIN && userActionActive) ? 1.0f : 0.0f;
    	    	
        decalsTags.alphaFader.set("all", decalsFade, 1.0f);
    	
    }
}
