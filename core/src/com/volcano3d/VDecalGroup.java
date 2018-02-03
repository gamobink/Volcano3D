package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

public class VDecalGroup {

	private SceneManager sceneManager;

	public Array<VDecal> decals = new Array<VDecal>();
	
	private DecalBatch decalBatch = null;
	
	public float	fadeAlpha = 1.0f;
	public boolean 	fadeOffAlpha = false;
	public boolean 	fadeOnAlpha = false;
	
    public VDecalGroup(SceneManager o){
    	sceneManager = o;
    }		
	public void init(){
		decalBatch = new DecalBatch(new CameraGroupStrategy(sceneManager.camera.get()));
		for(int i=0;i<decals.size;i++){
			decals.get(i).init();
		}
	}
	public void addDecal(VDecal d){
		decals.add(d);
	}	
	public void setFadeOff(){
		fadeOnAlpha = false;
		fadeOffAlpha = true;		
	}
	public void setFadeOn(){
		fadeOnAlpha = true;
		fadeOffAlpha = false;				
	}	
    public void render(){
    	float dt = Gdx.graphics.getDeltaTime();
    	if(fadeOffAlpha){
    		fadeAlpha -= dt;
    		if(fadeAlpha <= 0)fadeAlpha = 0;
    	}else if(fadeOnAlpha){
    		fadeAlpha += dt;    		
    		if(fadeAlpha >= 1)fadeAlpha = 1;
    	}
    	
    	Gdx.gl.glDisable(GL20.GL_DEPTH_TEST); 
    	for(int i=0;i<decals.size;i++){
	    	VDecal d = decals.get(i);
	    	if(d!=null){
	    		d.decal.lookAt(sceneManager.camera.get().position, sceneManager.camera.get().up);
	    		d.decal.setColor(1, 1, 1, fadeAlpha);
	    		decalBatch.add(d.decal);
	    	}
    	}
    	decalBatch.flush();
    }
	public int Intersect(Ray r){
		for(int i=0;i<decals.size;i++){
			if(decals.get(i).Intersect(r)){
				return i;
			}
		}		
		return -1;
	}
}
