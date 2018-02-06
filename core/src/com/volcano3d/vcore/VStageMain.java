package com.volcano3d.vcore;

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
import com.volcano3d.vcamera.VCamera;

public class VStageMain extends InputListener {

	VMain volcano = null;
	
	protected Stage loaderStage = null;	
	public Stage mainStage = null;
	
	protected BitmapFont font = null;

	private TextButton buttonMain = null;
	private TextButton buttonNavigation = null;
	protected Table mainNavigationTable	= null;	
	
	VStageMain(VMain s){
		volcano = s;
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
		
        buttonNavigation = new TextButton("Navi", style);
        buttonNavigation.setName("BUTTON_NAVI");
        buttonNavigation.addListener(this); 
        
        Table table = new Table();
        table.setFillParent(true);
        mainStage.addActor(table);

        //table.setDebug(true);
        table.add(buttonMain)
                .width(Value.percentWidth(0.2f, table))
                .height(Value.percentHeight(0.05f, table))
                .top()
                .left();
        table.add().top().expandX();
        table.add(buttonNavigation)
			    .width(Value.percentWidth(0.2f, table))
			    .height(Value.percentHeight(0.05f, table))
			    .top()
			    .right();
        table.row();
        table.add().expand();        
        
        //Navigation menu table
        mainNavigationTable = new Table();
        mainNavigationTable.setFillParent(true);
        mainStage.addActor(mainNavigationTable);

        TextButton buttonCloseNavi = new TextButton("X", style);
        buttonCloseNavi.setName("BUTTON_CLOSENAVI");
        buttonCloseNavi.addListener(this);
        
        TextButton buttonStaticView1 = new TextButton("Volcano", style);
        buttonStaticView1.setName("BUTTON_VIEW1");
        buttonStaticView1.addListener(this);         

        TextButton buttonStaticView2 = new TextButton("Sea", style);
        buttonStaticView2.setName("BUTTON_VIEW2");
        buttonStaticView2.addListener(this); 
        
        TextButton buttonStaticView3 = new TextButton("Beach", style);
        buttonStaticView3.setName("BUTTON_VIEW3");
        buttonStaticView3.addListener(this);         
        
        mainNavigationTable.add(buttonCloseNavi)
		        .width(Value.percentWidth(0.1f, table))
		        .height(Value.percentHeight(0.05f, table));
        mainNavigationTable.row();        
        mainNavigationTable.add(buttonStaticView1)
			    .width(Value.percentWidth(0.3f, table))
			    .height(Value.percentHeight(0.05f, table));
        mainNavigationTable.row();
        mainNavigationTable.add(buttonStaticView2)
			    .width(Value.percentWidth(0.3f, table))
			    .height(Value.percentHeight(0.05f, table));
        mainNavigationTable.row();        
        mainNavigationTable.add(buttonStaticView3)
			    .width(Value.percentWidth(0.3f, table))
			    .height(Value.percentHeight(0.05f, table));
        mainNavigationTable.row();
        
        mainNavigationTable.setVisible(false);	
        
        
	}
	
    public void renderLoader(){
        loaderStage.draw();
    }	
	public void renderMainStage(){
		if(volcano.camera.getState() == VCamera.States.MAIN){
			buttonNavigation.setVisible(true);
			buttonMain.setVisible(false);
		}else{
			buttonNavigation.setVisible(false);
			buttonMain.setVisible(true);
		}
		mainStage.draw();
	}
	
    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {
        Actor a = e.getListenerActor();
        if(a.getName() == "BUTTON_MAIN"){
        	volcano.camera.setCameraState(VCamera.States.MAIN);
        }
        if(a.getName() == "BUTTON_VIEW1"){
        	volcano.camera.setCameraState(VCamera.States.STATIC_1);
        	mainNavigationTable.setVisible(false);
        }  
        if(a.getName() == "BUTTON_VIEW2"){
        	volcano.camera.setCameraState(VCamera.States.STATIC_3);
        	mainNavigationTable.setVisible(false);	
        }  
        if(a.getName() == "BUTTON_VIEW3"){
        	volcano.camera.setCameraState(VCamera.States.STATIC_4);
        	mainNavigationTable.setVisible(false);	
        }          
        if(a.getName() == "BUTTON_NAVI"){
        	mainNavigationTable.setVisible(true);	
        }        
        if(a.getName() == "BUTTON_CLOSENAVI"){
        	mainNavigationTable.setVisible(false);	
        }                
    }	
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {

        return true;   //return true stops event propagation
    }	
}
