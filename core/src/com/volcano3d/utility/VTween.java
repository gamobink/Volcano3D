package com.volcano3d.utility;

import com.badlogic.gdx.utils.Array;

public class VTween {
	
	public class Value{
		public Value(String i, float t, float v, float soff, float son){
			id = i;
			target = t;
			value = v;
			speedOff = soff;
			speedOn = son;
		}
		public String id = "";
		public float target = 0;
		public float value = 0;
		float speedOff = 0;
		float speedOn = 0;
	}
	
	public Array<Value> values = new Array<Value>(); 
	
	public float getValue(String id){
		Value v = get(id);
		if(v != null)return v.value;
		return 0;
	}
	public void set(String id, float target, float speed){
		set(id, target, speed, speed);
	}
	public void set(String id, float target, float speedOff, float speedOn){
		Value v = get(id);
		if(v != null){
			v.target = target;
			v.speedOff = speedOff;
			v.speedOn = speedOn;
		}else{
			values.add(new Value(id, target, target, speedOff, speedOn));
		}
	}	
	public Value get(String id){
		for(int i=0; i<values.size; i++){
			Value v = values.get(i);
			if(v.id == id)return v;
		}
		return null;
	}
	public void tween(float dt){
		for(int i=0; i<values.size; i++){
			Value v = values.get(i);
			float d = v.target - v.value;
			
			float step = 0;
			if(d > 0)step = v.speedOn * dt;
			else step = -(v.speedOff * dt);
	
			if(Math.abs(d) > Math.abs(step)){
				v.value += step;
			}else{
				v.value = v.target;
			}
		}
	}
}
