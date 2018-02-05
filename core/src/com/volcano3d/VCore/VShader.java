package com.volcano3d.VCore;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.volcano3d.SceneManager;
import com.volcano3d.Utility.TextAsset;

public class VShader {
	
	//Default shader will render all models
	//Use only if you know what you do!!!!
	private class DefaultShaderExt extends DefaultShader{
		public DefaultShaderExt(Renderable renderable, Config config) {
			super(renderable, config, createPrefix(renderable, config));
		}		
		public boolean canRender (final Renderable renderable) {
			return true;
		}
	}
	
	protected SceneManager sceneManager = null;
	protected DefaultShaderExt shader = null;
	protected DefaultShader.Config shaderConfig = null;
	protected String vsName = "";
	protected String fsName = "";
	protected String vsString = "";
	protected String fsString = "";	
	
	public VShader(SceneManager s, String vname, String fname){
		sceneManager = s;
		vsName = vname;
		fsName = fname;
		sceneManager.assetsManager.load(vsName, TextAsset.class);		
		sceneManager.assetsManager.load(fsName, TextAsset.class);		
	}	
	
	public void init(Renderable renderable){
		if(sceneManager.assetsManager.isLoaded(vsName)
				&& sceneManager.assetsManager.isLoaded(fsName)) {
			
			vsString = sceneManager.assetsManager.get(vsName, TextAsset.class ).getString();
			fsString = sceneManager.assetsManager.get(fsName, TextAsset.class ).getString();
			
			shaderConfig = new DefaultShader.Config(vsString, fsString);
			
			shader = new DefaultShaderExt(renderable, shaderConfig);
			shader.init();
		}
	}
	public DefaultShader get(){
		return shader;
	}
}
