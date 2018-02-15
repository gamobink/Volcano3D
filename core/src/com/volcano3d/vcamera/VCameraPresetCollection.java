package com.volcano3d.vcamera;

import java.util.EnumMap;

import com.volcano3d.vcamerapresets.VCameraPresetMain;
import com.volcano3d.vcamerapresets.VCameraPresetStatic;

public class VCameraPresetCollection {
	
	public enum PresetsIdentifiers{
		//VCameraPresetMain
		MAIN,						//Main interactive camera
		MAIN_OVER_STATIC_VIEW_1, 	//Main camera over Volcano view. Transition to STATIC_VIEW_1
		MAIN_OVER_STATIC_VIEW_2,	//Main camera over Hill view. Transition to STATIC_VIEW_2 
		//VCameraPresetStatic
		STATIC_VIEW_1, 				//Volcano
		STATIC_VIEW_2,				//Hill	
		STATIC_VIEW_3,				//Sea	
		STATIC_VIEW_4,				//Beach
		STATIC_VIEW_5,				//Rocks
		STATIC_VIEW_6				//Rain			
	}
	
	public VCameraPreset 		finalPreset = null;
	public EnumMap<PresetsIdentifiers, VCameraPreset> cameraPresets = new EnumMap<PresetsIdentifiers, VCameraPreset>(PresetsIdentifiers.class);
	
	public VCameraPresetCollection(VCameraPreset cam){
		finalPreset = cam;
		
		addPreset(PresetsIdentifiers.MAIN, new VCameraPresetMain(PresetsIdentifiers.MAIN));
		addPreset(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_1, new VCameraPresetMain(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_1));
		addPreset(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_2, new VCameraPresetMain(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_2));	
		addPreset(PresetsIdentifiers.STATIC_VIEW_1, new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_1));
		addPreset(PresetsIdentifiers.STATIC_VIEW_2, new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_2));	
		addPreset(PresetsIdentifiers.STATIC_VIEW_3, new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_3));	
		addPreset(PresetsIdentifiers.STATIC_VIEW_4, new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_4));	
		
		transitionToPreset(PresetsIdentifiers.MAIN);
	}
	
	public void addPreset(PresetsIdentifiers identifier, VCameraPreset preset){
		cameraPresets.put(identifier, preset);
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
