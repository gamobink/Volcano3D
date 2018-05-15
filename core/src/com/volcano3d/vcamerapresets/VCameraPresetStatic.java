package com.volcano3d.vcamerapresets;

import com.volcano3d.vcamera.VCameraPreset;
import com.volcano3d.vcamera.VCameraPresetCollection;

public class VCameraPresetStatic extends VCameraPreset {
	
    //0 - magmatic 
    //1 - pegmatite
    //2 - hydrothermal
    //3 - pneymatolitic
    //4 - metamorphic        
    //5 - chemical
    //6 - organic
    //7 - sediment		
	public static final float anglePositions[][] = {
			{168, -16},	//0 
			{195, -10},	//1   	                                             
			{133, -6},	//2
			{136, 20},	//3
			{203, -20},	//4
			{224, 4},	//5   	                                             
			{279, -4},	//6
			{336, 0},	//7			
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
			//Magmatic
			this.anglePos.x = anglePositions[0][0];
			this.anglePos.y = anglePositions[0][1];	
			break;
		case STATIC_VIEW_2:
			//Pegmatite
			this.anglePos.x = anglePositions[1][0];
			this.anglePos.y = anglePositions[1][1];	
			break;			
		case STATIC_VIEW_3:
			//Hydrothermal
			this.anglePos.x = anglePositions[2][0];
			this.anglePos.y = anglePositions[2][1];		
			break;			
		case STATIC_VIEW_4:
			//Pneymatolitic
			this.anglePos.x = anglePositions[3][0];
			this.anglePos.y = anglePositions[3][1];					
			break;
		case STATIC_VIEW_5:
			//Metamorphic
			this.anglePos.x = anglePositions[4][0];
			this.anglePos.y = anglePositions[4][1];					
			break;
		case STATIC_VIEW_6:
			//Chemical
			this.anglePos.x = anglePositions[5][0];
			this.anglePos.y = anglePositions[5][1];					
			break;
		case STATIC_VIEW_7:
			//Organic
			this.anglePos.x = anglePositions[6][0];
			this.anglePos.y = anglePositions[6][1];					
			break;
		case STATIC_VIEW_8:
			//Sediment
			this.anglePos.x = anglePositions[7][0];
			this.anglePos.y = anglePositions[7][1];					
			break;	
		case STATIC_VIEW_EASTEREGG:
			this.anglePos.x = 270;
			this.anglePos.y = 4;	
			pivotPosition.set(850, 0, -150);
			fov = 45f;
			break;
		default:
			break;						
		}
	}	
}
