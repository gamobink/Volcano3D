package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Timer;
import com.volcano3d.utility.TextAsset;
import com.volcano3d.utility.TextAssetLoader;
import com.volcano3d.utility.VCommon;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcamera.VCameraPresetCollection;
import com.volcano3d.vdecal.VDecal;
import com.volcano3d.vdecal.VDecalGroup;
import com.volcano3d.vparticles.VParticleEffect;
import com.volcano3d.vstage.VFollowPathEditor;
import com.volcano3d.vstage.VStageMain;

/**
 * Created by T510 on 8/2/2017.
 */
/*
 * */
public class VMain{

    public AssetManager assetsManager = new AssetManager();
    public Environment environment = new Environment();
    public VCamera camera = null;   
    public VInputProcessor inputProcessor = new VInputProcessor(this);
    public VStageMain stage2D = null;
    public VDecalGroup decalsTags = null;
    public boolean  objectsLoaded = false;    
    public VScene scene = new VScene(this);
    public float userActionActiveDelay = 50;
    protected boolean userActionActive = false;
    private Timer.Task userActionActiveCountdown = null;

    public VParticleEffect	particleEffectSmoke = null;

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
        
        scene.create();

        decalsTags.addDecal(new VDecal("pinhead1.png", new Vector3(-220, 140, -10), new Vector2(36,50)));        
        decalsTags.addDecal(new VDecal("pinhead1.png", new Vector3(-32, 82, 8), new Vector2(36,50)));
        decalsTags.addDecal(new VDecal("pinhead1.png", new Vector3(146, 26, -216), new Vector2(36,50)));
        decalsTags.addDecal(new VDecal("pinhead1.png", new Vector3(-7, 35, -550), new Vector2(36,50)));

        particleEffectSmoke = new VParticleEffect(this, "point.pfx");

        pathEdit.setPath(stage2D.pathAction1, "pathAction1");
    }    
    //Call on loading complete
    void init(){

    	scene.init();
    	
    	decalsTags.init();
    	
    	particleEffectSmoke.init();
    	
//    	stage2D.introStage.showIntro();
    	stage2D.introStage.hideIntro();
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

        scene.renderToWaterTextures(camera, environment);
        
    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);
        
        particleEffectSmoke.render(camera.get());
        
        scene.render(camera, environment);

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
    
    public void setUserActionActive(){
    	
    	userActionActive = true;
    	stage2D.introStage.hideIntro();
    	
    	if(userActionActiveCountdown != null)userActionActiveCountdown.cancel();
    	
    	userActionActiveCountdown = Timer.schedule(new Timer.Task() {
	        @Override
	        public void run(){
	        	onUserActionLost();
	        }}, userActionActiveDelay);
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
    	
    	float waterFade = 1.0f;
    	float groundPart1Fade = 1.0f;
    	float groundPart2Fade = 1.0f;
    	
    	if(camera.getState() != VCamera.States.MAIN){
	    	if(angleUnits < 36 || (angleUnits > 177 && angleUnits < 360))waterFade = 0.0f;
	    	if(angleUnits < 48 || angleUnits > 280)groundPart1Fade = 0.0f;
	    	if(angleUnits < 241 && angleUnits > 76)groundPart2Fade = 0.0f;
    	}
    	    	
        scene.get("water").alphaFader.set("water", waterFade, 1f);    	
        scene.get("ground").alphaFader.set("groundPart1", groundPart2Fade, 1f);
        scene.get("ground").alphaFader.set("groundPart4", groundPart1Fade, 1f);
        scene.get("ground").alphaFader.set("groundFar1", groundPart2Fade, 1f);

        
        float decalsFade = (camera.getState() == VCamera.States.MAIN && userActionActive) ? 1.0f : 0.0f;
    	    	
        decalsTags.alphaFader.set("all", decalsFade, 1.0f);
    	
    }
}
