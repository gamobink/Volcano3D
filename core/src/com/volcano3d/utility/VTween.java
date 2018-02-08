package com.volcano3d.utility;

import com.badlogic.gdx.utils.Array;

public class VTween {
	
	public class Value{
		public Value(String i, float t, float v, float s){
			id = i;
			target = t;
			value = v;
			speed = s;
		}
		public String id = "";
		public float target = 0;
		public float value = 0;
		float speed = 0;
	}
	
	public Array<Value> values = new Array<Value>(); 
	
	public float getValue(String id){
		Value v = get(id);
		if(v != null)return v.value;
		return 0;
	}
	public void set(String id, float target, float speed){
		Value v = get(id);
		if(v != null){
			v.target = target;
			v.speed = speed;
		}else{
			values.add(new Value(id, target, target, speed));
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
			float step = v.speed * dt;
			if(Math.abs(d) > step){
				if(d>0)v.value += step;
				else v.value -= step;
			}else{
				v.value = v.target;
			}
		}
	}
}
