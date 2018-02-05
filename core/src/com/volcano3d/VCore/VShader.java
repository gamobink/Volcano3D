package com.volcano3d.VCore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.volcano3d.SceneManager;
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

		//if(sceneManager.assetsManager.isLoaded(vsName)
		//		&& sceneManager.assetsManager.isLoaded(fsName)) {
			
			//vsString = sceneManager.assetsManager.get(vsName, TextAsset.class ).getString();
			//fsString = sceneManager.assetsManager.get(fsName, TextAsset.class ).getString();
			
			//System.out.println(shaderConfig.fragmentShader);
			
	        String vert = Gdx.files.internal("shaders/sky.vertex.glsl").readString();
	        String frag = Gdx.files.internal("shaders/sky.fragment.glsl").readString();

			shaderConfig = new DefaultShader.Config(vert, frag);

			shader = new DefaultShader(renderable, shaderConfig, "", vert, frag);
			shader.init();
	//	}
	}
	public DefaultShader get(){
		return shader;
	}
	
}
