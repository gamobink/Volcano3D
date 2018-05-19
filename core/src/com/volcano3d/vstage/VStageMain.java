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
import com.badlogic.gdx.utils.Array;
import com.volcano3d.utility.VFilteredTexture;
import com.volcano3d.utility.VCommon;
import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcamerapresets.VCameraPresetStatic;
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
	
	public Array<VActionFollowPath> pathActionsButtonsIn = new Array<VActionFollowPath>();
	public Array<VActionFollowPath> pathActionsButtonsOut = new Array<VActionFollowPath>();
	
	protected Map<String, Group> viewButtonsMap = new HashMap<String, Group>();
	
	public VStageMainIntro introStage;
    
	public boolean isNavigationOpen = false;
	
	protected Map<String, VStageInfoWindow> infoWindowMap = new HashMap<String, VStageInfoWindow>();
	
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
        
        createInfoWindows();       
        
        buttonReturn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/button-return.png"))), 
				new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/button-return-on.png"))));        
        buttonReturn.setName("BUTTON_MAIN");
        buttonReturn.addListener(this);        
		
        buttonNavigation = new ImageButton(new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/button-main.png"))), 
        									new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/button-main-on.png"))));
        buttonNavigation.setName("BUTTON_NAVI");
        buttonNavigation.addListener(this); 
        
        buttonInfo = new ImageButton(new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/button-info.png"))), 
				new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/button-info-on.png"))));
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
        
        buttonCloseNavi = new ImageButton(new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/button-close.png"))), 
				new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/button-close-on.png"))));        
        buttonCloseNavi.setName("BUTTON_CLOSENAVI");
        buttonCloseNavi.setSize(buttonSize, buttonSize);
        buttonCloseNavi.addListener(this);
        buttonCloseNavi.setPosition((swidth * 0.5f) - (buttonIconSize * 0.5f), swidth * 0.65f); 
        
        mainStage.addActor(buttonCloseNavi);
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = VStaticAssets.Fonts.menuIconsLabel;
        
        String[] buttonLabelsImages = {      		 
    	        "magmatic",
    	        "pegmatite",
    	        "hydrothermal",
    	        "pneymatolitic",
    	        "metamorphic",
    	        "chemical",
    	        "organic",
    	        "sediment"	        		
        };        
        
        for(int i=1; i<=8; i++){
        	Group g = new Group();

        	ImageButton imgb = new ImageButton(new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/"+buttonLabelsImages[i-1]+".png"))), 
    									new TextureRegionDrawable(new TextureRegion(new VFilteredTexture("icons/"+buttonLabelsImages[i-1]+"_on.png"))));        

        	imgb.setSize(buttonIconSize, buttonIconSize);
        	g.setVisible(false);
        	
        	Label l = new Label(VStaticAssets.Text.buttonLabelsTexts[i-1], labelStyle);
        	l.setPosition(0, -45);
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
        viewButtonsMap.get("B_VIEW7").setPosition(200, 430);
        viewButtonsMap.get("B_VIEW8").setPosition(200, 170);
        
		float sWidth = mainStage.getWidth();
		float sHeight = mainStage.getHeight();	
		
		pathActionsButtonsIn.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView1ButtonMoveIn, sWidth, sHeight));
		pathActionsButtonsIn.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView2ButtonMoveIn, sWidth, sHeight));
		pathActionsButtonsIn.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView3ButtonMoveIn, sWidth, sHeight));
		pathActionsButtonsIn.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView4ButtonMoveIn, sWidth, sHeight));
		pathActionsButtonsIn.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView5ButtonMoveIn, sWidth, sHeight));
		pathActionsButtonsIn.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView6ButtonMoveIn, sWidth, sHeight));
		pathActionsButtonsIn.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView7ButtonMoveIn, sWidth, sHeight));
		pathActionsButtonsIn.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView8ButtonMoveIn, sWidth, sHeight));

		pathActionsButtonsOut.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView1ButtonMoveOut, sWidth, sHeight));
		pathActionsButtonsOut.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView2ButtonMoveOut, sWidth, sHeight));
		pathActionsButtonsOut.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView3ButtonMoveOut, sWidth, sHeight));
		pathActionsButtonsOut.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView4ButtonMoveOut, sWidth, sHeight));
		pathActionsButtonsOut.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView5ButtonMoveOut, sWidth, sHeight));
		pathActionsButtonsOut.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView6ButtonMoveOut, sWidth, sHeight));
		pathActionsButtonsOut.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView7ButtonMoveOut, sWidth, sHeight));
		pathActionsButtonsOut.add(new VActionFollowPath(VStaticAssets.ActorActionsPaths.pathView8ButtonMoveOut, sWidth, sHeight));		
		
        transitionCloseNavigationTable();
        
        //!!!!!!!!!!!!! DEBUG
//        mainStage.setDebugUnderMouse(true);
//        mainStage.setDebugAll(true);
        
	}
	
	public void onLoad(){
		for(Map.Entry<String, VStageInfoWindow> m:infoWindowMap.entrySet()){  
			m.getValue().onLoad();
		}
	}
	
    public void renderLoader(){
        loaderStage.draw();
    }	
	public void renderMainStage(){
		
		mainStage.act(Gdx.graphics.getDeltaTime());
		mainStage.draw();
		
		VCommon.drawSystemStats();
		
		
		shapeRenderer.setProjectionMatrix(mainStage.getCamera().combined);

		for(int i=0; i<pathActionsButtonsIn.size; i++){
		//	pathActionsButtonsIn.get(i).drawDebug(shapeRenderer, 0.5f, 0.5f, 0.5f, 0.5f);
		}
		for(int i=0; i<pathActionsButtonsOut.size; i++){
		//	pathActionsButtonsOut.get(i).drawDebug(shapeRenderer, 0.5f, 0.5f, 0.5f, 0.5f);
		}		
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
		/*
		for(Map.Entry<String, Group> m:viewButtonsMap.entrySet()){  
			   Group g = m.getValue();
			   g.setVisible(true);
			   g.addAction(Actions.sequence(Actions.fadeIn(0.5f), Actions.touchable(Touchable.enabled)));
		} /**/
		buttonCloseNavi.setVisible(true);
		buttonCloseNavi.addAction(Actions.fadeIn(0.5f));
		
		isNavigationOpen = true;
		
		final float durations[] = {
				0.8f,	//1 
				0.7f,	//2 
				0.6f,	//3	
				0.5f,	//4 
				0.4f,	//5 
				0.3f,	//6 
				0.2f,	//7
				0.1f,	//8		
		};
		float startDelay = 0;
		for(int i=0; i<pathActionsButtonsIn.size; i++){
			VActionFollowPath p = pathActionsButtonsIn.get(i);
			p.setDuration(durations[i]);
			p.setReverse(false);
			//p.setInterpolation(Interpolation.circleOut);	
			Group g = this.viewButtonsMap.get("B_VIEW"+(i+1));
			if(g != null){
				g.setVisible(false);
				g.setPosition(buttonCenterPos.x, buttonCenterPos.y);
				g.setColor(1,1,1,0);
				g.clearActions();
				g.addAction(
						Actions.sequence(
							Actions.delay(startDelay),
							Actions.show(),						
							Actions.parallel(
								Actions.fadeIn(durations[i]),
								p
							),
							Actions.touchable(Touchable.enabled)
						));	
			}
			startDelay += 0.1f;
		}		

		/**/

	}
	
	public void transitionCloseNavigationTable(){
		
		isNavigationOpen = false;
		
//		for(Map.Entry<String, Group> m:viewButtonsMap.entrySet()){  
//			   Group g = m.getValue();   
//			   g.clearActions();
//			   g.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.fadeOut(0.5f)));
//		} 
		buttonCloseNavi.clearActions();
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
		final float durations[] = {
				0.6f,	//1 [6]
				0.7f,	//2 [7]
				0.8f,	//3	[8]
				0.1f,	//4 [1]
				0.2f,	//5 [2]
				0.3f,	//6 [3]
				0.4f,	//7 [4]
				0.5f,	//8	[5]			
		};
		
		for(int i=0; i<pathActionsButtonsOut.size; i++){
			VActionFollowPath p = pathActionsButtonsOut.get(i);
			p.setDuration(durations[i]);
			p.setReverse(true);
			//p.setInterpolation(Interpolation.circleOut);	
			Group g = this.viewButtonsMap.get("B_VIEW"+(i+1));
			if(g != null){
				//g.setColor(1,1,1,1);
				g.clearActions();
				g.addAction(
						Actions.sequence(
							Actions.touchable(Touchable.disabled),
							Actions.parallel(
								Actions.fadeOut(durations[i]),
								p
							),
							Actions.hide()
						));	
			}
		}		
	}	
	
    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {
    	Actor a = e.getListenerActor();
    	if(a.getName().compareTo("BUTTON_MAIN") == 0){
        	volcano.camera.setCameraState(VCamera.States.MAIN);
        	transitionToMainView();
        	hideAllInfoWindows();
        }
        if(a.getName().compareTo("B_VIEW1") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_1);
        	transitionCloseNavigationTable();
        	transitionToStaticView(0.8f);
        }  
        if(a.getName().compareTo("B_VIEW2") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_2);
        	transitionCloseNavigationTable();
        	transitionToStaticView(0.8f);
        }  
        if(a.getName().compareTo("B_VIEW3") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_3);
        	transitionCloseNavigationTable();	
        	transitionToStaticView(0.8f);
        } 
        if(a.getName().compareTo("B_VIEW4") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_4);
        	transitionCloseNavigationTable();	
        	transitionToStaticView(0.8f);
        } 
        if(a.getName().compareTo("B_VIEW5") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_5);
        	transitionCloseNavigationTable();	
        	transitionToStaticView(0.8f);
        } 
        if(a.getName().compareTo("B_VIEW6") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_6);
        	transitionCloseNavigationTable();	
        	transitionToStaticView(0.8f);
        }  
        if(a.getName().compareTo("B_VIEW7") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_7);
        	transitionCloseNavigationTable();	
        	transitionToStaticView(0.8f);
        }  
        if(a.getName().compareTo("B_VIEW8") == 0){
        	volcano.camera.setCameraState(VCamera.States.STATIC_8);
        	transitionCloseNavigationTable();	
        	transitionToStaticView(0.8f);
        }
        if(a.getName().compareTo("BUTTON_NAVI") == 0 && !isNavigationOpen){
        	volcano.camera.cameraPanEnabled = false;
        	transitionOpenNavigationTable();
        }else if(a.getName().compareTo("BUTTON_CLOSENAVI") == 0 
        		|| (isNavigationOpen && a.getName().compareTo("BUTTON_NAVI") == 0)){
        	volcano.camera.cameraPanEnabled = true;
        	transitionCloseNavigationTable();
        }
        if(a.getName().compareTo("BUTTON_INFO") == 0){
        	VStageInfoWindow infoWnd = null;
    		for(Map.Entry<String, VStageInfoWindow> m:infoWindowMap.entrySet()){  
    			VStageInfoWindow iw = m.getValue();
    			if(m.getValue().isVisible()){
    				infoWnd = iw;
    			}
    		}
    		if(infoWnd != null){
    			infoWnd.hide();
    			volcano.camera.cameraPanEnabled = true;
    			volcano.camera.gravityEnabled = true;
    		}else{
    	    	float minDst = 720;
    	    	int nearestObj = 0;
    	    	for(int i=0; i<VCameraPresetStatic.anglePositions.length; i++){
    	    		float ad = Math.abs(VCommon.angleCircleDistance(volcano.camera.anglePos.x, VCameraPresetStatic.anglePositions[i][0]));
    	    		float bd = Math.abs(VCommon.angleCircleDistance(volcano.camera.anglePos.y, VCameraPresetStatic.anglePositions[i][1]));
    	    		if((ad + bd) < minDst){
    	    			nearestObj = i;
    	    			minDst = (ad + bd);
    	    		}
    	    	}
    	    	switch(nearestObj){
    	    	case 0:
    	    		volcano.camera.setCameraState(VCamera.States.STATIC_1);
    	    		break;
    	    	case 1:
    	    		volcano.camera.setCameraState(VCamera.States.STATIC_2);
    	    		break;
    	    	case 2:
    	    		volcano.camera.setCameraState(VCamera.States.STATIC_3);
    	    		break;
    	    	case 3:
    	    		volcano.camera.setCameraState(VCamera.States.STATIC_4);
    	    		break; 
    	    	case 4:
    	    		volcano.camera.setCameraState(VCamera.States.STATIC_5);
    	    		break;
    	    	case 5:
    	    		volcano.camera.setCameraState(VCamera.States.STATIC_6);
    	    		break; 
    	    	case 6:
    	    		volcano.camera.setCameraState(VCamera.States.STATIC_7);
    	    		break; 
    	    	case 7:
    	    		volcano.camera.setCameraState(VCamera.States.STATIC_8);
    	    		break;     	    		
    	    	};
    		}
        }
        
		for(Map.Entry<String, VStageInfoWindow> m:infoWindowMap.entrySet()){  
			m.getValue().touch(a, x, y);
		}        
    }	
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {
    	volcano.setUserActionActive();
        return true;   //return true stops event propagation
    }	
    public void touchDragged(InputEvent e, float x, float y, int pointer){
    	Actor a = e.getListenerActor();
		for(Map.Entry<String, VStageInfoWindow> m:infoWindowMap.entrySet()){  
			m.getValue().drag(a, x, y);
		}
    }
    
    public void createInfoWindows(){

        VStageInfoWindow inf = new VStageInfoWindow(this);
        inf.setTitle(VStaticAssets.Text.magmaticProcessTitle);
        inf.setText(VStaticAssets.Text.methamorphicProcessText, 0.0625f);
        inf.setSliderPositionY(0.35f);
        inf.addImage("foto/magm4.jpg", VStaticAssets.Text.magm4);
        inf.addImage("foto/magm1.jpg", VStaticAssets.Text.magm1);
        inf.addImage("foto/magm2.jpg", VStaticAssets.Text.magm2);
        inf.addImage("foto/magm3.jpg", VStaticAssets.Text.magm3);        
        infoWindowMap.put("info1", inf);

        inf = new VStageInfoWindow(this);
        inf.setTitle(VStaticAssets.Text.pegmatiteProcessTitle);
        inf.setText(VStaticAssets.Text.pegmatiteProcessText, 0.0625f);
        inf.setSliderPositionY(0.4f);
        inf.addImage("foto/pegma1.jpg", VStaticAssets.Text.pegma1);
        inf.addImage("foto/pegma2.jpg", VStaticAssets.Text.pegma2);        
        inf.addImage("foto/pegma3.jpg", VStaticAssets.Text.pegma3);                
        infoWindowMap.put("info2", inf);
        
        inf = new VStageInfoWindow(this);
        inf.setTitle(VStaticAssets.Text.hydrothermalProcessTitle);
        inf.setText(VStaticAssets.Text.hydrothermalProcessText, 0.0625f);
        inf.setSliderPositionY(0.20f);
        inf.addImage("foto/hidro4.jpg", VStaticAssets.Text.hidro4); 
        inf.addImage("foto/hidro1.jpg", VStaticAssets.Text.hidro1);
        inf.addImage("foto/hidro2.jpg", VStaticAssets.Text.hidro2);        
        inf.addImage("foto/hidro3.jpg", VStaticAssets.Text.hidro3);                       
        infoWindowMap.put("info3", inf); 
        
        inf = new VStageInfoWindow(this);
        inf.setTitle(VStaticAssets.Text.pneymatholiticProcessTitle);
        inf.setText(VStaticAssets.Text.pneymatholiticProcessText, 0.0625f);
        inf.setSliderPositionY(0.20f);        
        inf.addImage("foto/pneim4.jpg", VStaticAssets.Text.pneim4);        
        inf.addImage("foto/pneim5.jpg", VStaticAssets.Text.pneim5);        
        inf.addImage("foto/pneim1.jpg", VStaticAssets.Text.pneim1);
        inf.addImage("foto/pneim2.jpg", VStaticAssets.Text.pneim2);        
        inf.addImage("foto/pneim3.jpg", VStaticAssets.Text.pneim3);
        infoWindowMap.put("info4", inf);         

        inf = new VStageInfoWindow(this);
        inf.setTitle(VStaticAssets.Text.methamorphicProcessTitle);
        inf.setText(VStaticAssets.Text.methamorphicProcessText, 0.0625f);
        inf.setSliderPositionY(0.35f);
        inf.addImage("foto/metam4.jpg", VStaticAssets.Text.metam4);        
        inf.addImage("foto/metam1.jpg", VStaticAssets.Text.metam1);
        inf.addImage("foto/metam2.jpg", VStaticAssets.Text.metam2);        
        inf.addImage("foto/metam3.jpg", VStaticAssets.Text.metam3);
        infoWindowMap.put("info5", inf);                 

        inf = new VStageInfoWindow(this);
        inf.setTitle(VStaticAssets.Text.chemicalProcessTitle);
        inf.setText(VStaticAssets.Text.chemicalProcessText, 0.0625f);
        inf.setSliderPositionY(0.45f);
        inf.addImage("foto/chem4.jpg", ""); 
        inf.addImage("foto/chem1.jpg", VStaticAssets.Text.chem1);
        inf.addImage("foto/chem2.jpg", VStaticAssets.Text.chem2);        
        inf.addImage("foto/chem3.jpg", VStaticAssets.Text.chem3);                
        infoWindowMap.put("info6", inf);           
        
        inf = new VStageInfoWindow(this);
        inf.setTitle(VStaticAssets.Text.organicProcessTitle);
        inf.setText(VStaticAssets.Text.organicProcessText, 0.0625f);
        inf.setSliderPositionY(0.4f);
        inf.addImage("foto/organ2.jpg", VStaticAssets.Text.organ2);
        inf.addImage("foto/organ1.jpg", VStaticAssets.Text.organ1);                
        infoWindowMap.put("info7", inf);

        inf = new VStageInfoWindow(this);
        inf.setTitle(VStaticAssets.Text.sedimentationProcessTitle);
        inf.setText(VStaticAssets.Text.sedimentationProcessText, 0.0625f);
        inf.setSliderPositionY(0.2f);
        inf.addImage("foto/sedim4.jpg", VStaticAssets.Text.sedim4);                
        inf.addImage("foto/sedim1.jpg", VStaticAssets.Text.sedim1);
        inf.addImage("foto/sedim2.jpg", VStaticAssets.Text.sedim2);        
        inf.addImage("foto/sedim3.jpg", VStaticAssets.Text.sedim3);
        infoWindowMap.put("info8", inf);          
        
    }
    //Called from camera transition complete callback
    public void showInfoWindow(String name){
    	infoWindowMap.get(name).show();
    }        
    public void hideAllInfoWindows(){
		for(Map.Entry<String, VStageInfoWindow> m:infoWindowMap.entrySet()){  
			m.getValue().hide();
		}
    }
    
}
