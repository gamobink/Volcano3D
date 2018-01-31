package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.Utility.TextAsset;
import com.volcano3d.Utility.TextAssetLoader;

/**
 * Created by T510 on 8/2/2017.
 */

public class SceneManager {

    public AssetManager assetsManager;
    public boolean  assetsLoaded = false;
    public boolean  objectsLoaded = false;    
    
    public Environment environment = null;
    public VCamera camera = new VCamera();   
    public CameraInputController camController = null;

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
    
    public boolean renderUndergroundPart1 = false;
    
    public VRenderable modelPivot = null;   
    
    public VShader shaderSky = null;
    
    public VDecal decalMapTag1 = null;
    public VDecal decalMapTag2 = null;
    public VDecal decalMapTag3 = null;
    public VDecal decalMapTag4 = null;
    
    //private Vector2 prevDragPos = new Vector2();
    //private Vector2 dragTransl = new Vector2();
    
    private VStage2D stage2D = null;
    
    public SceneManager(){

        assetsManager = new AssetManager();

        assetsManager.setLoader(TextAsset.class,new TextAssetLoader(new InternalFileHandleResolver()));
        
        assetsManager.setErrorListener(new AssetErrorListener() {
            @Override
            public void error(AssetDescriptor assetDescriptor, Throwable throwable) {
                System.out.println("ASSET: "+assetDescriptor.toString()+" - "+throwable.getMessage());
            }
        });

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(0.9f, 0.9f, 0.5f,  -1, -0.8f, 1));  
        environment.add(new DirectionalLight().set(0.4f, 0.4f, 0.6f,  0f, -0.8f, 1));  
        
 //       environment.add(new PointLight().set(0.8f, 0.8f, 0.8f,   0, 300, 0,   1000.0f));  
        
        shaderSky = new VShader(this, "shaders/sky.vertex.glsl", "shaders/sky.fragment.glsl");
//        shaderSky = new VShader(this, "shaders/default.vertex.glsl", "shaders/default.fragment.glsl");
        
        modelSkybox = new VRenderable(this, "sky.g3dj", shaderSky);
        modelWater = new VRenderable(this, "water.g3dj", shaderSky);     
        
        modelGround1 = new VRenderable(this, "ground1.g3dj");        
        modelGround2 = new VRenderable(this, "ground2.g3dj");
        modelGroundCenter = new VRenderable(this, "groundCenter.g3dj");
        modelGroundFar = new VRenderable(this, "groundFar.g3dj");
        modelGroundAround1 = new VRenderable(this, "groundAround1.g3dj");
        modelGroundAround2 = new VRenderable(this, "groundAround2.g3dj");
        modelGroundAround3 = new VRenderable(this, "groundAround3.g3dj");
        modelUnderground1 = new VRenderable(this, "underground.g3dj", shaderSky);
        
        
        decalMapTag1 = new VDecal(this, "sign.png", new Vector3(-220, 150, -10), new Vector2(50,50));
        decalMapTag2 = new VDecal(this, "sign2.png", new Vector3(-32, 92, 8), new Vector2(50,50));
        decalMapTag3 = new VDecal(this, "sign3.png", new Vector3(146, 42, -216), new Vector2(50,50));
        decalMapTag4 = new VDecal(this, "sign4.png", new Vector3(-7, 45, -550), new Vector2(50,50));        
        
//        modelPivot = new VRenderable(this, "pivot.g3dj");
        
        stage2D = new VStage2D(this);
    }

    void init(){
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
    	
    	//modelPivot.init();   
    	
    	decalMapTag1.init();
    	decalMapTag2.init();
    	decalMapTag3.init();
    	decalMapTag4.init();    	
    }	
    
    void processFrame() {

        if (!assetsManager.update()) {
            assetsLoaded = false;
            stage2D.renderLoader();
            return;
        }
        assetsLoaded = true;
        
        if(!objectsLoaded){
        	init();
        	objectsLoaded = true;
        }
        
        camera.update();
        
        modelSkybox.render(camera.get(), environment);
        modelWater.render(camera.get(), environment);
        
        modelGround1.render(camera.get(), environment);        
        if(!renderUndergroundPart1)modelGround2.render(camera.get(), environment);
        modelGroundCenter.render(camera.get(), environment);
        modelGroundFar.render(camera.get(), environment);
        modelGroundAround1.render(camera.get(), environment);        
        if(!renderUndergroundPart1)modelGroundAround2.render(camera.get(), environment);        
        modelGroundAround3.render(camera.get(), environment); 
        if(renderUndergroundPart1)modelUnderground1.render(camera.get(), environment);
        
        //modelPivot.render(camera.get(), environment);

        decalMapTag1.render();
        decalMapTag2.render();
        decalMapTag3.render();
        decalMapTag4.render();
        
        //Load all assets before creating new objects
        if (assetsManager.getQueuedAssets() > 0) {// && createGameObjectArray.size > 0
            assetsManager.finishLoading();
        }
    }
    
    public void onPan(float x, float y, float deltaX, float deltaY){

    	camera.pan(new Vector2(deltaX, deltaY));
    }

    public void onTap(float x, float y, int count, int button){
    	
    	System.out.println("Tap");
    	
    	Ray r = camera.get().getPickRay(x, y);
    	Vector3 decalCenter = decalMapTag1.position;
    	Vector3 decalSize = new Vector3(decalMapTag1.size.x, decalMapTag1.size.y, decalMapTag1.size.x);    	
    	if(r!= null && Intersector.intersectRayBoundsFast(r, decalCenter, decalSize)){
    		
    	}
    	
    }
    
    public void onKeyDown(int keycode){
    	camera.onKeyDown(keycode);

    	if(keycode == 8){	//'1'    		
    		renderUndergroundPart1 = false;
    		camera.setCameraMode(0);
    	}
    	if(keycode == 9){	//'2'    		
    		renderUndergroundPart1 = true;
    		camera.setCameraMode(1);
    	}    	
    	if(keycode == 10){	//'3'    		
    		renderUndergroundPart1 = false;
    		camera.setCameraMode(2);
    	} 
    	if(keycode == 11){	//'4'    		
    		renderUndergroundPart1 = false;
    		camera.setCameraMode(3);
    	}     	
    }
    
    public void dispose(){
//        for (final GameObject go : this.gameObjectArray) {
//            go.dispose();
//        }
//        this.gameObjectArray.clear();
    }

//    private void CollisionTest(){
//        for (int a = 0; a < gameObjectArray.size; a++) {
//            for (int b = a; b < gameObjectArray.size; b++) {
//                if(a != b){
//                    GameObject ao = gameObjectArray.get(a);
//                    GameObject bo = gameObjectArray.get(b);
//                    if(ao.collide && bo.collide) {
//                        Vector3 pt = new Vector3();
//                        if (collisionManager.Collide(ao, bo, pt)) {
//                            ao.onCollision(bo, pt);
//                            bo.onCollision(ao, pt);
//                        }
//                    }
//                }
//            }
//        }
//    }

    //TODO: bounding box check
//    public GameObject traceRay(Ray ray, Vector3 out){
//
//        GameObject obj = null;
//        float prevT = 100000;
//        Vector3 inter = new Vector3();
//        //boolean hasInt = false;
//        GameObject o = null;
//        for (int i = 0; i < gameObjectArray.size; i++) {
//            o = gameObjectArray.get(i);
//            if (o.collide) {
//                if (o.intersectRay(ray, inter)) {
//                    float l = out.cpy().sub(ray.origin).len2();
//                    if(l < prevT){
//                        prevT = l;
//                        out.set(inter);
//                        //hasInt = true;
//                        obj = o;
//                    }
//                }
//            }
//        }
//        return obj;
//    }
}
