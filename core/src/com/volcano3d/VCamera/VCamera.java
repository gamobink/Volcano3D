package com.volcano3d.VCamera;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.SceneManager;
import com.volcano3d.Utility.VCommon;
import com.volcano3d.VCamera.VCameraPresetCollection.PresetsIdentifiers;

public class VCamera extends VCameraPreset implements VCameraPreset.VCameraPresetCallback {
	
	public SceneManager sceneManager = null;
	
	public PerspectiveCamera cam;

	public VCameraPresetCollection cameraPresetsCollection = new VCameraPresetCollection(this);
	
	public int	cameraMode = 0;
	
	public VCamera(SceneManager o){
		super(null);
		sceneManager = o;
		setCallback(this);
		cam = new PerspectiveCamera(35, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.near = 1f;
        cam.far = 3000;
        cam.update();
	}
	
	//TODO: ENUM
	//Transition sequences to specified views
	//TRANSITION_TO_MAIN
	//TRANSITION_TO_VIEW1
	//TRANSITION_TO_VIEW2
	public void setCameraMode(int mode){
		cameraMode = mode;
		if(mode == 0){
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.MAIN);
		}
		if(mode == 1){
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_1);
		}		
		if(mode == 2){
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_1);
		}
		if(mode == 3){
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_3);
		}		
		if(mode == 4){
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_4);
		}
	}
	public void update(){
        
		update(cam);
        
	}	
	
	public PerspectiveCamera get(){
		return cam;
	}
	public void pan(Vector2 mouseDrag){
		mouseDrag.x = -mouseDrag.x;
		if(cameraMode == 0)addMomentum(mouseDrag);
	}
	//tmp: for testing
	public void onKeyDown(int key){
		//System.out.println(key);
		//spacebar : 62
		//'a' : 29
		if(key == 62){
			System.out.println(anglePos);
		}
		if(key == 29){	//'a'
			setCameraMode(1);
//			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_1);			
//			float pos = (float)Math.random() * 360.0f;
//			setTransitionFov(90);
//			setTransitionDistance(300);
			//setTransitionAngleX(pos);
			//setTransitionPivot(new Vector3((float)Math.random() * 360.0f,100,(float)Math.random() * 360.0f));			
			//System.out.println(pos);
		}
	}
	public void onPresetTransitionComplete(VCameraPresetCollection.PresetsIdentifiers sourceIdentifier, 
			VCameraPresetCollection.PresetsIdentifiers targetIdentifier){
		
		sceneManager.renderUndergroundPart1 = false;
		
		switch(cameraMode){
			case 1:				
				switch(targetIdentifier){
				case  MAIN_OVER_STATIC_VIEW_1:
					sceneManager.renderUndergroundPart1 = true;
					this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_1);
					break;
				}
				break;		
		}
		System.out.println("complete: "+sourceIdentifier+" -> "+targetIdentifier);
	}
	public void onTransitionAngleXComplete(VCameraPresetCollection.PresetsIdentifiers sourceIdentifier, VCameraPresetCollection.PresetsIdentifiers targetIdentifier){
	//	System.out.println("complete(AngleX): "+sourceIdentifier+" -> "+targetIdentifier);
	}
	public void onTransitionAngleYComplete(VCameraPresetCollection.PresetsIdentifiers sourceIdentifier, VCameraPresetCollection.PresetsIdentifiers targetIdentifier){
	//	System.out.println("complete(AngleY): "+sourceIdentifier+" -> "+targetIdentifier);		
	}
	public void onTransitionFovComplete(VCameraPresetCollection.PresetsIdentifiers sourceIdentifier, VCameraPresetCollection.PresetsIdentifiers targetIdentifier){
//		System.out.println("complete(Fov): "+sourceIdentifier+" -> "+targetIdentifier);	
	}
	public void onTransitionDistanceComplete(VCameraPresetCollection.PresetsIdentifiers sourceIdentifier, VCameraPresetCollection.PresetsIdentifiers targetIdentifier){
//		System.out.println("complete(Distance): "+sourceIdentifier+" -> "+targetIdentifier);	
	}
	public void onTransitionPivotComplete(VCameraPresetCollection.PresetsIdentifiers sourceIdentifier, VCameraPresetCollection.PresetsIdentifiers targetIdentifier){
//		System.out.println("complete(Pivot): "+sourceIdentifier+" -> "+targetIdentifier);	
	}			
}
