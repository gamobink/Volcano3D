package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class VConfig {
	
	/********************** Configuration for development ***********************************/
	
	public boolean 	developmentMode = true; 
	public Vector2 	resolution = new Vector2(490f, 800f);
	public boolean 	fullScreen = false;
	public Vector2 	touchDragForceMult = new Vector2(10f, 5f);	
	public float[] 	introTextLabelShownInterval = {4,6,9,6,6,5};
	public float 	introTextTitleLabelVisibleTime = 8;
	
	/******************** Configuration for production (HD, full screen) ********************
	
	public boolean 	developmentMode = false; 
	public Vector2 	resolution = new Vector2(1080f, 1920f);
	public boolean 	fullScreen = true;
	public Vector2 	touchDragForceMult = new Vector2(1f, 0.5f);
	public float[] 	introTextLabelShownInterval = {4,6,9,6,6,5};
	public float 	introTextTitleLabelVisibleTime = 8;	
	
	/****************************************************************************************/	
	

	
	private static VConfig config = null;
	
	private VConfig(){}
	
	public static void loadConfig(String filename){
		
		if(config == null){
			/*
			if(Gdx.files.local(filename).exists()){				
			    FileHandle cfg = Gdx.files.local(filename);
			    String cfgText = cfg.readString();
				
			    System.out.println(cfgText);
			    
			}else{
				
				
				
				//VConfig c = new VConfig();
				
				class Cfg{
					public final boolean developmentMode = true; 
					public final Vector2 resolution = new Vector2(490f, 800f);
					public final boolean fullScreen = false;
					public final Vector2 touchDragForceMult = new Vector2(10f, 5f);	
					public final float[] introTextLabelShownInterval = {4,6,9,6,6,5};
					public final float 	introTextTitleLabelVisibleTime = 8;
				}		
				Cfg cfg1 = new Cfg();				
				
				Json json = new Json();
				String outputText = json.toJson(cfg1, Cfg.class);
				
				System.out.println(outputText);
				
				//FileHandle file = Gdx.files.local(filename);
				//file.writeString(outputText, false);
				
				//System.out.println("Config file not found, creating from default");
			}
			*/
			config = new VConfig();	
				
		}		
	}
	public static VConfig get(){
		return config;
	}	
}
