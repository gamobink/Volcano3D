package com.volcano3d.vcamerapresets;

import com.volcano3d.vcamera.VCameraPreset;
import com.volcano3d.vcamera.VCameraPresetCollection;
import com.volcano3d.vcamera.VCameraPreset.WayPoint;

public class VCameraPresetStatic extends VCameraPreset {
	public VCameraPresetStatic(VCameraPresetCollection.PresetsIdentifiers identifier){
		super(identifier);

        addWayPoint(new WayPoint(0, 2, 30));
        addWayPoint(new WayPoint(10, 3, 30));
        addWayPoint(new WayPoint(25, 5, 30));
        addWayPoint(new WayPoint(40, 6, 30));        
        addWayPoint(new WayPoint(60, 7, 30));
        addWayPoint(new WayPoint(80, 5, 30));
        addWayPoint(new WayPoint(100, 8, 30));        
        addWayPoint(new WayPoint(110, 10, 30));
        addWayPoint(new WayPoint(120, 9, 30)); 
        addWayPoint(new WayPoint(140, 10, 30)); 
        addWayPoint(new WayPoint(160, 9, 30));
        addWayPoint(new WayPoint(170, 8, 30));
        addWayPoint(new WayPoint(185, 5, 30)); 
        addWayPoint(new WayPoint(200, 6, 30)); 
        addWayPoint(new WayPoint(205, 2, 30)); 
        addWayPoint(new WayPoint(340, 2, 30)); 
        addWayPoint(new WayPoint(360, 2, 30)); 
		
        wayPointsEnabled = true;
        
		switch(identifier){
		case STATIC_VIEW_1:
			//View for volcano cross-section
			fov = 45.0f;
			gravityEnabled = false;
			distance = 750.0f;
			pivotPosition.set(-100, 0, -250);
			anglePos.x = 138;
			
			break;
		case STATIC_VIEW_2:
			//Hill cross-section
			this.fov = 65.0f;
			this.gravityEnabled = false;
			this.distance = 300.0f;
			this.pivotPosition.set(80, -20, 00);
			this.anglePos.x = 200;
			this.anglePos.y = 20;
			break;			
		case STATIC_VIEW_3:
			//Sea cross-section
			this.fov = 65.0f;
			this.gravityEnabled = false;
			this.distance = 600.0f;
			this.pivotPosition.set(-25, -120, -240);
			this.anglePos.x = 270;
			this.anglePos.y = 20;			
			break;			
		case STATIC_VIEW_4:
			//Beach cross-section
			this.fov = 55.0f;
			this.gravityEnabled = false;
			this.distance = 400.0f;
			this.pivotPosition.set(100, -20, -550);
			this.anglePos.x = 340;
			this.anglePos.y = 15;			
			break;
		default:
			break;						
		}
	}	
}
