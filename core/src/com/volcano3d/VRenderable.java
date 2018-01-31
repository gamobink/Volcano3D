package com.volcano3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;

/**
 * Created by T510 on 8/6/2017.
 */

public class VRenderable {

	private SceneManager sceneManager;

    private String modelName = "";

    public ModelBatch modelBatch = null;
    public ModelInstance modelInstance = null;    
    
    public VShader vShader = null;
    
    
    public DefaultShader shader = null;
    
    public VRenderable(SceneManager o){
    	sceneManager = o;
    }
    public VRenderable(SceneManager o, String filename){
    	sceneManager = o;
        modelName = filename;

        ModelLoader.ModelParameters modelParameters = new ModelLoader.ModelParameters();
        modelParameters.textureParameter.genMipMaps = true;
        modelParameters.textureParameter.minFilter = TextureFilter.MipMap;
        modelParameters.textureParameter.magFilter = TextureFilter.Linear;

        sceneManager.assetsManager.load(modelName, Model.class, modelParameters);
    	vShader = null;
    }
    public VRenderable(SceneManager o, String filename, VShader shader){
    	sceneManager = o;
        modelName = filename;
        
        ModelLoader.ModelParameters modelParameters = new ModelLoader.ModelParameters();
        modelParameters.textureParameter.genMipMaps = true;
        modelParameters.textureParameter.minFilter = TextureFilter.MipMap;
        modelParameters.textureParameter.magFilter = TextureFilter.Linear;
        
    	sceneManager.assetsManager.load(modelName, Model.class, modelParameters);
    	vShader = shader;
    }

    public void init(){
        
        String vert = Gdx.files.internal("shaders/sky.vertex.glsl").readString();
        String frag = Gdx.files.internal("shaders/sky.fragment.glsl").readString();
//        modelBatch = new ModelBatch(vert, frag);
        //modelBatch = new ModelBatch();
        
        if(sceneManager.assetsManager.isLoaded(modelName)) {
            Model model = sceneManager.assetsManager.get(modelName, Model.class);
            modelInstance = new ModelInstance(model);
            
            if(vShader != null){
            	modelBatch = new ModelBatch(vert, frag);
		        //Renderable renderable = new Renderable();
		        //renderable = modelInstance.getRenderable(renderable);
		        //vShader.init(renderable);

		        //System.out.println(vShader);
		        
		        //DefaultShader.Config shaderConfig = new DefaultShader.Config(vert, frag);
				
				//System.out.println(shaderConfig.fragmentShader);
				
				//shader = new DefaultShader(renderable, shaderConfig, "", vert, frag);
				//shader.init();		
				//System.out.println(shader);
            }else modelBatch = new ModelBatch();
        }else{
            System.out.println("Renderable:init asset not loaded "+modelName);
        }
    }

    public void translate(Vector3 pos){
        if(modelInstance != null) {
            modelInstance.transform.idt();
            modelInstance.transform.translate(pos);
        }
    }

    public void setColor(float r, float g, float b){
        if(modelInstance != null)modelInstance.materials.get(0).set(ColorAttribute.createDiffuse(r,g,b,1));
    }

    public void render(PerspectiveCamera cam, Environment env){
        modelBatch.begin(cam);
        if(modelInstance != null){
        	//modelBatch.render(modelInstance, env, shader);
        	//if(vShader != null)modelBatch.render(modelInstance, env, vShader.shader);
        	//else 
        	modelBatch.render(modelInstance, env);
        }
        else System.out.println("Renderable:render instance not created "+modelName);
        modelBatch.end();       
    }

    public void dispose(){
        modelBatch.dispose();
    }

}
