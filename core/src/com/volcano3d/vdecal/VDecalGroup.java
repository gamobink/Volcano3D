package com.volcano3d.vdecal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.vcore.VMain;

public class VDecalGroup {

	private VMain volcano;

	public Array<VDecal> decals = new Array<VDecal>();
	
	private DecalBatch decalBatch = null;
	
	public float	fadeAlpha = 1.0f;
	public boolean 	fadeOffAlpha = false;
	public boolean 	fadeOnAlpha = false;
	
    public VDecalGroup(VMain v){
    	volcano = v;
    }		
	public void init(){
		decalBatch = new DecalBatch(new CameraGroupStrategy(volcano.camera.get()));
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
    	if(fadeAlpha > 0){
	    	Gdx.gl.glDisable(GL20.GL_DEPTH_TEST); 
	    	for(int i=0;i<decals.size;i++){
		    	VDecal d = decals.get(i);
		    	if(d!=null){
		    		d.decal.lookAt(volcano.camera.get().position, volcano.camera.get().up);
		    		d.decal.setColor(1, 1, 1, fadeAlpha);
		    		decalBatch.add(d.decal);
		    	}
	    	}
	    	decalBatch.flush();
    	}
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
