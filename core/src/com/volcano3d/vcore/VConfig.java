package com.volcano3d.vcore;

import com.badlogic.gdx.math.Vector2;

public class VConfig {
	
	/********************** Configuration for development ***********************************
	
	public boolean 	developmentMode = true; 
	public Vector2 	resolution = new Vector2(490f, 800f);
	public boolean 	fullScreen = false;
	public String	modelFileExt = ".g3dj";						//Json model files
	public Vector2 	touchDragForceMult = new Vector2(10f, 5f);	
	public float[] 	introTextLabelShownInterval = {4,6,9,6,6,5};
	public float 	introTextTitleLabelVisibleTime = 8;
	public float 	userActionActiveTimeout = 50;
	public Vector2 	tagDecalsSize = new Vector2(30f, 30f);	
	
	/******************** Configuration for production (HD, full screen) ********************/
	
	public boolean 	developmentMode = false; 
	public Vector2 	resolution = new Vector2(1080f, 1920f);
	public boolean 	fullScreen = true;
	public String	modelFileExt = ".g3dj";						//Binary model files
	public Vector2 	touchDragForceMult = new Vector2(1f, 0.5f);
	public float[] 	introTextLabelShownInterval = {5,10,15,7,15,15};
	public float 	introTextTitleLabelVisibleTime = 8;
	public float 	userActionActiveTimeout = 50;	
	public Vector2 	tagDecalsSize = new Vector2(30f, 30f);	
				
	/****************************************************************************************/	
	
	private static VConfig config = null;
	
	private VConfig(){}
	
	public static void loadConfig(String filename){
		if(config == null){
			config = new VConfig();	
		}		
	}
	public static VConfig get(){
		return config;
	}	
}
