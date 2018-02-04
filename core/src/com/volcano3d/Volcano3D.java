package com.volcano3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class Volcano3D extends ApplicationAdapter implements InputProcessor, GestureListener {

	protected SceneManager sceneManager;
	
	@Override
	public void create () {	
		sceneManager = new SceneManager();
		
		GestureDetector gd = new GestureDetector(this);
		Gdx.input.setInputProcessor(new InputMultiplexer(sceneManager.stage2D.mainStage, gd, this));
	}
	//, sceneManager.stage2D

	@Override
	public void render () {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);		
		
		sceneManager.processFrame();
	}
	
	@Override
	public void dispose () {

		sceneManager.dispose();
	}
	@Override public boolean mouseMoved (int screenX, int screenY) {

		return false;
	}
	
	@Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {

		return true;
	}
	
	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {

		return true;
	}
	
	@Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {

		return true;
	}
	
	@Override public boolean keyDown (int keycode) {
		sceneManager.onKeyDown(keycode);
		return false;
	}
	
	@Override public boolean keyUp (int keycode) {
		return false;
	}
	
	@Override public boolean keyTyped (char character) {
		return false;
	}
	
	@Override public boolean scrolled (int amount) {
		return false;
	}
	
	//gestures
	@Override public boolean touchDown(float x, float y, int pointer, int button)  {

		return true;
	}	
	@Override
    public boolean tap(float x, float y, int count, int button) {
		sceneManager.onTap(x, y, count, button);
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {

        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
    //	System.out.println("pan "+x+", "+y+" delta "+deltaX+", "+deltaY);
    	sceneManager.onPan(x,y, deltaX, deltaY);
        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {

        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
            Vector2 pointer1, Vector2 pointer2) {

        return true;
    }
    @Override
    public void pinchStop(){
    	
    	
    }    
    @Override
    public boolean panStop(float x, float y, int pointer, int button){
    	
    	return true;
    }
}
