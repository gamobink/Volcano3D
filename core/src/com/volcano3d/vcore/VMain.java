package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Timer;
import com.volcano3d.utility.VFilteredTexture;
import com.volcano3d.utility.VFilteredTextureLoader;
import com.volcano3d.utility.TextAsset;
import com.volcano3d.utility.TextAssetLoader;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcamera.VCameraPresetCollection;
import com.volcano3d.vdecal.VDecal;
import com.volcano3d.vdecal.VDecalGroup;
import com.volcano3d.vscene.VScene;
import com.volcano3d.vstage.VStageMain;

/**
 * Created by T510 on 8/2/2017.
 */
/*
 * */
public class VMain{

    public AssetManager assetsManager = new AssetManager();
    public VCamera camera = null;   
    public VInputProcessor inputProcessor = new VInputProcessor(this);
    public VStageMain stage2D = null;
    public VDecalGroup decalsTags = null;
    public boolean  objectsLoaded = false;    
    public VScene scene = new VScene(this);
    public float userActionActiveDelay = VConfig.get().userActionActiveTimeout;
    protected boolean userActionActive = false;
    private Timer.Task userActionActiveCountdown = null;

    public boolean hideUndergroundPart = false;
    public boolean showUnderwaterCenterOnly = false;

   // public VFollowPathEditor pathEdit = new VFollowPathEditor(this);
        
    public VMain(){
        assetsManager.setLoader(TextAsset.class,new TextAssetLoader(new InternalFileHandleResolver()));
        assetsManager.setErrorListener(new AssetErrorListener() {
            @Override
            public void error(@SuppressWarnings("rawtypes") AssetDescriptor assetDescriptor, Throwable throwable) {
                System.out.println("ASSET: "+assetDescriptor.toString()+" - "+throwable.getMessage());
            }
        });
        
        ParticleEffectLoader loader = new ParticleEffectLoader(new InternalFileHandleResolver());
        assetsManager.setLoader(ParticleEffect.class, loader);        
        
        assetsManager.setLoader(VFilteredTexture.class, new VFilteredTextureLoader(new InternalFileHandleResolver()));
        
        VStaticAssets.Init();
        
        create();

        Gdx.input.setInputProcessor(new InputMultiplexer(stage2D.mainStage, inputProcessor.gestureDetector, inputProcessor));
    }
    
    /*
    public void switchInputProc(boolean i){
    	if(i)Gdx.input.setInputProcessor(new InputMultiplexer(pathEdit.stage)); 
    	else Gdx.input.setInputProcessor(new InputMultiplexer(stage2D.mainStage, inputProcessor.gestureDetector, inputProcessor));
    }*/
    
    public void create(){
    	
        stage2D = new VStageMain(this);
        camera = new VCamera(this);
        decalsTags = new VDecalGroup(this);
        		
        scene.create();
        
        decalsTags.addDecal(new VDecal("icons/magmatic.png", new Vector3(-250, 70, 80), VConfig.get().tagDecalsSize)); 	//0 - Magmatic 
        decalsTags.addDecal(new VDecal("icons/pegmatite.png", new Vector3(-32, 82, 8), VConfig.get().tagDecalsSize));		//1 - Pegmatite
        decalsTags.addDecal(new VDecal("icons/hydrothermal.png", new Vector3(-330, 62, -80), VConfig.get().tagDecalsSize));	//2 - hydrothermal
        decalsTags.addDecal(new VDecal("icons/pneymatolitic.png", new Vector3(-200, 160, -10), VConfig.get().tagDecalsSize));	//3 - pneymatolitic
        decalsTags.addDecal(new VDecal("icons/metamorphic.png", new Vector3(-100, 32, 120), VConfig.get().tagDecalsSize));	//4 - metamorphic        
        decalsTags.addDecal(new VDecal("icons/chemical.png", new Vector3(80, 25, 8), VConfig.get().tagDecalsSize));		//5 - chemical
        decalsTags.addDecal(new VDecal("icons/organic.png", new Vector3(146, 25, -216), VConfig.get().tagDecalsSize));	//6 - organic
        decalsTags.addDecal(new VDecal("icons/sediment.png", new Vector3(0, 25, -550), VConfig.get().tagDecalsSize));		//7 - sediment 

    //    pathEdit.setPath(stage2D.pathActionsButtonsIn.get(0), "pathAction1");
    }    
    //Call on loading complete
    void onLoad(){

    	scene.onLoad();
    	
    	decalsTags.onLoad();
    	
    	stage2D.onLoad();
    	
    	stage2D.introStage.showIntro();
//    	stage2D.introStage.hideIntro();
    }	
    
    public void render() {
        
    	if (!assetsManager.update()) {
            stage2D.renderLoader();
            return;
        }
        
        if(!objectsLoaded){
        	onLoad();
        	objectsLoaded = true;
        }
                
        camera.update();
        
        updateModelFaders();

        scene.renderToWaterTextures(camera);
        
    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);
        
        scene.render(camera);
        
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
	        //0 - Magmatic 
	        //1 - Pegmatite
	        //2 - hydrothermal
	        //3 - pneymatolitic
	        //4 - metamorphic
	        //5 - chemical
	        //6 - organic
	        //7 - sediment	    	
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
			case 4:
				camera.setCameraState(VCamera.States.STATIC_5);
				this.stage2D.transitionToStaticView(0);
				break;	
			case 5:
				camera.setCameraState(VCamera.States.STATIC_6);
				this.stage2D.transitionToStaticView(0);
				break;	
			case 6:
				camera.setCameraState(VCamera.States.STATIC_7);
				this.stage2D.transitionToStaticView(0);
				break;					
			case 7:
				camera.setCameraState(VCamera.States.STATIC_8);
				this.stage2D.transitionToStaticView(0);
				break;
			case 8:
				camera.setCameraState(VCamera.States.STATIC_EASTEREGG);
				this.stage2D.transitionToStaticView(0);
				break;				
			};
    	}
    	
    	setUserActionActive();
    }
    
    public void onKeyDown(int keycode){
    	camera.onKeyDown(keycode);
    	
    	//System.out.println(keycode);
    	/*
    	if(keycode == 51){	//'W'   
    		this.switchInputProc(true);
    	}
    	if(keycode == 8){	//'1'   
    		pathEdit.setPath(stage2D.pathActionsButtonsIn.get(0), "pathAction1");
    	}
    	if(keycode == 9){	//'2'    		
    		pathEdit.setPath(stage2D.pathActionsButtonsIn.get(1), "pathAction2");
    	}    	
    	if(keycode == 10){	//'3'    		
    		pathEdit.setPath(stage2D.pathActionsButtonsIn.get(2), "pathAction3");
    	} 
    	if(keycode == 11){	//'4'    		
    		pathEdit.setPath(stage2D.pathActionsButtonsIn.get(3), "pathAction4");
    	} 
    	if(keycode == 12){   		
    		pathEdit.setPath(stage2D.pathActionsButtonsIn.get(4), "pathAction5");
    	} 
    	if(keycode == 13){    		
    		pathEdit.setPath(stage2D.pathActionsButtonsIn.get(5), "pathAction6");
    	} 
    	if(keycode == 14){   		
    		pathEdit.setPath(stage2D.pathActionsButtonsIn.get(6), "pathAction7");
    	}  
    	if(keycode == 15){   		
    		pathEdit.setPath(stage2D.pathActionsButtonsIn.get(7), "pathAction8");
    	}      	
    	/**/
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
    	stage2D.hideAllInfoWindows();
    	stage2D.introStage.showIntro();
    	camera.setCameraState(VCamera.States.MAIN);
    	stage2D.transitionToMainView();
    }
    public void updateModelFaders(){

		float angleUnits = camera.anglePos.x;
    	if(angleUnits < 0){    		
    		angleUnits += 360.0f;    		
    	}
    	
    	float water1Fade = 1.0f;
    	float water2Fade = 1.0f;  
    	float groundPart1Fade = 1.0f;
    	float groundPart2Fade = 1.0f;
    	float schematicFade = 0.0f;
    	
    	if(camera.getState() != VCamera.States.MAIN && camera.getState() != VCamera.States.STATIC_EASTEREGG){
    		
    		schematicFade = 1.0f;
    		
	    	if(angleUnits < 36 || (angleUnits > 270 && angleUnits < 360))water1Fade = 0.0f;

	    	if(angleUnits < 226 && angleUnits > 170)water2Fade = 0.0f;	    	
	    	
	    	if(angleUnits < 322 && angleUnits > 226){
	    		water1Fade = 0.0f;
	    		water2Fade = 0.0f;
	    	}	    	
	    	if(angleUnits < 48 || angleUnits > 280)groundPart1Fade = 0.0f;
	    	if(angleUnits < 241 && angleUnits > 76)groundPart2Fade = 0.0f;
    	}
    	
        scene.groundScene.waterModel.alphaFader.set("water1", water1Fade, 5f, 1f);
        scene.groundScene.waterModel.alphaFader.set("water2", water2Fade, 5f, 1f);
        scene.groundScene.waterFoamModel.alphaFader.set("foamFar2", water1Fade, 5f, 1f);
        scene.groundScene.waterFoamModel.alphaFader.set("foamFar1", water2Fade, 5f, 1f);
        
        scene.groundScene.groundModel.alphaFader.set("groundPart1", groundPart2Fade, 3f, 0.7f);
        scene.groundScene.groundModel.alphaFader.set("groundPart4", groundPart1Fade, 3f, 0.7f);
        scene.groundScene.groundModel.alphaFader.set("groundFar1", groundPart2Fade, 3f, 0.7f);
        
        scene.groundScene.lavaOuterModel.alphaFader.set("lavaFar", groundPart2Fade, 3f, 0.7f);
        
        scene.crossectionScene.schemaL1Model.alphaFader.set("Plane04", schematicFade, 3f, 0.7f);

        //System.out.println(schematicFade);
        
        if(water2Fade < 1.0f){
        	showUnderwaterCenterOnly = true;
        }else{
        	showUnderwaterCenterOnly = false;
        }
        
        if(water1Fade < 1.0f || water2Fade < 1.0f){
        	hideUndergroundPart = true;
        }else{
        	hideUndergroundPart = false;
        }
        
        
        float decalsFade = 0.0f;
        
        if(camera.getState() == VCamera.States.MAIN 
        		&& userActionActive
        		&& !stage2D.isNavigationOpen)decalsFade = 1.0f;
    	    	
        decalsTags.alphaFader.set("all", decalsFade, 1.0f);
    	
    }
}
