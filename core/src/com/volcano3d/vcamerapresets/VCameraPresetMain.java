package com.volcano3d.vcamerapresets;

import com.volcano3d.vcamera.VCameraPreset;
import com.volcano3d.vcamera.VCameraPresetCollection;

public class VCameraPresetMain extends VCameraPreset {
	public VCameraPresetMain(VCameraPresetCollection.PresetsIdentifiers identifier){
		super(identifier);
		
        addWayPoint(new WayPoint(0, 3));
        addWayPoint(new WayPoint(10, 3));
        addWayPoint(new WayPoint(25, 5));
        addWayPoint(new WayPoint(40, 6));        
        addWayPoint(new WayPoint(60, 7));
        addWayPoint(new WayPoint(80, 5));
        addWayPoint(new WayPoint(100, 8));        
        addWayPoint(new WayPoint(110, 10));
        addWayPoint(new WayPoint(120, 9)); 
        addWayPoint(new WayPoint(140, 10)); 
        addWayPoint(new WayPoint(160, 9));
        addWayPoint(new WayPoint(170, 8));
        addWayPoint(new WayPoint(185, 5)); 
        addWayPoint(new WayPoint(200, 6)); 
        addWayPoint(new WayPoint(205, 3)); 
        addWayPoint(new WayPoint(340, 3)); 
        addWayPoint(new WayPoint(360, 3)); 
        
        wayPointsEnabled = true;
        
		switch(identifier){
		case MAIN:
			gravityEnabled = true;
			break;
		default:
			break;			
		}
	}	
}
