package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class VDecal {

	private SceneManager sceneManager;
	
	private DecalBatch decalBatch = null;
	private Decal decal = null;
	private Vector3	position = new Vector3(0,0,0);
	private Vector2	size = new Vector2(0,0);
	private String imageName = "";
	
    public VDecal(SceneManager o, String image, Vector3 pos, Vector2 sizei){
    	sceneManager = o;
    	position = pos;
    	imageName = image;
    	size = sizei;
    }
	
    public void init(){
    	
    	TextureRegion texture = new TextureRegion(new Texture(Gdx.files.internal(imageName)));
    	
    	texture.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
    	
    	decalBatch = new DecalBatch(new CameraGroupStrategy(sceneManager.camera.get()));
    	
    	decal = Decal.newDecal(size.x, size.y, texture);

    	decal.setPosition(position);
    }
    
    public void render(){
    	Gdx.gl.glDisable(GL20.GL_DEPTH_TEST); 
    	decal.lookAt(sceneManager.camera.get().position, sceneManager.camera.get().up);
    	decalBatch.add(decal);
    	decalBatch.flush();
    }
    
}
