package com.volcano3d.vstage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcore.VMain;

public class VStageMain extends InputListener {

	VMain volcano = null;
	
	protected Stage loaderStage = null;	
	public Stage mainStage = null;
	
	protected BitmapFont font = null;

	private TextButton buttonReturn = null;
	private TextButton buttonNavigation = null;
	private TextButton buttonInfo = null;	
	private Vector2	buttonCenterPos = new Vector2();
	private Vector2	buttonNavigationRightPos = new Vector2();
	private Vector2	buttonInfoLeftPos = new Vector2();	
	protected Table mainNavigationTable	= null;	
	
	public ShapeRenderer shapeRenderer = new ShapeRenderer();

	public VActionFollowPath pathAction1 = new VActionFollowPath();	
	public VActionFollowPath pathAction2 = new VActionFollowPath();	
	public VActionFollowPath pathAction3 = new VActionFollowPath();	
	
//	private ImageButton buttonReturn = new ImageButton(buttonSkin.getDrawable("buttonon"), buttonSkin.getDrawable("buttonoff"));
	
	public VStageMain(VMain s){
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
        
        buttonReturn = new TextButton("Return", style);
        buttonReturn.setName("BUTTON_MAIN");
        buttonReturn.addListener(this);        
		
        buttonNavigation = new TextButton("Navi", style);
        buttonNavigation.setName("BUTTON_NAVI");
        buttonNavigation.addListener(this); 
        
        buttonInfo = new TextButton("Info", style);
        buttonInfo.setName("BUTTON_INFO");
        buttonInfo.addListener(this);         
        
        float swidth = mainStage.getWidth();
        float buttonSize = swidth * 0.15f;
        float buttonOffset = swidth * 0.02f;        
        
        buttonCenterPos.set((swidth/2)-(buttonSize/2), buttonOffset);
        buttonNavigationRightPos.set(swidth - buttonSize - buttonOffset, buttonOffset);
        buttonInfoLeftPos.set(buttonOffset, buttonOffset);
        
        buttonReturn.setSize(buttonSize, buttonSize);
        buttonReturn.setPosition(buttonCenterPos.x, buttonCenterPos.y);
        buttonReturn.setVisible(false);
        
        buttonInfo.setSize(buttonSize, buttonSize);
        buttonInfo.setPosition(buttonCenterPos.x, buttonCenterPos.y);
        buttonInfo.setVisible(false);        
        
        buttonNavigation.setSize(buttonSize, buttonSize);
        buttonNavigation.setPosition(buttonCenterPos.x, buttonCenterPos.y);
        
        mainStage.addActor(buttonInfo);
        mainStage.addActor(buttonReturn);
        mainStage.addActor(buttonNavigation);
        
        //Navigation menu table
        mainNavigationTable = new Table();
        mainNavigationTable.setFillParent(true);
        mainStage.addActor(mainNavigationTable);
        
        //TODO: a.setOrigin(a.getWidth()/2, a.getHeight()/2); 
        //center of button
        
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
        
        TextButton buttonStaticView4 = new TextButton("TODO:Hill", style);
        buttonStaticView4.setName("BUTTON_VIEW4");
        buttonStaticView4.addListener(this);         

        TextButton buttonStaticView5 = new TextButton("TODO:Cloud", style);
        buttonStaticView5.setName("BUTTON_VIEW5");
        buttonStaticView5.addListener(this);                 
        
        TextButton buttonStaticView6 = new TextButton("TODO:Rocks", style);
        buttonStaticView6.setName("BUTTON_VIEW6");
        buttonStaticView6.addListener(this);                         
        
        mainNavigationTable.add(buttonCloseNavi)
		        .width(Value.percentWidth(0.1f, mainNavigationTable))
		        .height(Value.percentHeight(0.05f, mainNavigationTable));
        mainNavigationTable.row();        
        mainNavigationTable.add(buttonStaticView1)
			    .width(Value.percentWidth(0.3f, mainNavigationTable))
			    .height(Value.percentHeight(0.05f, mainNavigationTable));
        mainNavigationTable.row();
        mainNavigationTable.add(buttonStaticView2)
			    .width(Value.percentWidth(0.3f, mainNavigationTable))
			    .height(Value.percentHeight(0.05f, mainNavigationTable));
        mainNavigationTable.row();        
        mainNavigationTable.add(buttonStaticView3)
			    .width(Value.percentWidth(0.3f, mainNavigationTable))
			    .height(Value.percentHeight(0.05f, mainNavigationTable));
        mainNavigationTable.row();
        mainNavigationTable.add(buttonStaticView4)
			    .width(Value.percentWidth(0.3f, mainNavigationTable))
			    .height(Value.percentHeight(0.05f, mainNavigationTable));
        mainNavigationTable.row();        
        mainNavigationTable.add(buttonStaticView5)
			    .width(Value.percentWidth(0.3f, mainNavigationTable))
			    .height(Value.percentHeight(0.05f, mainNavigationTable));
        mainNavigationTable.row();        
        mainNavigationTable.add(buttonStaticView6)
			    .width(Value.percentWidth(0.3f, mainNavigationTable))
			    .height(Value.percentHeight(0.05f, mainNavigationTable));
        mainNavigationTable.row();
        
        mainNavigationTable.setVisible(false);	
        
		float sWidth = mainStage.getWidth();
		float sHeight = mainStage.getHeight();		
        pathAction1.addPoint(0.489796f * sWidth, 0.012500f * sHeight);
        pathAction1.addPoint(0.497959f * sWidth, 0.226250f * sHeight);
        pathAction1.addPoint(0.322449f * sWidth, 0.215000f * sHeight);
        pathAction1.addPoint(0.053061f * sWidth, 0.261250f * sHeight);
        pathAction1.addPoint(0.038775f * sWidth, 0.427500f * sHeight);
        pathAction1.addPoint(0.116326f * sWidth, 0.700000f * sHeight);
        pathAction1.addPoint(0.606122f * sWidth, 0.758750f * sHeight);
        pathAction1.addPoint(0.914286f * sWidth, 0.506250f * sHeight);
        pathAction1.addPoint(0.781633f * sWidth, 0.350000f * sHeight);
	}
	
    public void renderLoader(){
        loaderStage.draw();
    }	
	public void renderMainStage(){
		
		mainStage.act(Gdx.graphics.getDeltaTime());
		mainStage.draw();
		
		shapeRenderer.setProjectionMatrix(mainStage.getCamera().combined);
		pathAction1.drawDebug(shapeRenderer, 0.5f, 0.5f, 0.5f, 0.5f);
		pathAction2.drawDebug(shapeRenderer, 0.5f, 0.5f, 0.5f, 0.5f);
		pathAction3.drawDebug(shapeRenderer, 0.5f, 0.5f, 0.5f, 0.5f);
	}
	
	public void transitionToStaticView(){
		
		//TODO: Actions.touchable(Touchable.disabled),
		
		buttonReturn.clearActions();
		buttonInfo.clearActions();
		buttonNavigation.clearActions();
		
		buttonReturn.addAction(Actions.sequence(Actions.show(), 
												Actions.fadeIn(0.1f),
												Actions.moveTo(this.buttonNavigationRightPos.x, this.buttonNavigationRightPos.y, 0.5f, Interpolation.swingIn)));
		
		buttonInfo.addAction(Actions.sequence(Actions.show(), 
												Actions.fadeIn(0.1f),
												Actions.moveTo(this.buttonInfoLeftPos.x, this.buttonInfoLeftPos.y, 0.5f, Interpolation.swingIn)));

		buttonNavigation.addAction(Actions.sequence(Actions.delay(0.4f),
													Actions.fadeOut(0.1f),
													Actions.hide()
													));													
	}
	public void transitionToMainView(){
		
		buttonReturn.clearActions();
		buttonInfo.clearActions();		
		buttonNavigation.clearActions();
		
		buttonReturn.addAction(Actions.sequence(Actions.show(), 
												Actions.moveTo(this.buttonCenterPos.x, this.buttonCenterPos.y, 0.5f, Interpolation.swingIn),
												Actions.fadeOut(0.5f), 
												Actions.hide()
											));

		buttonInfo.addAction(Actions.sequence(Actions.show(), 
												Actions.moveTo(this.buttonCenterPos.x, this.buttonCenterPos.y, 0.5f, Interpolation.swingIn),
												Actions.fadeOut(0.5f), 
												Actions.hide()
											));		
		
		buttonNavigation.addAction(Actions.sequence(Actions.show(), 
													Actions.fadeIn(0.5f)));
	}	
	
	public void transitionOpenNavigationTable(){
		
		mainNavigationTable.clearActions();
		
		mainNavigationTable.addAction(Actions.sequence(Actions.show(), 
										Actions.moveTo(0, 0, 0.5f, Interpolation.circle)
									));
	}
	
	public void transitionCloseNavigationTable(){
		
		mainNavigationTable.clearActions();
		
		mainNavigationTable.addAction(Actions.sequence(Actions.show(), 
				Actions.moveTo(0, -700, 0.5f, Interpolation.circle),
				Actions.hide()
			));
	}	
	
    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {
        Actor a = e.getListenerActor();
        if(a.getName() == "BUTTON_MAIN"){
        	volcano.camera.setCameraState(VCamera.States.MAIN);
        	transitionToMainView();
        }
        if(a.getName() == "BUTTON_VIEW1"){
        	volcano.camera.setCameraState(VCamera.States.STATIC_1);
        	transitionCloseNavigationTable();
        	transitionToStaticView();
        }  
        if(a.getName() == "BUTTON_VIEW2"){
        	volcano.camera.setCameraState(VCamera.States.STATIC_3);
        	transitionCloseNavigationTable();
        	transitionToStaticView();
        }  
        if(a.getName() == "BUTTON_VIEW3"){
        	volcano.camera.setCameraState(VCamera.States.STATIC_4);
        	transitionCloseNavigationTable();	
        	transitionToStaticView();
        }          
        if(a.getName() == "BUTTON_NAVI"){
//        	mainNavigationTable.setVisible(true);
        	transitionOpenNavigationTable();
        }        
        if(a.getName() == "BUTTON_CLOSENAVI"){
//        	mainNavigationTable.setVisible(false);	
        	transitionCloseNavigationTable();
        }                
    }	
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {

        return true;   //return true stops event propagation
    }	
}
