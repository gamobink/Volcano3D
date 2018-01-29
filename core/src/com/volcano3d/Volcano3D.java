package com.volcano3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Volcano3D extends ApplicationAdapter implements InputProcessor {

	protected SceneManager sceneManager;
	
	@Override
	public void create () {	
		sceneManager = new SceneManager();
		
		Gdx.input.setInputProcessor(this);
	}

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
		sceneManager.onTouchDown(screenX, screenY);
		return true;
	}
	
	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {
		sceneManager.onTouchDrag(screenX, screenY);
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
	
	
}
