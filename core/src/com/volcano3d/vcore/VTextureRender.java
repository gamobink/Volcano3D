package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class VTextureRender {
	
	protected VMain volcano = null;
	
	protected PerspectiveCamera camFb;
	protected FrameBuffer fb;
	protected Texture texture;
	
	public VTextureRender(VMain v){
		volcano = v;
		fb = new FrameBuffer(Format.RGBA8888, 800, 800, true);
		texture = fb.getColorBufferTexture();			
	}
	public void beginRender(){
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		Gdx.gl.glDisable(GL30.GL_SCISSOR_TEST);		
		fb.begin();
	}
	public void endRender(){
		fb.end();
	}
	public Texture get(){
		return texture;
	}
}
