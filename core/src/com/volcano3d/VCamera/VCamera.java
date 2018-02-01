package com.volcano3d.VCamera;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.Utility.VCommon;

public class VCamera extends VCameraPreset {

	public PerspectiveCamera cam;

	public VCameraPresetCollection cameraPresetsCollection = new VCameraPresetCollection(this);
	
	public VCamera(){
		cam = new PerspectiveCamera(35, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.near = 1f;
        cam.far = 3000;
        cam.update();
	}
	
	//TODO: ENUM
	//Camera settings presets. Switch by fading between them
	// 	class VCameraPreset
	//
	//	onPresetTransitionComplete() callback
	//
	public void setCameraMode(int mode){
//		if(mode == 0){
//			cam.fieldOfView = 35.0f;
//			applyGravity = true;
//			applyWayPoints = true;
//			distance = 650.0f;
//			pivotPosition.set(-50,11,-150);	
//		}
//		if(mode == 1){
//			cam.fieldOfView = 40.0f;
//			applyGravity = false;
//			distance = 1000.0f;
//			pivotPosition.set(-220, 0, -10);
//			moveToTargetX(148);
//		}		
//		if(mode == 2){
//			cam.fieldOfView = 40.0f;
//			applyGravity = false;
//			distance = 300.0f;
//			pivotPosition.set(-7, 45, -550);
//			moveToTargetX(0);
//		}
//		if(mode == 3){
//			cam.fieldOfView = 40.0f;
//			applyGravity = false;
//			distance = 300.0f;
//			pivotPosition.set(146, 42, -216);
//			moveToTargetX(270);
//		}		
	}
	public void update(){
        
		update(cam);
        
	}	
	
	public PerspectiveCamera get(){
		return cam;
	}
	public void pan(Vector2 mouseDrag){
		mouseDrag.x = -mouseDrag.x;
		addMomentum(mouseDrag);
	}
	//tmp: for testing
	public void onKeyDown(int key){
		//System.out.println(key);
		//spacebar : 62
		//'a' : 29
		if(key == 62){
			System.out.println(anglePos);			
		}
		if(key == 29){
			float pos = (float)Math.random() * 360.0f;
			moveToTargetX(pos);
			//System.out.println(pos);
		}
	}
}
