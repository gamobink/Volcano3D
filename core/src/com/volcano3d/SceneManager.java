package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

/**
 * Created by T510 on 8/2/2017.
 */

public class SceneManager {

    public AssetManager assetsManager;
    public boolean  assetsLoaded = false;
    public boolean  objectsLoaded = false;    
    
    public Environment environment = null;
    public PerspectiveCamera cam = null;   
    public CameraInputController camController = null;

    public Renderable modelSkybox = null; 
    public Renderable modelWater = null; 
    
    public SceneManager(){

        assetsManager = new AssetManager();

        assetsManager.setErrorListener(new AssetErrorListener() {
            @Override
            public void error(AssetDescriptor assetDescriptor, Throwable throwable) {
                System.out.println("ASSET: "+assetDescriptor.toString()+" - "+throwable.getMessage());
            }
        });

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 0.7f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));        
        
        cam = new PerspectiveCamera(40, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(200.7f, 1.4f, 6f);
        cam.lookAt(0,1.1f,0);
        cam.near = 1f;
        cam.far = 50000f;
        cam.update();        
        
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(new InputMultiplexer(camController));  //, camController, , sceneManager.guiMainStage.getStage()        
        
        //modelSkybox = new Renderable(this, "scene.g3dj");
        modelSkybox = new Renderable(this, "sky.g3dj");
        modelWater = new Renderable(this, "water.g3dj");
    }

    void init(){
    	modelSkybox.init();
    	modelWater.init();
    	
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
        
        modelSkybox.render(cam, environment);
        modelWater.render(cam, environment);

        //Load all assets before creating new objects
        if (assetsManager.getQueuedAssets() > 0) {// && createGameObjectArray.size > 0
            assetsManager.finishLoading();
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
