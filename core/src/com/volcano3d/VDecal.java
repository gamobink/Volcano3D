package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class VDecal {
	
	public Decal decal = null;
	public Vector3	position = new Vector3(0,0,0);
	public Vector2	size = new Vector2(0,0);
	private String imageName = "";
	
    public VDecal(String image, Vector3 pos, Vector2 sizei){
    	position = pos;
    	imageName = image;
    	size = sizei;
    }
	
    public void init(){

    	TextureRegion texture = new TextureRegion(new Texture(Gdx.files.internal(imageName)));
    	
    	texture.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);    	

    	decal = Decal.newDecal(size.x, size.y, texture, true);
    	decal.setPosition(position);
    }
    
    public boolean Intersect(Ray ray){
    	Vector3 decalSize = new Vector3(size.x, size.y, size.x); 
    	if(ray!= null && Intersector.intersectRayBoundsFast(ray, position, decalSize)){
    		return true;
    	}
    	return false;
    }
}
