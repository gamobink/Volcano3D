package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class VStage2D {

	SceneManager sceneManager = null;
	
	protected Stage loaderStage = null;	
	protected Stage mainStage = null;
		
	VStage2D(SceneManager s){
		sceneManager = s;
		loaderStage = new Stage();
		mainStage = new Stage();
		
		//Create loader stage		
        Table tableLoader = new Table();
        tableLoader.setFillParent(true);
        tableLoader.background(
        		new TextureRegionDrawable(
        				new TextureRegion(
        						new Texture(Gdx.files.internal("ldm_logo_loder.png")))));		
        loaderStage.addActor(tableLoader);

        
		
	}
	
    public void renderLoader(){
        loaderStage.draw();
    }	
	
	
}
