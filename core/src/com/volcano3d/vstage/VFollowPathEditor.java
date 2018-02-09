package com.volcano3d.vstage;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.volcano3d.vcamera.VCamera;

public class VFollowPathEditor extends InputListener  {

	public Stage stage = new Stage();
	
	protected BitmapFont font = null;
	
	public VActionFollowPath path = new VActionFollowPath();
	
	public ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	public int pathSelectedPoint = -1;
	public boolean addPoints = false;
	
	public String varName = "path";
	public String suffixX = " * sWidth";
	public String suffixY = " * sHeight";	
	
	public VFollowPathEditor(){
		
        font = new BitmapFont();

        TextureAtlas buttonsAtlas = new TextureAtlas("gui.pack");
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);        
        
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.down = buttonSkin.getDrawable("buttonon");
        style.up = buttonSkin.getDrawable("buttonoff");
        style.font = font;        
     
        stage.addListener(this);
        
		float sWidth = stage.getWidth();
		float sHeight = stage.getHeight();	
		
        path.addPoint(0.489796f * sWidth, 0.012500f * sHeight);
        path.addPoint(0.497959f * sWidth, 0.226250f * sHeight);
        path.addPoint(0.322449f * sWidth, 0.215000f * sHeight);
        path.addPoint(0.053061f * sWidth, 0.261250f * sHeight);
        path.addPoint(0.038775f * sWidth, 0.427500f * sHeight);
        path.addPoint(0.116326f * sWidth, 0.700000f * sHeight);
        path.addPoint(0.606122f * sWidth, 0.758750f * sHeight);
        path.addPoint(0.914286f * sWidth, 0.506250f * sHeight);
        path.addPoint(0.781633f * sWidth, 0.350000f * sHeight);
              
	}
	public void render(){	

		stage.draw();

		shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
		
		path.drawDebug(shapeRenderer);
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for(int i=0; i<path.controlPoints.size; i++){
			Vector2 p = path.controlPoints.get(i);
			shapeRenderer.setColor(0, 0, 1, 1);
			if(i == pathSelectedPoint)shapeRenderer.setColor(0.4f, 0.4f, 1, 1);
			shapeRenderer.rect(p.x-5, p.y-5, 10, 10);
		}
		shapeRenderer.end();		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		for(int i=0; i<path.controlPoints.size; i++){
			Vector2 p = path.controlPoints.get(i);
			shapeRenderer.setColor(0.5f, 0.5f, 1, 1);
			if(i == pathSelectedPoint)shapeRenderer.setColor(1, 1, 1, 1);
			shapeRenderer.rect(p.x-5, p.y-5, 10, 10);
		}
		for(int i=0; i<path.controlPoints.size; i++){
			if(i == (path.controlPoints.size - 1))break;
			Vector2 p = path.controlPoints.get(i);
			Vector2 p2 = path.controlPoints.get(i+1);
			shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
			shapeRenderer.line(p.x, p.y, p2.x, p2.y);
		}		
		shapeRenderer.end();		

	}	
	public boolean 	keyDown(InputEvent event, int keycode){
		addPoints = false;
		if(keycode == 112 && pathSelectedPoint >= 0){
			path.controlPoints.removeIndex(pathSelectedPoint);
		}
		if(keycode == 8)addPoints = true;
		if(keycode == 62){
			for(int i=0; i<path.controlPoints.size; i++){
				Vector2 p = path.controlPoints.get(i).cpy();
				p.x = p.x / stage.getWidth();
				p.y = p.y / stage.getHeight();				
				System.out.format(varName+".addPoint(%ff"+suffixX+", %ff"+suffixY+");", p.x, p.y);System.out.println("");
			}	
		}
		return true;
	}
	
    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {

    }	
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {
    	
    	if(addPoints){
    		path.controlPoints.add(new Vector2(x,y));
    	}else{
	    	pathSelectedPoint = -1;
	    	//System.out.println(x+", "+y);
			for(int i=0; i<path.controlPoints.size; i++){
				Vector2 p = path.controlPoints.get(i);
				if(x > (p.x - 5) && y > (p.y - 5) && x < (p.x + 5) && y < (p.y + 5)){
					pathSelectedPoint = i;
					break;
				}
			}    	
    	}
        return true;   //return true stops event propagation
    }	
    public void touchDragged(InputEvent event, float x, float y, int pointer){    	
		if(pathSelectedPoint >= 0){
			Vector2 p = path.controlPoints.get(pathSelectedPoint);
			if(p != null)p.set(x,y);
		}
    }
}
