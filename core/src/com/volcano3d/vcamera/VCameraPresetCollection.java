package com.volcano3d.vcamera;

import java.util.EnumMap;

import com.volcano3d.vcamerapresets.VCameraPresetMain;
import com.volcano3d.vcamerapresets.VCameraPresetStatic;

public class VCameraPresetCollection {
	
	public enum PresetsIdentifiers{
		//VCameraPresetMain
		MAIN,						//Main interactive camera
		//VCameraPresetStatic
		STATIC_VIEW_1, 				
		STATIC_VIEW_2,					
		STATIC_VIEW_3,					
		STATIC_VIEW_4,				
		STATIC_VIEW_5,				
		STATIC_VIEW_6,				
		STATIC_VIEW_7,					
		STATIC_VIEW_8,
		STATIC_VIEW_EASTEREGG
	}
	
	public VCameraPreset 		finalPreset = null;
	public EnumMap<PresetsIdentifiers, VCameraPreset> cameraPresets = new EnumMap<PresetsIdentifiers, VCameraPreset>(PresetsIdentifiers.class);
	
	public VCameraPresetCollection(VCameraPreset cam){
		finalPreset = cam;
		
		addPreset(new VCameraPresetMain(PresetsIdentifiers.MAIN));
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_1));
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_2));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_3));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_4));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_5));
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_6));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_7));	
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_8));
		addPreset(new VCameraPresetStatic(PresetsIdentifiers.STATIC_VIEW_EASTEREGG));
		
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
