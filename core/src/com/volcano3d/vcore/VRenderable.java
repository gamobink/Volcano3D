package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by T510 on 8/6/2017.
 */
//TODO: modelInstance.(Array<Node>)nodes.get(0).(Array<NodePart>)parts.get(0).enabled
public class VRenderable {
	
	protected VMain sceneManager;

	protected String modelName = "";

	protected ModelBatch modelBatch = null;
	protected ModelInstance modelInstance = null;    
    
    public ShaderProvider shaderProvider = null;
    
    protected BlendingAttribute blendingAttribute;
    protected Material material;
	
	protected boolean 	fadeOffAlpha = false;
	protected boolean 	fadeOnAlpha = false;
	protected float		fadeAlphaSpeeed = 0.5f;
	
    public VRenderable(VMain o){
    	sceneManager = o;
    }
    public VRenderable(VMain o, String filename){
    	this(o, filename, null);
    }
    public VRenderable(VMain o, String filename, ShaderProvider shader){
    	sceneManager = o;
        modelName = filename;
        
        ModelLoader.ModelParameters modelParameters = new ModelLoader.ModelParameters();
        modelParameters.textureParameter.genMipMaps = true;
        modelParameters.textureParameter.minFilter = TextureFilter.MipMap;
        modelParameters.textureParameter.magFilter = TextureFilter.Linear;
        
    	sceneManager.assetsManager.load(modelName, Model.class, modelParameters);
    	shaderProvider = shader;
    }

    public void init(){
        
        if(sceneManager.assetsManager.isLoaded(modelName)) {
            Model model = sceneManager.assetsManager.get(modelName, Model.class);
            modelInstance = new ModelInstance(model);
            
            material = modelInstance.materials.get(0);            
            blendingAttribute = new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            
            if(shaderProvider != null)modelBatch = new ModelBatch(shaderProvider);
            else modelBatch = new ModelBatch();
        }else{
            System.out.println("Renderable:init asset not loaded "+modelName);
        }
    }
    public void setNodeEnabled(String id, boolean enabled){
    	Node n = modelInstance.getNode(id, true);
    	if(n!=null){
    		for(int i=0; i<n.parts.size; i++)n.parts.get(i).enabled = enabled;
    	}
    }    
    public void translate(Vector3 pos){
        if(modelInstance != null) {
            modelInstance.transform.idt();
            modelInstance.transform.translate(pos);
        }
    }
	public void setFadeOff(){
		fadeOnAlpha = false;
		fadeOffAlpha = true;		
	}
	public void setFadeOn(){
		fadeOnAlpha = true;
		fadeOffAlpha = false;				
	}
    public void setColor(float r, float g, float b){
        if(modelInstance != null)modelInstance.materials.get(0).set(ColorAttribute.createDiffuse(r,g,b,1));
    }
    public void setTransparency(float f){
    	blendingAttribute.opacity = f;
    	material.set(blendingAttribute);
    }
    
    public void render(PerspectiveCamera cam, Environment env){
    	float dt = Gdx.graphics.getDeltaTime();
    	if(fadeOffAlpha){
    		blendingAttribute.opacity -= dt * fadeAlphaSpeeed;
    		if(blendingAttribute.opacity <= 0)blendingAttribute.opacity = 0;
    		material.set(blendingAttribute);
    	}else if(fadeOnAlpha){
    		blendingAttribute.opacity += dt * fadeAlphaSpeeed;    		
    		if(blendingAttribute.opacity >= 1)blendingAttribute.opacity = 1;
    		material.set(blendingAttribute);
    	}  
    	if(blendingAttribute.opacity > 0){
	        modelBatch.begin(cam);
	        if(modelInstance != null){
	        	modelBatch.render(modelInstance, env);
	        }
	        else System.out.println("Renderable:render instance not created "+modelName);
	        modelBatch.end();       
    	}
    }

    public void dispose(){
        modelBatch.dispose();
    }

}
