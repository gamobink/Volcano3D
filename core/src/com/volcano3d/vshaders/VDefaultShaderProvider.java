package com.volcano3d.vshaders;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.volcano3d.utility.TextAsset;
import com.volcano3d.vcore.VMain;

public class VDefaultShaderProvider extends DefaultShaderProvider{
	
	protected VMain sceneManager = null;

	protected 	DefaultShader.Config shaderConfig = null;
	protected 	ShaderProgram	shaderProgram = null;
	
	protected String vsName = "";
	protected String fsName = "";
	protected String vsString = "";
	protected String fsString = "";	
	
	public VDefaultShaderProvider(VMain s, String vname, String fname){
		super();
		sceneManager = s;
		vsName = vname;
		fsName = fname;
		sceneManager.assetsManager.load(vsName, TextAsset.class);		
		sceneManager.assetsManager.load(fsName, TextAsset.class);
		shaderConfig = new DefaultShader.Config();
	}		
	public void init(){
		if(sceneManager.assetsManager.isLoaded(vsName)
				&& sceneManager.assetsManager.isLoaded(fsName)) {			
			vsString = sceneManager.assetsManager.get(vsName, TextAsset.class ).getString();
			fsString = sceneManager.assetsManager.get(fsName, TextAsset.class ).getString();
		}
	}
	public Shader getShader (Renderable renderable) {
		Shader shader = renderable.shader;		
		if (shader != null && shader.canRender(renderable)) return shader;
        
        if(shaderProgram == null){
	        String prefix = DefaultShader.createPrefix(renderable, shaderConfig);
	        shaderProgram = new ShaderProgram(prefix + vsString, prefix + fsString);
        }        
        DefaultShader defaultShader = new DefaultShader(renderable, shaderConfig, shaderProgram);
        defaultShader.init();
		
        return defaultShader;
	}	
}


