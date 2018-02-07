package com.volcano3d.vcamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.volcano3d.vcamera.VCameraPresetCollection.PresetsIdentifiers;
import com.volcano3d.vcore.VMain;

public class VCamera extends VCameraPreset implements VCameraPreset.VCameraPresetCallback {

	public enum States{
		MAIN,					//Main interactive camera
		STATIC_1, 				//Volcano
		STATIC_2,				//Hill	
		STATIC_3,				//Sea	
		STATIC_4,				//Beach			
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
				switch(this.getCurrentPreset()){
				case STATIC_VIEW_1:
					this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_1);					
				break;
				default:
					this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.MAIN);
					break;
				}			
			}
			break;
		case STATIC_1:
			this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.MAIN_OVER_STATIC_VIEW_1);
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
		if(cameraState == States.MAIN)addMomentum(mouseDrag);
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
					switch(targetIdentifier){
					case  MAIN_OVER_STATIC_VIEW_1:
						this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.STATIC_VIEW_1);
						break;
					default:
						break;
					}
					break;
				case MAIN:
					switch(targetIdentifier){
					case  MAIN_OVER_STATIC_VIEW_1:
						this.cameraPresetsCollection.transitionToPreset(PresetsIdentifiers.MAIN);
						break;
					default:
						break;
					}						
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
