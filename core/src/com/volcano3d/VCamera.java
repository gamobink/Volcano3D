package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.volcano3d.Utility.VCommon;

public class VCamera {

	public PerspectiveCamera cam;
	
	private Vector3 pivotPosition = new Vector3(-50,11,-150);	
	private float distance = 650.0f;
	
	private Vector2 anglePos = new Vector2(-26, 7);
	private Vector2 friction = new Vector2(0.3f, 0.3f);
	private Vector2 velocity = new Vector2(0,0);
	private Vector2 velocityMax = new Vector2(100,100);
	private Vector2 momentum = new Vector2(0,0);
	
	public float targetX = 0;	//[0 : 360]
	public boolean moveToTargetX = false;
	public float targetY = 0;	//[-85 : 85]
	public boolean moveToTargetY = false;	
	public boolean applyGravity = true;	//rename to: gravityEnabled
	
	public class WayPoint{
		public WayPoint(float a, float my){
			anglePos = a;
			minY = my;
		}
		public float anglePos = 0; //[0 : 360]
		public float minY = 5; //[-85 : 85]
		//targetDistance
		//targetFOV
	};
	public Array<WayPoint> wayPoints = new Array<WayPoint>();
	private boolean applyWayPoints = true;
	//public boolean wayPointsEnabled = true;
	//public boolean paningEnabled = true;
		
	
	public VCamera(){
		
		cam = new PerspectiveCamera(35, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.near = 1f;
        cam.far = 3000;
        cam.update();
        
        wayPoints.add(new WayPoint(0, 2));
        wayPoints.add(new WayPoint(10, 3));
        wayPoints.add(new WayPoint(25, 5));
        wayPoints.add(new WayPoint(40, 6));        
        wayPoints.add(new WayPoint(60, 7));
        wayPoints.add(new WayPoint(80, 5));
        wayPoints.add(new WayPoint(100, 8));        
        wayPoints.add(new WayPoint(110, 10));    
        wayPoints.add(new WayPoint(120, 9)); 
        wayPoints.add(new WayPoint(140, 10)); 
        wayPoints.add(new WayPoint(160, 9));
        wayPoints.add(new WayPoint(170, 8));
        wayPoints.add(new WayPoint(185, 5)); 
        wayPoints.add(new WayPoint(200, 6)); 
        wayPoints.add(new WayPoint(205, 2)); 
        wayPoints.add(new WayPoint(340, 2)); 
        wayPoints.add(new WayPoint(360, 2));         
	}
	//TODO: ENUM
	//Camera settings presets. Switch by fading between them
	// 	class VCameraPreset
	//
	//	onPresetTransitionComplete() callback
	//
	public void setCameraMode(int mode){
		if(mode == 0){
			cam.fieldOfView = 35.0f;
			applyGravity = true;
			applyWayPoints = true;
			distance = 650.0f;
			pivotPosition.set(-50,11,-150);	
		}
		if(mode == 1){
			cam.fieldOfView = 40.0f;
			applyGravity = false;
			distance = 1000.0f;
			pivotPosition.set(-220, 0, -10);
			moveToTargetX(148);
		}		
		if(mode == 2){
			cam.fieldOfView = 40.0f;
			applyGravity = false;
			distance = 300.0f;
			pivotPosition.set(-7, 45, -550);
			moveToTargetX(0);
		}
		if(mode == 3){
			cam.fieldOfView = 40.0f;
			applyGravity = false;
			distance = 300.0f;
			pivotPosition.set(146, 42, -216);
			moveToTargetX(270);
		}		
	}
	public void update(){
        
		Quaternion q = new Quaternion();
		q.setEulerAngles(anglePos.x, anglePos.y, 0.0f);
		
		Vector3 nm = new Vector3(0,0,1);

		nm = q.transform(nm).nor();
		
		cam.direction.set(nm);
		
		nm.scl(distance);
		
		cam.position.set((pivotPosition.cpy().sub(nm)));
        cam.up.set(0,1,0);
        
        cam.update();
        
        updateVelocity();
        
	}	
	private void updateVelocity(){
		float dt = Gdx.graphics.getDeltaTime();
		
		velocity.add(momentum);
		momentum.set(0,0);
		
		anglePos.add(velocity.cpy().scl(dt));
		
		velocity.x = velocity.x * (float)Math.pow(friction.x, dt);
		velocity.y = velocity.y * (float)Math.pow(friction.y, dt);
		
		if(moveToTargetX){			
			float diffX = targetX - anglePos.x;
	        while (diffX < -180) diffX += 360;
	        while (diffX > 180) diffX -= 360;
	        velocity.x = diffX;
	        if(Math.abs(diffX) < 4.0f)moveToTargetX = false;
		}
		if(moveToTargetY){
			float diffY = targetY - anglePos.y;
	        while (diffY < -180) diffY += 360;
	        while (diffY > 180) diffY -= 360;
	        velocity.y = diffY;
	        if(Math.abs(diffY) < 4.0f)moveToTargetY = false;
		}		
		WayPoint wp = getInterpolatedWayPoint();
		//Limit camera movement Y axis
		if(wp.minY > anglePos.y && applyWayPoints)velocity.y = -(anglePos.y - wp.minY) * 10.0f;
		
		//Add small gravity to camera
		if(applyGravity){
			float offsetY = 3.0f;
			if(anglePos.y > (wp.minY + offsetY)){
				velocity.y += ((wp.minY + offsetY) - anglePos.y) * 0.3f * dt;
			}
		}
		//Clamp to maximum velocity
		velocity.x = Math.min(Math.max(velocity.x, -velocityMax.x), velocityMax.x);
		velocity.y = Math.min(Math.max(velocity.y, -velocityMax.y), velocityMax.y);		
		
		if(anglePos.x > 360 || anglePos.x < -360)anglePos.x = 0;
		if(anglePos.y > 360 || anglePos.y < -360)anglePos.y = 0;		
		
		if(anglePos.y > 85)anglePos.y = 85;
		if(anglePos.y < -85)anglePos.y = -85;

		//System.out.println(anglePos);
	}	
	public void addMomentum(Vector2 v){
		Vector2 mv = v.scl(Gdx.graphics.getDeltaTime());
		mv.scl(new Vector2(10,5));
		momentum.add(mv);
	}
	public void moveToTargetX(float angleX){
		angleX = Math.min(Math.max(angleX, 0), 360);
		moveToTargetX = true;
		targetX = angleX;
	}
	public void moveToTargetY(float angleY){
		angleY = Math.min(Math.max(angleY, -85), 85);
		moveToTargetY = true;
		targetY = angleY;
	}
	
	private WayPoint getInterpolatedWayPoint(){
		
		float absAngleX = anglePos.x;
		if(absAngleX < 0)absAngleX = 360.0f - (float)Math.abs(absAngleX);
		
		int inext = 0;
		for(int i=0; i<wayPoints.size; i++){
			inext = (inext + 1) % wayPoints.size;
			WayPoint a = wayPoints.get(i);
			WayPoint b = wayPoints.get(inext);			
			
			float f = 0;
			boolean found = false;
			if(absAngleX >= a.anglePos && absAngleX < b.anglePos){
				f = (absAngleX - a.anglePos) / (b.anglePos - a.anglePos);
				found = true;
			}else if(a.anglePos > b.anglePos && absAngleX >= a.anglePos){
				
//				special case where, for example a=355, b=5
				
//				float fm = 0;
//				float fp = 0;
//				if(a.anglePos < 360)fm = 360 - a.anglePos;
//				if(b.anglePos > 0)fm += b.anglePos;
//				
//				f = (b.anglePos - absAngleX) / fm;				
				
			}else if(absAngleX < a.anglePos){
				f = absAngleX / a.anglePos;
				found = true;
			}
			if(found){				
				//System.out.println(f+" ["+a.anglePos+" - "+b.anglePos+"] "+absAngleX);
				//System.out.println(f+" ["+a.minY+" - "+b.minY+"]");				
				return new WayPoint(absAngleX, VCommon.lerp(a.minY, b.minY, f));
			}
		}
		
		return new WayPoint(0,0);
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
