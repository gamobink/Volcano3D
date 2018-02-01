package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class VDecal {

	private SceneManager sceneManager;
	
	private DecalBatch decalBatch = null;
	private Decal decal = null;
	public Vector3	position = new Vector3(0,0,0);
	public Vector2	size = new Vector2(0,0);
	private String imageName = "";
	
    public VDecal(SceneManager o, String image, Vector3 pos, Vector2 sizei){
    	sceneManager = o;
    	position = pos;
    	imageName = image;
    	size = sizei;
    }
	
    public void init(){
//    	Gdx.gl.glEnable(GL30.GL_GENERATE_MIPMAP);, Pixmap.Format.RGB565, true
    	TextureRegion texture = new TextureRegion(new Texture(Gdx.files.internal(imageName)));
    	
    	texture.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
    	
    	decalBatch = new DecalBatch(new CameraGroupStrategy(sceneManager.camera.get()));
    	
    	decal = Decal.newDecal(size.x, size.y, texture, true);

    	decal.setPosition(position);
    }
    
    public void render(){
    	Gdx.gl.glDisable(GL20.GL_DEPTH_TEST); 
    	decal.lookAt(sceneManager.camera.get().position, sceneManager.camera.get().up);
    	decalBatch.add(decal);
    	decalBatch.flush();
    }
    public boolean Intersect(Ray ray){
    	Vector3 decalSize = new Vector3(size.x, size.y, size.x); 
    	if(ray!= null && Intersector.intersectRayBoundsFast(ray, position, decalSize)){
    		return true;
    	}
    	return false;
    }
}
