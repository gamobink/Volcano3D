package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
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
    public VRenderable modelGroundM0 = null; 
    public VRenderable modelGroundM1 = null; 
    public VRenderable modelGroundM2 = null; 
    public VRenderable modelGroundM3 = null; 
    public VRenderable modelGroundM4 = null; 
    public VRenderable modelPivot = null;   
    
    public VShader shaderSky = null;
    
    public VDecal decalMapTag1 = null;
    
    private Vector2 prevDragPos = new Vector2();
    private Vector2 dragTransl = new Vector2();
    
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
        environment.add(new DirectionalLight().set(2f, 2f, 0.9f,  0f, -0.8f, -1));  
        environment.add(new DirectionalLight().set(0.4f, 0.4f, 0.6f,  0f, -0.8f, 1));  
        
 //       environment.add(new PointLight().set(0.8f, 0.8f, 0.8f,   0, 300, 0,   1000.0f));  
        
        shaderSky = new VShader(this, "shaders/sky.vertex.glsl", "shaders/sky.fragment.glsl");
//        shaderSky = new VShader(this, "shaders/default.vertex.glsl", "shaders/default.fragment.glsl");
        
        modelSkybox = new VRenderable(this, "sky.g3dj", shaderSky);
        modelWater = new VRenderable(this, "water.g3dj");        
        modelGroundM0 = new VRenderable(this, "ground_m0.g3dj");        
        modelGroundM1 = new VRenderable(this, "ground_m1.g3dj");
        modelGroundM2 = new VRenderable(this, "ground_m2.g3dj");
        modelGroundM3 = new VRenderable(this, "ground_m3.g3dj");
        modelGroundM4 = new VRenderable(this, "ground_m4.g3dj");
        
        decalMapTag1 = new VDecal(this, "water.png", new Vector3(-220, 150, -10), new Vector2(50,50));
        
//        modelPivot = new VRenderable(this, "pivot.g3dj");
    }

    void init(){
    	modelSkybox.init();
    	modelWater.init();
    	modelGroundM0.init();    	
    	modelGroundM1.init();    	
    	modelGroundM2.init();
    	modelGroundM3.init();
    	modelGroundM4.init();
//    	modelPivot.init();   
    	decalMapTag1.init();
    }	
    
    void processFrame() {

        if (!assetsManager.update()) {
            assetsLoaded = false;
        //    guiMainStage.renderLoader();
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
        modelGroundM0.render(camera.get(), environment);
        modelGroundM1.render(camera.get(), environment);        
        modelGroundM2.render(camera.get(), environment);
        modelGroundM3.render(camera.get(), environment);
        modelGroundM4.render(camera.get(), environment);
        //modelPivot.render(camera.get(), environment);

        decalMapTag1.render();
        
        //Load all assets before creating new objects
        if (assetsManager.getQueuedAssets() > 0) {// && createGameObjectArray.size > 0
            assetsManager.finishLoading();
        }
    }
    
    public void onTouchDrag(int sx, int sy){
    	
    	Vector2 p = new Vector2(sx,sy);
    	dragTransl = p.sub(prevDragPos);
    	
//    	System.out.println(dragTransl);
    	camera.pan(dragTransl);
    	
    	prevDragPos.set(sx, sy);    	    	
    }
    
    public void onTouchDown(int sx, int sy){
    	prevDragPos.set(sx, sy);    	
    }    
    
    public void onKeyDown(int keycode){
    	camera.onKeyDown(keycode);
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
