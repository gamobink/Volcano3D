package com.volcano3d.vcamerapresets;

import com.volcano3d.vcamera.VCameraPreset;
import com.volcano3d.vcamera.VCameraPresetCollection;
import com.volcano3d.vcamera.VCameraPreset.WayPoint;

public class VCameraPresetStatic extends VCameraPreset {
	public VCameraPresetStatic(VCameraPresetCollection.PresetsIdentifiers identifier){
		super(identifier);

		addWayPoint(new WayPoint(8, 7, 20));
		addWayPoint(new WayPoint(48, 8, 20));		
		addWayPoint(new WayPoint(60, 10, 20));
		addWayPoint(new WayPoint(90, 12, 20));
		addWayPoint(new WayPoint(103, 14, 20));
		addWayPoint(new WayPoint(110, 11, 20));
		addWayPoint(new WayPoint(117, 7, 20));
        addWayPoint(new WayPoint(138, -23, 20));
        addWayPoint(new WayPoint(209, -19, 20));
        addWayPoint(new WayPoint(219, -8, 20));
        addWayPoint(new WayPoint(270, -7, 20));
        addWayPoint(new WayPoint(318, -3, 20));        
        addWayPoint(new WayPoint(340, 2, 20));                
        addWayPoint(new WayPoint(349, 7, 20));                
		
        wayPointsEnabled = true;
        
		fov = 55f; //45.0f;
		gravityEnabled = false;
		distance = 750.0f;
		pivotPosition.set(-100, 0, -250);
		
		switch(identifier){
		case STATIC_VIEW_1:
			//View for volcano cross-section
			anglePos.x = 138;
			break;
		case STATIC_VIEW_2:
			//Hill cross-section
			this.anglePos.x = 200;
			break;			
		case STATIC_VIEW_3:
			//Sea cross-section
			this.anglePos.x = 270;
			this.anglePos.y = 2;			
			break;			
		case STATIC_VIEW_4:
			//Beach cross-section
			this.anglePos.x = 340;
			this.anglePos.y = 8;			
			break;
		default:
			break;						
		}
	}	
}
