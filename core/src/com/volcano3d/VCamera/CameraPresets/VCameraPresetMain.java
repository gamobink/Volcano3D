package com.volcano3d.VCamera.CameraPresets;

import com.volcano3d.VCamera.VCameraPreset;
import com.volcano3d.VCamera.VCameraPresetCollection;
import com.volcano3d.VCamera.VCameraPreset.WayPoint;

public class VCameraPresetMain extends VCameraPreset {
	public VCameraPresetMain(VCameraPresetCollection.PresetsIdentifiers identifier){
		super(identifier);
		
        addWayPoint(new WayPoint(0, 2));
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
        addWayPoint(new WayPoint(205, 2)); 
        addWayPoint(new WayPoint(340, 2)); 
        addWayPoint(new WayPoint(360, 2)); 
        
        wayPointsEnabled = true;
        
		switch(identifier){
		case MAIN:
			
			break;
		case MAIN_OVER_STATIC_VIEW_1:

			fov = 40.0f;
			gravityEnabled = false;
			distance = 750.0f;
//			pivotPosition.set(-220, 0, -10);
			anglePos.x = 138;
			
			break;			
		case MAIN_OVER_STATIC_VIEW_2:
			
			break;			
		}
	}	
}
