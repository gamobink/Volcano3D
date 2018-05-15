package com.volcano3d.vcamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.volcano3d.vcamera.VCameraPresetCollection.PresetsIdentifiers;
import com.volcano3d.vcore.VConfig;
import com.volcano3d.vcore.VMain;

public class VCamera extends VCameraPreset implements VCameraPreset.VCameraPresetCallback {

	public enum States{
		MAIN,					//Main interactive camera
		STATIC_1, 				//Volcano
		STATIC_2,				//Hill	
		STATIC_3,				//Sea	
		STATIC_4,				//Beach	
		STATIC_5,				//Rocks
		STATIC_6,				//Rain
		STATIC_7,				//Rain
		STATIC_8,				//Rain
		STATIC_EASTEREGG
	}
	
	private VMain volcano = null;
	
	public PerspectiveCamera cam;

	private VCameraPresetCollection cameraPresetsCollection = new VCameraPresetCollection(this);
	
	private States	cameraState = States.MAIN;
	
	public VCamera(VMain o){
		super(null);
		setSceneManager(o);
		setCallback(this);
		cam = new PerspectiveCamera(35, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.near = 1f;
        cam.far = 3000;
        cam.update();
	}

	public void setCameraState(States state){
		switch(state){
		case MAIN:
			if(cameraState != States.MAIN){
				this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.MAIN);
			}			
			break;
		case STATIC_1:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_1);
			break;
		case STATIC_2:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_2);
			break;
		case STATIC_3:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_3);
			break;			
		case STATIC_4:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_4);
			break;			
		case STATIC_5:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_5);
			break;						
		case STATIC_6:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_6);
			break;							
		case STATIC_7:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_7);
			break;							
		case STATIC_8:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_8);
			break;	
		case STATIC_EASTEREGG:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_EASTEREGG);
			break;				
		default:
			break;			
		};
		cameraState = state;
	}
	public void update(){
		update(cam);        
	}	
	public States getState(){
		return cameraState;
	}	
	public PerspectiveCamera get(){
		return cam;
	}
	public void pan(Vector2 mouseDrag){
		mouseDrag.x = -mouseDrag.x;
		if(cameraPanEnabled)addMomentum(mouseDrag, VConfig.get().touchDragForceMult);
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
//			setCameraMode(1);
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
		//System.out.println("complete: "+sourceIdentifier+" -> "+targetIdentifier);
		if(cameraState != null){			
			switch(cameraState){
				case STATIC_1:				
					volcano.stage2D.showInfoWindow("info1");
					break;
				case STATIC_2:				
					volcano.stage2D.showInfoWindow("info2");
					break;
				case STATIC_3:				
					volcano.stage2D.showInfoWindow("info3");
					break;
				case STATIC_4:				
					volcano.stage2D.showInfoWindow("info4");
					break;
				case STATIC_5:				
					volcano.stage2D.showInfoWindow("info5");
					break;
				case STATIC_6:				
					volcano.stage2D.showInfoWindow("info6");
					break;	
				case STATIC_7:				
					volcano.stage2D.showInfoWindow("info7");
					break;	
				case STATIC_8:				
					volcano.stage2D.showInfoWindow("info8");
					break;						
				case MAIN:
					
					break;
			default:
				break;		
			}
		}
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

	public VMain getSceneManager() {
		return volcano;
	}

	public void setSceneManager(VMain volcano) {
		this.volcano = volcano;
	}			
}
