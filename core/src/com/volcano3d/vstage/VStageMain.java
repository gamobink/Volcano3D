package com.volcano3d.vstage;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcore.VMain;
import com.volcano3d.vcore.VStaticAssets;

public class VStageMain extends InputListener {

	public VMain volcano = null;
	
	protected Stage loaderStage = null;	
	public Stage mainStage = null;
	
	private ImageButton buttonReturn = null;
	private ImageButton buttonNavigation = null;
	private ImageButton buttonInfo = null;		
	protected ImageButton buttonCloseNavi = null;

	private Vector2	buttonCenterPos = new Vector2();
	private Vector2	buttonNavigationRightPos = new Vector2();
	private Vector2	buttonInfoLeftPos = new Vector2();	
	
	public ShapeRenderer shapeRenderer = new ShapeRenderer();

	public VActionFollowPath pathAction1 = null;	
	public VActionFollowPath pathAction2 = new VActionFollowPath();	
	public VActionFollowPath pathAction3 = new VActionFollowPath();	
	
	protected Map<String, Group> viewButtonsMap = new HashMap<String, Group>(); 
	
	public VStageMainInfoWindow infoStage;
	public VStageMainIntro introStage;
    
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
        
        introStage = new VStageMainIntro(this);
        
        infoStage = new VStageMainInfoWindow(this);
        
        buttonReturn = new ImageButton(VStaticAssets.GUI.buttonsSkin.getDrawable("button-return-on"), VStaticAssets.GUI.buttonsSkin.getDrawable("button-return"));
        buttonReturn.setName("BUTTON_MAIN");
        buttonReturn.addListener(this);        
		
        buttonNavigation = new ImageButton(VStaticAssets.GUI.buttonsSkin.getDrawable("button-main-on"), VStaticAssets.GUI.buttonsSkin.getDrawable("button-main"));
        buttonNavigation.setName("BUTTON_NAVI");
        buttonNavigation.addListener(this); 
        
        buttonInfo = new ImageButton(VStaticAssets.GUI.buttonsSkin.getDrawable("button-info-on"), VStaticAssets.GUI.buttonsSkin.getDrawable("button-info"));
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
        
        float buttonIconSize = swidth * 0.15f;
        
        buttonCloseNavi = new ImageButton(VStaticAssets.GUI.buttonsSkin.getDrawable("button-close-on"), VStaticAssets.GUI.buttonsSkin.getDrawable("button-close"));
        buttonCloseNavi.setName("BUTTON_CLOSENAVI");
        buttonCloseNavi.setSize(buttonSize, buttonSize);
        buttonCloseNavi.addListener(this);
        buttonCloseNavi.setPosition(200, 300); 
        
        mainStage.addActor(buttonCloseNavi);
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = VStaticAssets.Fonts.calibri18Font;
        
        String[] buttonLabelsTexts = {
        		"Vulkāns",
        		"Zemūdens",
        		"Pludmale",
        		"Kalns",
        		"Nogruvums",
        		"Lietus"
        };
        
        for(int i=1; i<=6; i++){
        	Group g = new Group();
            ImageButton imgb = new ImageButton(VStaticAssets.GUI.buttonsSkin.getDrawable("button-generic-on"), VStaticAssets.GUI.buttonsSkin.getDrawable("button-generic"));
            imgb.setSize(buttonIconSize, buttonIconSize);
        	g.setVisible(false);
        	
        	Label l = new Label(buttonLabelsTexts[i-1], labelStyle);
        	l.setPosition(0, -10);
        	l.setAlignment(Align.center);
        	l.setWidth(buttonIconSize);

            g.setName("B_VIEW"+i);
            g.addListener(this);        		
        	
            g.addActor(imgb);
            g.addActor(l);
        	viewButtonsMap.put("B_VIEW"+i, g);
        	mainStage.addActor(g);
        }
        
        viewButtonsMap.get("B_VIEW1").setPosition(100, 200);
        viewButtonsMap.get("B_VIEW2").setPosition(60, 300);
        viewButtonsMap.get("B_VIEW3").setPosition(100, 400);
        viewButtonsMap.get("B_VIEW4").setPosition(300, 400);
        viewButtonsMap.get("B_VIEW5").setPosition(340, 300);
        viewButtonsMap.get("B_VIEW6").setPosition(300, 200);        
        
		float sWidth = mainStage.getWidth();
		float sHeight = mainStage.getHeight();	
		
		pathAction1 = new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView1ButtonMoveIn, sWidth, sHeight);
        
        transitionCloseNavigationTable();
        
        //!!!!!!!!!!!!! DEBUG 
        //mainStage.setDebugUnderMouse(true);
	}
	
    public void renderLoader(){
        loaderStage.draw();
    }	
	public void renderMainStage(){
		
		mainStage.act(Gdx.graphics.getDeltaTime());
		mainStage.draw();
		/*
		shapeRenderer.setProjectionMatrix(mainStage.getCamera().combined);
		pathAction1.drawDebug(shapeRenderer, 0.5f, 0.5f, 0.5f, 0.5f);
		pathAction2.drawDebug(shapeRenderer, 0.5f, 0.5f, 0.5f, 0.5f);
		pathAction3.drawDebug(shapeRenderer, 0.5f, 0.5f, 0.5f, 0.5f);
		*/
	}
	
	public void transitionToStaticView(float delay){
		
		transitionCloseNavigationTable();
		
		buttonReturn.clearActions();
		buttonInfo.clearActions();
		buttonNavigation.clearActions();
		
		buttonReturn.addAction(Actions.sequence(Actions.touchable(Touchable.disabled),
												Actions.delay(delay),
												Actions.show(), 
												Actions.fadeIn(0.1f),
												Actions.moveTo(this.buttonNavigationRightPos.x, this.buttonNavigationRightPos.y, 0.5f, Interpolation.swingIn),
												Actions.touchable(Touchable.enabled)));
		
		buttonInfo.addAction(Actions.sequence(Actions.touchable(Touchable.disabled),
												Actions.delay(delay),
												Actions.show(), 
												Actions.fadeIn(0.1f),
												Actions.moveTo(this.buttonInfoLeftPos.x, this.buttonInfoLeftPos.y, 0.5f, Interpolation.swingIn),
												Actions.touchable(Touchable.enabled)));

		buttonNavigation.addAction(Actions.sequence(Actions.touchable(Touchable.disabled),
													Actions.delay(delay),
													Actions.delay(0.4f),
													Actions.fadeOut(0.1f),
													Actions.hide()
													));													
	}
	public void transitionToMainView(){
		
		transitionCloseNavigationTable();
		
		buttonReturn.clearActions();
		buttonInfo.clearActions();		
		buttonNavigation.clearActions();
		
		buttonReturn.addAction(Actions.sequence(Actions.touchable(Touchable.disabled),
												Actions.show(), 
												Actions.moveTo(this.buttonCenterPos.x, this.buttonCenterPos.y, 0.5f, Interpolation.swingIn),
												Actions.fadeOut(0.5f), 
												Actions.hide()
											));

		buttonInfo.addAction(Actions.sequence(Actions.touchable(Touchable.disabled),
												Actions.show(), 
												Actions.moveTo(this.buttonCenterPos.x, this.buttonCenterPos.y, 0.5f, Interpolation.swingIn),
												Actions.fadeOut(0.5f), 
												Actions.hide()
											));		
		
		buttonNavigation.addAction(Actions.sequence(Actions.touchable(Touchable.disabled),
													Actions.show(), 
													Actions.fadeIn(0.5f),
													Actions.touchable(Touchable.enabled)));
	}	
	
	public void transitionOpenNavigationTable(){
		
		for(Map.Entry<String, Group> m:viewButtonsMap.entrySet()){  
			   Group g = m.getValue();
			   g.setVisible(true);
			   g.addAction(Actions.fadeIn(0.5f));
		} 
		buttonCloseNavi.setVisible(true);
		buttonCloseNavi.addAction(Actions.fadeIn(0.5f));
		
		/*
		mainNavigationTable.clearActions();
		
		mainNavigationTable.addAction(Actions.sequence(Actions.show(), 
										Actions.moveTo(0, 0, 0.5f, Interpolation.circle)
									));
		
		pathAction1.setDuration(0.8f);
		pathAction1.setReverse(false);
		pathAction1.setInterpolation(Interpolation.circleOut);
		buttonCloseNavi.addAction(Actions.sequence(Actions.fadeIn(0.5f),
													Actions.show(),
													pathAction1,
													Actions.touchable(Touchable.enabled)
													));
		
		
		*/
	}
	
	public void transitionCloseNavigationTable(){
		
		for(Map.Entry<String, Group> m:viewButtonsMap.entrySet()){  
			   Group g = m.getValue();   
			   g.addAction(Actions.fadeOut(0.5f));
		} 
		buttonCloseNavi.addAction(Actions.fadeOut(0.5f));
		/*
		mainNavigationTable.clearActions();
		
		mainNavigationTable.addAction(Actions.sequence(Actions.show(),
				Actions.moveTo(0, -700, 0.5f, Interpolation.circle),
				Actions.hide()
			));
		
		pathAction1.setDuration(0.7f);
		pathAction1.setReverse(true);
		pathAction1.setInterpolation(Interpolation.circleIn);
		buttonCloseNavi.addAction(Actions.sequence(Actions.touchable(Touchable.disabled),
													Actions.show(),
													pathAction1,
													Actions.fadeOut(0.5f)
													));
													*/
	}	
	
    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {
    	Actor a = e.getListenerActor();
    	if(a.getName().compareTo("BUTTON_MAIN") == 0){
        	volcano.camera.setCameraState(VCamera.States.MAIN);
        	transitionToMainView();
        }
        if(a.getName().compareTo("B_VIEW1") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_1);
        	transitionCloseNavigationTable();
        	transitionToStaticView(0.8f);
        }  
        if(a.getName().compareTo("B_VIEW2") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_3);
        	transitionCloseNavigationTable();
        	transitionToStaticView(0.8f);
        }  
        if(a.getName().compareTo("B_VIEW3") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_4);
        	transitionCloseNavigationTable();	
        	transitionToStaticView(0.8f);
        }          
        if(a.getName().compareTo("BUTTON_NAVI") == 0){
        	transitionOpenNavigationTable();
        }        
        if(a.getName().compareTo("BUTTON_CLOSENAVI") == 0){
        	transitionCloseNavigationTable();
        }                
    }	
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {
    	volcano.setUserActionActive();
        return true;   //return true stops event propagation
    }	
}
