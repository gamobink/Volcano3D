package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class VStage2D extends InputListener {

	SceneManager sceneManager = null;
	
	protected Stage loaderStage = null;	
	protected Stage mainStage = null;
	
	protected BitmapFont font = null;

	private TextButton buttonMain = null;
	
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

        font = new BitmapFont();
        font.getData().setScale(1.5f, 1.5f);        
        
        TextureAtlas buttonsAtlas = new TextureAtlas("gui.pack");
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);
        
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.down = buttonSkin.getDrawable("buttonon");
        style.up = buttonSkin.getDrawable("buttonoff");
        style.font = font;
        
        buttonMain = new TextButton("Return", style);
        buttonMain.setName("BUTTON_MAIN");
        buttonMain.addListener(this);        
		
        Table table = new Table();
        table.setFillParent(true);
        mainStage.addActor(table);

        //table.setDebug(true);
        table.add(buttonMain)
                .width(Value.percentWidth(0.2f, table))
                .height(Value.percentHeight(0.05f, table))
                .top()
                .left();
        table.add().expand();
	}
	
    public void renderLoader(){
        loaderStage.draw();
    }	
	public void renderMainStage(){
		mainStage.draw();
	}
	
    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {
        Actor a = e.getListenerActor();
        if(a.getName() == "BUTTON_MAIN"){
        	sceneManager.camera.setCameraMode(0);	
        }
    }	
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {

        return true;   //return true stops event propagation
    }	
}
