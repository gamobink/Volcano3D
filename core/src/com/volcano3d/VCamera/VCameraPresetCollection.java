package com.volcano3d.VCamera;

import java.util.EnumMap;

import com.badlogic.gdx.utils.Array;
import com.volcano3d.VCamera.CameraPresets.VCameraPresetMain;

public class VCameraPresetCollection {
	
	public enum PresetsIdentifiers{
		//VCameraPresetMain
		MAIN,						//Main interactive camera
		MAIN_OVER_STATIC_VIEW_1, 	//Main camera over Volcano view. Transition to STATIC_VIEW_1
		MAIN_OVER_STATIC_VIEW_2,	//Main camera over Hill view. Transition to STATIC_VIEW_2 
		//VCameraPresetStatic
		STATIC_VIEW_1, 				//Volcano
		STATIC_VIEW_2,				//Hill	
	}
	
	public VCameraPreset 		targetPreset = null;
	public EnumMap<PresetsIdentifiers, VCameraPreset> cameraPresets = new EnumMap<PresetsIdentifiers, VCameraPreset>(PresetsIdentifiers.class);
	
    //	e.get(PresetsIdentifiers.MAIN);
	
	public VCameraPresetCollection(VCameraPreset cam){
		targetPreset = cam;
		
		addPreset(PresetsIdentifiers.MAIN, new VCameraPresetMain(PresetsIdentifiers.MAIN));
		addPreset(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_1, new VCameraPresetMain(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_1));
		addPreset(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_2, new VCameraPresetMain(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_2));	
		
	}
	
	public void addPreset(PresetsIdentifiers identifier, VCameraPreset preset){
		cameraPresets.put(identifier, preset);
	}
	
	//TODO: 
	public void transitionToPreset(VCameraPreset preset){
		
		
		
	}
	
	
}
