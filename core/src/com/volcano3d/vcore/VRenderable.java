package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.volcano3d.utility.VTween;

/**
 * Created by T510 on 8/6/2017.
 */
public class VRenderable {
	
	protected VMain volcano;

	protected String modelName = "";

	protected ModelBatch modelBatch = null;
	protected ModelInstance modelInstance = null;
    
    public ShaderProvider shaderProvider = null;
    
    protected BlendingAttribute blendingAttribute;
	
    public VTween alphaFader = null;
    
    public VRenderable(VMain o){
    	volcano = o;
    }
    public VRenderable(VMain o, String filename){
    	this(o, filename, null);
    }
    public VRenderable(VMain o, String filename, ShaderProvider shader){
    	volcano = o;
        modelName = filename;
        
        ModelLoader.ModelParameters modelParameters = new ModelLoader.ModelParameters();
        modelParameters.textureParameter.genMipMaps = true;
        modelParameters.textureParameter.minFilter = TextureFilter.MipMap;
        modelParameters.textureParameter.magFilter = TextureFilter.Linear;
        
    	volcano.assetsManager.load(modelName, Model.class, modelParameters);
    	shaderProvider = shader;
    	
        blendingAttribute = new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void init(){        
        if(volcano.assetsManager.isLoaded(modelName)) {
            Model model = volcano.assetsManager.get(modelName, Model.class);
            modelInstance = new ModelInstance(model);
            
            if(shaderProvider != null)modelBatch = new ModelBatch(shaderProvider);
            else modelBatch = new ModelBatch();
        }else{
            System.out.println("Renderable:init asset not loaded "+modelName);
        }
    }
    public void enableTween(){
    	if(alphaFader == null){
    		alphaFader = new VTween();
    	}
    }
    public void disableTween(){
    	alphaFader = null;
    }
    public void setNodeEnabled(String id, boolean enabled){
    	Node n = getNode(id);
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
    public void setEnvironmentCubemap(String id, VCubemapRender cubemap){
    	setNodeMaterialAttribute(id, new CubemapAttribute(CubemapAttribute.EnvironmentMap, cubemap.cubemap));
    }
    public void setDiffuseTexture(String id, Texture texture){
    	setNodeMaterialAttribute(id, new TextureAttribute(TextureAttribute.Diffuse, texture));
    }    
    public void setAmbientTexture(String id, Texture texture){
    	setNodeMaterialAttribute(id, new TextureAttribute(TextureAttribute.Ambient, texture));
    }  
    public void setBumpTexture(String id, Texture texture){
    	setNodeMaterialAttribute(id, new TextureAttribute(TextureAttribute.Bump, texture));
    }      
    public void setReflectionTexture(String id, Texture texture){
    	setNodeMaterialAttribute(id, new TextureAttribute(TextureAttribute.Reflection, texture));
    }          
    public void setEmissiveTexture(String id, Texture texture){
    	setNodeMaterialAttribute(id, new TextureAttribute(TextureAttribute.Emissive, texture));
    }              
    public void setSpecularTexture(String id, Texture texture){
    	setNodeMaterialAttribute(id, new TextureAttribute(TextureAttribute.Specular, texture));
    }                  
    public void setColor(String id, float r, float g, float b){
    	setNodeMaterialAttribute(id, ColorAttribute.createDiffuse(r,g,b,1));
    }   
    public void setShininess(String id, float f){
    	setNodeMaterialAttribute(id, FloatAttribute.createShininess(f));
    }       
    public void setTransparency(String id, float f){    
    	BlendingAttribute b = new BlendingAttribute(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		b.opacity = f;
		setNodeMaterialAttribute(id, b);	
    }    
    public void setNodeMaterialAttribute(String id, Attribute attr){
    	Node n = getNode(id);
    	if(n!=null){
    		for(int i=0; i<n.parts.size; i++){
    			n.parts.get(i).material.set(attr);
    		}
    	}
    }    
    public Node getNode(String id){
    	if(id != null && id.length() > 0)return modelInstance.getNode(id, true);
    	else return modelInstance.nodes.get(0);
    }    
    public void render(PerspectiveCamera cam, Environment env){
    	if(alphaFader != null){
    		alphaFader.tween(Gdx.graphics.getDeltaTime());
    		for(int i = 0; i < alphaFader.values.size; i++){
    			VTween.Value v = alphaFader.values.get(i);
    			setTransparency(v.id, v.value);
    		}
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
