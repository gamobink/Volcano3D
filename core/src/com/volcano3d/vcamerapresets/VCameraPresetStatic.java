package com.volcano3d.vcamerapresets;

import com.volcano3d.vcamera.VCameraPreset;
import com.volcano3d.vcamera.VCameraPresetCollection;
import com.volcano3d.vcamera.VCameraPreset.WayPoint;

public class VCameraPresetStatic extends VCameraPreset {
	
	public static final float anglePositions[][] = {
			{138, 4},	//1
			{200, 4},	//2   	                                             
			{270, 2},	//3
			{340, 8},	//4
	};
	
	public VCameraPresetStatic(VCameraPresetCollection.PresetsIdentifiers identifier){
		super(identifier);

		addWayPoint(new WayPoint(0, 9, 20));		
		addWayPoint(new WayPoint(8, 8, 20));
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
        addWayPoint(new WayPoint(350, 5, 20));                
        addWayPoint(new WayPoint(354, 10, 20));                        
        addWayPoint(new WayPoint(360, 9, 20));                
        
        wayPointsEnabled = true;
        
		fov = 60f; //45.0f;
		gravityEnabled = false;
		distance = 700.0f;
		pivotPosition.set(-100, 0, -250);		
		cameraPanEnabled = false;
		
		switch(identifier){
		case STATIC_VIEW_1:
			//View for volcano cross-section
			this.anglePos.x = anglePositions[0][0];
			this.anglePos.y = anglePositions[0][1];	
			break;
		case STATIC_VIEW_2:
			//Hill cross-section
			this.anglePos.x = anglePositions[1][0];
			this.anglePos.y = anglePositions[1][1];	
			break;			
		case STATIC_VIEW_3:
			//Sea cross-section
			this.anglePos.x = anglePositions[2][0];
			this.anglePos.y = anglePositions[2][1];		
			break;			
		case STATIC_VIEW_4:
			//Beach cross-section
			this.anglePos.x = anglePositions[3][0];
			this.anglePos.y = anglePositions[3][1];					
			break;
		default:
			break;						
		}
	}	
}
