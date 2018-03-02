package com.volcano3d.vcamera;

import java.util.EnumMap;

import com.volcano3d.vcamerapresets.VCameraPresetMain;
import com.volcano3d.vcamerapresets.VCameraPresetStatic;

public class VCameraPresetCollection {
	
	public enum PresetsIdentifiers{
		//VCameraPresetMain
		MAIN,						//Main interactive camera
//		MAIN_OVER_STATIC_VIEW_1, 	//Main camera over Volcano view. Transition to STATIC_VIEW_1
//		MAIN_OVER_STATIC_VIEW_2,	//Main camera over Hill view. Transition to STATIC_VIEW_2 
//		MAIN_OVER_STATIC_VIEW_3,	//Main camera over Hill view. Transition to STATIC_VIEW_2 
//		MAIN_OVER_STATIC_VIEW_4,	//Main camera over Hill view. Transition to STATIC_VIEW_2 
		//VCameraPresetStatic
		STATIC_VIEW_1, 				//Volcano
		STATIC_VIEW_2,				//Hill	
		STATIC_VIEW_3,				//Sea	
		STATIC_VIEW_4,				//Beach
		STATIC_VIEW_5,				//Rocks
		STATIC_VIEW_6,				//Rain	
		STATIC_VIEW_7,				//Rain	
		STATIC_VIEW_8				//Rain	
	}
	
	public VCameraPreset 		finalPreset = null;
	public EnumMap<PresetsIdentifiers, VCameraPreset> cameraPresets = new EnumMap<PresetsIdentifiers, VCameraPreset>(PresetsIdentifiers.class);
	
	public VCameraPresetCollection(VCameraPreset cam){
		finalPreset = cam;
		
		addPreset(new VCameraPresetMain(PresetsIdentifiers.MAIN));
//		addPreset(new VCameraPresetMain(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_1));
//		addPreset(new VCameraPresetMain(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_2));
//		addPreset(new VCameraPresetMain(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_3));
//		addPreset(new VCameraPresetMain(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_4));
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_1));
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_2));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_3));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_4));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_5));
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_6));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_7));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_8));
		
		transitionToPreset(PresetsIdentifiers.MAIN);
	}
	
	public void addPreset(VCameraPreset preset){
		cameraPresets.put(preset.getPreset(), preset);
	}
	public VCameraPreset getPreset(PresetsIdentifiers identifier){
		return cameraPresets.get(identifier);
	}	
	
	public void transitionToPreset(PresetsIdentifiers targetPresetIdentifier){
		VCameraPreset target = getPreset(targetPresetIdentifier);
		if(target != null){			
			finalPreset.setTransitionFromPreset(target, targetPresetIdentifier);			
		}
	}
	
}
