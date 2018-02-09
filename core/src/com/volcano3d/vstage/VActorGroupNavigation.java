package com.volcano3d.vstage;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.volcano3d.vcamera.VCamera;


/////////////////////////////////////////////////////////////////////////////////////////////////
//
//	TODO: transfer here all the navigation menu code
//
public class VActorGroupNavigation {

	public VStageMain mainStage = null;
	
	protected Map<String, ImageButton> buttons = new HashMap<String, ImageButton>();  
	protected Map<String, VActionFollowPath> buttonAppearPaths = new HashMap<String, VActionFollowPath>();  	
	protected Map<String, VActionFollowPath> buttonDissapearPaths = new HashMap<String, VActionFollowPath>();  	
	
	public VActorGroupNavigation(VStageMain m){
		mainStage = m;
		
		float sWidth = mainStage.mainStage.getWidth();
		float buttonSize = sWidth * 0.15f;
		float buttonIconSize = sWidth * 0.15f;
		
		ImageButton button = null;
		
		button = null;//new ImageButton();
        button.setName("B_CLOSENAVI");
        button.setSize(buttonSize, buttonSize);
        button.setOrigin(buttonSize/2, buttonSize/2);
        button.addListener(mainStage);
        buttons.put("B_CLOSENAVI", button);
		
        for(int i=1; i<=6; i++){
    		button = null;//new ImageButton();
            button.setName("B_VIEW"+i);
            button.setSize(buttonIconSize, buttonIconSize);
            button.setOrigin(buttonIconSize/2, buttonIconSize/2);
            button.addListener(mainStage);
            buttons.put("B_VIEW"+i, button);
        }
        
        
        
	}
	
	
	public void onTouchUp(Actor a){
        if(a.getName() == "BUTTON_VIEW1"){
        	mainStage.volcano.camera.setCameraState(VCamera.States.STATIC_1);
        	transitionCloseNavigationTable();
        	mainStage.transitionToStaticView(0);
        }  
        if(a.getName() == "BUTTON_VIEW2"){
        	mainStage.volcano.camera.setCameraState(VCamera.States.STATIC_3);
        	transitionCloseNavigationTable();
        	mainStage.transitionToStaticView(0);
        }  
        if(a.getName() == "BUTTON_VIEW3"){
        	mainStage.volcano.camera.setCameraState(VCamera.States.STATIC_4);
        	transitionCloseNavigationTable();	
        	mainStage.transitionToStaticView(0);
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

	public void transitionOpenNavigationTable(){
		/*
		mainNavigationTable.clearActions();
		
		mainNavigationTable.addAction(Actions.sequence(Actions.show(), 
										Actions.moveTo(0, 0, 0.5f, Interpolation.circle)
									));
		
		pathAction1.setDuration(0.4f);
		pathAction1.setReverse(false);		
		buttonCloseNavi.addAction(Actions.sequence(Actions.fadeIn(0.5f),
													Actions.show(),
													pathAction1,
													Actions.touchable(Touchable.enabled)
													));   	    		
		*/
		
	}
	
	public void transitionCloseNavigationTable(){
		/*
		mainNavigationTable.clearActions();
		
		mainNavigationTable.addAction(Actions.sequence(Actions.show(),
				Actions.moveTo(0, -700, 0.5f, Interpolation.circle),
				Actions.hide()
			));
		
		pathAction1.setDuration(0.4f);
		pathAction1.setReverse(true);
		buttonCloseNavi.addAction(Actions.sequence(Actions.touchable(Touchable.disabled),
													Actions.show(),
													pathAction1,
													Actions.fadeOut(0.5f)
													));		
		 */
		
	}        
        
}
