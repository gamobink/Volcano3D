package com.volcano3d.VCore;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.volcano3d.SceneManager;

//Simple shader with no shading and with single texture
public class VShaderSimple extends VShader{

	public VShaderSimple(SceneManager s, String vname, String fname) {
		super(s, vname, fname);
	}

	private class DefaultShaderExt extends DefaultShader{
		public DefaultShaderExt(Renderable renderable, Config config) {
			super(renderable, config, createPrefix(renderable, config));
		}		
		public boolean canRender (final Renderable renderable) {
			return true;
		}
	}
	public void init(Renderable renderable){
		if(sceneManager.assetsManager.isLoaded(vsName)
				&& sceneManager.assetsManager.isLoaded(fsName)) {
//			
//			vsString = sceneManager.assetsManager.get(vsName, TextAsset.class ).getString();
//			fsString = sceneManager.assetsManager.get(fsName, TextAsset.class ).getString();
//			
//			shaderConfig = new DefaultShader.Config(vsString, fsString);
//			
//			shader = new DefaultShaderExt(renderable, shaderConfig);
//			shader.init();
		}
	}
	
}
