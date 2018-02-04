package com.volcano3d.VCamera.CameraPresets;

import com.volcano3d.VCamera.VCameraPreset;
import com.volcano3d.VCamera.VCameraPresetCollection;

public class VCameraPresetStatic extends VCameraPreset {
	public VCameraPresetStatic(VCameraPresetCollection.PresetsIdentifiers identifier){
		super(identifier);
		
		switch(identifier){
		case STATIC_VIEW_1:
			//View for volcano cross-section
			fov = 40.0f;
			gravityEnabled = false;
			distance = 1000.0f;
			pivotPosition.set(-220, -50, -10);
			anglePos.x = 148;
			
			break;
		case STATIC_VIEW_2:
			//Hill cross-section
			
			break;			
		case STATIC_VIEW_3:
			//Sea cross-section
			this.fov = 40.0f;
			this.gravityEnabled = false;
			this.distance = 300.0f;
			this.pivotPosition.set(146, 42, -216);
			this.anglePos.x = 270;
			break;			
		case STATIC_VIEW_4:
			//Beach cross-section
			this.fov = 40.0f;
			this.gravityEnabled = false;
			this.distance = 300.0f;
			this.pivotPosition.set(-7, 45, -550);
			this.anglePos.x = 0;
			break;
		default:
			break;						
		}
	}	
}
