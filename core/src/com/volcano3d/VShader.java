package com.volcano3d;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.volcano3d.Utility.TextAsset;

public class VShader {
	
	private SceneManager sceneManager = null;
	public DefaultShader shader = null;
	public DefaultShader.Config shaderConfig = null;
	public String vsName = "";
	public String fsName = "";
	public String vsString = "";
	public String fsString = "";	
	
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
			
			System.out.println(shaderConfig.fragmentShader);
			
			shader = new DefaultShader(renderable, shaderConfig, "", vsString, fsString);
			shader.init();
		}
	}
	
}
