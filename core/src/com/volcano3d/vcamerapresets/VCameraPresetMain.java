package com.volcano3d.vcamerapresets;

import com.volcano3d.vcamera.VCameraPreset;
import com.volcano3d.vcamera.VCameraPresetCollection;

public class VCameraPresetMain extends VCameraPreset {
	public VCameraPresetMain(VCameraPresetCollection.PresetsIdentifiers identifier){
		super(identifier);
		
        addWayPoint(new WayPoint(0, 6));
        addWayPoint(new WayPoint(10, 7));
        addWayPoint(new WayPoint(25, 7));
        addWayPoint(new WayPoint(40, 8));        
        addWayPoint(new WayPoint(60, 9));
        addWayPoint(new WayPoint(80, 11));
        addWayPoint(new WayPoint(100, 10));        
        addWayPoint(new WayPoint(110, 10));
        addWayPoint(new WayPoint(120, 11)); 
        addWayPoint(new WayPoint(140, 11)); 
        addWayPoint(new WayPoint(160, 12));
        addWayPoint(new WayPoint(170, 13));
        addWayPoint(new WayPoint(185, 11)); 
        addWayPoint(new WayPoint(200, 10)); 
        addWayPoint(new WayPoint(205, 9)); 
        addWayPoint(new WayPoint(280, 2)); 
        addWayPoint(new WayPoint(340, 6)); 
        addWayPoint(new WayPoint(360, 6)); 
        
        wayPointsEnabled = true;
        
		switch(identifier){
		case MAIN:
			gravityEnabled = false;
			break;
		default:
			break;			
		}
	}	
}
