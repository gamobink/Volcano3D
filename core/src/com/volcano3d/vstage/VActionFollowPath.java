package com.volcano3d.vstage;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;

public class VActionFollowPath extends TemporalAction {
	
	public Array<Vector2> controlPoints = new Array<Vector2>();

	public void addPoint(float x, float y){
		controlPoints.add(new Vector2(x,y));
	}
	public void addPoint(Vector2 v){
		controlPoints.add(v.cpy());
	}	
		
	public Vector2 getBezierPoint(float f){
		
		if(this.controlPoints.size < 2)return new Vector2(0,0);
		Array<Vector2> tmp = new Array<Vector2>();
		for(int i=0;i<controlPoints.size; i++)tmp.add(controlPoints.get(i).cpy());		
		int i = tmp.size - 1;
		while(i > 0){
			for(int k = 0; k < i; k++){				
				Vector2 k0v = tmp.get(k);
				Vector2 k1v = tmp.get(k + 1);	
				Vector2 sub = k1v.cpy().sub(k0v.cpy());
				sub.scl(f);
				k0v.add(sub);								
			}
			i--;
		}
		return tmp.get(0).cpy();
	}

	@Override
	protected void update(float percent) {
		Vector2 p = getBezierPoint(percent);
		target.setPosition(p.x-target.getWidth()/2, p.y-target.getHeight()/2); 		
	}
	public void drawDebug(ShapeRenderer shapeRenderer){
		drawDebug(shapeRenderer, 1,1,0,1);		
	}
	
	public void drawDebug(ShapeRenderer shapeRenderer, float r, float g, float b, float a){
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(r, g, b, a);
		for(float f = 0; f < 1.0f; f += 0.01f){
			Vector2 v = getBezierPoint(f);
			Vector2 v2 = getBezierPoint(f + 0.01f);			
			shapeRenderer.line(v.x, v.y, v2.x, v2.y);			
		}
		shapeRenderer.end();		
	}
}
