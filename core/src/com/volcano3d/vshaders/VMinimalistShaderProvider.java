package com.volcano3d.vshaders;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.volcano3d.utility.TextAsset;
import com.volcano3d.vcore.VMain;

//Simple shader with no shading and with single texture
public class VMinimalistShaderProvider extends BaseShaderProvider{
	
	protected VMain volcano = null;
	
	protected 	ShaderProgram	shaderProgram = null;
	
	protected String vsName = "";
	protected String fsName = "";
	protected String vsString = "";
	protected String fsString = "";
	
	public VMinimalistShaderProvider(VMain s, String vname, String fname) {
		super();
		volcano = s;
		vsName = vname;
		fsName = fname;
		volcano.assetsManager.load(vsName, TextAsset.class);		
		volcano.assetsManager.load(fsName, TextAsset.class);		
	}
	
	public void init(){
		if(volcano.assetsManager.isLoaded(vsName)
				&& volcano.assetsManager.isLoaded(fsName)) {			
			vsString = volcano.assetsManager.get(vsName, TextAsset.class ).getString();
			fsString = volcano.assetsManager.get(fsName, TextAsset.class ).getString();
		}
	}
	public Shader getShader (Renderable renderable) {
		Shader shader = renderable.shader;	
		if (shader != null && shader.canRender(renderable)) return shader;
        
        if(shaderProgram == null){
	        shaderProgram = new ShaderProgram(vsString, fsString);
        }        
        VMinimalistShader minimalistShader = new VMinimalistShader(renderable, shaderProgram);
        minimalistShader.init();
		
        return minimalistShader;
	}
	protected Shader createShader (final Renderable renderable) {
		return new VMinimalistShader(renderable);
	}	
}
