package com.volcano3d.vcamerapresets;

import com.volcano3d.vcamera.VCameraPreset;
import com.volcano3d.vcamera.VCameraPresetCollection;

public class VCameraPresetMain extends VCameraPreset {
	public VCameraPresetMain(VCameraPresetCollection.PresetsIdentifiers identifier){
		super(identifier);
		
        addWayPoint(new WayPoint(0, 6, 20));
        addWayPoint(new WayPoint(10, 7, 20));
        addWayPoint(new WayPoint(25, 7, 15));
        addWayPoint(new WayPoint(40, 8, 15));        
        addWayPoint(new WayPoint(60, 9, 15));
        addWayPoint(new WayPoint(80, 11, 20));
        addWayPoint(new WayPoint(100, 14, 25));        
        addWayPoint(new WayPoint(110, 15, 30));
        addWayPoint(new WayPoint(120, 11, 35)); 
        addWayPoint(new WayPoint(140, 15, 35)); 
        addWayPoint(new WayPoint(160, 12, 30));
        addWayPoint(new WayPoint(170, 13, 30));
        addWayPoint(new WayPoint(185, 11, 30)); 
        addWayPoint(new WayPoint(200, 10, 25)); 
        addWayPoint(new WayPoint(205, 9, 25)); 
        addWayPoint(new WayPoint(280, 2, 20)); 
        addWayPoint(new WayPoint(340, 6, 20)); 
        addWayPoint(new WayPoint(360, 6, 20)); 
        
        wayPointsEnabled = true;
        
		switch(identifier){
		case MAIN:
			fov = 60f; //45.0f;
			gravityEnabled = true;
			break;
		default:
			break;			
		}
	}	
}
